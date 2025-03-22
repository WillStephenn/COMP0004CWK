<%@ page import="uk.ac.ucl.model.domain.Note" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>View Note</title>
  <style>
    .note-container { padding: 20px; margin: 20px 0; border: 1px solid #ddd; }
    .note-header { font-size: 1.5em; margin-bottom: 10px; }
    .note-metadata { color: #666; font-size: 0.9em; margin-bottom: 15px; }
    .note-content { margin: 15px 0; line-height: 1.5; }
    .note-url { margin: 10px 0; }
    .note-image { max-width: 100%; margin: 10px 0; }
    .categories { margin-top: 15px; }
    .category { display: inline-block; background: #f0f0f0; padding: 3px 8px; margin-right: 5px; border-radius: 3px; }
    .actions { margin: 20px 0; }
    .button { padding: 5px 10px; background: #f0f0f0; border: 1px solid #ccc; text-decoration: none; margin-right: 10px; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <%
    Note note = (Note) request.getAttribute("note");
    if (note != null) {
  %>
  <div class="note-container">
    <h1 class="note-header"><%= note.getHeader() %></h1>

    <div class="note-metadata">
      Created: <%= note.getCreationDate() %><br>
      Last Modified: <%= note.getLastModifiedDate() %>
    </div>

    <% if (note.hasText()) { %>
    <div class="note-content">
      <%= note.getTextContent().replace("\n", "<br>") %>
    </div>
    <% } %>

    <% if (note.hasUrl()) { %>
    <div class="note-url">
      <strong>URL:</strong> <a href="<%= note.getUrl() %>" target="_blank"><%= note.getUrl() %></a>
    </div>
    <% } %>

    <% if (note.hasImage()) { %>
    <div>
      <img src="<%= note.getImagePath() %>" alt="Note Image" class="note-image">
    </div>
    <% } %>

    <% if (note.getCategories() != null && !note.getCategories().isEmpty()) { %>
    <div class="categories">
      <strong>Categories:</strong>
      <% for (String categoryId : note.getCategories()) { %>
      <span class="category">
    <a href="noteIndex.html?category=<%= categoryId %>"><%= categoryId %></a>
  </span>
      <% } %>
    </div>
    <% } %>
  </div>

  <div class="actions">
    <a href="editNote.html?id=<%= note.getId() %>" class="button">Edit Note</a>
    <a href="deleteNote.html?id=<%= note.getId() %>" class="button">Delete Note</a>
    <a href="noteIndex.html" class="button">Back to Index</a>
  </div>

  <% } else { %>
  <p>Note not found.</p>
  <a href="noteIndex.html">Back to Index</a>
  <% } %>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>