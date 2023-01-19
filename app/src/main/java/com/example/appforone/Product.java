package com.example.appforone;

public final class Product {

    private final double price;
    private final double volume;

    public Product(double price, double volume) {
        this.price = price;
        this.volume = volume;
    }

    public static double different(Product productOne, Product productTwo) {
        return productOne.priceFor1Kg() - productTwo.priceFor1Kg();
    }

    public double priceFor1Gram() {
        return price / volume;
    }

    public double priceFor1Kg() {
        return round(priceFor1Gram() * 1000, 2);
    }

    public static double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String whatBigger(Product one, Product two) {
        String result;
        if (one.priceFor1Kg() > two.priceFor1Kg()) {
            result = "№2 дешевле на " + (round(one.priceFor1Kg() - two.priceFor1Kg(), 2)) + " руб за 1 ";
        } else if (two.priceFor1Kg() > one.priceFor1Kg()) {
            result = "№1 дешевле на " + (round(two.priceFor1Kg() - one.priceFor1Kg(), 2)) + " руб за 1 ";
        } else {
            result = "Цена товаров одинаоква";
        }
        return result;
    }
}
