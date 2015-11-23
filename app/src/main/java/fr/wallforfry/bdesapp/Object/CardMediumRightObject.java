package fr.wallforfry.bdesapp.Object;

/**
 * Created by wallerand on 16/11/2015.
 */
public class CardMediumRightObject {

    private String title;
    private String subtitle;
    private int imageUrl;

    public CardMediumRightObject(String title, String subtitle, int imageUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
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