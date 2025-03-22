package uk.ac.ucl.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import uk.ac.ucl.model.domain.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;

public class Model
{
  private List<Note> notes;
  private final ObjectMapper mapper = new ObjectMapper();
  private final String notesFilePath = "data/notes.json";

  public List<Note> loadNotes() {
    try {
      File file = new File(notesFilePath);
      if (!file.exists()) {
        return new ArrayList<>();
      }
      notes = mapper.readValue(file, new TypeReference<List<Note>>(){});
      return notes;
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public Boolean saveNotes(){
    try {
      mapper.writeValue(new File(notesFilePath), notes);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    return false;
    }
  }

  public List<Note> getNotes(){
    return notes;
  }





  // This also returns dummy data. The real version should use the keyword parameter to search
  // the data and return a list of matching items.
  public List<String> searchFor(String keyword)
  {
    return List.of("Search keyword is: "+ keyword, "result1", "result2", "result3");
  }
}
