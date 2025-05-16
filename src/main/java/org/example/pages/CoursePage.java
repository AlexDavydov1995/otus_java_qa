package org.example.pages;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import org.example.GuiceScoped;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.WatchEvent;
import java.util.Map;
import java.util.zip.ZipEntry;

public class CoursePage extends AbsBasePage<CoursePage> {

  private static final Logger LOG = LoggerFactory.getLogger(CoursePage.class);
  @FindBy(xpath = "//main//div[contains(@class, 'sc-s2pydo-6')]//h1[contains(@class, 'sc-1x9oq14-0')]")
  WebElement displayTitleElement;

  @FindBy(xpath = "//main//div[contains(@class,'sc-153sikp-4')]//div[contains(@class,'sc-153sikp-11')]")
  WebElement priceElement;

  @FindBy(xpath = "//main//div[contains(@class,'sc-153sikp-3')]//h3")
  WebElement title;

  @Inject
  CoursesPage coursesPage;

  @Inject
  public CoursePage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public CoursePage checkTitle(String expected) {
    assertThat(driver.getTitle()).isEqualTo(expected);
    return this;
  }

  public CoursePage checkDisplayTitle(String expected) {
    assertThat(displayTitleElement.getText()).isEqualTo(expected);
    return this;
  }

  public CoursePage checkCourseDate(String expected) {
    String expectedDayAndMonth = expected.split(",")[0];
    String pageSource = driver.getPageSource();
    Document doc = Jsoup.parse(pageSource);
    Elements dateElements = doc.select("main section div:contains(" + expectedDayAndMonth + ")");
    if (dateElements.isEmpty()) {
      throw new AssertionError("Дата курса '" + expectedDayAndMonth + "' не найдена на странице");
    }
    LOG.info("Найдена дата курса: " + dateElements.first().text());
    return this;
  }

  public CoursesPage getBackToCourses() {
    driver.navigate().back();
    return coursesPage;
  }

  public Map.Entry<String, String> getPrice() {
    waiter.waitForCondition(ExpectedConditions.visibilityOf(priceElement));
    return Map.entry(title.getText(), priceElement.getText());
  }
}
