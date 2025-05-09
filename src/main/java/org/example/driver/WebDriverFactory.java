package org.example.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import java.util.Locale;

public class WebDriverFactory {
  private String browserName = "";

  public WebDriverFactory() {
    browserName = System.getProperty("browser", "chrome")
        .toLowerCase(Locale.ROOT);
  }

  public WebDriver newDriver() {
    boolean isSupported = false;
    for (Browser browserData : Browser.values()) {
      if (browserName.equals(browserData.name().toLowerCase(Locale.ROOT))) {
        isSupported = true;
        break;
      }
    }
    if (!isSupported)
      throw new BrowserNotSupported(browserName);
    WebDriver driver;
    switch (Browser.valueOf(browserName.toUpperCase(Locale.ROOT))) {
      case CHROME:
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        break;
      case FIREFOX:
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        break;
      default:
        throw new BrowserNotSupported(browserName);
    }
    return new EventFiringDecorator<>(new ElementHighlightListener()).decorate(driver);
  }
}