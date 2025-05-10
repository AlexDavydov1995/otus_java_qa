import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.components.EducationMenuComponent;
import org.example.extensions.UIExtension;
import org.example.pages.CoursesPage;
import org.example.pages.MainPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;

@ExtendWith(UIExtension.class)
public class CategorySelectionTest {

  private static final Logger LOG = LogManager.getLogger();

  @Inject
  MainPage mainPage;

  @Inject
  CoursesPage coursesPage;

  @Inject
  EducationMenuComponent educationMenuComponent;

  @Test
  public void testRandomCategory() throws InterruptedException {
    mainPage.open();
    WebElement randomCategoryToClick = educationMenuComponent.chooseRandomCategoryElement();
    String category = randomCategoryToClick.getAccessibleName().replaceAll("\\s\\(\\d{1,3}\\)", "");
    LOG.debug("выбранная категория - " + category);
    randomCategoryToClick.click();
    Thread.sleep(2000);
    coursesPage.checkCoursesPageVisibility().checkCheckBoxInput(category);
  }

}
