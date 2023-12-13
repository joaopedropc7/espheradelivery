package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.DTOS.ProductRecord;
import br.com.esphera.delivery.models.DTOS.UserRecord;
import br.com.esphera.delivery.models.ProductModel;
import br.com.esphera.delivery.models.UserModel;
import br.com.esphera.delivery.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public UserModel getIdUserByAuthentication(HttpServletRequest request){
        String token = tokenService.recoverToken(request);
        Authentication authentication = tokenService.getAuthentication(token);
        UserModel userModel = (UserModel) authentication.getPrincipal();
        return userModel;
    }

}
