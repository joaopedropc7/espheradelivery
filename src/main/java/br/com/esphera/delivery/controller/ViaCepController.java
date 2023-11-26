package br.com.esphera.delivery.controller;

import br.com.esphera.delivery.models.AddressModel;
import br.com.esphera.delivery.models.DTOS.ViaCepDTO;
import br.com.esphera.delivery.models.OrderModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "Find address by cep", description = "Find address by cep",
            tags = {"CEP API"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = ViaCepDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<ViaCepDTO> getCep(@PathVariable String cep) {
        ViaCepDTO newCep = restTemplate.getForObject("https://viacep.com.br/ws/" + cep + "/json/", ViaCepDTO.class);
        return ResponseEntity.ok().body(newCep);
    }


}
