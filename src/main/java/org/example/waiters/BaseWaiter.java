package org.example.waiters;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BaseWaiter {
  private static final Duration TIME_TO_WAIT_SECONDS = Duration.ofSeconds(10L);
  private final WebDriver driver;

  public BaseWaiter(WebDriver driver) {
    this.driver = driver;
  }

  public boolean waitForCondition(ExpectedCondition condition) {
    return waitForCondition(condition, TIME_TO_WAIT_SECONDS);
  }

  public boolean waitForCondition(ExpectedCondition condition, Duration duration) {
    WebDriverWait webDriverWait = new WebDriverWait(driver, duration);
    try {
      webDriverWait.until(condition);
      return true;
    } catch (Exception ignored) {
      return false;
    }
  }
}
