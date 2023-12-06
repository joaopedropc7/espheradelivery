package br.com.esphera.delivery.repository;

import br.com.esphera.delivery.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    Optional<FileEntity> findFileEntityByNameFile(String fileName);

}
