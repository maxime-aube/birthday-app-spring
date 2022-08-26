package fr.maximob.birthdayapp.spring.services;

import fr.maximob.birthdayapp.spring.models.User;
import fr.maximob.birthdayapp.spring.repositories.UserRepository;
import fr.maximob.birthdayapp.spring.security.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> new ResourceAccessException("could not find user"));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceAccessException("could not find user"));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public MyUserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) return new MyUserPrincipal(user.get());
        else throw new UsernameNotFoundException("User \"" + username + "\" was not found");
    }
}
