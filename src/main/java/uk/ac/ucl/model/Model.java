package uk.ac.ucl.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import uk.ac.ucl.model.domain.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;


public class Model
{
  private Map<String, Note> notesMap = new HashMap<>();
  private final ObjectMapper mapper = new ObjectMapper();
  private final String notesFilePath = "data/notes.json";

  public Map<String, Note> loadNotes() {
    try {
      File file = new File(notesFilePath);
      if (!file.exists()) {
        return new HashMap<>();
      }

      // First load as list
      List<Note> notesList = mapper.readValue(file, new TypeReference<List<Note>>(){});

      // Convert to map
      notesMap = new HashMap<>();
      for (Note note : notesList) {
        notesMap.put(note.getId(), note);
      }

      return notesMap;
    } catch (IOException e) {
      e.printStackTrace();
      return new HashMap<>();
    }
  }

  public boolean saveNotes() {
    try {
      List<Note> notesList = new ArrayList<>(notesMap.values());
      mapper.writeValue(new File(notesFilePath), notesList);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public Note getNoteById(String id) {
    return notesMap.get(id);
  }

  public List<Note> getNotes(){
    return new ArrayList<>(notesMap.values());
  }

  public void addNote(Note note){
    notesMap.put(note.getId(), note);
  }

  // This also returns dummy data. The real version should use the keyword parameter to search
  // the data and return a list of matching items.
  public List<String> searchFor(String keyword)
  {
    return List.of("Search keyword is: "+ keyword, "result1", "result2", "result3");
  }
}


/*Data Access Methods required

open() or loadNotes() - Load notes from JSON file
saveNotes() - Save notes to JSON file
getNotes() - Return the collection of notes
getNoteById(String id) - Find specific note


Modification Methods

addNote(Note note) - Add a new note
updateNote(Note note) - Update existing note
deleteNote(String id) - Delete a note
createCategory(String category) - Create note category (Requirement 6)
addNoteToCategory(String noteId, String categoryId) - Add note to category


Search Methods

searchNotes(String query) - Search through notes
getNotesInCategory(String category) - Get notes in a category


Utility Methods

generateId() - Generate unique IDs for notes
validateNote(Note note) - Validate note data
backupNotes() - Create backups (optional)*/