package gui;

import db.DBConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewStudents extends JFrame {

    public ViewStudents() {
        setTitle("All Students");
        setSize(600, 300);
        setLocationRelativeTo(null);

        String[] cols = {"Roll", "Name", "Programme", "Mail"};
        String[][] data = new String[20][4];

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM student");

            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("RollNumber");
                data[i][1] = rs.getString("Name");
                data[i][2] = rs.getString("Programme");
                data[i][3] = rs.getString("Mail");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTable table = new JTable(data, cols);
        add(new JScrollPane(table));

        setVisible(true);
    }
}

