package com.lstolowski.adverity.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateUtils {
    companion object {
        fun toDate(date: String): LocalDate {
            var formatter = DateTimeFormatter.ofPattern("MM/dd/yy")
            return LocalDate.parse(date, formatter)
        }

    }
}