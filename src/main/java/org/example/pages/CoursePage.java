package org.example.pages;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.inject.Inject;
import org.example.GuiceScoped;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoursePage extends AbsBasePage<CoursePage> {

  private static final Logger LOG = LoggerFactory.getLogger(CoursePage.class);
  @FindBy(xpath = "/html/body/div[1]/div[2]/main/div/section/div[2]/div[2]/h1")
  WebElement displayTitleElement;

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
}
