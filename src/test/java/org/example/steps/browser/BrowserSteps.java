package org.example.steps.browser;

import com.google.inject.Inject;
import io.cucumber.java.ru.Пусть;
import org.example.GuiceScoped;
import org.example.driver.WebDriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserSteps {

  private static final Logger LOG = LoggerFactory.getLogger(BrowserSteps.class);
  @Inject
  private GuiceScoped guiceScoped;

  @Inject
  private WebDriverFactory factory;

  @Пусть("Выбран браузер (.*)$")
  public void openPage(String browserName) {
    LOG.info("Выбран браузер {}", browserName);
    guiceScoped.setDriver(factory.setBrowserName(browserName.toLowerCase()).newDriver());
  }

}
