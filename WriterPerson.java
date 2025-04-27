import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class WriterPerson extends Person {
    public WriterPerson(String name) {
        super(name);
    }

    public void writeBook(String title, String content, String currentUsername, String genre) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseManager.getConnection();
            String sql = "INSERT INTO books (title, author, last_editor, genre, content) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, currentUsername);
            pstmt.setString(3, currentUsername);
            pstmt.setString(4, genre);
            pstmt.setString(5, content);

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Book Created Successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error while creating book: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean editBook(String username, String title, String newGenre, String newContent) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String findSql = "SELECT id, content FROM books WHERE title = ? AND author = ?";
            PreparedStatement findStmt = conn.prepareStatement(findSql);
            findStmt.setString(1, title);
            findStmt.setString(2, username);
            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("id");
                String oldContent = rs.getString("content");

                String mergeContent = oldContent + "\n" + newContent;

                String updateSql = "UPDATE books SET content = ?, genre = ?, last_editor = ? WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, mergeContent);
                updateStmt.setString(2, newGenre);
                updateStmt.setString(3, username);
                updateStmt.setInt(4, bookId);

                int rowsUpdated = updateStmt.executeUpdate();
                return rowsUpdated > 0;
            } else {
                System.out.println("Book Not found for editing !");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error while editing book: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBook(String username, String title) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String findSql = "SELECT id FROM books WHERE title = ? AND author = ?";
            PreparedStatement findStmt = conn.prepareStatement(findSql);
            findStmt.setString(1, title);
            findStmt.setString(2, username);
            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("id");

                String deleteSql = "DELETE FROM books WHERE id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setInt(1, bookId);

                int rowsDeleted = deleteStmt.executeUpdate();
                return rowsDeleted > 0;
            } else {
                System.out.println("No books found with this title authored by you !");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
            return false;
        }
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