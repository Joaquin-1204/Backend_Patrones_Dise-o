package com.souldev.cart.services.observers;

import com.souldev.cart.entities.Sale;

public class SaleEmailNotifier implements SaleObserver{
    // esta es la clase de implementación concreta de un observador que envia email
    @Override
    public void onSaleCompleted(Sale sale) {
        //Aqui podemos ingresar la lógica para enviar un email de notificación
    }
}
