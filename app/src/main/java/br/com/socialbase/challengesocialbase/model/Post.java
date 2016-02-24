package br.com.socialbase.challengesocialbase.model;

import java.io.Serializable;

public class Post implements Serializable {

    private int revisionid;
    private String url;
    private String type;
    private String category;
    private String subject;
    private String title;
    private String username;
    private Image image;

    public Post() {}

    public Post(int revisionid, String url, String type, String category, String subject, String title, String username, Image image) {
        this.revisionid = revisionid;
        this.url = url;
        this.type = type;
        this.category = category;
        this.subject = subject;
        this.title = title;
        this.username = username;
        this.image = image;
    }

    public int getRevisionid() {
        return revisionid;
    }

    public void setRevisionid(int revisionid) {
        this.revisionid = revisionid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
