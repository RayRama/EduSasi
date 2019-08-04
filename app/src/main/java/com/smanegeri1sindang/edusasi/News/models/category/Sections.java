package com.smanegeri1sindang.edusasi.News.models.category;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "sections")
public class Sections {

    public Sections() {
    }

    @Ignore
    public Sections(int id) {
        this.id = id;
    }

    @Ignore
    public Sections(String name, int id, boolean enabled, int order) {
        this.name = name;
        this.id = id;
        this.enabled = enabled;
        this.order = order;
    }

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "id")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "enabled")
    private boolean enabled;

    @ColumnInfo(name = "order")
    private int order;

    @ColumnInfo(name = "img")
    private String img;

    @Ignore
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
