package com.artsiom.tools;

import java.io.Serializable;

public class Course implements Serializable {
    private static final long serialVersionUID = 2L;
    private String name;
    private int ects;

    public Course(String name, int ects) {
        this.name = name;
        this.ects = ects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", ects=" + ects +
                '}';
    }
}
