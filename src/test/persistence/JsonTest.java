package persistence;

import model.Course;
import static org.junit.jupiter.api.Assertions.assertEquals;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class JsonTest {
    
    protected void checkCourse(String code, int credits, double grade, Course course) {
        assertEquals(code, course.getCode());
        assertEquals(credits, course.getCredits());
        assertEquals(grade, course.getGrade(), 0.001); 
    }
}