package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.ProductRecord;
import br.com.esphera.delivery.models.DTOS.UserRecord;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.models.UserModel;
import br.com.esphera.delivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel createUser(UserRecord dto){
        UserModel user = new UserModel(dto);
        userRepository.save(user);
        return user;
    }

    public List<UserModel> findAllUsers(){
        List<UserModel> users = userRepository.findAll();
        return users;
    }

    public UserModel findById(Integer id){
        UserModel user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return user;
    }

    public UserModel updateUser(Integer id, UserRecord dto){
        UserModel user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setCpf(dto.cpf());
        user.setNumberCellphone(dto.numberCellphone());
        userRepository.save(user);
        return user;
    }

    public void inactiveUser(Integer id){
        UserModel user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        user.setInactive(true);
        userRepository.save(user);
    }

}
