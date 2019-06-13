package com.s3.observer_patern;

public interface Observable {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifyAllObservers();
}
