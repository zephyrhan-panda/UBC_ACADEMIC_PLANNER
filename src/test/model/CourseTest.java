package model;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    private Course testCourse;

    @BeforeEach
    void setup() {
        testCourse = new Course("CPSC210", 4, 98.0f);
    }

    @Test

    void testConstructor() {
        assertEquals("CPSC210", testCourse.getCode());
        assertEquals(4, testCourse.getCredits());
        assertEquals(98.0f, testCourse.getGrade());

    }

}
