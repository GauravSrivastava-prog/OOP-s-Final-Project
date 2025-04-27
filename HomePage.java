import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {
    private String username;

    public HomePage(String username) {
        this.username = username;
        setTitle("Home Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        JButton writerButton = new JButton("Login as a Writer");
        JButton readerButton = new JButton("Login as a Reader");

        add(welcomeLabel);
        add(writerButton);
        add(readerButton);

        writerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                WriterPerson writerPerson = new WriterPerson(username);
                WriterDashboard writerDashboard = new WriterDashboard(username);
                writerDashboard.setVisible(true);
            }
        });

        readerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                ReaderPerson readerPerson = new ReaderPerson(username);
                ReaderDashboard readerDashboard = new ReaderDashboard(readerPerson);
                readerDashboard.setVisible(true);
            }
        });
        setVisible(true);
    }
}