package org.example.common;

import com.google.inject.Inject;
import org.example.waiters.BaseWaiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class AbsCommon<T> {

  protected WebDriver driver;
  protected Actions actions;
  protected BaseWaiter waiter;

  @Inject
  public AbsCommon(WebDriver driver) {
    this.driver = driver;
    this.actions = new Actions(driver);
    this.waiter = new BaseWaiter(driver);
    PageFactory.initElements(this.driver, this);
  };
}
