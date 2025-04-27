import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;

    public LoginScreen() {
        setTitle("Login To  LITERARY WORKSPACE MANAGEMENT SYSTEM");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(signupButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            };
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignupScreen();
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        boolean isAuthenticated = DatabaseManager.authenticateUser(username, password);
        if (isAuthenticated) { // In oops class, we used to create an object of JFrame at every start,
            JOptionPane.showMessageDialog(this, "Login successful!"); // then, whenever we needed to display
            HomePage home = new HomePage(username); // showMessageDialog, we used that object , "frame".
            home.setVisible(true); // Here we just use the this keyword signifying the current object as we never
            this.dispose(); // created the JFrame object.
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openSignupScreen() {
        this.dispose();
        SignupScreen signupscreen = new SignupScreen();
        signupscreen.setVisible(true);
    }

    public static void main(String[] args) {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
    };
}