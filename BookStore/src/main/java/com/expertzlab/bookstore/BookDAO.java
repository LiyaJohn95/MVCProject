package com.expertzlab.bookstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author vijeesh
 *
 */
public class BookDAO {
	private String connectionURL;
	private Connection databaseConnection;
	private String username;
	private String password;

	/**
	 * 
	 * @param connectionURL
	 * @param username
	 * @param password
	 */
	public BookDAO(String connectionURL, String username, String password) {
		this.connectionURL = connectionURL;
		this.username = username;
		this.password = password;
	}

	/**
	 * 
	 * @throws SQLException
	 */
	private void makeDatabaseConnection() throws SQLException {
		if (databaseConnection == null || databaseConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new SQLException(e);
			}
			databaseConnection = DriverManager.getConnection(connectionURL, username, password);
		}
	}

	/**
	 * 
	 * @throws SQLException
	 */
	private void releaseDatabaseConnection() throws SQLException {
		if (databaseConnection != null && !databaseConnection.isClosed()) {
			databaseConnection.close();
		}
	}

	/**
	 * 
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public boolean createBook(Book book) throws SQLException {
		boolean rowInserted;
		try {
			
			System.out.println("Creating Book >>>"+ book);
			
			makeDatabaseConnection();
			String sql = "INSERT INTO books (title, author, description, price) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = databaseConnection.prepareStatement(sql);
			statement.setString(1, book.getTitle());
			statement.setString(2, book.getAuthor());
			statement.setString(3, book.getDescription());
			statement.setFloat(4, book.getPrice());
			rowInserted = statement.executeUpdate() > 0;
			statement.close();
			releaseDatabaseConnection();
		} catch (SQLException e) {
			System.out.println("Exception while insert");
			e.printStackTrace();
			throw e;
		}
		return rowInserted;
	}

	
	/**
	 * 
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public boolean updateBook(Book book) throws SQLException {
		String sql = "UPDATE books SET title = ?, author = ?, description = ?, price = ? WHERE book_id = ?";
		makeDatabaseConnection();

		PreparedStatement statement = databaseConnection.prepareStatement(sql);
		statement.setString(1, book.getTitle());
		statement.setString(2, book.getAuthor());
		statement.setString(3, book.getDescription());
		statement.setFloat(4, book.getPrice());
		statement.setInt(5, book.getId());

		boolean rowUpdated = statement.executeUpdate() > 0;
		statement.close();
		releaseDatabaseConnection();
		return rowUpdated;
	}
	
	/**
	 * 
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteBook(Book book) throws SQLException {
		String sql = "DELETE FROM books where book_id = ?";
		makeDatabaseConnection();
		PreparedStatement statement = databaseConnection.prepareStatement(sql);
		statement.setInt(1, book.getId());
		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		releaseDatabaseConnection();
		return rowDeleted;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Book getBook(int id) throws SQLException {
		Book book = null;
		String sql = "SELECT * FROM books WHERE book_id = ?";
		makeDatabaseConnection();
		PreparedStatement statement = databaseConnection.prepareStatement(sql);
		statement.setInt(1, id);

		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			String title = resultSet.getString(BookStoreConstants.FIELD_BOOK_TITLE);
			String author = resultSet.getString(BookStoreConstants.FIELD_BOOK_AUTHOR);
			String desc = resultSet.getString(BookStoreConstants.FIELD_BOOK_DESCRIPTION);
			float price = resultSet.getFloat(BookStoreConstants.FIELD_BOOK_PRICE);
			book = new Book(id, title, author,desc, price);
		}

		resultSet.close();
		statement.close();
		return book;
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Book> getAllBooks() throws SQLException {
		List<Book> listBook = new ArrayList<Book>();
		try {
			String sql = "SELECT * FROM books";
			makeDatabaseConnection();
			Statement statement = databaseConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt(BookStoreConstants.FIELD_BOOK_ID);
				String title = resultSet.getString(BookStoreConstants.FIELD_BOOK_TITLE);
				String author = resultSet.getString(BookStoreConstants.FIELD_BOOK_AUTHOR);
				String desc = resultSet.getString(BookStoreConstants.FIELD_BOOK_DESCRIPTION);
				float price = resultSet.getFloat(BookStoreConstants.FIELD_BOOK_PRICE);
				Book book = new Book(id, title, author,desc, price);
				listBook.add(book);
				System.out.println("BookDAO>>>>>>> listAllBook results-" + listBook);
			}
			resultSet.close();
			statement.close();
			releaseDatabaseConnection();

		} catch (SQLException e) {
			System.out.println("Exception while listAllBook");
			e.printStackTrace();
			throw e;
		}
		return listBook;
	}

}