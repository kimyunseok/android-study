package com.khs.designpatternexampleproject.model

data class User(
    var userEmail: String,
    var userName: String,
    var userContact: String,
    var userAddress: String,
    var userAge: String
) {
    fun copyUser(): User {
        return User(userEmail, userName, userContact, userAddress, userAge)
    }
}
