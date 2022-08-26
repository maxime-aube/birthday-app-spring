package fr.maximob.birthdayapp.spring.repositories;

import fr.maximob.birthdayapp.spring.models.Birthday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthdayRepository extends JpaRepository<Birthday, Long> {
}
