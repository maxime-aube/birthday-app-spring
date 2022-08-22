package fr.maximob.birthdayapp.birthdayapp.repositories;

import fr.maximob.birthdayapp.birthdayapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
