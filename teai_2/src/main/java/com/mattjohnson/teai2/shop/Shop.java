package com.mattjohnson.teai2.shop;
import java.text.DecimalFormat;

public interface Shop {

    DecimalFormat df2 = new DecimalFormat("#.##");

    void addProduct(String name, double price);

    void checkoutBasket();


}
