package uk.ac.ucl.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import uk.ac.ucl.model.domain.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;

public class Model
{
  private Map<String, Note> notesMap = new HashMap<>();
  private final ObjectMapper mapper = new ObjectMapper();
  private final String notesFilePath = "data/notes.json";

  public void loadNotes() {
    try {
      File file = new File(notesFilePath);
      if (!file.exists()) {
        return;
      }

      // First load as list
      List<Note> notesList = mapper.readValue(file, new TypeReference<List<Note>>(){});

      // Convert to map
      notesMap = new HashMap<>();
      for (Note note : notesList) {
        notesMap.put(note.getId(), note);
      }

    } catch (IOException e) {
      e.printStackTrace();
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

  public void putNote(Note note){
    // This creates a new note if a note with that id doesn't exist yet
    notesMap.put(note.getId(), note);
    saveNotes();
  }

  public void deleteNote(Note note){
    notesMap.remove(note.getId());
    saveNotes();
  }

  public List<Note> searchFor(String keyword) {
    if (keyword == null || keyword.trim().isEmpty()) {
      return new ArrayList<>();
    }

    List<Note> results = new ArrayList<>();

    for (Note note : notesMap.values()) {
      if (note.containsSearchTerm(keyword)) {
        results.add(note);
      }
    }

    return results;
  }
}
