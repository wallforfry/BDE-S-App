package fr.wallforfry.bdesapp.Object;

/**
 * Created by wallerand on 01/01/2016.
 */
public class ButownObject {

    private String profile;
    private String couverture;
    private String ville;

    public ButownObject(String profile, String couverture, String ville) {
        this.profile = profile;
        this.couverture = couverture;
        this.ville = ville;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCouverture() {
        return couverture;
    }

    public void setCouverture(String couverture) {
        this.couverture = couverture;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
