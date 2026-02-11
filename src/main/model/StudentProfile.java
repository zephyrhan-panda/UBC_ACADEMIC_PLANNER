package model;

import java.util.ArrayList;
import java.util.List;

//Represents a student's academic profile, managing their courses and 
//calculating admission probabilities for science specializations.
public class StudentProfile {
    private String name;
    private List<Course> completedCourses;

    /**
     * REQUIRES: name has a non-zero length.
     * MODIFIES: this
     * EFFECTS: the student's name is set to name;
     * completedCourses is initialized as an empty list.
     */
    public StudentProfile(String name) {
        this.name = name;
        this.completedCourses = new ArrayList<>();
    }

    /**
     * REQUIRES: course is not null.
     * MODIFIES: this
     * EFFECTS: adds the given course to the student's academic record.
     */
    public void addCourse(Course course) {
        // stub
    }

    /**
     * REQUIRES: completedCourses is not empty.
     * EFFECTS: returns the weighted average GPA of all courses in the profile,
     * using formula: sum(grade * credits) / total_credits
     */
    public double calculateGPA() {
        return 0.0; // stub
    }

    /**
     * REQUIRES: targetSpec is not null.
     * EFFECTS: returns true if the student meets the admission criteria:
     * 1. Student GPA >= Specialization's historical average.
     * 2. Student has completed all required prerequisites.
     */
    public boolean eligible(Specialization targetSpec) {
        return false; // stub
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return completedCourses;
    }
}
