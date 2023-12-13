package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.infra.security.TokenService;
import br.com.esphera.delivery.models.CompanyModel;
import br.com.esphera.delivery.models.DTOS.LoginDTO;
import br.com.esphera.delivery.models.DTOS.LoginResponseDTO;
import br.com.esphera.delivery.models.DTOS.RegisterDTO;
import br.com.esphera.delivery.models.UserModel;
import br.com.esphera.delivery.repository.UserRepository;
import br.com.esphera.delivery.service.AuthorizationService;
import br.com.esphera.delivery.service.CompanyService;
import br.com.esphera.delivery.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CompanyService companyService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity registerAccount(@RequestBody RegisterDTO data){
        if(this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        CompanyModel companyModel = companyService.getCompanyById(data.companyId());
        UserModel newUser = new UserModel(data, encryptedPassword, companyModel);
        this.repository.save(newUser);
        return ResponseEntity.ok().build().ok("Usuário cadastrado com sucesso");
    }


}
