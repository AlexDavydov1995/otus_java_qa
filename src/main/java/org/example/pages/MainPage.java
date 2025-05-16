package org.example.pages;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import org.example.annotations.Path;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Path("/")
public class MainPage extends AbsBasePage<MainPage> {

  @FindBy(xpath = "//main//div[contains(@class, sc-x072mc-0)]//div[contains(@class, sc-1sgv0wl-8)]")
  private WebElement breadcrumbs;

  @Inject
  public MainPage(WebDriver driver) {
    super(driver);
  }

  public MainPage checkBreadcrumbsVisibility() {
    assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf(breadcrumbs)));
    return this;
  }
}
