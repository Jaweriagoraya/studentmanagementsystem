package gui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class AttendanceFaculty extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AttendanceFaculty() {
        setTitle("Faculty Attendance");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Attendance"});
        table = new JTable(model);
        table.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(table);

        JPanel btnPanel = new JPanel();
        JButton presentBtn = new JButton("Mark Present");
        JButton absentBtn = new JButton("Mark Absent");

        presentBtn.setBackground(new Color(39, 174, 96));
        presentBtn.setForeground(Color.WHITE);
        absentBtn.setBackground(new Color(231, 76, 60));
        absentBtn.setForeground(Color.WHITE);

        btnPanel.add(presentBtn);
        btnPanel.add(absentBtn);

        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        loadFaculty();

        presentBtn.addActionListener(e -> markAttendance("Present"));
        absentBtn.addActionListener(e -> markAttendance("Absent"));

        setVisible(true);
    }

    private void loadFaculty() {
        model.setRowCount(0);
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT id, name, attendance_status FROM faculty";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(String.valueOf(rs.getInt("id")));
                row.add(rs.getString("name"));
                row.add(rs.getString("attendance_status"));
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void markAttendance(String status) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = Integer.parseInt(model.getValueAt(row, 0).toString());
            try (Connection con = DBConnection.getConnection()) {
                String sql = "UPDATE faculty SET attendance_status=? WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, status);
                ps.setInt(2, id);
                ps.executeUpdate();
                loadFaculty();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating attendance", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a faculty first!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}

