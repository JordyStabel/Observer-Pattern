package com.s3.observer_patern;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Item> amazonItemStock = new ArrayList<>();
        amazonItemStock.add(new Item("Razer Blackwidow", 150));
        amazonItemStock.add(new Item("Snickers bar", 1.75f));
        amazonItemStock.add(new Item("Football", 10));
        amazonItemStock.add(new Item("Headphone", 75));
        amazonItemStock.add(new Item("Alexa home speaker", 65));
        amazonItemStock.add(new Item("Kindle fire", 99));

        Amazon amazon = Amazon.getInstance();
        amazon.setAvailableItems(amazonItemStock);
        Bank bank = new Bank();

        Customer henk = new Customer("Henk", 100f, amazon);
        Customer truus = new Customer("Truus", 50f, amazon);
        Customer jeff = new Customer("Jeff Bezos", 10000f, amazon);

        amazon.subscribe(henk);
        amazon.subscribe(truus);
        amazon.subscribe(jeff);

        henk.subscribe(bank);
        truus.subscribe(bank);
        jeff.subscribe(bank);

        amazon.addItemToInventory(new Item("new iPhone", 1299f));

        Customer john = new Customer("John Doe", 89f, amazon);

        amazon.subscribe(john);
        john.subscribe(bank);

        amazon.addItemToInventory(new Item("Even newer iPhone", 1499f));

        System.out.println(String.format("Balance: €%.2f, items left: ", amazon.getBalance()));

        for (Item item : amazonItemStock) {
            System.out.println("        " + item.getItemName());
        }

        System.out.println(henk.toString());
        System.out.println(truus.toString());
        System.out.println(jeff.toString());
        System.out.println(john.toString());
    }
}
