package com.ifpb.androidfeedrss.model;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Notice implements Serializable {

    private String title;
    private String description;
    private String dateUpdate;
    private URL link;

    public Notice() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(String link) {
        try {
            this.link = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return Objects.equals(title, notice.title) &&
                Objects.equals(description, notice.description) &&
                Objects.equals(dateUpdate, notice.dateUpdate) &&
                Objects.equals(link, notice.link);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, description, dateUpdate, link);
    }

    @Override
    public String toString() {
        return "Notice{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateUpdate='" + dateUpdate + '\'' +
                ", link=" + link +
                '}';
    }
}
