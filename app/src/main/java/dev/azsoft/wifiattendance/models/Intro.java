package dev.azsoft.wifiattendance.models;

public class Intro {
    private int imageId;
    private String headline;
    private String subtitle;

    public Intro(int imageId, String headline, String subtitle) {
        this.imageId = imageId;
        this.headline = headline;
        this.subtitle = subtitle;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
