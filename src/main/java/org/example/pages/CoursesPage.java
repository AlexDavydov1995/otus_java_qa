package org.example.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import org.example.GuiceScoped;
import org.example.annotations.Path;
import org.example.exceptions.DelayException;
import org.example.utils.CourseDate;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage<CoursesPage> {

  private static final Logger LOG = LoggerFactory.getLogger(CoursesPage.class);

  @Inject
  public CoursePage coursePage;

  @FindBy(xpath = "//a[contains(@href, '/lessons/')]")
  private WebElement coursesTiles;
  @FindBy(xpath = "//div[text()='Каталог']")
  private WebElement header;
  @FindBy(xpath = "//button[contains(text(), 'Показать еще ')]")
  private WebElement expandButton;
  @FindBy(xpath = "//*[text()='Направление']/../following-sibling::div//div[contains(@class, 'sc-1fry39v-0')]")
  private List<WebElement> checkBoxInputs;
  @FindBy(xpath = "//main//div[contains(@class, sc-18q05a6-1)]//a[contains(@href, 'online/')]")
  private List<WebElement> preCoursesTiles;

  @Inject
  public CoursesPage(GuiceScoped guiceScoped) {
    super(guiceScoped);
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
    List<WebElement> courseWebElement = coursesTiles.findElements(By.xpath("//h6[1]/div")).stream()
        .filter(
            it -> it.getText().equals(course)
        )
        .toList();
    assertThat(courseWebElement.size()).isGreaterThanOrEqualTo(1);
    WebElement courseElement = courseWebElement.get(0);
    centerElement(courseElement);
    waiter.waitForCondition(ExpectedConditions.elementToBeClickable(courseElement));
    actions.moveToElement(courseElement).build().perform();
    courseElement.click();
    return coursePage;
  }

  public void findAndPrintCourseByExactDate(String date) {
    List<WebElement> coursesWithDate = coursesTiles.findElements(By.xpath("//div[contains(text(), '" + date + "')]"));
    for (WebElement courseWithDate : coursesWithDate) {
      String courseName = courseWithDate.findElement(By.xpath("../../../h6")).getAccessibleName();
      LOG.info("Курс {} стартует в дату {}", courseName, date);
    }
  }

  public void findAndPrintCourseAfterDate(String date) {
    CourseDate courseDate = new CourseDate(date);
    List<CourseDate> courseDates = findCourseDates().filter(it -> courseDate.compareTo(it) < 0).toList();
    for (CourseDate cd : courseDates) {
      LOG.info("Дата: {}", cd);
      findAndPrintCourseByExactDate(cd.toString());
    }
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
    String earliestDate = findCourseDates().reduce(CourseDate::min).orElseThrow().toString();
    LOG.info("Наиболее ранняя дата из найденных {}", earliestDate);
    WebElement earliestCourse = coursesTiles.findElements(By.xpath("//div[contains(text(), '" + earliestDate + "')]")).get(0);
    centerElement(earliestCourse);
    waiter.waitForCondition(ExpectedConditions.elementToBeClickable(earliestCourse));
    actions.moveToElement(earliestCourse).build().perform();
    earliestCourse.click();
    return earliestDate;
  }

  public String findAndClickLatest() {
    String latestDate = findCourseDates().reduce(CourseDate::max).orElseThrow().toString();
    LOG.info("Наиболее поздняя дата из найденных {}", latestDate);
    WebElement latestCourse = coursesTiles.findElements(By.xpath("//div[contains(text(), '" + latestDate + "')]")).get(0);
    centerElement(latestCourse);
    waiter.waitForCondition(ExpectedConditions.elementToBeClickable(latestCourse));
    actions.moveToElement(latestCourse).build().perform();
    latestCourse.click();
    return latestDate;
  }

  private Stream<CourseDate> findCourseDates() {
    return coursesTiles.findElements(By.xpath("//div[contains(@class, 'sc-157icee-1')]/div")).stream()
        .map(WebElement::getText)
        .filter(it -> !it.isEmpty() && !it.equals("О дате старта будет объявлено позже"))
        .map(it -> it.split("·")[0].trim())
        .map(CourseDate::new)
        .distinct()
        .sorted();
  }

  public Map<String, Integer> getPreCoursesPrices() {
    Map<String, Integer> result = Maps.newHashMap();
    String originalWindow = driver.getWindowHandle();
    for (WebElement preCourseTile : preCoursesTiles) {
      centerElement(preCourseTile);
      waiter.waitForCondition(ExpectedConditions.elementToBeClickable(preCourseTile));
      actions.moveToElement(preCourseTile).build().perform();
      preCourseTile.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
      waiter.waitForCondition(ExpectedConditions.numberOfWindowsToBe(2));
      Set<String> allWindows = driver.getWindowHandles();
      for (String window : allWindows) {
        if (!window.equals(originalWindow)) {
          driver.switchTo().window(window);
          Map.Entry<String, String> entry = coursePage.getPrice();
          LOG.debug("Цена курса - {}", entry);
          result.put(entry.getKey(), Integer.valueOf(entry.getValue().split(" ")[0]));
          driver.close();
          driver.switchTo().window(originalWindow);
          break;
        }
      }
    }
    return result;
  }
}
