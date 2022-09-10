package com.artsiom.workers;


import com.artsiom.workers.Strategy.SalaryAcademicWorkerAlgorithm1;
import com.artsiom.workers.Strategy.SalaryStrategy;

public class UniversityAcademicWorker extends Worker{
    int amountOfPublications;
    int amountOfCourses;

    public UniversityAcademicWorker(String name, String surname, String position, int amountOfHours, int overtimeHours, double hourlyRate, int amountOfPublications, int amountOfCourses) {
        super(name, surname, position, amountOfHours, overtimeHours, hourlyRate);
        setSalaryStrategy(new SalaryAcademicWorkerAlgorithm1());
        this.amountOfPublications = amountOfPublications;
        this.amountOfCourses = amountOfCourses;
    }

    @Override
    public double getSalary() {
        return salaryStrategy.countSalary(this);
    }


    public int getAmountOfPublications() {
        return amountOfPublications;
    }

    public void setAmountOfPublications(int amountOfPublications) {
        this.amountOfPublications = amountOfPublications;
    }

    public int getAmountOfCourses() {
        return amountOfCourses;
    }

    public void setAmountOfCourses(int amountOfCourses) {
        this.amountOfCourses = amountOfCourses;
    }

    public SalaryStrategy getSalaryStrategy() {
        return salaryStrategy;
    }

    public void setSalaryStrategy(SalaryStrategy salaryStrategy) {
        this.salaryStrategy = salaryStrategy;
    }
}
