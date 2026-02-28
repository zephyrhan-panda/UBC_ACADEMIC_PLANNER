package ui;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.StudentProfile;
import model.Course;
import model.Specialization;
import java.util.*;

// Represents the UBC Science Specialization Planner application user interface.
// This class manages user interactions, displays menus, and coordinates 
// between the user input and the academic planning models.
@ExcludeFromJacocoGeneratedReport
public class PlannerApp {
    private StudentProfile userProfile;
    private List<Specialization> availableSpecs;
    private Scanner userinput;
    private boolean started;

    // EFFECTS: constructs the planner, initializes data, and runs the application
    public PlannerApp() {
        init();
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: initializes profile, specialization list, scanner, and starting
    // status
    private void init() {
        userProfile = new StudentProfile("User");
        userinput = new Scanner(System.in);
        availableSpecs = new ArrayList<>();
        started = true;
        initializeDefaultData();
    }

    // MODIFIES: this
    // EFFECTS: populates the availableSpecs list with default UBC science data
    private void initializeDefaultData() {
        ArrayList<Double> cpscCutoffs = new ArrayList<>(Arrays.asList(83.0, 85.0, 84.0));
        ArrayList<Course> cpscPrereqs = new ArrayList<>();
        cpscPrereqs.add(new Course("CPSC110", 4, 0.0));
        cpscPrereqs.add(new Course("CPSC121", 4, 0.0));
        availableSpecs.add(new Specialization("Computer Science", cpscCutoffs, cpscPrereqs));
    }

    // MODIFIES: this
    // EFFECTS: runs the main application loop, processing commands until user quits
    private void runApp() {
        System.out.println("Welcome to the UBC Science Specialization Planner!");
        while (started) {
            displayMenu();
            String command = userinput.next().toLowerCase();
            if (command.equals("q")) {
                started = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Goodbye!");
    }

    // EFFECTS: displays a list of available menu options to the console
    private void displayMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1 -> Add a course to my record");
        System.out.println("2 -> View all specializations and cut-offs");
        System.out.println("3 -> How competitive my specialization is?");
        System.out.println("4 -> Predict my admission probability");
        System.out.println("5 -> Check missing prerequisites");
        System.out.println("6 -> View top searched specialization");
        System.out.println("7 -> View my current profile and courses");
        System.out.println("q -> Quit");
        System.out.print("Select an option: ");
    }

    // MODIFIES: this
    // EFFECTS: dispatches the user's menu selection to the corresponding method
    private void processCommand(String command) {
        if (command.equals("1")) {
            addCourseToProfile();
        } else if (command.equals("2")) {
            viewSpecializations();
        } else if (command.equals("3")) {
            viewRankings();
        } else if (command.equals("4")) {
            predictAdmissionProbability();
        } else if (command.equals("5")) {
            checkPrerequisites();
        } else if (command.equals("6")) {
            viewTopSearchedMajors();
        } else if (command.equals("7")) {
            viewMyProfile();
        } else {
            System.out.println("Invalid selection...");
        }
    }

    // MODIFIES: this
    // EFFECTS: guides user for course info and adds a new course to userProfile
    private void addCourseToProfile() {
        System.out.print("Enter code: ");
        String code = userinput.next();
        System.out.print("Enter credits: ");
        int credits = userinput.nextInt();
        System.out.print("Enter grade: ");
        double grade = userinput.nextDouble();
        userProfile.addCourse(new Course(code, credits, grade));
        System.out.println("Course added! Your current GPA: " + userProfile.calculateGPA());
    }

    // EFFECTS: displays the names and average historical cut-off GPAs
    // for all available specializations
    private void viewSpecializations() {
        System.out.println("\nAvailable Science Specializations:");
        for (int i = 0; i < availableSpecs.size(); i++) {
            Specialization s = availableSpecs.get(i);

            System.out.println("[" + i + "] " + s.getName()
                    + " (Average Cut-off: " + s.getHistoricalAverage() + ")");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to select a specialization, calculates the
    // admission
    // probability based on user's current GPA, and prints the result
    private void predictAdmissionProbability() {
        viewSpecializations();
        System.out.print("Enter the number before the specialization to predict: ");
        int index = userinput.nextInt();

        if (index >= 0 && index < availableSpecs.size()) {
            Specialization selected = availableSpecs.get(index);

            // Calling the non-trivial logic you wrote in the StudentProfile model
            double prob = userProfile.calculateAdmissionProbability(selected);

            System.out.println("Based on your GPA of " + userProfile.calculateGPA()
                    + ", your predicted admission probability for "
                    + selected.getName() + " is: " + prob + "%");
        } else {
            System.out.println("Invalid selection. Returning to menu.");
        }
    }

    // EFFECTS: displays the competitiveness ranking of specializations
    // Stub
    private void viewRankings() {
        System.out.println("Wait a second to see the ranking...");
    }

    // EFFECTS: identifies and prints courses missing from prerequisites for a
    // selected major
    private void checkPrerequisites() {
        viewSpecializations();
        System.out.print("Select specialization: ");
        int index = userinput.nextInt();
        Specialization selected = availableSpecs.get(index);
        ArrayList<Course> missing = userProfile.getMissingPrerequisites(selected);

        if (missing.isEmpty()) {
            System.out.println("All prerequisites met!");
        } else {
            System.out.println("You still need: ");
            for (Course c : missing) {
                System.out.println("- " + c.getCode());
            }
        }
    }

    // EFFECTS: shows the most searched major in the current session
    // Stub
    private void viewTopSearchedMajors() {
        System.out.println("Top searched major: Computer Science");
    }

    // EFFECTS: displays the student's name, list of all added courses with 
    //          their details, and the cumulative GPA
    private void viewMyProfile() {
        List<Course> courses = userProfile.getCourses();
        
        System.out.println("\n--- STUDENT ACADEMIC RECORD ---");
        System.out.println("Student Name: " + userProfile.getName());
        
        if (courses.isEmpty()) {
            System.out.println("No courses have been added yet.");
        } else {
            System.out.println("Courses:");
            for (Course c : courses) {
                System.out.println("- " + c.getCode() + ": " 
                                   + c.getCredits() + " credits, Grade: " 
                                   + c.getGrade());
            }
            System.out.println("\nCumulative GPA: " + userProfile.calculateGPA());
        }
        System.out.println("-------------------------------");
    }
}