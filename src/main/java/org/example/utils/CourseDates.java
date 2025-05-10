package org.example.utils;

import java.util.List;
import java.util.Objects;

public final class CourseDates implements Comparable<CourseDates> {
  private static final List<String> MONTHS = List.of(
      "января", "февраля", "марта", "апреля", "мая", "июня",
      "июля", "августа", "сентября", "октября", "ноября", "декабря"
  );

  Integer day;
  Integer month;
  Integer year;

  public CourseDates(String string) {
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
  public int compareTo(CourseDates o) {
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    CourseDates other = (CourseDates) obj;

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
