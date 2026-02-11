package model;

import java.util.ArrayList;

//   Represents a UBC Science specialization where:
//   name is the official title of the major,
//   cutoff is the list of historical average GPA required for entry,
//   prerequisites is a list of courses a student must complete to apply.
public class Specialization {
    private String name;
    private ArrayList<Double> cutoff;
    private ArrayList<Course> prerequisites;

    /**
     * REQUIRES: name has a non-zero length;
     * cutoff should be within [0, 100].
     * MODIFIES: this
     * EFFECTS: the name of the specialization is set to name;
     * the cutoff is set to cutoff;
     * the prerequisites are set to prerequisites.
     */
    public Specialization(String name, ArrayList<Double> cutoff, ArrayList<Course> prerequisites) {
        this.name = name;
        this.cutoff = cutoff;
        this.prerequisites = prerequisites;
    }

    /**
     * REQUIRES: cutoff is not empty, added from oldest to newest.
     * EFFECTS: returns a weighted average where more recent years have higher weights.
     * Weight for index i is set to be (i + 1) for now.
     */
    public double getHistoricalAverage() {
        double weightedSum = 0;
        int totalWeights = 0;

        for (int index = 0; index < cutoff.size(); index++) {
            int weight = index + 1; 
            weightedSum += cutoff.get(index) * weight;
            totalWeights += weight;
        }

        return weightedSum / totalWeights;
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

}
