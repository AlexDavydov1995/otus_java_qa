package org.example.steps.page;

import com.google.inject.Inject;
import io.cucumber.java.ru.Пусть;
import org.example.pages.CoursesPage;
import org.example.steps.LoggedSteps;
import java.util.Comparator;
import java.util.Objects;

public class CoursesPageSteps extends LoggedSteps {

  @Inject
  private CoursesPage coursesPage;

  @Пусть("Открыта страница выбора курсов$")
  public void openCoursePage() {
    coursesPage.open().checkCoursesPageVisibility();
  }

  @Пусть("курсы развернуты (\\d{1,3}) раз(?:а?)$")
  public void expandCoursesNTimes(int n) {
    coursesPage.expandNTimes(n);
  }

  @Пусть("Выбран курс с именем (.*)$")
  public void findCourseByName(String course) {
    coursesPage.findAndClickCourseByName(course);
  }

  @Пусть("Найдены курсы стартующие в дату (.*)$")
  public void findCoursesStartingOnDate(String date) {
    LOG.info("Ищем курсы стартующие в дату {}", date);
    coursesPage.findAndPrintCourseByExactDate(date);
  }

  @Пусть("Найдены курсы стартующие позже даты (.*)$")
  public void findCoursesStartingAfterDate(String date) {
    LOG.info("Ищем курсы стартующие после даты {}", date);
    coursesPage.findAndPrintCourseAfterDate(date);
  }

  @Пусть("Найден самый (дорогой|дешевый) курс$")
  public void findMostExpensiveOrCheapest(String sort) {
    Comparator<Double> comparator;
    if (Objects.equals(sort, "дорогой"))
      comparator = Comparator.naturalOrder();
    else
      comparator = Comparator.reverseOrder();
    LOG.info(comparator.toString());
    coursesPage.getPreCoursesPrices();
  }

}
