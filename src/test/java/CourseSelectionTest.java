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
        Arguments.of("DevOps Advanced"),
        Arguments.of("Системный аналитик"),
        Arguments.of("C++ Developer. Professional")
    );
  }

  @ParameterizedTest
  @MethodSource("courseDataProvider")
  void testCourseSearch(String courseName) throws InterruptedException {
    coursesPage.open()
        .expandNTimes(3)
        .waitCoursesVisible()
        .findAndClickCourseByName(courseName)
        .checkDisplayTitle(courseName);
  }
}
