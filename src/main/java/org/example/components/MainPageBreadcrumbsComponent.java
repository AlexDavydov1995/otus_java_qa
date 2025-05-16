package org.example.components;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import org.example.GuiceScoped;
import org.example.pages.CoursesPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPageBreadcrumbsComponent extends AbsComponent<MainPageBreadcrumbsComponent> {

  @Inject
  CoursesPage coursesPage;
  @FindBy(xpath = "//main//div[contains(@class, sc-x072mc-0)]//a[text()='Программирование']")
  private WebElement programmingBreadcrumb;
  @FindBy(xpath = "//main//div[contains(@class, sc-x072mc-0)]//a[text()='Подготовительные курсы']")
  private WebElement preCoursesBreadcrumb;

  @Inject
  public MainPageBreadcrumbsComponent(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public MainPageBreadcrumbsComponent checkVisibility() {
    assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf(programmingBreadcrumb)));
    assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf(preCoursesBreadcrumb)));
    return this;
  }

  public CoursesPage clickProgrammingBreadcrumb() {
    actions.moveToElement(programmingBreadcrumb).build().perform();
    programmingBreadcrumb.click();
    return coursesPage;
  }

  public CoursesPage clickPreCoursesBreadcrumb() {
    actions.moveToElement(preCoursesBreadcrumb).build().perform();
    preCoursesBreadcrumb.click();
    return coursesPage;
  }

}
