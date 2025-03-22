package uk.ac.ucl.model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Note {
    private String id;
    private String header = "Untitled Note";
    private String creationDate;
    private String lastModifiedDate;
    private String textContent = null;
    private String url = null;
    private String imagePath = null;
    private ArrayList<String> categories = new ArrayList<>();

    public Note() {
        this.id = UUID.randomUUID().toString();
        this.creationDate = LocalDateTime.now().toString();
        this.lastModifiedDate = LocalDateTime.now().toString();
    }

    public Note(String header, String textContent, String url, String imagePath, ArrayList<String> categories) {
        this.id = UUID.randomUUID().toString();
        this.creationDate = LocalDateTime.now().toString();
        this.lastModifiedDate = LocalDateTime.now().toString();

        this.setHeader(header);
        this.setTextContent(textContent);
        this.setUrl(url);
        this.setImagePath(imagePath);

        if (categories != null) {
            this.setCategories(categories);
        }
    }

    public boolean hasText() {
        return textContent != null && !textContent.isEmpty();
    }

    public boolean hasUrl() {
        return url != null && !url.isEmpty();
    }

    public boolean hasImage() {
        return imagePath != null && !imagePath.isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
    }

    public void addCategory(String category) {
        this.categories.add(category);
    }

    public void updateModificationTime(){
        this.lastModifiedDate = LocalDateTime.now().toString();
    }

    public boolean containsSearchTerm(String term) {
        if (term == null || term.isEmpty()) {
            return false;
        }

        String lowerTerm = term.toLowerCase();

        // Search in header
        if (header != null && header.toLowerCase().contains(lowerTerm)) {
            return true;
        }

        // Search in text content
        if (hasText() && textContent.toLowerCase().contains(lowerTerm)) {
            return true;
        }

        return false;
    }
}