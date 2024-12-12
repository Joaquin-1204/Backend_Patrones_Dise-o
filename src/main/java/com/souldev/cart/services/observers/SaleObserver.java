package com.souldev.cart.services.observers;

import com.souldev.cart.entities.Sale;

public interface SaleObserver {
    //Interfaz que define el método que sera implementado por los observadores
    void onSaleCompleted(Sale sale);// este método se llama cuando se completa una venta

}
