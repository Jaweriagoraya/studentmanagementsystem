package gui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    private JTextField user;
    private JPasswordField pass;

    public LoginFrame() {
        setTitle("General Login");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(52, 152, 219));

        JLabel title = new JLabel("LOGIN SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBounds(100, 30, 300, 40);
        panel.add(title);

        user = createField("Username", 100, panel);

        pass = new JPasswordField();
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 160, 100, 25);
        panel.add(passLabel);
        pass.setBounds(150, 160, 220, 30);
        panel.add(pass);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(80, 230, 120, 40);
        loginBtn.setBackground(new Color(39, 174, 96));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.addActionListener(e -> login());
        panel.add(loginBtn);

        JButton signupBtn = new JButton("SIGN UP");
        signupBtn.setBounds(250, 230, 120, 40);
        signupBtn.setBackground(new Color(241, 196, 15));
        signupBtn.setForeground(Color.BLACK);
        signupBtn.addActionListener(e -> signUp());
        panel.add(signupBtn);

        add(panel);
        setVisible(true);
    }

    private JTextField createField(String text, int y, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setBounds(50, y, 100, 25);
        panel.add(label);

        JTextField field = new JTextField();
        field.setBounds(150, y, 220, 30);
        panel.add(field);
        return field;
    }

    // Login method (general)
    private void login() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getText().trim());
            ps.setString(2, String.valueOf(pass.getPassword()).trim());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new Dashboard(); // open main dashboard
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Sign Up method (general)
    private void signUp() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
        if(option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();

            if(username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password");
                return;
            }

            try (Connection con = DBConnection.getConnection()) {
                // Check if username exists
                String checkSql = "SELECT * FROM users WHERE username=?";
                PreparedStatement checkPs = con.prepareStatement(checkSql);
                checkPs.setString(1, username);
                ResultSet rs = checkPs.executeQuery();
                if(rs.next()) {
                    JOptionPane.showMessageDialog(this, "Username already exists!");
                    return;
                }

                // Insert new user
                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Sign Up successful! You can now login.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
