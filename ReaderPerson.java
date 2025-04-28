import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ReaderPerson extends Person {

    public ReaderPerson(String name) {
        super(name);
    }

    public Book getBookByTitleAuthorGenre(String title, String author) {
        Book book = null;
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT id, content, genre FROM books WHERE title = ? AND author = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, author);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String content = rs.getString("content");
                String genre = rs.getString("genre");
                book = new Book(id, title, author, genre, content);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving book by title and author: " + e.getMessage());
        }
        return book;
    }

    public List<String> getBooksByUser(String username) {
        List<String> bookTitles = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT title FROM books WHERE author = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookTitles.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving books by user: " + e.getMessage());
        }
        return bookTitles;
    }

    public List<String> getBooksByGenre(String genre) {
        List<String> bookTitles = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT title FROM books WHERE genre = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookTitles.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving books by user: " + e.getMessage());
        }
        return bookTitles;
    }

    public void downloadBook(String bookTitle, String authorName, String genre) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT content FROM books WHERE title = ? AND author = ? AND genre = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bookTitle);
            stmt.setString(2, authorName);
            stmt.setString(3, genre);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String content = rs.getString("content");

                // Define file path (can be customized)
                String filePath = bookTitle + "_by_" + authorName + ".txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write("Title: " + bookTitle);
                    writer.newLine();
                    writer.write("Author: " + authorName);
                    writer.newLine();
                    writer.write("Genre: " + genre);
                    writer.newLine();
                    writer.write("\n--- Book Content ---\n");
                    writer.write(content);
                    writer.newLine();

                    JOptionPane.showMessageDialog(null, "Book downloaded successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error writing file: " + e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Book not found with the provided details.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching book details: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
