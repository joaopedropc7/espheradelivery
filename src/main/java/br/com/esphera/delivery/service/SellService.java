package br.com.esphera.delivery.service;

import br.com.esphera.delivery.exceptions.ResourceNotFoundException;
import br.com.esphera.delivery.models.DTOS.SellCreateRecord;
import br.com.esphera.delivery.models.EnderecoModel;
import br.com.esphera.delivery.models.Enums.StatusOrder;
import br.com.esphera.delivery.models.SellModel;
import br.com.esphera.delivery.models.ShoppingCartModel;
import br.com.esphera.delivery.repository.EnderecoRepository;
import br.com.esphera.delivery.repository.SellRepository;
import br.com.esphera.delivery.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<SellModel> findAllSells(){
        List<SellModel> sells = sellRepository.findAll();
        return sells;
    }

    public SellModel findByIdSell(Integer id){
        SellModel sellModel = sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        return  sellModel;
    }

    public void cancelSell(Integer id){
        SellModel sellModel = sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        sellModel.setOrderCancelled(true);
        sellModel.setStatusOrder(StatusOrder.Cancelado);
        sellRepository.save(sellModel);
    }

    public List<SellModel> findByStatusOrder(StatusOrder statusOrder){
        List<SellModel> sellModels = sellRepository.findByStatusOrder(statusOrder);
        return sellModels;
    }

    //Pedido em preparo
    public void setOrderPrepared(Integer id){
        SellModel sellModel = sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        sellModel.setStatusOrder(StatusOrder.EmPreparo);
        sellRepository.save(sellModel);
    }

    //Pedido pronto para Entrega/Retirada
    public void setOrderReady(Integer id){
        SellModel sellModel = sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        sellModel.setStatusOrder(StatusOrder.Pronto);
        sellRepository.save(sellModel);
    }

    //Pedido em rota de entrega
    public void setOrderRoute(Integer id){
        SellModel sellModel = sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        sellModel.setStatusOrder(StatusOrder.Rota);
        sellRepository.save(sellModel);
    }

    //Pedido entregue/retirado
    public void setOrderDelivered(Integer id){
        SellModel sellModel = sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
        sellModel.setStatusOrder(StatusOrder.Entregue);
        sellRepository.save(sellModel);
    }



}
