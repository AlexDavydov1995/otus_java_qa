package org.example.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import org.example.GuiceScoped;
import org.example.annotations.Path;
import org.example.exceptions.DelayException;
import org.example.utils.CourseDates;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.function.Consumer;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage<CoursesPage> {
  private static final Logger LOG = LoggerFactory.getLogger(CoursesPage.class);

  @FindBy(xpath = "//a[contains(@href, '/lessons/')]")
  private WebElement courses;

  @FindBy(xpath = "//div[text()='Каталог']")
  private WebElement header;

  @FindBy(xpath = "//button[contains(text(), 'Показать еще ')]")
  private WebElement expandButton;

  @FindBy(xpath = "//*[text()='Направление']/../following-sibling::div//div[contains(@class, 'sc-1fry39v-0')]")
  //xpath = "//*[text()='Направление']/../following-sibling::div//input
  private List<WebElement> checkBoxInputs;


  @Inject
  public CoursesPage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }


  public CoursesPage waitCoursesVisible() {
    waiter.waitForCondition(ExpectedConditions.visibilityOf(courses));
    return this;
  }

  public CoursesPage expandNTimes(int n) {
    int i = 0;
    while (i++ < n) {
      ((JavascriptExecutor) driver).executeScript(
          "arguments[0].scrollIntoView({block: 'center'});",
          expandButton
      );
      waiter.waitForCondition(ExpectedConditions.elementToBeClickable(expandButton));
      expandButton.click();
      try {
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        throw new DelayException();
      }

    }
    return this;
  }

  public CoursePage findAndClickCourseByName(String course) {
    List<WebElement> courseWebElement = courses.findElements(By.xpath("//h6[1]/div")).stream()
        .filter(
            it -> it.getText().equals(course)
        )
        .toList();
    assertThat(courseWebElement.size()).isEqualTo(1);
    WebElement courseElement = courseWebElement.get(0);
    centerElement(courseElement);
    waiter.waitForCondition(ExpectedConditions.elementToBeClickable(courseElement));
    actions.moveToElement(courseElement).build().perform();
    courseElement.click();
    return new CoursePage(guiceScoped);
  }

  public CoursesPage checkCoursesPageVisibility() {
    assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf(header)));
    return this;
  }

  public CoursesPage checkCheckBoxInput(String selectedCategory) {
    Consumer<WebElement> consumer = element -> {
      LOG.debug(element.getText() + " - " + element.getDomAttribute("value"));
      assertThat(Boolean.valueOf(
          element.getDomAttribute("value"))
      )
          .isEqualTo(
              element.getText().equals(selectedCategory)
          );
    };
    checkBoxInputs.forEach(consumer);
    return this;
  }

  public String findAndClickEarliest() {
    String earliestDate = findCourseDates().get(0).toString();
    LOG.info(earliestDate);
    WebElement earliestCourse = courses.findElements(By.xpath("//div[contains(text(), '" + earliestDate + "')]")).get(0);
    centerElement(earliestCourse);
    waiter.waitForCondition(ExpectedConditions.elementToBeClickable(earliestCourse));
    actions.moveToElement(earliestCourse).build().perform();
    earliestCourse.click();
    return earliestDate;
  }

  public String findAndClickLatest() {
    List<CourseDates> courseDates = findCourseDates();
    String latestDate = courseDates.get(courseDates.size() - 1).toString();
    LOG.info(latestDate);
    WebElement latestCourse = courses.findElements(By.xpath("//div[contains(text(), '" + latestDate + "')]")).get(0);
    centerElement(latestCourse);
    waiter.waitForCondition(ExpectedConditions.elementToBeClickable(latestCourse));
    actions.moveToElement(latestCourse).build().perform();
    latestCourse.click();
    return latestDate;
  }

  private List<CourseDates> findCourseDates() {
    return courses.findElements(By.xpath("//div[contains(@class, 'sc-157icee-1')]/div")).stream()
        .map(WebElement::getText)
        .filter(it -> !it.isEmpty() && !it.equals("О дате старта будет объявлено позже"))
        .map(it -> it.split("·")[0].trim())
        .map(CourseDates::new)
        .distinct()
        .sorted()
        .toList();
  }
}
