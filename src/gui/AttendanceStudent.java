package gui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;

public class AttendanceStudent extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JSpinner dateSpinner;

    public AttendanceStudent() {
        setTitle("Student Attendance");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Date picker
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Date:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        topPanel.add(dateSpinner);

        // Table
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Status"});
        table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(table);

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton presentBtn = new JButton("Mark Present");
        JButton absentBtn = new JButton("Mark Absent");
        JButton saveBtn = new JButton("Save Attendance");

        presentBtn.setBackground(new Color(39, 174, 96));
        presentBtn.setForeground(Color.WHITE);
        absentBtn.setBackground(new Color(231, 76, 60));
        absentBtn.setForeground(Color.WHITE);
        saveBtn.setBackground(new Color(41, 128, 185));
        saveBtn.setForeground(Color.WHITE);

        btnPanel.add(presentBtn);
        btnPanel.add(absentBtn);
        btnPanel.add(saveBtn);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        loadStudents();

        // Button actions
        presentBtn.addActionListener(e -> markStatus("Present"));
        absentBtn.addActionListener(e -> markStatus("Absent"));
        saveBtn.addActionListener(e -> saveAttendance());

        setVisible(true);
    }

    private void loadStudents() {
        model.setRowCount(0);
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT id, name FROM students";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(String.valueOf(rs.getInt("id")));
                row.add(rs.getString("name"));
                row.add("Absent"); // default status
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void markStatus(String status) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            model.setValueAt(status, row, 2);
        } else {
            JOptionPane.showMessageDialog(this, "Select a student first!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void saveAttendance() {
        java.util.Date selectedDate = (java.util.Date) dateSpinner.getValue();
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO student_attendance (student_id, attendance_date, status) VALUES (?, ?, ?)" +
                    " ON DUPLICATE KEY UPDATE status=?";
            PreparedStatement ps = con.prepareStatement(sql);

            for (int i = 0; i < model.getRowCount(); i++) {
                int id = Integer.parseInt(model.getValueAt(i, 0).toString());
                String status = model.getValueAt(i, 2).toString();

                ps.setInt(1, id);
                ps.setDate(2, sqlDate);
                ps.setString(3, status);
                ps.setString(4, status);
                ps.addBatch();
            }
            ps.executeBatch();
            JOptionPane.showMessageDialog(this, "Attendance saved for " + sqlDate);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving attendance", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
