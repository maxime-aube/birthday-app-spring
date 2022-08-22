package fr.maximob.birthdayapp.birthdayapp.services;

import fr.maximob.birthdayapp.birthdayapp.models.Birthday;
import fr.maximob.birthdayapp.birthdayapp.repositories.BirthdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BirthdayService {

    @Autowired
    private BirthdayRepository birthdayRepository;
    @Autowired
    private UserService userService;

    public List<Birthday> getAllBirthdays() {
        return birthdayRepository.findAll();
    }

    public List<Birthday> getBirthdaysByUserId(Long userId) {
        return userService.getOneUserById(userId).getBirthdays().stream().toList();
    }

    public Birthday save(Birthday birthday) {
        return birthdayRepository.save(birthday);
    }

}
