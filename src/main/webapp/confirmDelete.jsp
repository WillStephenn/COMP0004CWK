<%@ page import="uk.ac.ucl.model.domain.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Confirm Delete</title>
  <style>
    .confirmation { max-width: 600px; margin: 40px auto; text-align: center; }
    .warning { color: #c00; font-weight: bold; margin: 20px 0; }
    .note-title { font-size: 1.2em; margin: 10px 0; }
    .actions { margin-top: 30px; }
    .button { padding: 8px 15px; margin: 0 10px; display: inline-block; text-decoration: none; }
    .delete-button { background: #f00; color: white; }
    .cancel-button { background: #ccc; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <div class="confirmation">
    <h2>Confirm Delete</h2>

    <% Note note = (Note) request.getAttribute("note"); %>

    <p class="warning">Are you sure you want to delete this note?</p>

    <p class="note-title">"<%= note.getHeader() %>"</p>

    <p>This action cannot be undone.</p>

    <div class="actions">
      <a href="deleteNote.html?id=<%= note.getId() %>&confirmed=yes" class="button delete-button">Delete</a>
      <a href="viewNote.html?id=<%= note.getId() %>" class="button cancel-button">Cancel</a>
    </div>
  </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>