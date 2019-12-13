package cc.properton

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cc.properton.utils.Constants
import co.paystack.android.Paystack
import co.paystack.android.PaystackSdk.*
import co.paystack.android.Transaction
import co.paystack.android.exceptions.ExpiredAccessCodeException
import co.paystack.android.model.Card
import co.paystack.android.model.Charge
import kotlinx.android.synthetic.main.activity_pay_stack_payment.*
import org.json.JSONException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*


class PayStackPaymentActivity : AppCompatActivity() {

    lateinit var dialog: ProgressDialog
    lateinit var transaction: Transaction
    private lateinit var charge: Charge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_stack_payment)

        val backend_url = Constants.PAYSTACK_HEROKU_BACKEND_URL
        val paystack_public_key = resources.getString(R.string.payStack_test_public_key)

        if (BuildConfig.DEBUG && (backend_url == "")) {
            throw AssertionError("Please set a backend url before running the sample")
        }
        if (BuildConfig.DEBUG && (paystack_public_key == "")) {
            throw AssertionError("Please set a public key before running the sample")
        }

        setPublicKey(paystack_public_key)

        //initialize sdk
        initialize(applicationContext)

        button_perform_transaction.setOnClickListener {
            try {
                startAFreshCharge(false)
            } catch (e: Exception) {
                this@PayStackPaymentActivity.textView_error.text = String.format(
                    "An error occurred while charging card: %s %s",
                    e.javaClass.simpleName,
                    e.message
                )

            }

        }

        button_perform_local_transaction.setOnClickListener {
            try {
                startAFreshCharge(true)
            } catch (e: Exception) {
                this@PayStackPaymentActivity.textView_error.text = String.format(
                    "An error occurred while charging card: %s %s",
                    e.javaClass.simpleName,
                    e.message
                )

            }

        }

    }

    private fun startAFreshCharge(local: Boolean) {

        // initialize the charge
        val charge = Charge()
        charge.card = loadCardFromForm()

        dialog = ProgressDialog(this@PayStackPaymentActivity)
        dialog.setMessage("Performing transaction... please wait")
        dialog.show()

        if (local) {
            // Set transaction params directly in app (note that these params
            // are only used if an access_code is not set. In debug mode,
            // setting them after setting an access code would throw an exception

            charge.amount = 2000
            charge.email = "customer@email.com"
            charge.reference = "ChargedFromAndroid_" + Calendar.getInstance().timeInMillis
            try {
                charge.putCustomField("Charged From", "Android SDK")
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            chargeCard()
        } else {
            // Perform transaction/initialize on our server to get an access code
            // documentation: https://developers.paystack.co/reference#initialize-a-transaction
            FetchAccessCodeFromServer().execute(Constants.PAYSTACK_HEROKU_BACKEND_URL + "/new-access-code")
        }
    }

    private fun loadCardFromForm(): Card {
        //validate fields
        val card: Card

        val cardNum = edit_card_number.text.toString().trim()

        //build card object with ONLY the number, update the other fields later
        card = Card.Builder(cardNum, 0, 0, "").build()
        val cvc = edit_cvc.text.toString().trim()
        //update the cvc field of the card
        card.cvc = cvc

        //validate expiry month;
        val sMonth = edit_expiry_month.text.toString().trim()
        var month = 0
        try {
            month = Integer.parseInt(sMonth)
        } catch (ignored: Exception) {
        }

        card.expiryMonth = month

        val sYear = edit_expiry_year.text.toString().trim()
        var year = 0
        try {
            year = Integer.parseInt(sYear)
        } catch (ignored: Exception) {
        }

        card.expiryYear = year

        return card
    }

    private fun chargeCard() {
        //transaction = null
        chargeCard(this@PayStackPaymentActivity, charge, object : Paystack.TransactionCallback {
            // This is called only after transaction is successful
            override fun onSuccess(transaction: Transaction) {
                dismissDialog()

                this@PayStackPaymentActivity.transaction = transaction
                textView_error.text = " "
                Toast.makeText(this@PayStackPaymentActivity, transaction.reference, Toast.LENGTH_LONG)
                    .show()
                updateTextViews()
                VerifyOnServer().execute(transaction.reference)
            }

            // This is called only before requesting OTP
            // Save reference so you may send to server if
            // error occurs with OTP
            // No need to dismiss dialog
            override fun beforeValidate(transaction: Transaction) {
                this@PayStackPaymentActivity.transaction = transaction
                Toast.makeText(this@PayStackPaymentActivity, transaction.reference, Toast.LENGTH_LONG)
                    .show()
                updateTextViews()
            }

            override fun onError(error: Throwable, transaction: Transaction) {
                // If an access code has expired, simply ask your server for a new one
                // and restart the charge instead of displaying error
                this@PayStackPaymentActivity.transaction = transaction
                if (error is ExpiredAccessCodeException) {
                    this@PayStackPaymentActivity.startAFreshCharge(false)
                    this@PayStackPaymentActivity.chargeCard()
                    return
                }

                dismissDialog()

                if (transaction.reference != null) {
                    Toast.makeText(
                        this@PayStackPaymentActivity,
                        transaction.reference + " concluded with error: " + error.message,
                        Toast.LENGTH_LONG
                    ).show()
                    textView_error.text = String.format(
                        "%s  concluded with error: %s %s",
                        transaction.reference,
                        error.javaClass.simpleName,
                        error.message
                    )
                    VerifyOnServer().execute(transaction.reference)
                } else {
                    Toast.makeText(this@PayStackPaymentActivity, error.message, Toast.LENGTH_LONG).show()
                    textView_error.text = String.format(
                        "Error: %s %s",
                        error.javaClass.simpleName,
                        error.message
                    )
                }
                updateTextViews()
            }

        })
    }

    override fun onPause() {
        super.onPause()

        if (dialog.isShowing) {
            dialog.dismiss()
        }
        //dialog = null
    }

    private fun dismissDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    private fun updateTextViews() {
        if (transaction.reference != null) {
            textview_reference.text = String.format("Reference: %s", transaction.reference)
        } else {
            textview_reference.text = "No transaction"
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class FetchAccessCodeFromServer : AsyncTask<String, Void, String>() {
        private var error: String? = null

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                if(::charge.isInitialized) {
                    charge.accessCode = result
                    chargeCard()
                }
            } else {
                this@PayStackPaymentActivity.textview_backend_message.text = String.format(
                    "There was a problem getting a new access code form the backend: %s",
                    error
                )
                dismissDialog()
            }
        }

        override fun doInBackground(vararg ac_url: String): String? {
            try {
                val url = URL(ac_url[0])
                val `in` = BufferedReader(
                    InputStreamReader(
                        url.openStream()
                    )
                )

                val inputLine: String
                inputLine = `in`.readLine()
                `in`.close()
                return inputLine
            } catch (e: Exception) {
                error = e.javaClass.simpleName + ": " + e.message
            }

            return null
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class VerifyOnServer : AsyncTask<String, Void, String>() {
        private var reference: String? = null
        private var error: String? = null

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                this@PayStackPaymentActivity.textview_backend_message.text = String.format(
                    "Gateway response: %s",
                    result
                )

            } else {
                this@PayStackPaymentActivity.textview_backend_message.text = String.format(
                    "There was a problem verifying %s on the backend: %s ",
                    this.reference,
                    error
                )
                dismissDialog()
            }
        }

        override fun doInBackground(vararg reference: String): String? {
            try {
                this.reference = reference[0]
                val url = URL(Constants.PAYSTACK_HEROKU_BACKEND_URL + "/verify/" + this.reference)
                val `in` = BufferedReader(
                    InputStreamReader(
                        url.openStream()
                    )
                )

                val inputLine: String
                inputLine = `in`.readLine()
                `in`.close()
                return inputLine
            } catch (e: Exception) {
                error = e.javaClass.simpleName + ": " + e.message
            }

            return null
        }
    }

}
