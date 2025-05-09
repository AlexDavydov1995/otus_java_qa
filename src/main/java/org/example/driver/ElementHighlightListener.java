package org.example.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class ElementHighlightListener implements WebDriverListener {
  private static final String HIGHLIGHT_STYLE =
      "border: 3px solid red; background-color: yellow;";

  @Override
  public void beforeClick(WebElement element) {
    highlightElement(element);
  }

  @Override
  public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
    highlightElement(element);
  }

  @Override
  public void beforeClear(WebElement element) {
    highlightElement(element);
  }

  private void highlightElement(WebElement element) {
    try {
      String originalStyle = element.getAttribute("style");
      ((JavascriptExecutor) ((WrapsDriver) element).getWrappedDriver())
          .executeScript(
              "arguments[0].setAttribute('style', arguments[1]);",
              element,
              HIGHLIGHT_STYLE);
      Thread.sleep(200);
      if (originalStyle != null) {
        ((JavascriptExecutor) ((WrapsDriver) element).getWrappedDriver())
            .executeScript(
                "arguments[0].setAttribute('style', arguments[1]);",
                element,
                originalStyle);
      } else {
        ((JavascriptExecutor) ((WrapsDriver) element).getWrappedDriver())
            .executeScript(
                "arguments[0].removeAttribute('style');",
                element);
      }
    } catch (Exception e) {
      System.err.println("Error while highlighting element: " + e.getMessage());
    }
  }
}
