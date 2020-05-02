package com.mattjohnson.teai2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("Pro")
@ConfigurationProperties(prefix = "pro")
public class ShopPro implements Shop {

    private List<Product> basket;
    private double tax;
    private double discount;

    public ShopPro() {
        this.basket = new ArrayList<>();
    }


    @Override
    public void addProduct(String name, double price) {
        Product product = new Product(name, price);
        basket.add(product);
    }

    @Override
    public void checkoutBasket() {
        System.out.println("Shop Plus (price + tax " + df2.format(tax) +"% - discount " + df2.format(discount) +"%)");
        for (Product product:basket) {
            System.out.println(product.getName() + " " + df2.format(product.getPrice() + calculateTax(product) - calculateDiscount(product)) +
                    " (base price: " + df2.format(product.getPrice()) +
                    ", tax amount: " + df2.format(calculateTax(product)) +
                    ", discount amount: "+ df2.format(calculateDiscount(product)) + ")");
        }
    }

    private double calculateTax(Product product) {
        return product.getPrice()*(tax/100);
    }

    private double calculateDiscount(Product product) {
        return product.getPrice()*(discount/100);
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
