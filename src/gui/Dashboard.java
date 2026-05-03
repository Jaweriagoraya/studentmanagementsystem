package gui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        setTitle("Dashboard");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(52, 152, 219));
        panel.setLayout(new GridLayout(1, 2, 50, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        panel.add(createBtn("Student", e -> openStudentPanel()));
        panel.add(createBtn("Faculty", e -> openFacultyPanel()));

        add(panel);
        setVisible(true);
    }

    private JButton createBtn(String text, java.awt.event.ActionListener a) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 22));
        b.setBackground(new Color(39, 174, 96));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.addActionListener(a);
        return b;
    }

    // ---------------- Student Panel ----------------
    private void openStudentPanel() {
        JFrame studentFrame = new JFrame("Student Panel");
        studentFrame.setSize(700, 450);
        studentFrame.setLocationRelativeTo(null);
        studentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new GridLayout(2, 2, 30, 30));
        studentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        studentPanel.setBackground(new Color(236, 240, 241));

        studentPanel.add(createBtn("Add Student", e -> new AddStudent()));
        studentPanel.add(createBtn("View Students", e -> new ViewStudent()));
        studentPanel.add(createBtn("Attendance", e -> new AttendanceStudent()));
        studentPanel.add(createBtn("Update/Delete", e -> new UpdateDeleteStudent()));

        studentFrame.add(studentPanel);
        studentFrame.setVisible(true);
    }

    // ---------------- Faculty Panel ----------------
    private void openFacultyPanel() {
        JFrame facultyFrame = new JFrame("Faculty Panel");
        facultyFrame.setSize(700, 450);
        facultyFrame.setLocationRelativeTo(null);
        facultyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel facultyPanel = new JPanel();
        facultyPanel.setLayout(new GridLayout(2, 2, 30, 30));
        facultyPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        facultyPanel.setBackground(new Color(236, 240, 241));

        facultyPanel.add(createBtn("Add Faculty", e -> new AddFaculty()));
        facultyPanel.add(createBtn("View Faculty", e -> new ViewFaculty()));
        facultyPanel.add(createBtn("Attendance", e -> new AttendanceFaculty()));
        facultyPanel.add(createBtn("Update/Delete", e -> new UpdateDeleteFaculty()));

        facultyFrame.add(facultyPanel);
        facultyFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard());
    }
}



