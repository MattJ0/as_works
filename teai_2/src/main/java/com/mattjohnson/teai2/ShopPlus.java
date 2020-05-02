package com.mattjohnson.teai2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("Plus")
@ConfigurationProperties(prefix = "shop-plus")
public class ShopPlus implements Shop {

    private List<Product> basket;
    private double tax;

    public ShopPlus() {
        this.basket = new ArrayList<>();
    }

    @Override
    public void addProduct(String name, double price) {
        Product product = new Product(name, price);
        basket.add(product);
    }

    @Override
    public void checkoutBasket() {
        System.out.println("Shop Plus (price + tax " + df2.format(tax) +"%)");
        for (Product product:basket) {
            System.out.println(product.getName() + " " + df2.format(product.getPrice() + calculateTax(product)) +
                    " (base price: " + df2.format(product.getPrice()) +
                    ", tax amount: " + df2.format(calculateTax(product)) + ")");
        }
    }

    private double calculateTax(Product product) {
        return product.getPrice()*(tax/100);
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}

