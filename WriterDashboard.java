import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WriterDashboard extends JFrame {
    private String currentusername;

    public WriterDashboard(String username) {
        this.currentusername = username;

        setTitle("Writer Dashboard");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton writeBook = new JButton("Write a Book");
        JButton editBook = new JButton("Edit a Book");
        JButton deleteBook = new JButton("Delete a Book");
        JButton downloadButton = new JButton("Download Book");
        JButton logout = new JButton("Exit");

        writeBook.addActionListener(e -> showWriteBookForm());
        editBook.addActionListener(e -> showEditBookForm());
        deleteBook.addActionListener(e -> showDeleteBookForm());
        downloadButton.addActionListener(e -> showDownloadBookForm());
        logout.addActionListener(e -> {
            dispose();
            new LoginScreen();
        });
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new HomePage(currentusername);
            }
        });

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(welcomeLabel);
        panel.add(writeBook);
        panel.add(editBook);
        panel.add(deleteBook);
        panel.add(downloadButton);
        panel.add(logout);

        add(panel);
        setVisible(true);
    }

    private void showWriteBookForm() {
        JFrame frame = new JFrame("Write a New Book");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Enter Book Title:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JTextField titleField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        JLabel contentLabel = new JLabel("Start Writing Your Book:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(contentLabel, gbc);

        JTextArea contentArea = new JTextArea(10, 20);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        gbc.gridx = 1;
        panel.add(scrollPane, gbc);

        JLabel genreLabel = new JLabel("Select Genre:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(genreLabel, gbc);

        String[] genres = { "Acoustic", "Ambient", "Chill", "Classical", "Dance", "Indie", "Jazz" };
        JComboBox<String> genreComboBox = new JComboBox<>(genres);
        gbc.gridx = 1;
        panel.add(genreComboBox, gbc);

        JButton submitbtn = new JButton("Submit");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitbtn, gbc);

        submitbtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String content = contentArea.getText().trim();
            String genre = (String) genreComboBox.getSelectedItem();

            if (!title.isEmpty() && !content.isEmpty()) {
                WriterPerson writer = new WriterPerson(currentusername);
                writer.writeBook(title, content, currentusername, genre);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Please Fill in Both Fields!", "Missing Information",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showEditBookForm() {
        JFrame frame = new JFrame("Edit a Book");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ReaderPerson reader = new ReaderPerson(currentusername);
        List<String> bookTitles = reader.getBooksByUser(currentusername);

        JLabel selectLabel = new JLabel("Select Book:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(selectLabel, gbc);

        JComboBox<String> bookComboBox = new JComboBox<>(bookTitles.toArray(new String[0]));
        gbc.gridx = 1;
        panel.add(bookComboBox, gbc);

        JLabel contentLabel = new JLabel("Add New Content:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(contentLabel, gbc);

        JTextArea contentArea = new JTextArea(12, 30);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        gbc.gridx = 1;
        panel.add(scrollPane, gbc);

        JLabel genreLabel = new JLabel("New Genre:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(genreLabel, gbc);

        String[] genres = { "Acoustic", "Ambient", "Chill", "Classical", "Dance", "Indie", "Jazz" };
        JComboBox<String> genreComboBox = new JComboBox<>(genres);
        gbc.gridx = 1;
        panel.add(genreComboBox, gbc);

        JButton submitButton = new JButton("Update Book");
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            String selectedBook = (String) bookComboBox.getSelectedItem();
            String newContent = contentArea.getText().trim();
            String newGenre = (String) genreComboBox.getSelectedItem();

            if (selectedBook != null && !newContent.isEmpty()) {
                WriterPerson writer = new WriterPerson(currentusername);
                boolean success = writer.editBook(currentusername, selectedBook, newGenre, newContent);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Book updated successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update the book.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a book and fill in the content.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showDeleteBookForm() {
        JFrame frame = new JFrame("Delete a Book");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ReaderPerson readerPerson = new ReaderPerson(currentusername);
        List<String> bookTitles = readerPerson.getBooksByUser(currentusername);

        JLabel selectLabel = new JLabel("Select Book to Delete:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(selectLabel, gbc);

        JComboBox<String> bookComboBox = new JComboBox<>(bookTitles.toArray(new String[0]));
        gbc.gridx = 1;
        panel.add(bookComboBox, gbc);

        JButton deleteButton = new JButton("Delete Book");
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(deleteButton, gbc);

        deleteButton.addActionListener(e -> {
            String selectedBook = (String) bookComboBox.getSelectedItem();

            if (selectedBook != null) {
                int confirm = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to delete '" + selectedBook + "'?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    WriterPerson writer = new WriterPerson(currentusername);
                    boolean success = writer.deleteBook(currentusername, selectedBook);

                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Book deleted successfully!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to delete the book.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a book to delete!", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showDownloadBookForm() {
        JFrame frame = new JFrame("Download a Book");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Enter Book Title:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JTextField titleField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        JLabel authorLabel = new JLabel("Enter Author Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(authorLabel, gbc);

        JTextField authorField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        JLabel genreLabel = new JLabel("Enter Genre:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(genreLabel, gbc);

        JTextField genreField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(genreField, gbc);

        JButton downloadButton = new JButton("Download Book");
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(downloadButton, gbc);

        downloadButton.addActionListener(e -> {
            String bookTitle = titleField.getText().trim();
            String authorName = authorField.getText().trim();
            String genre = genreField.getText().trim();

            if (bookTitle.isEmpty() || authorName.isEmpty() || genre.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                WriterPerson writerPerson = new WriterPerson(authorName);
                writerPerson.downloadBook(bookTitle, authorName, genre);
                frame.dispose();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}