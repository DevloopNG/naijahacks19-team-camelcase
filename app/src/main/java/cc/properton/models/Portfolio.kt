package cc.properton.models

data class Portfolio(
    val id: String,
    var title: String = "",
    var location: String = "",
    var type: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var totalAmount: String = "",
    var amountInvested: String = "",
    var numberOfPeopleNeeded: String = "",
    var status: String = ""
)