package com.mattjohnson.teai2.controller;

import com.mattjohnson.teai2.model.Product;
import com.mattjohnson.teai2.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Component
@Profile("Plus")
@ConfigurationProperties(prefix = "plus")
class ShopPlus {

    private Logger logger = LoggerFactory.getLogger(ShopStart.class);

    private ProductService productService;
    private List<Product> cart;

    private BigDecimal tax;

    @Autowired
    public ShopPlus(ProductService productService) {
        this.productService = productService;
        this.cart = new ArrayList<>();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runShop() {
        addGeneratedProducts(productService.generateRandomProducts());
        checkoutCart();
    }

    public void checkoutCart() {
        logger.info("ShopPlus");
        logger.info("Cart contain: " + cart.toString());
        logger.info("Total net price: " + totalPrice().toString());
        logger.info("Total gross price (VAT=" + tax + "%): " + getTotalGrossPrice().toString());
    }

    public BigDecimal totalPrice() {
        BigDecimal total = cart.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getTotalGrossPrice(){
        BigDecimal totalGross = totalPrice().multiply(tax.divide(new BigDecimal(100)).add(new BigDecimal(1)));
        return totalGross.setScale(2, RoundingMode.HALF_UP);
    }

    public void addGeneratedProducts(List<Product> products) {
        cart.addAll(products);
        logger.info("Generated products have been added to the cart");
    }


    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}

