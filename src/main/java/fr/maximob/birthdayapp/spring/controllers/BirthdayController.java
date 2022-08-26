package fr.maximob.birthdayapp.spring.controllers;

import fr.maximob.birthdayapp.spring.models.Birthday;
import fr.maximob.birthdayapp.spring.services.BirthdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/birthdays")
public class BirthdayController {

    @Autowired
    private BirthdayService birthdayService;

    @GetMapping(value = {"", "/"})
    public List<Birthday> getBirthdays() {
        return birthdayService.getAllBirthdays();
    }

    @GetMapping("/{birthdayId}")
    public Birthday getBirthdayById(@PathVariable("birthdayId") Long id) {
        return birthdayService.getOneBirthdayById(id);
    }
}
