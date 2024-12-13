package com.souldev.cart.services.facade;

import com.souldev.cart.entities.ShoppingCart;
import com.souldev.cart.services.DetailService;
import com.souldev.cart.services.SaleService;
import com.souldev.cart.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShoppingFacade {
    private final SaleService saleService;
    private final DetailService detailService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingFacade(SaleService saleService, DetailService detailService, ShoppingCartService shoppingCartService) {
        this.saleService = saleService;
        this.detailService = detailService;
        this.shoppingCartService = shoppingCartService;
    }


    public void completeSale(String userName) {
        saleService.createSale(userName);
    }

    public List<ShoppingCart> getShoppingCart(String userName) {
        return shoppingCartService.getListByClient(userName);
    }

}
