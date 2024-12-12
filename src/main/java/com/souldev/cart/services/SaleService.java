package com.souldev.cart.services;

import com.souldev.cart.entities.Detail;
import com.souldev.cart.entities.Sale;
import com.souldev.cart.entities.ShoppingCart;
import com.souldev.cart.repositories.SaleRepository;
import com.souldev.cart.security.entities.User;
import com.souldev.cart.security.services.UserService;
import com.souldev.cart.services.observers.SaleObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class SaleService {

    private final SaleRepository saleRepository;
    private final List<SaleObserver> observers = new ArrayList<>();
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final DetailService detailService;
    @Autowired
    public SaleService(SaleRepository saleRepository, UserService userService, ShoppingCartService shoppingCartService,
                       DetailService detailService) {
        this.saleRepository = saleRepository;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.detailService = detailService;
    }

    //metodo para agregar un obervador
    public void addObserver(SaleObserver observer) {
        observers.add(observer);
    }

    //metodo para remover un observador
    public void removeObserver(SaleObserver observer){
        observers.remove(observer);
    }

    public List<Sale> getSalesByClient(String userName){
        return this.saleRepository.findByClient_UserName(userName);
    }

    //metodo que crea una venta y notifica al observador
    public void createSale(String userName){
        User client = this.userService.getByUserName(userName).get();
        List<ShoppingCart> shoppingCartList = this.shoppingCartService.getListByClient(client.getUserName());
        DecimalFormat decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        double total = shoppingCartList.stream().mapToDouble(shoppingCartItem -> shoppingCartItem.getProduct().getPrice()
                * shoppingCartItem.getAmount()).sum();
        Sale sale = new Sale(Double.parseDouble(decimalFormat.format(total)), new Date(), client);
        Sale saveSale = this.saleRepository.save(sale);
        for (ShoppingCart shoppingCart : shoppingCartList) {
            Detail detail = new Detail();
            detail.setProduct(shoppingCart.getProduct());
            detail.setAmount(shoppingCart.getAmount());
            detail.setSale(saveSale);
            this.detailService.createDetail(detail);
        }
        this.shoppingCartService.cleanShoppingCart(client.getId());

        saleRepository.save(sale);
        notifyObservers(sale);//notifica a los observador una venta exitosa

    }

    //metood que notifica a todos los observadores sobre una venta completada
    private void notifyObservers(Sale sale){
        for (SaleObserver observer : observers){
            observer.onSaleCompleted(sale);
        }
    }
}
