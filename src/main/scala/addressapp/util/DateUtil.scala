package addressapp.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateUtil:
  val DATE_PATTERN = "dd.MM.yyyy"
  val DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN)
  extension (date: LocalDate)
    //Returns the given date as well formatted String
    def asString: String =
      if (date == null)
        return null
      return DATE_FORMATTER.format(date)

  extension (dateString: String)
    // Converts a String in the format of the defined
    // DATE_PATTERN to a LocalDate object
    def parseLocalDate: LocalDate =
      try
        LocalDate.parse(dateString, DATE_FORMATTER)
      catch
        case e: DateTimeParseException => null

    def isValid: Boolean =
      dateString.parseLocalDate != null