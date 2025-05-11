package org.example.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.GuiceScoped;
import org.example.components.CourseComponent;
import org.example.components.EducationMenuComponent;
import org.example.components.MainPageBreadcrumbsComponent;
import org.openqa.selenium.WebDriver;

public class GuiceComponentsModule extends AbstractModule {

  private final GuiceScoped guiceScoped;

  public GuiceComponentsModule(GuiceScoped guiceScoped) {
    this.guiceScoped = guiceScoped;
  }

  @Provides
  @Singleton
  public MainPageBreadcrumbsComponent getMainPageBreadcrumbsComponent() {
    return new MainPageBreadcrumbsComponent(guiceScoped);
  }

  @Provides
  @Singleton
  public CourseComponent getCourseComponent() {
    return new CourseComponent(guiceScoped);
  }

  @Provides
  @Singleton
  public EducationMenuComponent getEducationMenuComponent() {
    return new EducationMenuComponent(guiceScoped);
  }
}
