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
import java.util.ArrayList;

@WebServlet("/categories.html")
public class CategoriesServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            Model model = ModelFactory.getModel();
            model.loadNotes();

            // Get all notes that represent categories
            List<Note> allNotes = model.getNotes();
            List<Note> categories = new ArrayList<>();

            for (Note note : allNotes) {
                // Check if this note is a category (based on content)
                if (note.hasText() && note.getTextContent().startsWith("Category: ")) {
                    categories.add(note);
                }
            }

            request.setAttribute("categories", categories);

            // Handle create category if needed
            String action = request.getParameter("action");
            if ("create".equals(action)) {
                String categoryName = request.getParameter("categoryName");
                if (categoryName != null && !categoryName.isEmpty()) {
                    model.createCategory(categoryName);
                    response.sendRedirect("categories.html");
                    return;
                }
            }

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/categories.jsp");
            dispatch.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading categories: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Create a new category
        try {
            String categoryName = request.getParameter("categoryName");
            if (categoryName != null && !categoryName.trim().isEmpty()) {
                Model model = ModelFactory.getModel();
                model.loadNotes();
                model.createCategory(categoryName);
            }

            response.sendRedirect("categories.html");

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error creating category: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }
}