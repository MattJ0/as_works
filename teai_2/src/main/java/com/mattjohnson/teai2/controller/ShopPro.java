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
@Profile("Pro")
@ConfigurationProperties(prefix = "pro")
class ShopPro{


    private Logger logger = LoggerFactory.getLogger(ShopStart.class);

    private ProductService productService;
    private List<Product> cart;

    private BigDecimal tax;
    private BigDecimal discount;

    @Autowired
    public ShopPro(ProductService productService) {
        this.productService = productService;
        this.cart = new ArrayList<>();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runShop() {
        addGeneratedProducts();
        checkoutCart();
    }

    public void checkoutCart() {
        logger.info("ShopPro");
        logger.info("Cart contain: " + cart.toString());
        logger.info("Total net price: " + getTotalPrice().toString());
        logger.info("Total gross price (VAT=" + tax + "%): " + getTotalGrossPrice().toString());
        logger.info("Total gross price with discount(Discount=" + discount + "%): " + getTotalGrossPriceWithDiscount().toString());

    }

    public void addGeneratedProducts() {
        cart.addAll(productService.generateRandomProducts());
        logger.info("Generated products have been added to the cart");
    }

    public BigDecimal getTotalPrice() {
        BigDecimal total = cart.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.setScale(2, RoundingMode.HALF_UP);
    }


    private BigDecimal getTotalGrossPrice(){
        BigDecimal totalGross = getTotalPrice().multiply(tax.divide(new BigDecimal(100)).add(new BigDecimal(1)));
        return totalGross.setScale(2, RoundingMode.HALF_UP);
    }


    private BigDecimal getTotalGrossPriceWithDiscount() {
        return  getTotalGrossPrice().subtract(getTotalGrossPrice().multiply(discount.divide(new BigDecimal(100)))).setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
