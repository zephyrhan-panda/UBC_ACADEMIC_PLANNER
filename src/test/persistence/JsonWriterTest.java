package persistence;

import model.Course;
import model.StudentProfile;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExcludeFromJacocoGeneratedReport
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            // 测试如果路径里包含非法的字符（如 \0），会不会乖乖抛出异常
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // 抓到了异常，测试通过！
        }
    }

    @Test
    void testWriterEmptyStudentProfile() {
        try {
            // 1. 造一个空档案
            StudentProfile profile = new StudentProfile("Fang Han");
            
            // 2. 写进文件
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStudentProfile.json");
            writer.open();
            writer.write(profile);
            writer.close();

            // 3. 读出来验证
            JsonReader reader = new JsonReader("./data/testWriterEmptyStudentProfile.json");
            profile = reader.read();
            assertEquals("Fang Han", profile.getName());
            assertEquals(0, profile.getCourses().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStudentProfile() {
        try {
            // 1. 造一个有两门课的档案
            StudentProfile profile = new StudentProfile("Fang Han");
            profile.addCourse(new Course("CPSC210", 4, 95.0));
            profile.addCourse(new Course("MATH200", 3, 88.0));

            // 2. 写进文件
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudentProfile.json");
            writer.open();
            writer.write(profile);
            writer.close();

            // 3. 读出来验证，这里复用了咱们在 JsonTest 里写的 checkCourse 方法！
            JsonReader reader = new JsonReader("./data/testWriterGeneralStudentProfile.json");
            profile = reader.read();
            assertEquals("Fang Han", profile.getName());
            List<Course> courses = profile.getCourses();
            assertEquals(2, courses.size());
            checkCourse("CPSC210", 4, 95.0, courses.get(0));
            checkCourse("MATH200", 3, 88.0, courses.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}