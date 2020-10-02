package model;

import java.util.ArrayList;

public class Observable {

    private ArrayList<Observer> observers = new ArrayList<Observer>();

    public void notifyObservers(UpdateType type){
        for (Observer obs :
                observers) {
            obs.update(type);

        }
    }

    public void addObserver(Observer obs){
        observers.add(obs);
    }

}
