package com.muasdev.simpleandroidmvvm.utils

fun String.isValidEmail(): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return this.isNotEmpty() && this.matches(emailPattern)
}