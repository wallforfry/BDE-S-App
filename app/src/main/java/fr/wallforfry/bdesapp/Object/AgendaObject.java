package fr.wallforfry.bdesapp.Object;

import java.util.Date;

import fr.wallforfry.bdesapp.Fragments.AgendaFragment;

/**
 * Created by wallerand on 29/12/2015.
 */

public class AgendaObject {

    private String summary;
    private String description;
    private String location;
    private Date start;
    private Date end;

    public AgendaObject(String summary, String description, Date start, Date end, String location) {
        this.summary = summary;
        this.description = description;
        this.location = location;
        this.start = start;
        this.end = end;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

}