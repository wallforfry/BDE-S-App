package fr.wallforfry.bdesapp.Object;

/**
 * Created by wallerand on 29/12/2015.
 */

public class AgendaObject {

    private String summary;
    private String description;

    public AgendaObject(String summary, String description) {
        this.summary = summary;
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}