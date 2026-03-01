package persistence;

import model.Course;
import model.StudentProfile;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            StudentProfile profile = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStudentProfile() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStudentProfile.json");
        try {
            StudentProfile profile = reader.read();
            assertEquals("Fang Han", profile.getName());
            assertEquals(0, profile.getCourses().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralStudentProfile() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStudentProfile.json");
        try {
            StudentProfile profile = reader.read();
            assertEquals("Fang Han", profile.getName());
            List<Course> courses = profile.getCourses();
            assertEquals(2, courses.size());
            
            checkCourse("CPSC210", 4, 95.0, courses.get(0));
            checkCourse("MATH200", 3, 93.0, courses.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
