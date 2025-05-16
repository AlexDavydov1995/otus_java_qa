package org.example.pages;

import com.google.inject.Inject;
import org.example.GuiceScoped;
import org.example.annotations.Path;
import org.example.components.MainPageBreadcrumbsComponent;

@Path("/")
public class MainPage extends AbsBasePage<MainPage> {

  @Inject
  MainPageBreadcrumbsComponent mainPageBreadcrumbsComponent;

  @Inject
  CoursesPage coursesPage;

  @Inject
  public MainPage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public CoursesPage clickPreCoursesCategory() {
    return mainPageBreadcrumbsComponent.checkVisibility().clickPreCoursesBreadcrumb();
  }
}
