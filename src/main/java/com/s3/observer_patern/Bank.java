package com.s3.observer_patern;

public class Bank implements Observer {

    public Bank() {
    }

    @Override
    public void update(Object object) {
        // Get the available stock from Amazon & buy random item if it's still available
        if (object instanceof Customer) {
            Customer customer = (Customer) object;

            if (customer.getBalance() < 0 && !customer.isBankrupt()) {
                // Fine customer and force him/her/it to unsubscribe from Amazon and sell back enough items to get out of the red
                System.out.println(String.format("%s went on a spending spree and got punished!", customer.getName()));
                customer.forceSell();
            }
        }
    }
}
