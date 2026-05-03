package gui;

import javax.swing.*;

public class CoursePanel extends JFrame {
    public CoursePanel() {
        setTitle("Courses");
        setSize(400, 200);
        setLocationRelativeTo(null);
        add(new JLabel("Course module (can be extended)", SwingConstants.CENTER));
        setVisible(true);
    }
}

