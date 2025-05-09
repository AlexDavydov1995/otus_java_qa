package org.example.pages;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;

public class CoursePage extends AbsBasePage<CoursePage> {
  @Inject
  public CoursePage(WebDriver driver) {
    super(driver);
  }
}
