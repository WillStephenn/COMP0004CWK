package uk.ac.ucl.servlets;

import java.io.IOException;

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

@WebServlet("/deleteNote.html")
public class DeleteNoteServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            String id = request.getParameter("id");
            if (id == null || id.isEmpty()) {
                response.sendRedirect("noteIndex.html");
                return;
            }

            Model model = ModelFactory.getModel();
            model.loadNotes();
            Note note = model.getNoteById(id);

            if (note == null) {
                request.setAttribute("errorMessage", "Note not found with ID: " + id);
                ServletContext context = getServletContext();
                RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
                dispatch.forward(request, response);
                return;
            }

            // Check for confirmation
            String confirmed = request.getParameter("confirmed");
            if ("yes".equals(confirmed)) {
                // Delete the note
                model.deleteNote(note);
                model.saveNotes();
                response.sendRedirect("noteIndex.html");
            } else {
                // Show confirmation page
                request.setAttribute("note", note);
                ServletContext context = getServletContext();
                RequestDispatcher dispatch = context.getRequestDispatcher("/confirmDelete.jsp");
                dispatch.forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error deleting note: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }
}