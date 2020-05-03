package com.mattjohnson.teai2.shop;


import com.mattjohnson.teai2.product.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("Basic")
class ShopBasic implements Shop {

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
