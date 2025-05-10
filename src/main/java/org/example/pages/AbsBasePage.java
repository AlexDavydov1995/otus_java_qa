package org.example.pages;

import com.google.inject.Inject;
import org.example.annotations.Path;
import org.example.common.AbsCommon;
import org.example.exceptions.DelayException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

abstract public class AbsBasePage<T> extends AbsCommon<T> {
  private static final String BASE_URL = "https://otus.ru";

  @Inject
  public AbsBasePage(WebDriver driver) {
    super(driver);
  }

  public T open() {
    driver.manage().window().maximize();
    driver.get(BASE_URL + getPagePath());
    return (T) this;
  }

  private String getPagePath() {
    Class<? extends AbsBasePage> clazz = this.getClass();
    if (clazz.isAnnotationPresent(Path.class)) {
      return clazz.getAnnotation(Path.class).value();
    }
    return "";
  }

  public void centerElement(WebElement element) {
    try {
      ((JavascriptExecutor) driver).executeScript(
          "arguments[0].scrollIntoView({block: 'center'});",
          element
      );
      Thread.sleep(1000);
    } catch (InterruptedException exception) {
      throw new DelayException();
    }
  }

  protected void closeCookiesMessage() {
    WebElement cookieMessageButton = driver.findElement(By.cssSelector(".sc-9a4spb-0.ckCZjI"));
    moveAndClick(cookieMessageButton);
  }

  public void moveToElement(WebElement element) {
    actions.moveToElement(element).perform();
  }

  public void moveAndClick(WebElement element) {
    waiter.waitForCondition(ExpectedConditions.elementToBeClickable(element));
    this.moveToElement(element);
    element.click();
  }

}
