<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.domain.Note" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Search Results</title>
  <style>
    .results-container { max-width: 800px; margin: 20px auto; }
    .search-info { margin-bottom: 20px; }
    .search-term { font-weight: bold; }
    .result-list { list-style: none; padding: 0; }
    .result-item { margin-bottom: 15px; padding: 10px; border: 1px solid #ddd; }
    .result-title { font-weight: bold; font-size: 1.1em; }
    .result-snippet { margin: 5px 0; color: #333; }
    .highlight { background: yellow; font-weight: bold; }
    .no-results { margin: 30px 0; font-style: italic; color: #666; }
  </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <div class="results-container">
    <h2>Search Results</h2>

    <%
      String searchTerm = (String) request.getAttribute("searchTerm");
      List<Note> searchResults = (List<Note>) request.getAttribute("searchResults");
    %>

    <div class="search-info">
      Search for: <span class="search-term">"<%= searchTerm %>"</span>
      <p>Found <%= searchResults.size() %> result(s)</p>
    </div>

    <% if (searchResults.isEmpty()) { %>
    <p class="no-results">No notes match your search criteria.</p>
    <% } else { %>
    <ul class="result-list">
      <% for (Note note : searchResults) { %>
      <li class="result-item">
        <div class="result-title">
          <a href="viewNote.html?id=<%= note.getId() %>"><%= note.getHeader() %></a>
        </div>

        <% if (note.hasText()) {
          // Create a snippet with the matching text
          String content = note.getTextContent();
          int index = content.toLowerCase().indexOf(searchTerm.toLowerCase());
          String snippet;

          if (index != -1) {
            int start = Math.max(0, index - 50);
            int end = Math.min(content.length(), index + searchTerm.length() + 50);
            snippet = content.substring(start, end);

            // Add ellipsis if we trimmed the text
            if (start > 0) snippet = "..." + snippet;
            if (end < content.length()) snippet = snippet + "...";

            // Add highlight to the search term
            String regex = "(?i)" + searchTerm.replaceAll("\\s+", "\\\\s+");
            snippet = snippet.replaceAll(regex, "<span class=\"highlight\">$0</span>");
          } else {
            // If the term was found in the title but not content
            snippet = content.length() > 100 ? content.substring(0, 100) + "..." : content;
          }
        %>
        <div class="result-snippet"><%= snippet %></div>
        <% } %>

        <div class="result-meta">
          Last modified: <%= note.getLastModifiedDate() %>
        </div>
      </li>
      <% } %>
    </ul>
    <% } %>

    <p>
      <a href="search.html">New Search</a> |
      <a href="noteIndex.html">Back to Index</a>
    </p>
  </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>