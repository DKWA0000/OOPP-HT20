package model;

import java.util.ArrayList;

/**
 * Class responsible for holding the observers and notifying them on a update.
 *
 * @Author Lucas Karlsson
 * @see UpdateType
 * @see Observer
 */

public abstract class Observable {

    private ArrayList<Observer> observers = new ArrayList<Observer>();

    /**
     * Method for notifying the observers given a type of update.
     *
     * @param type Type of update.
     * @see UpdateType
     * @see Observer
     */
    public void notifyObservers(UpdateType type) {
        for (Observer obs :
                observers) {
            obs.update(type);

        }
    }

    public void addObserver(Observer obs) {
        observers.add(obs);
    }

}
