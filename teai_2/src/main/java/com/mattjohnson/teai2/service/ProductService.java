package com.mattjohnson.teai2.service;

import com.mattjohnson.teai2.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@ConfigurationProperties(prefix = "ps")
public class ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    private Random random = new Random();

    private int quantity;
    private double MIN_PRICE;
    private double MAX_PRICE;


    public List<Product> generateRandomProducts() {
        List<Product> randomProducts = new ArrayList<>();
        for(int i = 1; i < quantity +1; i++) {
            String name = "Product#" + i;
            BigDecimal price = generatePrice();
            Product product = new Product(name, price);
            randomProducts.add(product);
            logger.info("Generated new" + product.toString());
        }
        return randomProducts;
    }


    private BigDecimal generatePrice() {
        return BigDecimal.valueOf(random.nextDouble() * (MAX_PRICE - MIN_PRICE) + MIN_PRICE).setScale(2, RoundingMode.HALF_UP);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getMIN_PRICE() {
        return MIN_PRICE;
    }

    public void setMIN_PRICE(double MIN_PRICE) {
        this.MIN_PRICE = MIN_PRICE;
    }

    public double getMAX_PRICE() {
        return MAX_PRICE;
    }

    public void setMAX_PRICE(double MAX_PRICE) {
        this.MAX_PRICE = MAX_PRICE;
    }
}
