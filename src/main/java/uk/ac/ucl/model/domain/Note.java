package uk.ac.ucl.model.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Note {
    private String id;
    private String header = "Untitled Note";
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private String textContent = null;
    private String url = null;
    private String imagePath = null;

    public Note(String header, String textContent, String url, String imagePath) {
        this.id = UUID.randomUUID().toString();
        this.header = header;
        this.textContent = textContent;
        this.url = url;
        this.imagePath = imagePath;
        this.creationDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
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
        this.lastModifiedDate = LocalDateTime.now();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public boolean hasContent() {
        return hasText() || hasUrl() || hasImage();
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