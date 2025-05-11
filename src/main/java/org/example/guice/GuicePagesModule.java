package org.example.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.GuiceScoped;
import org.example.pages.CoursePage;
import org.example.pages.CoursesPage;
import org.example.pages.MainPage;

public class GuicePagesModule extends AbstractModule {

  private final GuiceScoped guiceScoped;

  public GuicePagesModule(GuiceScoped guiceScoped) {
    this.guiceScoped = guiceScoped;
  }

  @Provides
  public GuiceScoped getGuiceScoped() {
    return guiceScoped;
  }

  @Provides
  @Singleton
  public MainPage getMainPage() {
    return new MainPage(guiceScoped);
  }

  @Provides
  @Singleton
  public CoursesPage getCoursesPage() {
    return new CoursesPage(guiceScoped);
  }

  @Provides
  @Singleton
  public CoursePage getCoursePage() {
    return new CoursePage(guiceScoped);
  }
}
