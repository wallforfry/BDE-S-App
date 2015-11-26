package fr.wallforfry.bdesapp.Object;

/**
 * Created by wallerand on 15/11/2015.
 */
public class CardPictureOnlyObject {
    private String title;
    private String subtitle;
    private String imageUrl;

    public CardPictureOnlyObject(String title, String subtitle, String imageUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
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
}