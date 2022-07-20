package net.aplat.pb.bo;

public class PictureIndexBO {
    private String title;
    private String cover;

    public PictureIndexBO() {
    }

    public PictureIndexBO(String title, String cover) {
        this.title = title;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
