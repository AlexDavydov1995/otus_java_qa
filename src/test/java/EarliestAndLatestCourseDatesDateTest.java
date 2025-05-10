import com.google.inject.Inject;
import org.example.extensions.UIExtension;
import org.example.pages.CoursePage;
import org.example.pages.CoursesPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(UIExtension.class)
public class EarliestAndLatestCourseDatesDateTest {

  @Inject
  CoursesPage coursesPage;

  @Inject
  CoursePage coursePage;

  @Test
  public void testEarliestCourseDate() {
    String earliestDate = coursesPage.open()
        .expandNTimes(5)
        .waitCoursesVisible()
        .findAndClickEarliest();
    coursePage.checkCourseDate(earliestDate);
  }

  @Test
  public void testLatestCourseDate() {
    String latestDate = coursesPage.open()
        .expandNTimes(5)
        .waitCoursesVisible()
        .findAndClickLatest();
    coursePage.checkCourseDate(latestDate);
  }
}
