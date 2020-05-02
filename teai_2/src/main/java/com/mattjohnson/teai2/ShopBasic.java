package com.mattjohnson.teai2;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("Basic")
public class ShopBasic implements Shop {

    private List<Product> basket;

    public ShopBasic() {
        this.basket = new ArrayList<>();
    }

    @Override
    public void addProduct(String name, double price) {
        Product product = new Product(name, price);
        basket.add(product);
    }

    @Override
    public void checkoutBasket() {
        System.out.println("Shop");
        for (Product product : basket) {
            System.out.println(product.getName() + " " + df2.format(product.getPrice()));
        }
    }
}
