
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>My Book Collection </title>
</head>
<body>

<%@include file="header.jsp" %>

    <div align="center">
        <table border="0" cellpadding="5">
            <caption><h2>List of Books</h2></caption>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Description</th>
                <th>Price</th>
                <th></th>
            </tr>
            <c:forEach var="book" items="${listBook}">
                <tr>
                    <td><c:out value="${book.id}" /></td>
                    <td><c:out value="${book.title}" /></td>
                    <td><c:out value="${book.author}" /></td>
                    <td><c:out value="${book.description}" /></td>
                    <td><c:out value="${book.price}" /></td>
                    <td>
                        <a href="/editBook?id=<c:out value='${book.id}' />">Edit</a>                       
                        <a href="/deleteBook?id=<c:out value='${book.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    
    <%@include file="footer.jsp" %>
  
</body>
</html>