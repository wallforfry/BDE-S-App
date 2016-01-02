package fr.wallforfry.bdesapp.Object;

import java.util.Date;

import fr.wallforfry.bdesapp.Fragments.AgendaFragment;

/**
 * Created by wallerand on 29/12/2015.
 */

public class AgendaObject {

    private int id;
    private String iddb;
    private String summary;
    private String description;
    private String location;
    private String date;
    private String hour;
    private String start;
    private String end;

    public AgendaObject(int id, String iddb, String summary, String description, String date, String hour, String start, String end, String location) {
        this.id = id;
        this.iddb = iddb;
        this.summary = summary;
        this.description = description;
        this.location = location;
        this.date = date;
        this.hour = hour;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIddb() {
        return iddb;
    }

    public void setIddb(String iddb) {
        this.iddb = iddb;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}