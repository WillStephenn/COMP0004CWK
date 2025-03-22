<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Error</title>
    <style>
        .error-container {
            max-width: 600px;
            margin: 40px auto;
            text-align: center;
            padding: 20px;
            border: 1px solid #f0f0f0;
            border-radius: 5px;
        }
        .error-icon {
            font-size: 48px;
            color: #c00;
            margin-bottom: 20px;
        }
        .error-message {
            color: #c00;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .navigation {
            margin-top: 30px;
        }
        .home-link {
            display: inline-block;
            padding: 8px 16px;
            background-color: #f0f0f0;
            text-decoration: none;
            color: #333;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <div class="error-container">
        <div class="error-icon">!</div>
        <h2>Oops! Something went wrong</h2>

        <% if (request.getAttribute("errorMessage") != null) { %>
        <p class="error-message"><%= request.getAttribute("errorMessage") %></p>
        <% } else { %>
        <p class="error-message">An unexpected error occurred.</p>
        <% } %>

        <div class="navigation">
            <a href="noteIndex.html" class="home-link">Return to Note Index</a>
        </div>
    </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>