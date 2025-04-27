# Literary Workspace Management System

## Overview
The **Literary Workspace Management System** is a terminal-based Java application designed to manage the workspace for writers and readers. This system allows writers to write, edit, and delete books, while readers can read, download, and access books created by the writers. The project uses a MySQL database to store book details and user data.

## Features

### Writer Features:
- **Write New Book**: Writers can create new books by entering a title, genre, and content.
- **Edit Existing Book**: Writers can edit the content of their books.
- **Delete Book**: Writers can delete their books from the system.
- **Download Book**: Writers can download their books as a `.txt` file.
  
### Reader Features:
- **View Available Books**: Readers can view a list of available books written by other users.
- **Read Book**: Readers can read the content of any book from the system.
- **Download Book**: Readers can download books in `.txt` format.
  
### User Management:
- **Login**: Users (writers/readers) can log into the system using their credentials.
- **Signup**: New users can register and create an account.

## Technologies Used
- **Java**: The core programming language used to build the application.
- **Swing**: Used for the GUI components to allow user interaction.
- **MySQL**: A relational database to store and manage book and user data.
- **JDBC**: Java Database Connectivity to interact with the MySQL database.

## File Structure
Below is the list of files in the project:

- **ReaderPerson.java**: Contains the logic for managing reader-related functionality such as viewing and downloading books.
- **ReaderDashboard.java**: GUI for the reader dashboard.
- **WriterPerson.java**: Contains the logic for managing writer-related functionality such as creating, editing, deleting, and downloading books.
- **WriterDashboard.java**: GUI for the writer dashboard.
- **Book.java**: Represents the book entity and contains methods for managing book data.
- **Person.java**: A base class representing a user (either a reader or a writer).
- **LoginScreen.java**: GUI for user login.
- **SignupScreen.java**: GUI for user registration.
- **HomePage.java**: Main menu page after logging in, allowing the user to choose their role.
- **DatabaseManager.java**: Manages database connections and queries.

## Database Schema

### Books Table:
| Column Name     | Data Type | Description                                 |
|-----------------|-----------|---------------------------------------------|
| id              | INT       | Unique identifier for the book (Primary Key)|
| title           | VARCHAR   | Title of the book                           |
| author          | VARCHAR   | Author of the book (username of the writer) |
| content         | TEXT      | Content of the book                         |
| genre           | VARCHAR   | Genre of the book                           |

### Users Table:
| Column Name     | Data Type | Description                 |
|-----------------|-----------|-----------------------------|
| id              | INT       | Unique identifier (Primary Key)|
| username        | VARCHAR   | Username of the user         |
| password        | VARCHAR   | Password of the user         |

## How to Run the Project
1. **Set up MySQL Database**: 
   - Create a MySQL database called `LiteraryWorkspaceDB`.
   - Create the necessary tables (`books`, `users`) in the database.

2. **Configure Database Connection**:
   - Open `DatabaseManager.java` and update the connection URL, username, and password if needed.

3. **Compile and Run**:
   - Compile all Java files using a Java compiler.
   - Run the `LoginScreen.java` to start the application.

4. **Login as Writer or Reader**:
   - After logging in, users can choose their role (Writer or Reader).
   - Writers can manage books (write, edit, delete), while readers can view and download books.

## Key Functionalities

### Writer:
- **Create a new book**: Provides functionality to add new books with title, genre, and content.
- **Edit a book**: Writers can modify the content of an existing book.
- **Delete a book**: Writers can delete books they authored.
- **Download a book**: Writers can download their books in `.txt` format.

### Reader:
- **View available books**: Readers can view a list of books available in the system.
- **Read a book**: Readers can read the content of a book.
- **Download a book**: Readers can download books in `.txt` format.

## Screenshots
- Login Screen
- Home Page
- Writer Dashboard
- Reader Dashboard

## Future Enhancements
- Add more user roles or permissions.
- Implement a rating system for books.
- Add comments or reviews for books.
- Implement advanced search for books based on genres, titles, etc.

## Contributing
Feel free to fork the repository, contribute via pull requests, or open issues to improve the project.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
- This project was built as part of learning Java programming and database integration.
