package fr.maximob.birthdayapp.birthdayapp.services;

import fr.maximob.birthdayapp.birthdayapp.models.User;
import fr.maximob.birthdayapp.birthdayapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> new ResourceAccessException("could not find user"));
    }

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceAccessException("could not find user"));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
