<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.domain.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Note Categories</title>
  <style>
    .categories-container { max-width: 800px; margin: 20px auto; }
    .category-list { list-style: none; padding: 0; }
    .category-item { margin-bottom: 10px; padding: 15px; border: 1px solid #ddd; background: #f9f9f9; }
    .category-name { font-size: 1.2em; font-weight: bold; }
    .create-form { margin: 20px 0; padding: 15px; border: 1px solid #ddd; background: #f5f5f5; }
    .form-title { margin-top: 0; }
    .form-row { margin: 10px 0; }
    input[type="text"] { padding: 8px; width: 300px; }
    .button { padding: 8px 15px; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <div class="categories-container">
    <h2>Note Categories</h2>

    <div class="create-form">
      <h3 class="form-title">Create New Category</h3>
      <form method="post" action="categories.html">
        <div class="form-row">
          <label for="categoryName">Category Name:</label>
          <input type="text" id="categoryName" name="categoryName" required>
        </div>
        <div class="form-row">
          <input type="submit" value="Create Category" class="button">
        </div>
      </form>
    </div>

    <h3>Existing Categories</h3>

    <%
      List<Note> categories = (List<Note>) request.getAttribute("categories");
      if (categories == null || categories.isEmpty()) {
    %>
    <p>No categories have been created yet.</p>
    <% } else { %>
    <ul class="category-list">
      <% for (Note category : categories) { %>
      <li class="category-item">
        <div class="category-name">
          <a href="category.html?id=<%= category.getId() %>"><%= category.getHeader() %></a>
        </div>
        <div class="category-meta">
          Created: <%= category.getCreationDate() %>
        </div>
      </li>
      <% } %>
    </ul>
    <% } %>

    <p><a href="noteIndex.html">Back to Note Index</a></p>
  </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>