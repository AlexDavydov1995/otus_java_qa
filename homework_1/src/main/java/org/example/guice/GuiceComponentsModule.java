package org.example.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.components.EducationMenuComponent;
import org.example.components.MainPageBreadcrumbsComponent;
import org.openqa.selenium.WebDriver;

public class GuiceComponentsModule extends AbstractModule {

  private final WebDriver driver;

  public GuiceComponentsModule(WebDriver driver) {
    this.driver = driver;
  }

  @Provides
  @Singleton
  public MainPageBreadcrumbsComponent getMainPageBreadcrumbsComponent() {
    return new MainPageBreadcrumbsComponent(driver);
  }

  @Provides
  @Singleton
  public EducationMenuComponent getEducationMenuComponent() {
    return new EducationMenuComponent(driver);
  }
}
