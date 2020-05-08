package com.mattjohnson.teai2.controller;

import com.mattjohnson.teai2.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Component
@Profile("Plus")
@ConfigurationProperties(prefix = "plus")
class ShopPlus extends ShopStart {

    private Logger logger = LoggerFactory.getLogger(ShopPlus.class);


    private BigDecimal tax;

    @Autowired
    public ShopPlus(ProductService productService) {
        super(productService);
    }

    @Override
    public void checkoutCart() {
        logger.info("ShopPlus");
        logger.info("Total net price: " + getTotalPrice().toString());
        logger.info("Total gross price (VAT=" + tax + "%): " + getTotalGrossPrice().toString());
    }


    BigDecimal getTotalGrossPrice(){
        BigDecimal totalGross = getTotalPrice().multiply(tax.divide(new BigDecimal(100)).add(new BigDecimal(1)));
        return totalGross.setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}

