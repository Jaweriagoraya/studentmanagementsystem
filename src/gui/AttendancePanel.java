package gui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class AttendancePanel extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> typeCombo;

    public AttendancePanel() {
        setTitle("Attendance Management");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Top panel to select type (Student or Faculty)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(52, 152, 219));
        JLabel lbl = new JLabel("Select Type: ");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));

        typeCombo = new JComboBox<>(new String[]{"Student", "Faculty"});
        typeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        typeCombo.addActionListener(e -> loadAttendance());

        topPanel.add(lbl);
        topPanel.add(typeCombo);

        // Table to show attendance
        model = new DefaultTableModel();
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(table);

        // Button panel
        JPanel btnPanel = new JPanel();
        JButton markPresent = new JButton("Mark Present");
        JButton markAbsent = new JButton("Mark Absent");

        markPresent.setBackground(new Color(39, 174, 96));
        markPresent.setForeground(Color.WHITE);
        markPresent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        markPresent.addActionListener(e -> markAttendance("Present"));

        markAbsent.setBackground(new Color(231, 76, 60));
        markAbsent.setForeground(Color.WHITE);
        markAbsent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        markAbsent.addActionListener(e -> markAttendance("Absent"));

        btnPanel.add(markPresent);
        btnPanel.add(markAbsent);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        loadAttendance();
        setVisible(true);
    }

    private void loadAttendance() {
        String type = (String) typeCombo.getSelectedItem();
        model.setRowCount(0);
        model.setColumnCount(0);
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Attendance"});

        try (Connection con = DBConnection.getConnection()) {
            String tableName = type.equals("Student") ? "students" : "faculty";
            String sql = "SELECT id, name, attendance_status FROM " + tableName;
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
            String type = (String) typeCombo.getSelectedItem();
            String tableName = type.equals("Student") ? "students" : "faculty";

            try (Connection con = DBConnection.getConnection()) {
                String sql = "UPDATE " + tableName + " SET attendance_status=? WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, status);
                ps.setInt(2, id);
                ps.executeUpdate();
                loadAttendance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row first!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Test main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AttendancePanel());
    }
}


