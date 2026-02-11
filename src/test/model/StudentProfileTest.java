package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class StudentProfileTest {
    private StudentProfile testProfile;
    private Course c1;
    private Course c2;
    private Specialization spec;

    @BeforeEach
    void runBefore() {
        testProfile = new StudentProfile("Fang Han");

        c1 = new Course("CPSC110", 4, 94.0);
        c2 = new Course("MATH100", 3, 80.0);

        ArrayList<Double> cutoffs = new ArrayList<>();
        cutoffs.add(85.0); 
        
        ArrayList<Course> prereqs = new ArrayList<>();
        prereqs.add(c1); 
        
        spec = new Specialization("Computer Science", cutoffs, prereqs);
    }

    @Test
    void testConstructor() {
        assertEquals("Fang Han", testProfile.getName());
        assertNotNull(testProfile.getCourses());
        assertEquals(0, testProfile.getCourses().size());
    }

    @Test
    void testAddCourse() {
        testProfile.addCourse(c1);
        assertEquals(1, testProfile.getCourses().size());
        assertEquals(c1, testProfile.getCourses().get(0));
    }

    @Test
    void testCalculateGPA() {
        testProfile.addCourse(c1); 
        testProfile.addCourse(c2); 

        double expectedGPA = 88.0;
        assertEquals(expectedGPA, testProfile.calculateGPA(), 0.001);
    }

    @Test
    void testEligibleSuccess() {
        testProfile.addCourse(c1); 
        testProfile.addCourse(c2);

        assertTrue(testProfile.eligible(spec));
    }

    @Test
    void testEligibleFailGPA() {
        Course failingGrade = new Course("CPSC110", 4, 70.0);
        testProfile.addCourse(failingGrade);
        assertFalse(testProfile.eligible(spec));
    }

    @Test
    void testEligibleFailPrereq() {
        Course highGradeWrongCourse = new Course("PSYC101", 3, 95.0);
        testProfile.addCourse(highGradeWrongCourse);
        assertFalse(testProfile.eligible(spec));
    }
}
