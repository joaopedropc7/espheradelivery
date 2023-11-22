package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.DTOS.ViaCepDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/cep")
public class ViaCepController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{cep}")
    public ViaCepDTO getCep(@PathVariable String cep) {
        ViaCepDTO newCep = restTemplate.getForObject("https://viacep.com.br/ws/" + cep + "/json/", ViaCepDTO.class);
        return newCep;
    }


}
