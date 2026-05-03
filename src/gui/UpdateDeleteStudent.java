package gui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateDeleteStudent extends JFrame {

    private JTextField idField, nameField, classField, rollField, emailField;

    public UpdateDeleteStudent() {
        setTitle("Update/Delete Student");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Class:"));
        classField = new JTextField();
        panel.add(classField);

        panel.add(new JLabel("Roll No:"));
        rollField = new JTextField();
        panel.add(rollField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBackground(new Color(39, 174, 96));
        updateBtn.setForeground(Color.WHITE);
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(new Color(231, 76, 60));
        deleteBtn.setForeground(Color.WHITE);

        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());

        panel.add(updateBtn);
        panel.add(deleteBtn);

        add(panel);
        setVisible(true);
    }

    // ---------------- Update Student ----------------
    private void updateStudent() {
        String idText = idField.getText().trim();
        if(idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE students SET name=?, class=?, roll_no=?, email=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nameField.getText().trim());
            ps.setString(2, classField.getText().trim());
            ps.setString(3, rollField.getText().trim());
            ps.setString(4, emailField.getText().trim());
            ps.setInt(5, Integer.parseInt(idText));

            int updated = ps.executeUpdate();
            if (updated > 0)
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            else
                JOptionPane.showMessageDialog(this, "Student ID not found!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating student", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------- Delete Student ----------------
    private void deleteStudent() {
        String idText = idField.getText().trim();
        if(idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if(confirm != JOptionPane.YES_OPTION) return;

        try (Connection con = DBConnection.getConnection()) {
            // Delete attendance records first
            String deleteAttendance = "DELETE FROM student_attendance WHERE student_id=?";
            PreparedStatement ps1 = con.prepareStatement(deleteAttendance);
            ps1.setInt(1, Integer.parseInt(idText));
            ps1.executeUpdate();

            // Delete student
            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps2 = con.prepareStatement(sql);
            ps2.setInt(1, Integer.parseInt(idText));

            int deleted = ps2.executeUpdate();
            if (deleted > 0)
                JOptionPane.showMessageDialog(this, "Student and attendance deleted successfully!");
            else
                JOptionPane.showMessageDialog(this, "Student ID not found!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting student", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
