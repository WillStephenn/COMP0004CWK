<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Search Notes</title>
  <style>
    .search-container { max-width: 600px; margin: 40px auto; }
    .search-form { margin: 20px 0; display: flex; }
    .search-input { flex-grow: 1; padding: 10px; font-size: 1em; }
    .search-button { padding: 10px 20px; margin-left: 10px; }
    .error { color: red; margin: 10px 0; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <div class="search-container">
    <h2>Search Notes</h2>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <p class="error"><%= request.getAttribute("errorMessage") %></p>
    <% } %>

    <form method="post" action="search.html" class="search-form">
      <input type="text" name="searchTerm" placeholder="Enter search term..." class="search-input" required>
      <input type="submit" value="Search" class="search-button">
    </form>

    <p>Search will look through note titles and content to find matching results.</p>

    <p><a href="noteIndex.html">Back to Index</a></p>
  </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>