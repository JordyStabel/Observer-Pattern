package com.s3.observer_patern;

import java.util.ArrayList;
import java.util.Random;

public class Customer implements Observer, Observable {

    private String name;
    private Amazon amazon;
    private boolean bankrupt = false;

    // Probably less than ideal to make this public
    public float getBalance() {
        return balance;
    }

    private void changeBalance(float balance) {
        this.balance += balance;
        System.out.println(String.format("%ss balance: €%.2f change: €%.2f", name, this.balance, balance));

        // Notify the bank that the balance has changed
        notifyAllObservers();
    }

    private float balance;
    private ArrayList<Item> shoppingCart;

    // Only the bank
    private ArrayList<Observer> observers;

    private static Random random = new Random();

    public Customer(String name, float balance, Amazon amazon) {
        this.name = name;
        this.balance = balance;
        this.shoppingCart = new ArrayList<>();
        this.amazon = amazon;
        observers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void forceSell() {
        amazon.unsubscribe(this);

        float amazonCredits = 0f;

        // Sell items till balance is higher than 0
        while (balance + amazonCredits < 0 && !shoppingCart.isEmpty()) {
            amazonCredits += amazon.buyFromCustomer(shoppingCart.get(0));
            shoppingCart.remove(0);
        }

        if (balance + amazonCredits < 0) {
            bankrupt = true;
        }
        changeBalance(amazonCredits);
    }

    @Override
    public void update(Object object) {
        // Get the available stock from Amazon & buy random item if it's still available
        if (object instanceof Amazon) {


            //Amazon amazon = (Amazon) object;
            ArrayList<Item> stock = amazon.getAvailableItems();

            if (stock.isEmpty() || isBankrupt()) {
                return;
            }

            Item item = stock.get(random.nextInt(stock.size()));

            // Customer wil buy an 'impulse buy' item as long as he/she/it isn't bankrupt
            amazon.sellToCustomer(item);
            shoppingCart.add(item);
            System.out.println(String.format("%s bought: %s", name, item.getItemName()));
            changeBalance(-item.getPrice());
        }
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public String toString() {
        return name +
                ", bankrupt= " + bankrupt +
                ", balance= €" + balance +
                ", shoppingCart= " + shoppingCart;
    }
}
