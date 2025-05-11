package org.example.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.exceptions.BrowserNotSupported;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import java.time.Duration;
import java.util.Locale;

public class WebDriverFactory {
  private String browserName = "";

  public WebDriverFactory() {
    browserName = System.getProperty("browser", "chrome")
        .toLowerCase(Locale.ROOT);
  }

  public WebDriverFactory setBrowserName(String browserName) {
    this.browserName = browserName;
    return this;
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
      case CHROME -> {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
      }
      case FIREFOX -> {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
      }
      case EDGE -> {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        // Базовые опции для Edge
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Специфичные опции для Edge
        options.addArguments("--disable-features=msEdgePromoDialog");
        options.addArguments("--disable-popup-blocking");
        driver = new EdgeDriver(options);
      }
      default -> throw new BrowserNotSupported(browserName);
    }
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
    return new EventFiringDecorator<>(new ElementHighlightListener()).decorate(driver);
  }
}