<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.domain.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Notes Application</title>
  <style>
    .note-list { list-style: none; padding: 0; }
    .note-item { margin-bottom: 10px; padding: 10px; border: 1px solid #ddd; }
    .note-header { font-weight: bold; font-size: 1.2em; }
    .note-date { color: #666; font-size: 0.8em; }
    .sort-controls, .view-controls { margin: 15px 0; }
    .actions { margin: 20px 0; }
    .button { padding: 5px 10px; background: #f0f0f0; border: 1px solid #ccc; text-decoration: none; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Index</h2>

  <!-- Sorting and view controls -->
  <div class="sort-controls">
    Sort by:
    <a href="noteIndex.html?sort=date">Date Created</a> |
    <a href="noteIndex.html?sort=modified">Last Modified</a> |
    <a href="noteIndex.html?sort=title">Title</a>
  </div>

  <div class="view-controls">
    View as:
    <a href="noteIndex.html?view=summary">Summary</a> |
    <a href="noteIndex.html?view=full">Full Content</a>
  </div>

  <div class="category-filter">
    <form action="noteIndex.html" method="get">
      <label for="filterCategory">Filter by category:</label>
      <input type="text" id="filterCategory" name="category" value="<%= request.getParameter("category") != null ? request.getParameter("category") : "" %>">
      <input type="submit" value="Filter">
      <% if(request.getParameter("category") != null && !request.getParameter("category").isEmpty()) { %>
      <a href="noteIndex.html">Clear filter</a>
      <% } %>
    </form>
  </div>

  <!-- Actions -->
  <div class="actions">
    <a href="editNote.html" class="button">Create New Note</a>
    <a href="search.html" class="button">Search Notes</a>
  </div>

  <!-- Note list -->
  <ul class="note-list">
    <%
      List<Note> notes = (List<Note>) request.getAttribute("notes");
      String viewType = (String) request.getAttribute("viewType");

      if (notes != null && !notes.isEmpty()) {
        for (Note note : notes) {
    %>
    <li class="note-item">
      <div class="note-header">
        <a href="viewNote.html?id=<%= note.getId() %>"><%= note.getHeader() %></a>
      </div>
      <div class="note-date">
        Created: <%= note.getCreationDate() %><br>
        Modified: <%= note.getLastModifiedDate() %>
      </div>
      <% if (note.getCategories() != null && !note.getCategories().isEmpty()) { %>
      <div class="note-categories">
        <strong>Categories:</strong>
        <% for (String category : note.getCategories()) { %>
        <a href="noteIndex.html?category=<%= category %>" class="category-tag"><%= category %></a>
        <% } %>
      </div>
      <% } %>
      <% if ("full".equals(viewType)) { %>
      <% if (note.hasText()) { %>
      <div class="note-content">
        <%= note.getTextContent() %>
      </div>
      <% } %>

      <% if (note.hasUrl()) { %>
      <div class="note-url">
        <strong>URL:</strong> <a href="<%= note.getUrl() %>" target="_blank"><%= note.getUrl() %></a>
      </div>
      <% } %>

      <% if (note.hasImage()) { %>
      <div class="note-image">
        <img src="<%= note.getImagePath() %>" alt="Note Image" style="max-width: 300px;">
      </div>
      <% } %>
      <% } %>
      <div class="note-actions">
        <a href="editNote.html?id=<%= note.getId() %>">Edit</a> |
        <a href="deleteNote.html?id=<%= note.getId() %>">Delete</a>
      </div>
    </li>
    <%
      }
    } else {
    %>
    <p>No notes found. Create your first note!</p>
    <% } %>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
