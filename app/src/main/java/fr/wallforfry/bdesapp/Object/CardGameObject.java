package fr.wallforfry.bdesapp.Object;

/**
 * Created by wallerand on 14/11/2015.
 */
public class CardGameObject {
    private String text;
    private String pkgName;
    private int imageUrl;

    public CardGameObject(String text, int imageUrl, String pkgName) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.pkgName = pkgName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPkgName() {
        return pkgName;
    }
}