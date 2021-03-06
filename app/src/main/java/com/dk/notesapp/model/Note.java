package com.dk.notesapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.dk.notesapp.database.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Note implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String description;
    private Date lastUpdateDate;
    private static final String DEFAULT_TITLE = "Новая заметка";

    public Note() {
        id = String.valueOf(System.currentTimeMillis());
        title = DEFAULT_TITLE;
        description = "";
        lastUpdateDate = new Date(System.currentTimeMillis());
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
