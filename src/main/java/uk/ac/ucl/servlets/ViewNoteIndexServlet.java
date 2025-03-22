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
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


@WebServlet("/noteIndex.html")
public class ViewNoteIndexServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        try{
            // Load Notes from model
            Model model = ModelFactory.getModel();
            model.loadNotes();
            List<Note> notes = model.getNotes();

            String filterCategory = request.getParameter("category");
            if (filterCategory != null && !filterCategory.trim().isEmpty()) {
                // Filter notes by category
                List<Note> filteredNotes = new ArrayList<>();
                for (Note note : notes) {
                    if (note.getCategories() != null && note.getCategories().contains(filterCategory.trim())) {
                        filteredNotes.add(note);
                    }
                }
                notes = filteredNotes;
            }

            // Sort Notes
            String sortBy = request.getParameter("sort");
            if (sortBy == null || sortBy.isEmpty()) {
                // Default sort: by creation date
                notes.sort(Comparator.comparing(Note::getCreationDate));
            } else if (sortBy.equals("title")) {
                // Sort alphabetically by title
                notes.sort(Comparator.comparing(Note::getHeader, String.CASE_INSENSITIVE_ORDER));
            } else if (sortBy.equals("modified")) {
                // Sort by last modified (newest first)
                notes.sort(Comparator.comparing(Note::getLastModifiedDate).reversed());
            }
            // Add notes to the request object so the JSP can access them
            request.setAttribute("notes", notes);

            // Forward to the JSP
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/noteIndex.jsp");
            dispatch.forward(request, response);
        } catch (Exception e) {
            // Proper error handling
            System.err.println("Error in ViewNoteIndexServlet: " + e.getMessage());
            e.printStackTrace();

            // Set error message and forward to error page
            request.setAttribute("errorMessage", "Failed to load notes: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }
}