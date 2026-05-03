package gui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class ViewStudent extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public ViewStudent() {
        setTitle("View Students");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Class", "Roll No", "Email", "Attendance"});
        table = new JTable(model);
        table.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll);

        loadStudents(); // Load data from DB
        setVisible(true);
    }

    private void loadStudents() {
        model.setRowCount(0); // Clear existing rows
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM students";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(String.valueOf(rs.getInt("id")));
                row.add(rs.getString("name"));
                row.add(rs.getString("class"));
                row.add(rs.getString("roll_no"));
                row.add(rs.getString("email"));
                row.add(rs.getString("attendance_status"));
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading students", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

