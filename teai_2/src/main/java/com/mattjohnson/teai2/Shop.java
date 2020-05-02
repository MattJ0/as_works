package com.mattjohnson.teai2;
import java.text.DecimalFormat;

public interface Shop {

    public static DecimalFormat df2 = new DecimalFormat("#.##");

    public void addProduct(String name, double price);

    public void checkoutBasket();


}
