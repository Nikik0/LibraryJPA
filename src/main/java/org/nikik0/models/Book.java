package org.nikik0.models;

//import jakarta.persistence.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "books", schema = "public")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookid;
    @Column(name = "name")
    @NotEmpty(message = "Book name cant be empty")
    @Size(min = 2, max=100, message = "Book name length in not correct")
    private String name;
    @Column(name = "author")
    @NotEmpty(message = "Author cant be empty")
    @Size(min = 2, max = 100, message = "Author name length isn't correct")
    private String author;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;
    public Book(int bookid, String name, String author) {
        this.bookid = bookid;
        this.name = name;
        this.author= author;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public Book() {

    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaken() {
        if (owner!=null)
            return owner.getCustomerid();
        else
            return 0;
    }

    public void setTaken(int taken) {
        if (this.owner==null)
            this.owner = new Customer();
        if (taken == 0){
            this.owner = null;
            return;
        }
        this.owner.setCustomerid(taken);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
