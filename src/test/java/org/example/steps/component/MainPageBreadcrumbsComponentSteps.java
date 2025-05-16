package org.example.steps.component;

import com.google.inject.Inject;
import io.cucumber.java.ru.Пусть;
import org.example.components.MainPageBreadcrumbsComponent;
import org.example.steps.LoggedSteps;

public class MainPageBreadcrumbsComponentSteps extends LoggedSteps {
  @Inject
  MainPageBreadcrumbsComponent mainPageBreadcrumbsComponent;

  @Пусть("Выбрана категория Подготовительные курсы$")
  public void chooseCategory() {
    mainPageBreadcrumbsComponent.checkVisibility().clickPreCoursesBreadcrumb();
  }
}
