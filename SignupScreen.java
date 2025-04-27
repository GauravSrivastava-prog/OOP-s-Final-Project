import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class SignupScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JComboBox<String> roleComboBox;

    public SignupScreen() {
        setTitle("Signup");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel usernameLabel = new JLabel("New Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("New Password:");
        passwordField = new JPasswordField();
        add(new JLabel("Select Role:"));
        roleComboBox = new JComboBox<>(new String[] { "reader", "writer" });
        add(roleComboBox);

        registerButton = new JButton("Register");

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSignup();
            }
        });
    }

    private void handleSignup() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        boolean isCreated = DatabaseManager.createUser(username, password, role);

        if (isCreated) {
            JOptionPane.showMessageDialog(this, "Signup Successfull, Please Login!");
            new LoginScreen().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Signup Failed, Username already exists!", "Signup Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}