package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
}
