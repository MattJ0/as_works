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
@Profile("Pro")
@ConfigurationProperties(prefix = "pro")
class ShopPro extends ShopPlus{


    private Logger logger = LoggerFactory.getLogger(ShopPro.class);

    private BigDecimal tax;
    private BigDecimal discount;

    @Autowired
    public ShopPro(ProductService productService) {
        super(productService);
    }


    @Override
    public void checkoutCart() {
        logger.info("ShopPro");
        logger.info("Total net price: " + getTotalPrice().toString());
        logger.info("Total gross price (VAT=" + tax + "%, discount=" + discount + "%): " + getTotalGrossPrice().toString());
        logger.info("Total gross price with discount(Discount=" + discount + "%): " + getTotalGrossPriceWithDiscount().toString());

    }


    private BigDecimal getTotalGrossPriceWithDiscount() {
        return  getTotalGrossPrice().subtract(getTotalGrossPrice().multiply(discount.divide(new BigDecimal(100)))).setScale(2, RoundingMode.HALF_UP);
    }

    BigDecimal getTotalGrossPrice() {
        BigDecimal totalGross = getTotalPrice().multiply(tax.divide(new BigDecimal(100)).add(new BigDecimal(1)));
        return totalGross.setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public BigDecimal getTax() {
        return tax;
    }

    @Override
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
