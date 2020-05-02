package com.mattjohnson.teai2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShopServiceMain {

    private final double MIN_PRICE = 50.00;
    private final double MAX_PRICE = 300.00;
    private static final Random random = new Random();


    private final Shop shop;

    @Autowired
    public ShopServiceMain(Shop shop) {
        this.shop = shop;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        addProducts(shop);
        shop.checkoutBasket();
    }

    private void addProducts(Shop shop) {
        for(int i = 1; i < 6; i++) {            //number from TASK_DESCRIPTION.txt
            String name = "Product#" + i;
            double price = generatePrice();
            shop.addProduct(name, price);
        }
    }

    private double generatePrice() {
        return random.nextDouble() * (MAX_PRICE - MIN_PRICE) + MIN_PRICE;
    }
}
