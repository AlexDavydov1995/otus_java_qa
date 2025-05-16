package org.example.common;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.waiters.BaseWaiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public abstract class AbsCommon<T> {

  protected Logger log = LogManager.getLogger(AbsCommon.class);

  protected WebDriver driver;
  protected Actions actions;
  protected BaseWaiter waiter;

  @Inject
  public AbsCommon(WebDriver driver) {
    this.driver = driver;
    this.actions = new Actions(driver);
    this.waiter = new BaseWaiter(driver);
    PageFactory.initElements(this.driver, this);
  }

  ;
}
