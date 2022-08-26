package fr.maximob.birthdayapp.spring.controllers;

import fr.maximob.birthdayapp.spring.models.Birthday;
import fr.maximob.birthdayapp.spring.models.User;
import fr.maximob.birthdayapp.spring.services.BirthdayService;
import fr.maximob.birthdayapp.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BirthdayService birthdayService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{userId}/birthdays")
    public ResponseEntity<Birthday> createBirthday(
            @PathVariable("userId") Long id,
            @RequestParam("firstname") final String firstname,
            @RequestParam("lastname") final String lastname,
            @RequestParam("date") final String date) {
        try {
            User user = userService.getUserById(id);
            Birthday birthday = new Birthday(null, LocalDate.parse(date), firstname, lastname, user);
            return ResponseEntity.ok(birthdayService.save(birthday));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{userId}/birthdays/{birthdayId}")
    public ResponseEntity<Birthday> updateBirthday(
            @PathVariable("userId") Long id,
            @PathVariable("birthdayId") Long birthdayId,
            @RequestParam("firstname") final String firstname,
            @RequestParam("lastname") final String lastname,
            @RequestParam("date") final String date) {
        try {
            User user = userService.getUserById(id);
            Birthday birthday = new Birthday(birthdayId, LocalDate.parse(date), firstname, lastname, user);
            return ResponseEntity.ok(birthdayService.save(birthday));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
