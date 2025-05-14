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
        Arguments.of("Базы данных",
            "Полный курс по работе с реляционными и нереляционными Nosql базами данных для профессионалов. Все основные и популярные БД: Postgresql, Mysql, mongodb, cassandra, redis"),
        Arguments.of("Системный аналитик", "Специализация системный аналитик. Освойте востребованную профессию системного аналитика с нуля"),
        Arguments.of("Бизнес-аналитик 1С", "Курс бизнес-аналитик 1С")
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
