package model;

// Represents a single course with its course code, 
// credits, and the grade the student achieved
public class Course {
    private String code;
    private int credits;
    private float grade;

    /*
     * REQUIRES: courseCode has a non-zero length;
     * courseCredits > 0;
     * studentGrade should be within [0,100].
     * MODIFIES: this
     * EFFECTS: the code of course is set to courseCode; the credits of course is
     * set to be the courseCredits; the grade of the course is set as
     * studentGrade.
     */
    public Course(String courseCode, int courseCredits, float studentGrade) {
        // stub
    }

    public String getCode() {
        return "";
    }

    public int getCredits() {
        return 0;
    }

    public float getGrade() {
        return 0.0f;
    }

}
