package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserDetails findByEmail(String email);

    UserModel findUserModelByEmail(String email);
}
