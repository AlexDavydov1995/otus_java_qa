package org.example.steps.page;

import com.google.inject.Inject;
import io.cucumber.java.ru.Пусть;
import org.example.pages.CoursesPage;

public class CoursesPageSteps {
  @Inject
  private CoursesPage coursesPage;

  @Пусть("Открыта страница выбора курсов$")
  public void openCoursePage() {
    coursesPage.open();
  }
  @Пусть("курсы развернуты (\\d{1,3}) раз(?:а?)$")
  public void expandCoursesNTimes(int n) {
    coursesPage.expandNTimes(n);
  }

  @Пусть("Выбран курс с именем (.*)$")
  public void findCourseByName(String course) {
    coursesPage.findAndClickCourseByName(course);
  }
}
