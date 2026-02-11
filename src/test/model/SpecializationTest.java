package model;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SpecializationTest {
    private Specialization testSpecialization;
    private ArrayList<Course> testPrerequisites;
    private Course course1;
    private Course course2;
    private ArrayList<Double> testCutoffs;

    @BeforeEach
    void runBefore() {
        testPrerequisites = new ArrayList<>();
        course1 = new Course("CPSC110", 4, 0.0);
        course2 = new Course("CPSC121", 4, 0.0);
        testPrerequisites.add(course1);
        testPrerequisites.add(course2);

        testCutoffs = new ArrayList<>();
        testCutoffs.add(85.0);
        testCutoffs.add(87.0);
        testCutoffs.add(89.0);

        testSpecialization = new Specialization("Computer Science", testCutoffs, testPrerequisites);
    }

    @Test
    void testConstructor() {

        assertEquals("Computer Science", testSpecialization.getName());
        assertEquals(testCutoffs, testSpecialization.getCutoff());

        ArrayList<Course> retrievedPrereqs = testSpecialization.getPrerequisites();
        assertEquals(2, retrievedPrereqs.size());
        assertTrue(retrievedPrereqs.contains(course1));
        assertTrue(retrievedPrereqs.contains(course2));

    }

    @Test
    void testGetHistoricalAverageWeighted() {
        ArrayList<Double> manyCutoffs = new ArrayList<>();
        manyCutoffs.add(80.0); 
        manyCutoffs.add(84.0); 
        manyCutoffs.add(90.0); 
        
        double expected = 518.0 / 6.0;

        Specialization weightedSpec = new Specialization("Computer Science", manyCutoffs, new ArrayList<>());

        assertEquals(expected, weightedSpec.getHistoricalAverage(), 0);
    }
}