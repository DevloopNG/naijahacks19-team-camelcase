package cc.properton

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        sInstance = this
        sContext = applicationContext

        appContext = sContext
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var sContext: Context? = null

        @SuppressLint("StaticFieldLeak")
        private var sInstance: App? = null

        //        if(sContext == null){
        //            return getApplica;
        //        }
        var appContext: Context?
            get() = sContext
            private set(context) {
                sContext = context
            }
    }
}