package ra.uploadvideo.model;

public class Video {
    private int id;
    private String name;
    private String description;
    private String title;

    public Video(int id, String name, String description, String title) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.title = title;
    }

    public Video() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
