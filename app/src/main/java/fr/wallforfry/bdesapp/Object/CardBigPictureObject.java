package fr.wallforfry.bdesapp.Object;

/**
 * Created by wallerand on 15/11/2015.
 */
public class CardBigPictureObject {
    private String title;
    private String subtitle;
    private String text;
    private String imageUrl;
    private Boolean open = false;

    public CardBigPictureObject(String title, String subtitle, String text, String imageUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}