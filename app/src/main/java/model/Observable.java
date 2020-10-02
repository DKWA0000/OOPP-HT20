package model;

import java.util.ArrayList;

public class Observable {

    private ArrayList<Observer> observers = new ArrayList<Observer>();

    public void notifyObservers(){
        for (Observer obs :
                observers) {
            obs.update();

        }
    }

    public void addObserver(Observer obs){
        observers.add(obs);
    }

}
