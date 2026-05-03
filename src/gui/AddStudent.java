package gui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddStudent extends JFrame {

    private JTextField idField, nameField, classField, rollField, emailField;

    public AddStudent() {
        setTitle("Add Student");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Student ID:"));
        idField = new JTextField(); // now editable for manual input
        panel.add(idField);

        panel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Class:"));
        classField = new JTextField();
        panel.add(classField);

        panel.add(new JLabel("Roll Number:"));
        rollField = new JTextField();
        panel.add(rollField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        JButton addBtn = new JButton("Add Student");
        addBtn.setBackground(new Color(39, 174, 96));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        panel.add(addBtn);

        addBtn.addActionListener(e -> saveStudent());

        add(panel);
        setVisible(true);
    }

    private void saveStudent() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String sclass = classField.getText().trim();
        String roll = rollField.getText().trim();
        String email = emailField.getText().trim();

        if(idText.isEmpty() || name.isEmpty() || sclass.isEmpty() || roll.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO students (id, name, class, roll_no, email, attendance_status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idText)); // manual ID
            ps.setString(2, name);
            ps.setString(3, sclass);
            ps.setString(4, roll);
            ps.setString(5, email);
            ps.setString(6, "Absent"); // default attendance

            int added = ps.executeUpdate();
            if(added > 0) {
                JOptionPane.showMessageDialog(this, "Student added successfully!");
                // Clear fields for next entry
                idField.setText("");
                nameField.setText("");
                classField.setText("");
                rollField.setText("");
                emailField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
