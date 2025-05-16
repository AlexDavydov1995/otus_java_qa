package org.example.pages;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CoursePage extends AbsBasePage<CoursePage> {

  @FindBy(xpath = "//main//div[contains(@class, 'sc-s2pydo-6')]//h1[contains(@class, 'sc-1x9oq14-0')]")
  WebElement displayTitleElement;

  @Inject
  public CoursePage(WebDriver driver) {
    super(driver);
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
    Elements dateElements =  doc.select("main section div:contains(" + expectedDayAndMonth + ")");
    if (dateElements.isEmpty()) {
      throw new AssertionError("Дата курса '" + expectedDayAndMonth + "' не найдена на странице");
    }
    log.info("Найдена дата курса: " + dateElements.first().text());
    return this;
  }
}
