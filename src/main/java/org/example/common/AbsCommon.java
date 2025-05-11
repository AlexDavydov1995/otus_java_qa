package org.example.common;

import com.google.inject.Inject;
import org.example.GuiceScoped;
import org.example.waiters.BaseWaiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class AbsCommon<T> {

  protected GuiceScoped guiceScoped;
  protected WebDriver driver;
  protected Actions actions;
  protected BaseWaiter waiter;

  @Inject
  public AbsCommon(GuiceScoped guiceScoped) {
    this.guiceScoped = guiceScoped;
    this.driver = this.guiceScoped.getDriver();
    this.actions = new Actions(driver);
    this.waiter = new BaseWaiter(driver);
    PageFactory.initElements(this.driver, this);
  }

  ;
}
