package org.example.components;

import com.google.inject.Inject;
import org.example.GuiceScoped;
import org.example.pages.CoursePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CourseComponent extends AbsComponent<CourseComponent> {

  private By locator;

  @Inject
  public CourseComponent(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public CourseComponent setElement(By locator) {
    this.locator = locator;
    return this;
  }

  public CoursePage click() {
    WebElement courseElement = driver.findElement(locator);
    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});",
        courseElement
    );
    waiter.waitForCondition(ExpectedConditions.elementToBeClickable(courseElement));
    actions.moveToElement(courseElement).build().perform();
    courseElement.click();
    return new CoursePage(guiceScoped);
  }
}
