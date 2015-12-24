package fr.wallforfry.bdesapp.Object;

/**
 * Created by wallerand on 16/11/2015.
 */
public class CardMediumRightObject {

    private String title;
    private String subtitle;
    private String imageUrl;
    private String actionOuvrir;
    private String actionPartager;

    public CardMediumRightObject(String title, String subtitle, String imageUrl, String actionOuvrir, String actionPartager) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
        this.actionOuvrir = actionOuvrir;
        this.actionPartager = actionPartager;
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

    public String getActionOuvrir() {
        return actionOuvrir;
    }

    public void setActionOuvrir(String actionOuvrir) {
        this.actionOuvrir = actionOuvrir;
    }

    public String getActionPartager() {
        return actionPartager;
    }

    public void setActionPartager(String actionPartager) {
        this.actionPartager = actionPartager;
    }
}