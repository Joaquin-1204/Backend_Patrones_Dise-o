package com.souldev.cart.services.facade;

import com.souldev.cart.entities.ShoppingCart;
import com.souldev.cart.services.DetailService;
import com.souldev.cart.services.SaleService;
import com.souldev.cart.services.ShoppingCartService;
import com.souldev.cart.services.observers.SaleEmailNotifier;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShoppingFacade {
    //Esta clase facade simplifica operaciones de ventas, detalles y carrito de compra
    private final SaleService saleService;
    private final SaleEmailNotifier saleEmailNotifier;
    private final DetailService detailService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingFacade(SaleService saleService, SaleEmailNotifier saleEmailNotifier, DetailService detailService, ShoppingCartService shoppingCartService) {
        this.saleService = saleService;
        this.saleEmailNotifier = saleEmailNotifier;
        this.saleService.addObserver(saleEmailNotifier);
        this.detailService = detailService;
        this.shoppingCartService = shoppingCartService;
    }


    //Método que completa una venta utilizando los servicios internos
    public void completeSale(String userName) {
        saleService.createSale(userName);
    }

    //Método que obtiene el carrito de compras de un usuario
    public List<ShoppingCart> getShoppingCart(String userName) {
        return shoppingCartService.getListByClient(userName);
    }

}
