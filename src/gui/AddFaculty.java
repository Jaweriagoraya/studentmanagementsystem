package gui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddFaculty extends JFrame {

    private JTextField idField, nameField, deptField, emailField, phoneField;

    public AddFaculty() {
        setTitle("Add Faculty");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Faculty ID:"));
        idField = new JTextField(); // manual ID entry
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Department:"));
        deptField = new JTextField();
        panel.add(deptField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        JButton addBtn = new JButton("Add Faculty");
        addBtn.setBackground(new Color(39, 174, 96));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        panel.add(addBtn);

        addBtn.addActionListener(e -> addFaculty());

        add(panel);
        setVisible(true);
    }

    private void addFaculty() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String dept = deptField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if(idText.isEmpty() || name.isEmpty() || dept.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO faculty (id, name, department, email, phone, attendance_status) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idText)); // manual ID
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, "Absent"); // default attendance

            int added = ps.executeUpdate();
            if(added > 0) {
                JOptionPane.showMessageDialog(this, "Faculty added successfully!");
                // Clear fields
                idField.setText("");
                nameField.setText("");
                deptField.setText("");
                emailField.setText("");
                phoneField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add faculty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
