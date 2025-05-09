package org.example.pages;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import org.example.annotations.Path;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage<CoursesPage> {
  @FindBy(xpath = "/html/body/div[1]/div[2]/main/div[2]/section[2]/div[2]/div")
  private WebElement courses;

  @FindBy(xpath = "/html/body/div[1]/div[2]/main/div[2]/section[2]/div[2]/button")
  private WebElement expandCoursesButton;

  @Inject
  public CoursesPage(WebDriver driver) {
    super(driver);
  }


  public CoursesPage expandCoursesNTimes(int n) throws InterruptedException {
    int i = 0;
    while (i++ < n) {
      ((JavascriptExecutor) driver).executeScript(
          "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});",
          expandCoursesButton
      );
      Thread.sleep(2000);
      waiter.waitForCondition(ExpectedConditions.visibilityOf(expandCoursesButton));
      waiter.waitForCondition(ExpectedConditions.elementToBeClickable(expandCoursesButton));
      actions.moveToElement(expandCoursesButton).build().perform();
      expandCoursesButton.click();
    }
    return this;
  }

  public CoursePage findAndClickCourseByName(String course) throws InterruptedException{
    waiter.waitForCondition(ExpectedConditions.visibilityOf(courses));
    List<WebElement> courseWebElement = courses.findElements(By.xpath("//a/h6[1]/div")).stream()
        .filter(
            it -> it.getText().equals(course)
        )
        .toList();
    assertThat(courseWebElement.size()).isEqualTo(1);
    WebElement courseElement = courseWebElement.get(0);
    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});",
        courseElement
    );
    Thread.sleep(2000);
    waiter.waitForCondition(ExpectedConditions.elementToBeClickable(courseElement));
    actions.moveToElement(courseElement).build().perform();
    courseElement.click();
    return new CoursePage(driver);
  }

}
