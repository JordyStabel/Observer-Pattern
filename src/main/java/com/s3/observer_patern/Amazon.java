package com.s3.observer_patern;

import java.util.ArrayList;

public class Amazon implements Observable {

    private static Amazon amazonSingleton;

    public static synchronized Amazon getInstance() {
        if (amazonSingleton == null) {
            amazonSingleton = new Amazon(new ArrayList<>());
        }
        return amazonSingleton;
    }

    private ArrayList<Item> availableItems;
    private ArrayList<Observer> observers;
    private float balance = 1000;

    public Amazon(ArrayList<Item> availableItems) {
        this.availableItems = availableItems;
        this.observers = new ArrayList<>();
    }

    public float getBalance() {
        return balance;
    }

    public void addItemToInventory(Item item) {
        availableItems.add(item);
        notifyAllObservers();
    }

    public ArrayList<Item> getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(ArrayList<Item> availableItems) {
        this.availableItems = availableItems;
    }

    public void sellToCustomer(Item item) {
        balance += item.getPrice();
        availableItems.remove(item);
    }

    public float buyFromCustomer(Item item) {
        float itemValue = item.getPrice() * .75f;
        balance -= itemValue;
        addItemToInventory(item);
        return itemValue;
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
        try {
            for (Observer observer : observers) {
                observer.update(this);
            }
        } catch(Exception e) {

        }
    }
}
