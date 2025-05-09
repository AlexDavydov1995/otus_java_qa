package org.example.components;

import com.google.inject.Inject;
import org.example.pages.CoursesPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPageBreadcrumbsComponent extends AbsComponent<MainPageBreadcrumbsComponent> {

  @FindBy(xpath = "/html/body/div[1]/div[2]/main/div[1]/div[3]/div/div[3]/div/a[1]")
  private WebElement programmingBreadcrumb;

  @Inject
  public MainPageBreadcrumbsComponent(WebDriver driver) {
    super(driver);
  }

  public CoursesPage clickProgrammingBreadcrumb() {
    actions.moveToElement(programmingBreadcrumb).build().perform();
    programmingBreadcrumb.click();
    return new CoursesPage(driver);
  }
}
