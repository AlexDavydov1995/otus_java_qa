import com.google.inject.Inject;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.MainPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class FirstTest {

  @Inject
  WebDriver webDriver;

  @Inject
  MainPage mainPage;

  @Test
  public void firstTest() {
    WebDriver driver = WebDriverManager.chromedriver().create();
    Wait wait = new FluentWait(driver)
        .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
        .pollingEvery(Duration.of(100, ChronoUnit.MILLIS))
        .ignoring(Exception.class);

    wait.until(it -> true);
  }
}
