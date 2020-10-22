package model;

import java.util.ArrayList;

/**
 * Class responsible for holding the observers and notifying them on a update.
 *
 * @Author Lucas Karlsson
 * @see UpdateType
 * @see Observer
 */

public abstract class Observable<T> {

    private ArrayList<Observer<T>> observers = new ArrayList<Observer<T>>();

    /**
     * Method for notifying the observers given a type of update.
     *
     * @param data Type of update.
     * @see UpdateType
     * @see Observer
     */
    public void notifyObservers(T data) {
        for (Observer obs :
                observers) {
            obs.notify(data);

        }
    }

    public void addObserver(Observer<T> obs) {

        if(observers.contains(obs))
            return;

        observers.add(obs);

    }

    public void removeObserver(Observer<T> obs){
        if(observers.contains(obs))
            observers.remove(obs);
    }
}
