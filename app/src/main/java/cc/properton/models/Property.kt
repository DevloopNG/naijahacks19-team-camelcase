package cc.properton.models

data class Property(
    val id: String,
    var title: String = "",
    var location: String = "",
    var type: String = "",
    var imagesUrl: ArrayList<String>? = null,
    var startDate: String = "",
    var endDate: String = "",
    var totalAmount: String = "",
    var amountRaised: String = "",
    var numberOfPeopleNeeded: String = ""
)