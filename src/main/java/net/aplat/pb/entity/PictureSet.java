package net.aplat.pb.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PictureSet implements Labelable {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String groupName;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "pictureSet", orphanRemoval = true)
    private List<Picture> pictures;

    @ManyToMany
    private List<Label> labels;

    public PictureSet() {
    }

    public PictureSet(String title, String groupName) {
        this.title = title;
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String group) {
        this.groupName = group;
    }

    public List<Picture> getPictures() {
        if (pictures == null) {
            pictures = new ArrayList<>();
        }
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Label> getLabels() {
        if (labels == null) {
            labels = new ArrayList<>();
        }
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}
