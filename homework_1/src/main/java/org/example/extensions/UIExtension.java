package org.example.extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.driver.WebDriverFactory;
import org.example.guice.GuiceComponentsModule;
import org.example.guice.GuicePagesModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

public class UIExtension implements BeforeEachCallback, AfterEachCallback {

  private Injector injector = null;

  @Override
  public void afterEach(ExtensionContext extensionContext) throws Exception {
    extensionContext.getTestInstance()
        .ifPresent(instance -> {
          WebDriver driver = injector.getProvider(WebDriver.class).get();
          if (driver != null) {
            driver.quit();
          }
        });
  }

  @Override
  public void beforeEach(ExtensionContext extensionContext) throws Exception {
    WebDriver driver = new WebDriverFactory().newDriver();
    injector = Guice.createInjector(new GuicePagesModule(driver), new GuiceComponentsModule(driver));
    injector.injectMembers(extensionContext.getTestInstance().get());
  }

}
