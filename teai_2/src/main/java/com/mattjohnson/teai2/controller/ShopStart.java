package com.mattjohnson.teai2.controller;


import com.mattjohnson.teai2.model.Product;
import com.mattjohnson.teai2.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Component
@Profile("Start")
class ShopStart {

    private Logger logger = LoggerFactory.getLogger(ShopStart.class);

    private ProductService productService;
    private List<Product> cart = new ArrayList<>();

    @Autowired
    public ShopStart(ProductService productService) {
        this.productService = productService;
    }



    @EventListener(ApplicationReadyEvent.class)
    public void runShop() {
        addGeneratedProducts(productService.generateRandomProducts());
        checkoutCart();
    }
    public void checkoutCart() {
        logger.info("ShopStart");
        logger.info("Total price: " + getTotalPrice().toString());
    }

    public void addGeneratedProducts(List<Product> products) {
        cart.addAll(products);
        logger.info("Generated products have been added to the cart");
    }

    public void addProduct(String name, BigDecimal price) {
        cart.add(new Product(name, price));
    }

    public BigDecimal getTotalPrice() {
        BigDecimal result = cart.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return result.setScale(2, RoundingMode.HALF_UP);
    }


}
