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
        completedCourses.add(course);
    }

    /**
     * REQUIRES: completedCourses is not empty.
     * EFFECTS: returns the weighted average GPA of all courses in the profile,
     * using formula: sum(grade * credits) / total_credits
     */
    public float calculateGPA() {
        float sumGrade = 0.0f;
        int totalCredits = 0;

        for (Course c : completedCourses) {
            sumGrade += c.getGrade() * c.getCredits();
            totalCredits += c.getCredits();
        }
        return sumGrade / totalCredits;
    }

    /**
     * REQUIRES: targetSpec is not null.
     * EFFECTS: returns true if the student meets the admission criteria:
     * 1. Student GPA >= Specialization's historical average.
     * 2. Student has completed all required prerequisites.
     */
    public boolean eligible(Specialization targetSpec) {
        double studentGPA = calculateGPA();
        if (studentGPA < targetSpec.getHistoricalAverage()) {
            return false;
        }

        for (Course requiredc : targetSpec.getPrerequisites()) {
            if (!hasCompleted(requiredc.getCode())) {
                return false;
            }
        }
        return true;
    }

    /**
     * REQUIRES: courseCode is not null.
     * EFFECTS: returns true if there is a course in completedCourses
     * with the given courseCode; false otherwise.
     */
    private boolean hasCompleted(String courseCode) {
        for (Course c : completedCourses) {
            if (c.getCode().equalsIgnoreCase(courseCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * REQUIRES: spec != null
     * EFFECTS: returns a list of prerequisite courses for the given specialization
     * that the student has not yet completed.
     */
    public ArrayList<Course> getMissingPrerequisites(Specialization spec) {
        ArrayList<Course> prereqCourses = spec.getPrerequisites();
        ArrayList<Course> missingCourses = new ArrayList<>();

        for (Course c : prereqCourses) {
            if (!hasCompleted(c.getCode())) {
                missingCourses.add(c);
            }
        }
        return missingCourses;
    }

    /**
     * REQUIRES: spec != null
     * EFFECTS: returns a double between 0 and 100 representing the
     * admission probability based on GPA difference.
     */
    public double calculateAdmissionProbability(Specialization spec) {
        double currentGPA = calculateGPA();
        double avgCutoff = spec.getHistoricalAverage();

        if (currentGPA >= avgCutoff + 5.0) {
            return 95.0;
        } else if (currentGPA <= avgCutoff - 10.0) {
            return 5.0;
        } else {
            double diff = currentGPA - avgCutoff;
            return 50.0 + (diff * 5.0);
        }
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return completedCourses;
    }
}
