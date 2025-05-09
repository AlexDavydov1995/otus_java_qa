import com.google.inject.Inject;
import org.example.components.CourseComponent;
import org.example.extensions.UIExtension;
import org.example.pages.CoursesPage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@ExtendWith(UIExtension.class)
public class CourseTest {

  @Inject
  CoursesPage coursesPage;

  @Inject
  CourseComponent courseComponent;

  static Stream<String> courseDataProvider() {
    return Stream.of(
        "Kotlin QA Engineer",
        "Administrator Linux. Basic",
        "BI-аналитика"
    );
  }

  @ParameterizedTest
  @MethodSource("courseDataProvider")
  void testCourseSearch(String courseName) throws InterruptedException {
    coursesPage.open()
        .findAndClickCourseByName(courseName);

    Thread.sleep(2000);
  }
}
