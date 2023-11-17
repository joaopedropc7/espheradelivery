package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.SellCreateRecord;
import br.com.esphera.delivery.models.EnderecoModel;
import br.com.esphera.delivery.models.SellModel;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.repository.EnderecoRepository;
import br.com.esphera.delivery.repository.SellRepository;
import br.com.esphera.delivery.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellService {

    @Autowired
    private SellRepository sellRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public SellModel createSell(SellCreateRecord data){
        ShoppingCartModel shoppingCartModel = shoppingCartRepository.findById(data.shoppingCartId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        EnderecoModel enderecoModel = new EnderecoModel(data.enderecoRecord());
        enderecoRepository.save(enderecoModel);
        SellModel sellModel = new SellModel(data, enderecoModel, shoppingCartModel);
        sellRepository.save(sellModel);
        return sellModel;
    }


}
