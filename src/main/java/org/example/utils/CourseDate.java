package org.example.utils;

import java.util.List;
import java.util.Objects;

public final class CourseDate implements Comparable<CourseDate> {
  private static final List<String> MONTHS = List.of(
      "января", "февраля", "марта", "апреля", "мая", "июня",
      "июля", "августа", "сентября", "октября", "ноября", "декабря"
  );

  Integer day;
  Integer month;
  Integer year;

  public CourseDate(String string) {
    if (string == null || string.trim().isEmpty()) {
      throw new IllegalArgumentException("Date string cannot be null or empty");
    }

    String[] splittedString = string.split("\\s");
    if (splittedString.length != 3) {
      throw new IllegalArgumentException("Invalid date format. Expected: 'dd MMMM yyyy', actual: " + string);
    }

    day = Integer.valueOf(splittedString[0]);
    month = MONTHS.indexOf(splittedString[1].replace(",", "")) + 1;
    year = Integer.valueOf(splittedString[2]);
  }

  @Override
  public int compareTo(CourseDate o) {
    int yearCompare = this.year.compareTo(o.year);
    if (yearCompare != 0) {
      return yearCompare;
    }

    int monthCompare = Integer.compare(this.month, o.month);
    if (monthCompare != 0) {
      return monthCompare;
    }

    return this.day.compareTo(o.day);
  }

  public static CourseDate max(CourseDate a, CourseDate b) {
    if(a.compareTo(b) >= 0)
      return a;
    else
      return b;
  }

  public static CourseDate min(CourseDate a, CourseDate b) {
    if(a.compareTo(b) >= 0)
      return b;
    else
      return a;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    CourseDate other = (CourseDate) obj;

    return Objects.equals(day, other.day)
        && Objects.equals(month, other.month)
        && Objects.equals(year, other.year);
  }

  @Override
  public int hashCode() {
    return Objects.hash(day, month, year);
  }

  @Override
  public String toString() {
    return day + " " + MONTHS.get(month - 1) + ", " + year;
  }
}
