package gui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateDeleteFaculty extends JFrame {

    private JTextField idField, nameField, deptField, emailField, phoneField;

    public UpdateDeleteFaculty() {
        setTitle("Update/Delete Faculty");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Faculty ID:"));
        idField = new JTextField();
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

        JButton updateBtn = new JButton("Update");
        updateBtn.setBackground(new Color(39, 174, 96));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.addActionListener(e -> updateFaculty());

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(new Color(231, 76, 60));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.addActionListener(e -> deleteFaculty());

        panel.add(updateBtn);
        panel.add(deleteBtn);

        add(panel);
        setVisible(true);
    }

    private void updateFaculty() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE faculty SET name=?, department=?, email=?, phone=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nameField.getText());
            ps.setString(2, deptField.getText());
            ps.setString(3, emailField.getText());
            ps.setString(4, phoneField.getText());
            ps.setInt(5, Integer.parseInt(idField.getText()));

            int updated = ps.executeUpdate();
            if (updated > 0)
                JOptionPane.showMessageDialog(this, "Faculty updated successfully!");
            else
                JOptionPane.showMessageDialog(this, "Faculty ID not found!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating faculty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFaculty() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM faculty WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idField.getText()));

            int deleted = ps.executeUpdate();
            if (deleted > 0)
                JOptionPane.showMessageDialog(this, "Faculty deleted successfully!");
            else
                JOptionPane.showMessageDialog(this, "Faculty ID not found!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting faculty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

