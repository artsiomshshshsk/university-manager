package com.artsiom.workers.Strategy;


import com.artsiom.workers.UniversityAcademicWorker;
import com.artsiom.workers.Worker;

import java.io.Serializable;

public class SalaryAcademicWorkerAlgorithm1 implements SalaryStrategy,Serializable {
    @Override
    public double countSalary(Worker worker) {
        UniversityAcademicWorker w = (UniversityAcademicWorker) worker;
        int amountOfHours = w.getAmountOfHours();
        double hourlyRate = w.getHourlyRate();
        int overtimeHours = w.getOvertimeHours();
        int amountOfPublications = w.getAmountOfPublications();
        int amountOfCourses = w.getAmountOfCourses();


        double total = 0;
        total += amountOfHours * hourlyRate + overtimeHours * 0.5;
        total += amountOfPublications * 50;
        total += amountOfCourses * 100;
        return total;

    }
}
