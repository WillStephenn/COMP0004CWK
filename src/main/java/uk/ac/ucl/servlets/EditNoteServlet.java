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

@WebServlet("/editNote.html")
public class EditNoteServlet extends HttpServlet {

    // Display the edit form
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            String id = request.getParameter("id");
            Model model = ModelFactory.getModel();
            model.loadNotes();

            // If editing existing note, populate form with its data
            if (id != null && !id.isEmpty()) {
                Note note = model.getNoteById(id);
                if (note != null) {
                    request.setAttribute("note", note);
                } else {
                    request.setAttribute("errorMessage", "Note not found with ID: " + id);
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
                    dispatch.forward(request, response);
                    return;
                }
            }

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/editNote.jsp");
            dispatch.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error preparing edit form: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }

    // Process the form submission
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            String id = request.getParameter("id");
            String header = request.getParameter("header");
            String textContent = request.getParameter("textContent");
            String url = request.getParameter("url");
            String imagePath = request.getParameter("imagePath");
            String[] categories = request.getParameterValues("categories");

            Model model = ModelFactory.getModel();
            model.loadNotes();

            Note note;

            // Create new note or update existing
            if (id == null || id.isEmpty()) {
                // New note
                note = new Note(header, textContent, url, imagePath);
            } else {
                // Existing note
                note = model.getNoteById(id);
                if (note == null) {
                    request.setAttribute("errorMessage", "Note not found with ID: " + id);
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
                    dispatch.forward(request, response);
                    return;
                }

                // Update note data
                note.setHeader(header);
                note.setTextContent(textContent);
                note.setUrl(url);
                note.setImagePath(imagePath);
            }

            // Update categories
            if (categories != null) {
                ArrayList<String> categoryList = new ArrayList<>();
                for (String category : categories) {
                    categoryList.add(category);
                }
                note.setCategories(categoryList);
            } else {
                note.setCategories(new ArrayList<>());
            }

            // Save note to model
            model.putNote(note);
            if (!model.saveNotes()) {
                request.setAttribute("errorMessage", "Failed to save note");
                ServletContext context = getServletContext();
                RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
                dispatch.forward(request, response);
                return;
            }

            // Redirect to view the saved note
            response.sendRedirect("viewNote.html?id=" + note.getId());

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error saving note: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }
}