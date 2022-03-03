package Observers;

import Observable.Subject;

import java.util.Observable;

public interface Observer {
    public void update(Subject subject);
}
