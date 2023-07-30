package net.aplat.pb.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Picture implements Labelable {
    @Id
    @GeneratedValue
    private Long id;
    private String path;
    private String name;
    private int orderNum;
    @ManyToOne
    private PictureSet pictureSet;
    @ManyToMany
    private List<Label> labels;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int order) {
        this.orderNum = order;
    }

    public PictureSet getPictureSet() {
        return pictureSet;
    }

    public void setPictureSet(PictureSet pictureSet) {
        this.pictureSet = pictureSet;
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
