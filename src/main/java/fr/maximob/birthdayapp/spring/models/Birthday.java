package fr.maximob.birthdayapp.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Birthday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String firstname;
    private String lastname;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Birthday() {
        super();
    }

    public Birthday(Long id, LocalDate date, String firstname, String lastname, User user) {
        super();
        this.id = id;
        this.date = date;
        this.firstname = firstname;
        this.lastname = lastname;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Birthday{" +
                "id=" + id +
                ", date=" + date +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", user=" + user +
                '}';
    }
}
