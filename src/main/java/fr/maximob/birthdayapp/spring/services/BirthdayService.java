package fr.maximob.birthdayapp.spring.services;

import fr.maximob.birthdayapp.spring.models.Birthday;
import fr.maximob.birthdayapp.spring.repositories.BirthdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

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

    public Birthday getOneBirthdayById(Long id) {
        return birthdayRepository.findById(id).orElseThrow(() -> new ResourceAccessException("could not find birthday"));
    }

    public List<Birthday> getBirthdaysByUserId(Long userId) {
        return userService.getUserById(userId).getBirthdays().stream().toList();
    }

    public Birthday save(Birthday birthday) {
        return birthdayRepository.save(birthday);
    }

}
