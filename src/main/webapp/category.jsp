<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.domain.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Category Details</title>
  <style>
    .category-container { max-width: 800px; margin: 20px auto; }
    .category-header { margin-bottom: 20px; }
    .category-title { font-size: 1.5em; }
    .note-list { list-style: none; padding: 0; }
    .note-item { margin-bottom: 10px; padding: 10px; border: 1px solid #ddd; }
    .note-title { font-weight: bold; }
    .add-note-form { margin: 20px 0; padding: 15px; border: 1px solid #ddd; background: #f5f5f5; }
    .note-actions { margin-top: 10px; }
    .button { padding: 5px 10px; text-decoration: none; background: #f0f0f0; border: 1px solid #ccc; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <div class="category-container">
    <%
      Note category = (Note) request.getAttribute("category");
      List<Note> notesInCategory = (List<Note>) request.getAttribute("notesInCategory");
    %>

    <div class="category-header">
      <h2 class="category-title">Category: <%= category.getHeader() %></h2>
      <p>Created: <%= category.getCreationDate() %></p>
    </div>

    <h3>Notes in this Category</h3>

    <% if (notesInCategory.isEmpty()) { %>
    <p>No notes are in this category yet.</p>
    <% } else { %>
    <ul class="note-list">
      <% for (Note note : notesInCategory) { %>
      <li class="note-item">
        <div class="note-title">
          <a href="viewNote.html?id=<%= note.getId() %>"><%= note.getHeader() %></a>
        </div>
        <div class="note-meta">
          Modified: <%= note.getLastModifiedDate() %>
        </div>
        <div class="note-actions">
          <a href="category.html?id=<%= category.getId() %>&action=remove&noteId=<%= note.getId() %>"
             class="button" onclick="return confirm('Remove this note from the category?')">
            Remove from Category
          </a>
        </div>
      </li>
      <% } %>
    </ul>
    <% } %>

    <p>
      <a href="categories.html">Back to Categories</a> |
      <a href="noteIndex.html">Back to Note Index</a>
    </p>
  </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>