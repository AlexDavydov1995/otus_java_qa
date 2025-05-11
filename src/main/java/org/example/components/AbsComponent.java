package org.example.components;

import com.google.inject.Inject;
import org.example.GuiceScoped;
import org.example.common.AbsCommon;
import org.openqa.selenium.WebDriver;

public abstract class AbsComponent<T> extends AbsCommon<T> {

  @Inject
  public AbsComponent(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }
}
