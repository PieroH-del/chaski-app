package com.example.app_chaski.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val displayDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val displayTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val displayDateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    fun formatDate(dateString: String?): String {
        if (dateString == null) return ""
        return try {
            val date = apiDateFormat.parse(dateString)
            displayDateFormat.format(date!!)
        } catch (e: Exception) {
            dateString
        }
    }

    fun formatTime(dateString: String?): String {
        if (dateString == null) return ""
        return try {
            val date = apiDateFormat.parse(dateString)
            displayTimeFormat.format(date!!)
        } catch (e: Exception) {
            dateString
        }
    }

    fun formatDateTime(dateString: String?): String {
        if (dateString == null) return ""
        return try {
            val date = apiDateFormat.parse(dateString)
            displayDateTimeFormat.format(date!!)
        } catch (e: Exception) {
            dateString
        }
    }
}

object CurrencyUtils {
    fun formatPrice(price: Double): String {
        return String.format("S/. %.2f", price)
    }
}

object ValidationUtils {

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun isValidPhone(phone: String): Boolean {
        return phone.length == 9 && phone.all { it.isDigit() }
    }

    fun isValidName(name: String): Boolean {
        return name.isNotBlank() && name.length >= 3
    }
}

