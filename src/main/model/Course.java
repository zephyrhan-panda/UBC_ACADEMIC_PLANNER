package model;

// Represents a single course with its course code, 
// credits, and the grade the student achieved
public class Course {
    private String code;
    private int credits;
    private double grade;

    /*
     * REQUIRES: courseCode has a non-zero length;
     * courseCredits > 0;
     * studentGrade should be within [0,100].
     * MODIFIES: this
     * EFFECTS: the code of course is set to courseCode; the credits of course is
     * set to be the courseCredits; the grade of the course is set as
     * studentGrade.
     */
    public Course(String courseCode, int courseCredits, double studentGrade) {
        this.code = courseCode;
        this.credits = courseCredits;
        this.grade = studentGrade;
    }

    public String getCode() {
        return code;
    }

    public int getCredits() {
        return credits;
    }

    public double getGrade() {
        return grade;
    }

}
