package com.souldev.cart.services;

import com.souldev.cart.entities.ShoppingCart;
import com.souldev.cart.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShoppingCartService {

    private static ShoppingCartService instance; //Instancia unica de la clase
    private final ShoppingCartRepository shoppingCartRepository;
    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    //Método sincronized para obtener la instancia unica de ShoppingCartRepository
    public static synchronized ShoppingCartService getInstance(ShoppingCartRepository shoppingCartRepository) {
        if (instance == null) {
            instance = new ShoppingCartService(shoppingCartRepository);
        }
        return instance;
    }

    public List<ShoppingCart> getListByClient(String userName){
        return this.shoppingCartRepository.findByClient_UserName(userName);
    }
    public void cleanShoppingCart(String clientId){
        this.shoppingCartRepository.deleteByClient_Id(clientId);
    }
    public void removeProduct(String id){
        this.shoppingCartRepository.deleteById(id);
    }
    public void addProduct(ShoppingCart shoppingCart){
        this.shoppingCartRepository.save(shoppingCart);
    }
    public Long getCountByClient(String clientId){
        return this.shoppingCartRepository.countByClient_Id(clientId);
    }
}
