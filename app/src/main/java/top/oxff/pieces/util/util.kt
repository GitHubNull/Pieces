package top.oxff.pieces.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Year
import java.time.YearMonth
import java.util.Calendar
import java.util.Locale

fun getFirstAndLastDayOfMonth(yearMonth: YearMonth): Pair<LocalDateTime, LocalDateTime> {
    // 获取指定月份的第一天和最后一天
    val firstDayOfMonth = yearMonth.atDay(1).minusDays(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()

    // 将日期转换为 LocalDateTime 对象
    val firstDateTime = LocalDateTime.of(firstDayOfMonth, LocalTime.MIN)
    val lastDateTime = LocalDateTime.of(lastDayOfMonth, LocalTime.MAX)

    return Pair(firstDateTime, lastDateTime)
}

fun getFirstAndLastDayOfYear(year: Year): Pair<LocalDateTime, LocalDateTime> {
    val firstDayOfYear = Year.of(year.value).atDay(1)
    val lastDayOfYear = Year.of(year.value).atDay(1).plusYears(1).minusDays(1)

    val startDateTime = LocalDateTime.of(firstDayOfYear, LocalTime.MIN)
    val endDateTime = LocalDateTime.of(lastDayOfYear, LocalTime.MAX)
    return Pair(startDateTime, endDateTime)
}

fun getChineseWeekday(dateStr: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.time = sdf.parse(dateStr)!! // 将 Calendar 对象设置为指定的日期

    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "日"
        Calendar.MONDAY -> "一"
        Calendar.TUESDAY -> "二"
        Calendar.WEDNESDAY -> "三"
        Calendar.THURSDAY -> "四"
        Calendar.FRIDAY -> "五"
        Calendar.SATURDAY -> "六"
        else -> ""
    }
}

fun getDayOfMonth(dateStr: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.time = sdf.parse(dateStr)!! // 将 Calendar 对象设置为指定的日期

    return calendar.get(Calendar.DAY_OF_MONTH).toString()
}

fun getMonthString(dateStr: String): String {
    val sdf = SimpleDateFormat("yyyy-M", Locale.getDefault())
    val date = sdf.parse(dateStr) ?: return ""
    val calendar = Calendar.getInstance()
    calendar.time = date
    return (calendar.get(Calendar.MONTH) + 1).toString()
}