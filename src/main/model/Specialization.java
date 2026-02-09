package model;

import java.util.ArrayList;

public class Specialization {
    private String name;
    private float cutoff;
    private ArrayList<Course> prerequisites;

    /**
     * REQUIRES: name has a non-zero length;
     * cutoff should be within [0, 100].
     * MODIFIES: this
     * EFFECTS: the name of the specialization is set to name;
     * the cutoff is set to cutoff;
     * the prerequisites are set to prerequisites.
     */
    public Specialization(String name, Float cutoff, ArrayList<Course> prerequisites) {
        this.name = name;
        this.cutoff = cutoff;
        this.prerequisites = prerequisites;
    }

    public String getName() {
        return name;
    }

    public Float getCutoff() {
        return cutoff;
    }

    public ArrayList<Course> getPrerequisites() {
        return prerequisites;
    }

}
