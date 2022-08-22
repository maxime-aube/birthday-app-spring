package fr.maximob.birthdayapp.birthdayapp.repositories;

import fr.maximob.birthdayapp.birthdayapp.models.Birthday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthdayRepository extends JpaRepository<Birthday, Long> {
}
