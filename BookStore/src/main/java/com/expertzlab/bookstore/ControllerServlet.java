package com.expertzlab.bookstore;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * 
 * @author vijeesh
 *
 */
public class ControllerServlet extends HttpServlet {
    private BookDAO bookDAO;
 
    public void init() {
    	String databaseUrl = getServletContext().getInitParameter("jdbcURL");
        String username = getServletContext().getInitParameter("jdbcUsername");
        String password = getServletContext().getInitParameter("jdbcPassword");
        bookDAO = new BookDAO(databaseUrl, username, password);
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
 
        System.out.println("Controller action >>>"+ action);
        try {
            switch (action) {
            case "/newBook":
                showNewForm(request, response);
                break;
            case "/insertBook":
                insertBook(request, response);
                break;
            case "/deleteBook":
                deleteBook(request, response);
                break;
            case "/editBook":
                showEditForm(request, response);
                break;
            case "/updateBook":
                updateBook(request, response);
                break;
            default:
                listBook(request, response);
                break;
            }
        } catch (Exception e) {
        	e.printStackTrace();
            throw new ServletException(e);
        }
    }
 
    private void listBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Book> listBook = bookDAO.getAllBooks();
        request.setAttribute("listBook", listBook);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
        dispatcher.forward(request, response);
    }
 
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(BookStoreConstants.PARAM_BOOK_ID));
        Book book = bookDAO.getBook(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
        request.setAttribute("book", book);
        dispatcher.forward(request, response);
 
    }
 
    private void insertBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String title = request.getParameter(BookStoreConstants.PARAM_BOOK_TITLE);
        String author = request.getParameter(BookStoreConstants.PARAM_BOOK_AUTHOR);
        String description = request.getParameter(BookStoreConstants.PARAM_BOOK_DESCRIPTION);
        float price = Float.parseFloat(request.getParameter(BookStoreConstants.PARAM_BOOK_PRICE));
 
        Book newBook = new Book(title, author,description, price);
        bookDAO.createBook(newBook);
        response.sendRedirect("listBook");
    }
 
    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter(BookStoreConstants.PARAM_BOOK_ID));
        String title = request.getParameter(BookStoreConstants.PARAM_BOOK_TITLE);
        String author = request.getParameter(BookStoreConstants.PARAM_BOOK_AUTHOR);
        String description = request.getParameter(BookStoreConstants.PARAM_BOOK_DESCRIPTION);
        float price = Float.parseFloat(request.getParameter(BookStoreConstants.PARAM_BOOK_PRICE));
        Book book = new Book(id, title, author,description, price);
        bookDAO.updateBook(book);
        response.sendRedirect("listBook");
    }
 
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter(BookStoreConstants.PARAM_BOOK_ID));
 
        Book book = new Book(id);
        bookDAO.deleteBook(book);
        response.sendRedirect("listBook");
    }
}