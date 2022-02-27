package edu.school21.cinema.models;

public class Image {

    private Long id;

    private String originalName;
    private String uniqueName;
    private String imagePath;
    private Long userId;

    public Image(Long id, String originalName, String uniqueName, String imagePath, Long userId) {
        this.id = id;
        this.originalName = originalName;
        this.uniqueName = uniqueName;
        this.imagePath = imagePath;
        this.userId = userId;
    }

    public Image(String originalName, String uniqueName, String imagePath, Long userId) {
        this.originalName = originalName;
        this.uniqueName = uniqueName;
        this.imagePath = imagePath;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
