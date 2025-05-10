package org.example.components;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class EducationMenuComponent extends AbsComponent<EducationMenuComponent> {
  private static Random RANDOM = new Random();
  @FindBy(xpath = "//span[@title='Обучение']")
  private WebElement educationMenuButton;
  @FindBy(xpath = "//p[text()='Все курсы']/following-sibling::div")
  private WebElement allCoursesElement;

  @Inject
  public EducationMenuComponent(WebDriver driver) {
    super(driver);
  }

  public WebElement chooseRandomCategoryElement() {
    waiter.waitForCondition(ExpectedConditions.visibilityOf(educationMenuButton));
    actions.moveToElement(educationMenuButton).build().perform();
    waiter.waitForCondition(ExpectedConditions.elementToBeClickable(allCoursesElement));
    List<WebElement> categoryWebElements = allCoursesElement.findElements(By.xpath("//p[text()='Все курсы']/following-sibling::div/a"));
    return categoryWebElements.stream()
        .filter(it ->
            !Objects.equals(it.getAccessibleName(), "Специализации (13)")
        )
        .skip(RANDOM.nextInt(categoryWebElements.size()))
        .findFirst().orElseThrow();
  }
}
