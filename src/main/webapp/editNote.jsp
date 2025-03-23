<%@ page import="uk.ac.ucl.model.domain.Note" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Edit Note</title>
  <style>
    .form-container { max-width: 800px; margin: 20px auto; }
    .form-group { margin-bottom: 15px; }
    label { display: block; margin-bottom: 5px; font-weight: bold; }
    input[type="text"], textarea { width: 100%; padding: 8px; box-sizing: border-box; }
    textarea { min-height: 200px; }
    .buttons { margin-top: 20px; }
    .button { padding: 8px 15px; margin-right: 10px; cursor: pointer; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <%
    Note note = (Note) request.getAttribute("note");
    boolean isNew = (note == null);

    if (isNew) {
      note = new Note("", "", "", "",  new ArrayList<>());
  %>
  <h2>Create New Note</h2>
  <% } else { %>
  <h2>Edit Note</h2>
  <% } %>

  <div class="form-container">
    <form method="post" action="editNote.html">
      <% if (!isNew) { %>
      <input type="hidden" name="id" value="<%= note.getId() %>">
      <% } %>

      <div class="form-group">
        <label for="header">Title:</label>
        <input type="text" id="header" name="header" value="<%= note.getHeader() %>" required>
      </div>

      <div class="form-group">
        <label for="textContent">Content:</label>
        <textarea id="textContent" name="textContent"><%= note.getTextContent() != null ? note.getTextContent() : "" %></textarea>
      </div>

      <div class="form-group">
        <label for="url">URL:</label>
        <input type="text" id="url" name="url" value="<%= note.getUrl() != null ? note.getUrl() : "" %>">
      </div>

      <div class="form-group">
        <label for="imagePath">Image Path:</label>
        <input type="text" id="imagePath" name="imagePath" value="<%= note.getImagePath() != null ? note.getImagePath() : "" %>">
      </div>

      <div class="form-group">
        <label for="categories">Categories (comma separated):</label>
        <input type="text" id="categories" name="categories"
               value="<%= note.getCategories() != null ? String.join(", ", note.getCategories()) : "" %>">
        <small>Enter categories separated by commas (e.g., work, personal, important)</small>
      </div>

      <div class="buttons">
        <input type="submit" value="Save Note" class="button">
        <a href="<%= isNew ? "noteIndex.html" : "viewNote.html?id=" + note.getId() %>" class="button">Cancel</a>
      </div>
    </form>
  </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>