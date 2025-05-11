package org.example.steps.page;

import com.google.inject.Inject;
import io.cucumber.java.ru.Пусть;
import org.example.pages.CoursePage;

public class CoursePageSteps {
  @Inject
  private CoursePage coursePage;

  @Пусть("Проверить что имя страницы (.*) и в теле страницы есть (.*)$")
  public void checkTitleAndBody(String title, String courseName) {
    coursePage.checkTitle(title).checkDisplayTitle(courseName);
  }
}
