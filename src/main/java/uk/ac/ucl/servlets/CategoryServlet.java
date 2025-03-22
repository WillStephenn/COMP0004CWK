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

@WebServlet("/category.html")
public class CategoryServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            String categoryId = request.getParameter("id");
            if (categoryId == null || categoryId.isEmpty()) {
                response.sendRedirect("categories.html");
                return;
            }

            Model model = ModelFactory.getModel();
            model.loadNotes();

            // Get the category note
            Note category = model.getNoteById(categoryId);
            if (category == null) {
                request.setAttribute("errorMessage", "Category not found");
                ServletContext context = getServletContext();
                RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
                dispatch.forward(request, response);
                return;
            }

            // Get all notes in this category
            List<Note> notesInCategory = model.getNotesInCategory(categoryId);

            request.setAttribute("category", category);
            request.setAttribute("notesInCategory", notesInCategory);

            // Handle add/remove actions
            String action = request.getParameter("action");
            String noteId = request.getParameter("noteId");

            if ("add".equals(action) && noteId != null) {
                model.addNoteToCategory(noteId, categoryId);
                response.sendRedirect("category.html?id=" + categoryId);
                return;
            } else if ("remove".equals(action) && noteId != null) {
                Note note = model.getNoteById(noteId);
                if (note != null) {
                    note.removeCategory(categoryId);
                    model.putNote(note);
                    model.saveNotes();
                    response.sendRedirect("category.html?id=" + categoryId);
                    return;
                }
            }

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/category.jsp");
            dispatch.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading category: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }
}