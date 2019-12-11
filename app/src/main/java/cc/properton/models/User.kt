package cc.properton.models

class User(
    val id: String?,
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var dob: String = "",
    var stateOfOrigin: String = "",
    var lgaOfOrigin: String = "",
    var profilePicUrl: String = "",
    var phoneNumber: String = "",
    var emailAddress: String = ""
)