package fr.maximob.birthdayapp.birthdayapp.models;

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
    private User user;

    public Birthday() {
        super();
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
