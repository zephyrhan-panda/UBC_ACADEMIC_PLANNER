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
    private Specialization cpscSpec;

    @BeforeEach
    void runBefore() {
        testProfile = new StudentProfile("Fang Han");

        c1 = new Course("CPSC110", 4, 94.0);
        c2 = new Course("MATH100", 3, 80.0);

        ArrayList<Double> cutoffs = new ArrayList<>();
        cutoffs.add(85.0);

        ArrayList<Course> prereqs = new ArrayList<>();
        prereqs.add(c1);
        prereqs.add(c2);

        spec = new Specialization("Computer Science", cutoffs, prereqs);

        cpscSpec = new Specialization("Computer Science", new ArrayList<>(), prereqs);
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

    @Test
    void testGetMissingPrerequisitesAllMissing() {
        ArrayList<Course> missing = testProfile.getMissingPrerequisites(cpscSpec);

        assertEquals(2, missing.size());
        assertTrue(missing.contains(c1));
        assertTrue(missing.contains(c2));
    }

    @Test
    void testGetMissingPrerequisitesSomeMissing() {
        testProfile.addCourse(c1);

        ArrayList<Course> missing = testProfile.getMissingPrerequisites(cpscSpec);

        assertEquals(1, missing.size());
        assertEquals(c2, missing.get(0));
        assertFalse(missing.contains(c1));
    }

    @Test
    void testGetMissingPrerequisitesNoneMissing() {

        testProfile.addCourse(c1);
        testProfile.addCourse(c2);

        ArrayList<Course> missing = testProfile.getMissingPrerequisites(cpscSpec);

        assertTrue(missing.isEmpty());
    }

    @Test
    void testGetMissingPrerequisitesCaseInsensitive() {
        Course lowerCaseCourse = new Course("cpsc 110", 4, 85.0);
        testProfile.addCourse(lowerCaseCourse);

        ArrayList<Course> missing = testProfile.getMissingPrerequisites(cpscSpec);

        for (Course c : missing) {
            assertNotEquals("CPSC 110", c.getCode().toUpperCase());
        }
    }

    @Test
    void testCalculateProbabilityHighGPA() {
        testProfile.addCourse(c1);
        assertEquals(95.0, testProfile.calculateAdmissionProbability(spec));
    }

    @Test
    void testCalculateProbabilityAverageGPA() {
        Course avgCourse = new Course("MATH100", 3, 85.0);
        testProfile.addCourse(avgCourse);
        assertEquals(50.0, testProfile.calculateAdmissionProbability(spec));
    }

    @Test
    void testCalculateProbabilityLowGPA() {
        Course lowCourse = new Course("CPSC110", 4, 70.0);
        testProfile.addCourse(lowCourse);
        assertEquals(5.0, testProfile.calculateAdmissionProbability(spec));
    }
}
