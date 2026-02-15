package model;

import java.util.ArrayList;
import java.util.List;

// Represents a UBC Science specialization with a name, historical cutoffs, and prerequisites.
public class Specialization {
    private String name;
    private ArrayList<Double> cutoff;
    private ArrayList<Course> prerequisites;
    private int searchCount;

    /**
     * REQUIRES: name has a non-zero length; cutoff values are within [0, 100].
     * MODIFIES: this
     * EFFECTS: initializes specialization with name, cutoffs, and prerequisites;
     * sets searchCount to 0.
     */
    public Specialization(String name, ArrayList<Double> cutoff, ArrayList<Course> prerequisites) {
        this.name = name;
        this.cutoff = cutoff;
        this.prerequisites = prerequisites;
        this.searchCount = 0;
    }

    /**
     * REQUIRES: cutoff is not empty.
     * EFFECTS: returns a weighted average of historical cutoffs; 
     * if cutoff list is empty, returns 0.0 to avoid NaN.
     */
    public double getHistoricalAverage() {
        if (cutoff.isEmpty()) {
            return 0.0;
        }
        double weightedSum = 0;
        int totalWeights = 0;

        for (int index = 0; index < cutoff.size(); index++) {
            int weight = index + 1;
            weightedSum += cutoff.get(index) * weight;
            totalWeights += weight;
        }
        return weightedSum / totalWeights;
    }

    /**
     * MODIFIES: this
     * EFFECTS: increments the searchCount by 1.
     */
    public void addSearchCount() {
        searchCount++;
    }

    /**
     * REQUIRES: spec and profile are not null.
     * EFFECTS: returns a list of prerequisites from the given specialization that
     * are not yet present in the student's profile.
     */
    public ArrayList<Course> getMissingPrerequisites(Specialization spec, StudentProfile profile) {
        ArrayList<Course> prereqcourses = spec.getPrerequisites();
        ArrayList<Course> missingCourses = new ArrayList<>();
        List<Course> completed = profile.getCourses();

        for (Course c : prereqcourses) {
            if (!hasCompleted(c, completed)) {
                missingCourses.add(c);
            }
        }
        return missingCourses;
    }

    /**
     * EFFECTS: returns true if a course with the same code as c exists in the completed list;
     * false otherwise.
     */
    public Boolean hasCompleted(Course c, List<Course> completed) {
        for (Course completedCourse : completed) {
            if (completedCourse.getCode().equalsIgnoreCase(c.getCode())) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Double> getCutoff() {
        return cutoff;
    }

    public ArrayList<Course> getPrerequisites() {
        return prerequisites;
    }
    
    public int getSearchCount() {
        return searchCount;
    }
}
