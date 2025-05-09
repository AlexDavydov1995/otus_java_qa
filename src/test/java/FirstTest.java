import com.google.inject.Inject;
import org.example.components.MainPageBreadcrumbsComponent;
import org.example.extensions.UIExtension;
import org.example.pages.MainPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(UIExtension.class)
public class FirstTest {

  @Inject
  MainPage mainPage;

  @Inject
  MainPageBreadcrumbsComponent mainPageBreadcrumbsComponent;

  @Test
  public void firstTest() throws InterruptedException {
    mainPage.open()
        .checkBreadcrumbsVisibility();

    mainPageBreadcrumbsComponent.clickProgrammingBreadcrumb();
    Thread.sleep(2000);
  }

}
