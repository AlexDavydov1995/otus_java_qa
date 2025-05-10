import com.google.inject.Inject;
import org.example.extensions.UIExtension;
import org.example.pages.CoursesPage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@ExtendWith(UIExtension.class)
public class CourseSelectionTest {

  @Inject
  CoursesPage coursesPage;

  static Stream<Arguments> courseDataProvider() {
    return Stream.of(
        Arguments.of("Kotlin QA Engineer", "Курс Kotlin QA Engineer. Разработка тестов для всех платформ где используется Kotlin"),
        Arguments.of("Administrator Linux. Basic", "Курс Администратор Linux, базовый уровень"),
        Arguments.of("BI-аналитика", "Курс по BI-аналитике. Best Practice по созданию кастомных дашбордов и работе с Power BI, Tableau, Apache Superset")
    );
  }

  @ParameterizedTest
  @MethodSource("courseDataProvider")
  void testCourseSearch(String courseName, String pageTitle) throws InterruptedException {
    coursesPage.open()
        .expandNTimes(3)
        .waitCoursesVisible()
        .findAndClickCourseByName(courseName)
        .checkTitle(pageTitle)
        .checkDisplayTitle(courseName);
  }
}
