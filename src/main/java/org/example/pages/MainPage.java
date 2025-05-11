package org.example.pages;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import org.example.GuiceScoped;
import org.example.annotations.Path;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Path("/")
public class MainPage extends AbsBasePage<MainPage> {

  @FindBy(xpath = "/html/body/div[1]/div[2]/main/div[1]/div[3]/div/div[3]")
  private WebElement breadcrumbs;

  @Inject
  public MainPage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public MainPage checkBreadcrumbsVisibility() {
    assertTrue(waiter.waitForCondition(ExpectedConditions.visibilityOf(breadcrumbs)));
    return this;
  }
}
