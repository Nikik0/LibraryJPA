package org.nikik0.models;

//import jakarta.persistence.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customers", schema = "public")
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerid;
    @Column(name = "name")
    @NotEmpty(message = "Name cant be empty")
    @Size(min = 2, max = 30, message = "Name length is incorrect")
    private String name;
    @Column(name = "age")
    @Max(value = 200, message = "Age cant be this big")
    @Min(value = 0, message = "Age cant be less than 0")
    private int age;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Book> booksTaken;

    public Customer(int customerid, String name, int age) {
        this.customerid = customerid;
        this.name = name;
        this.age = age;
        this.booksTaken = new ArrayList<>();
    }

    public Customer() {

    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Book> getBooksTaken() {
        return booksTaken;
    }

    public void setBooksTaken(ArrayList<Book> booksTaken) {
        this.booksTaken = booksTaken;
    }
}
