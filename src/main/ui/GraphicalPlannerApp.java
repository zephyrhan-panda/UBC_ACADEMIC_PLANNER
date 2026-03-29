package ui;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Course;
import model.Specialization;
import model.StudentProfile;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Represents the graphical user interface for the UBC Science Planner
@ExcludeFromJacocoGeneratedReport
public class GraphicalPlannerApp extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/profile.json";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private StudentProfile profile;
    private List<Specialization> availableSpecs;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // UI Components
    private DefaultListModel<String> listModel;
    private JList<String> courseList;
    private JTextField codeField;
    private JTextField creditsField;
    private JTextField gradeField;

    // EFFECTS: constructs the main application window and initializes data
    public GraphicalPlannerApp() {
        super("UBC Science Specialization Planner");
        initData();
        initGUI();
    }

    // MODIFIES: this
    // EFFECTS: initializes the student profile, JSON tools, and default specializations
    private void initData() {
        profile = new StudentProfile("User");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        availableSpecs = new ArrayList<>();
        ArrayList<Double> cpscCutoffs = new ArrayList<>(Arrays.asList(83.0, 85.0, 84.0));
        ArrayList<Course> cpscPrereqs = new ArrayList<>();
        cpscPrereqs.add(new Course("CPSC110", 4, 0.0));
        availableSpecs.add(new Specialization("Computer Science", cpscCutoffs, cpscPrereqs));
    }

    // MODIFIES: this
    // EFFECTS: sets up the main layout, panels, buttons, and visual components
    private void initGUI() {
        setupWindowListener();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());

        ImageIcon logoIcon = new ImageIcon("data/ubcscience.jpg");
        JLabel imageLabel = new JLabel(logoIcon);
        add(imageLabel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        courseList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(courseList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("My Academic Record"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: sets up the window listener to print event log on exit
    private void setupWindowListener() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Event Log:");
                for (model.Event next : model.EventLog.getInstance()) {
                    System.out.println(next.toString());
                }
                System.exit(0);
            }
        });
    }

    // EFFECTS: creates and returns the panel containing input fields and buttons
    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(createInputPanel());
        panel.add(createActionPanel());
        return panel;
    }

    // EFFECTS: creates the top row panel with input fields and the add button
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new FlowLayout());
        codeField = new JTextField(7);
        creditsField = new JTextField(3);
        gradeField = new JTextField(4);
        JButton addButton = createButton("Add Course", "Add");

        inputPanel.add(new JLabel("Code:"));
        inputPanel.add(codeField);
        inputPanel.add(new JLabel("Credits:"));
        inputPanel.add(creditsField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(addButton);
        
        return inputPanel;
    }

    // EFFECTS: creates the bottom row panel with predict, save, and load buttons
    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.add(createButton("Predict Admission", "Predict"));
        actionPanel.add(createButton("Save Data", "Save"));
        actionPanel.add(createButton("Load Data", "Load"));
        return actionPanel;
    }

    // EFFECTS: creates a button with the given label and action command, sets this as listener
    private JButton createButton(String label, String command) {
        JButton button = new JButton(label);
        button.setActionCommand(command);
        button.addActionListener(this);
        return button;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: handles button click events based on the action command
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Add")) {
            addCourseAction();
        } else if (command.equals("Predict")) {
            predictAction();
        } else if (command.equals("Save")) {
            saveAction();
        } else if (command.equals("Load")) {
            loadAction();
        }
    }

    // MODIFIES: this
    // EFFECTS: reads input fields, creates a course, adds to profile, and updates display
    private void addCourseAction() {
        try {
            String code = codeField.getText();
            int credits = Integer.parseInt(creditsField.getText());
            double grade = Double.parseDouble(gradeField.getText());
            
            Course course = new Course(code, credits, grade);
            profile.addCourse(course);
            
            codeField.setText("");
            creditsField.setText("");
            gradeField.setText("");
            updateCourseList();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for credits and grade!");
        }
    }

    // EFFECTS: calculates admission probability and shows it in a pop-up dialog
    private void predictAction() {
        Specialization cs = availableSpecs.get(0);
        double prob = profile.calculateAdmissionProbability(cs);
        JOptionPane.showMessageDialog(this, 
                "Predicted probability for Computer Science: " + prob + "%");
    }

    // EFFECTS: saves the profile to file and shows a confirmation dialog
    private void saveAction() {
        try {
            jsonWriter.open();
            jsonWriter.write(profile);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Data saved successfully to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the profile from file, updates display, and shows a confirmation dialog
    private void loadAction() {
        try {
            profile = jsonReader.read();
            updateCourseList();
            JOptionPane.showMessageDialog(this, "Data loaded successfully from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes the visual list to match the courses in the current profile
    private void updateCourseList() {
        listModel.clear();
        for (Course c : profile.getCourses()) {
            listModel.addElement(c.getCode() + " - " + c.getCredits() + " credits - Grade: " + c.getGrade());
        }
    }

    // EFFECTS: starts the graphical application
    public static void main(String[] args) {
        new GraphicalPlannerApp();
    }
}