package org.example.pages;

import com.google.inject.Inject;
import org.example.annotations.Path;
import org.example.common.AbsCommon;
import org.openqa.selenium.WebDriver;

abstract public class AbsBasePage<T> extends AbsCommon<T> {
  private static final String BASE_URL = "https://otus.ru";

  @Inject
  public AbsBasePage(WebDriver driver) {
    super(driver);
  }

  private String getPagePath() {
    Class<? extends AbsBasePage> clazz = this.getClass();
    if (clazz.isAnnotationPresent(Path.class)) {
      return clazz.getAnnotation(Path.class).value();
    }
    return "";
  }

  public T open() {
    driver.manage().window().maximize();
    driver.get(BASE_URL + getPagePath());
    return (T) this;
  }

}
