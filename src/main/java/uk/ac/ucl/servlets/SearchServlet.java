package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.domain.Note;

import java.io.IOException;
import java.util.List;

@WebServlet("/search.html")
public class SearchServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {

    // Display search form
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/search.jsp");
    dispatch.forward(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {

    request.setCharacterEncoding("UTF-8");

    try {
      String searchTerm = request.getParameter("searchTerm");

      if (searchTerm == null || searchTerm.trim().isEmpty()) {
        request.setAttribute("errorMessage", "Please enter a search term");
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/search.jsp");
        dispatch.forward(request, response);
        return;
      }

      Model model = ModelFactory.getModel();
      model.loadNotes();
      List<Note> searchResults = model.searchFor(searchTerm);

      request.setAttribute("searchTerm", searchTerm);
      request.setAttribute("searchResults", searchResults);

      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/searchResults.jsp");
      dispatch.forward(request, response);

    } catch (Exception e) {
      request.setAttribute("errorMessage", "Error performing search: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
      dispatch.forward(request, response);
    }
  }
}