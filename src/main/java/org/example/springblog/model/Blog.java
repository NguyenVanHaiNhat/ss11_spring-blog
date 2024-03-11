package org.example.springblog.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String author;
    private Date dayOfWrite;

    public Blog() {
    }

    public Blog(String title, String content, String author, Date dayOfWrite) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.dayOfWrite = dayOfWrite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDayOfWrite() {
        return dayOfWrite;
    }

    public void setDayOfWrite(Date dayOfWrite) {
        this.dayOfWrite = dayOfWrite;
    }
}
