package com.artsiom.observers;


import com.artsiom.observable.Subject;

public interface Observer {
    public void update(Subject subject);
}
