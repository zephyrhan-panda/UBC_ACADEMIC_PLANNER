package model;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Course class.
@ExcludeFromJacocoGeneratedReport
public class CourseTest {
    private Course testCourse;

    @BeforeEach
    void runBefore() {
        testCourse = new Course("CPSC210", 4, 98.0);
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC210", testCourse.getCode());
        assertEquals(4, testCourse.getCredits());
        assertEquals(98.0, testCourse.getGrade());
    }

    @Test
    void testToJson() {
        JSONObject json = testCourse.toJson();
        assertEquals("CPSC210", json.getString("code"));
        assertEquals(4, json.getInt("credits"));
        assertEquals(98.0, json.getDouble("grade"));
    }
}
