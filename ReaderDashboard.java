import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ReaderDashboard extends JFrame {
    private ReaderPerson reader;
    private String username;

    // private JTextField titleField;
    private JTextField authorField;
    // private JTextField genreField;
    private JTextArea bookContentArea;
    private JButton searchButton;
    private JButton downloadButton;
    private JComboBox<String> genreComboBox;
    private JComboBox<String> bookComboBox;

    public ReaderDashboard(ReaderPerson reader) {
        this.reader = reader;
        this.username = reader.getName();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Reader Dashboard");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        inputPanel.add(new JLabel("Genre:"));
        String[] genres = { "Comedy", "Horror", "Romantic", "Historic", "Mythical", "Educational", "Poem" };
        genreComboBox = new JComboBox<>(genres);
        inputPanel.add(genreComboBox);

        inputPanel.add(new JLabel("Book Title:"));
        bookComboBox = new JComboBox<>();
        inputPanel.add(bookComboBox);

        inputPanel.add(new JLabel("Author Name:"));
        authorField = new JTextField();
        inputPanel.add(authorField);

        searchButton = new JButton("Search Book");
        inputPanel.add(searchButton);

        downloadButton = new JButton("Download Book");
        inputPanel.add(downloadButton);

        add(inputPanel, BorderLayout.NORTH);

        bookContentArea = new JTextArea();
        bookContentArea.setEditable(false);
        bookContentArea.setLineWrap(true);
        bookContentArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(bookContentArea);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBook();
            }
        });
        downloadButton.addActionListener(e -> showDownloadBookForm());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new HomePage(username);
            }
        });
        genreComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGenre = (String) genreComboBox.getSelectedItem();
                if (selectedGenre != null) {
                    List<String> books = reader.getBooksByGenre(selectedGenre);
                    bookComboBox.removeAllItems();
                    for (String bookTitle : books) {
                        bookComboBox.addItem(bookTitle);
                    }
                }
            }
        });
        setVisible(true);
    }

    private void searchBook() {
        String title = (String) bookComboBox.getSelectedItem();
        String author = authorField.getText().trim();
        String genre = (String) genreComboBox.getSelectedItem();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = reader.getBookByTitleAuthorGenre(title, author);

        if (book != null) {
            bookContentArea.setText(
                    "📖 Title: " + book.getTitle() + "\n" +
                            "👤 Author: " + book.getAuthor() + "\n" +
                            "🎵 Genre: " + book.getGenre() + "\n\n" +
                            "📝 Content:\n" + book.getContent());
        } else {
            bookContentArea.setText("");
            JOptionPane.showMessageDialog(this, "No book found matching the given details.", "Not Found",
                    JOptionPane.INFORMATION_MESSAGE);
        }
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
