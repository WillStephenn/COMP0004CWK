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

@WebServlet("/viewNote.html")
public class ViewNoteServlet extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            String id = request.getParameter("id");
            if (id == null || id.isEmpty()) {
                // Redirect to index if no ID provided
                response.sendRedirect("noteIndex.html");
                return;
            }

            Model model = ModelFactory.getModel();
            model.loadNotes();
            Note note = model.getNoteById(id);

            if (note == null) {
                // Note not found
                request.setAttribute("errorMessage", "Note not found with ID: " + id);
                ServletContext context = getServletContext();
                RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
                dispatch.forward(request, response);
                return;
            }

            request.setAttribute("note", note);

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/viewNote.jsp");
            dispatch.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error viewing note: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }
}
