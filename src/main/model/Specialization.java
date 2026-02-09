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
        // stub
    }

    public String getName() {
        return "";
    }

    public Float getCutoff() {
        return 0.0f;
    }

    public ArrayList<Course> getPrerequisites() {
        return null;
    }

}
