package com.pitt.entity;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "posts")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Post implements Comparable<Post>{
    @Id
    @Column(name = "Id")
    private int id;

    @Column(name = "ParentId")
    private int parentId; 

    @Column(name = "CreationDate")
    private String date;

    @Column(name = "Body")
    private String body;

    @Column(name = "Summary")
    private String summary;

    @Column(name = "Type")
    private char type;

    @Transient
    private String title;

    @Transient
    private double score;

    public Post(){}

    public Post(int id, int parentId, String date, char type, String summary) {
        this.id = id;
        this.parentId = parentId;
        this.date = date;
        this.summary = summary;
        this.type = type;
    }

    public Post(int id, int parentId, String date, String body, char type) {
        this.id = id;
        this.parentId = parentId;
        this.date = date;
        this.body = body;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getScore() { return score; }

    public void setScore(double score) { this.score = score; }

    public int getParentId() { return parentId; }

    public void setParentId(int parentId) { this.parentId = parentId; }

    public char getType() { return type; }

    public void setType(char type) { this.type = type; }

    public String getSummary() { return summary; }

    public void setSummary(String summary) { this.summary = summary; }

    @Override
    public int compareTo(Post o) {
        int res = Double.compare(o.score,this.score);
        if (res == 0){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date date1=format.parse(this.date);
                Date date2=format.parse(o.date);
                res = date1.compareTo(date2);
            }catch (ParseException pe){
                pe.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Post){
            Post tmp = (Post) obj;
            if (this.id == tmp.id)
                return true;
        }
        return false;
    }

}
