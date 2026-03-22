package ui;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import javax.swing.*;
import java.awt.*;

// Represents the graphical user interface for the UBC Science Planner
@ExcludeFromJacocoGeneratedReport
public class GraphicalPlannerApp extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    // EFFECTS: constructs the main application window
    public GraphicalPlannerApp() {
        super("UBC Science Specialization Planner"); 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); 
        
        

        pack(); 
        setLocationRelativeTo(null); 
        setVisible(true); 
    }

    // EFFECTS: starts the graphical application
    public static void main(String[] args) {
        new GraphicalPlannerApp();
    }
}
