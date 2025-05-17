package org.example.hooks;

import com.google.inject.Inject;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.example.GuiceScoped;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

  private static final Logger LOG = LoggerFactory.getLogger(Hooks.class);
  @Inject
  private GuiceScoped guiceScoped;

  @Before()
  public void beforeScenario(Scenario scenario) {
    LOG.info("start scenario {}", scenario.getName());
  }

  @After
  public void tearDown(Scenario scenario) {
    try {
      if (guiceScoped.getDriver() != null) {
        logBrowserConsoleLogs();
        guiceScoped.getDriver().quit();
      }
    } catch (Exception e) {
      LOG.error("Error during browser shutdown for scenario {}", scenario.getName(), e);
      scenario.log("Shutdown error: " + e.getMessage());
    }
    LOG.info("Finished scenario: {} - Status: {}",
        scenario.getName(), scenario.getStatus());
  }

  private void logBrowserConsoleLogs() {
    try {
      LogEntries logs = guiceScoped.getDriver().manage().logs().get(LogType.BROWSER);
      logs.forEach(logEntry ->
          LOG.debug("Browser Console: {} - {}", logEntry.getLevel(), logEntry.getMessage()));
    } catch (Exception e) {
      LOG.warn("Failed to get browser logs: {}", e.getMessage());
    }
  }
}
