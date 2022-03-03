package Workers;

import Observable.Person;
import Workers.Strategy.SalaryStrategy;

import java.io.Serializable;

public abstract class Worker extends Person{
    String position;
    int amountOfHours;
    int overtimeHours;
    double hourlyRate;
    SalaryStrategy salaryStrategy;



    public Worker(String name, String surname,String position,int amountOfHours,int overtimeHours,double hourlyRate) {
        super(name, surname);
        this.position = position;
        this.amountOfHours = amountOfHours;
        this.hourlyRate = hourlyRate;
        this.overtimeHours = overtimeHours;
    }

    public abstract double getSalary();

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAmountOfHours() {
        return amountOfHours;
    }

    public void setAmountOfHours(int amountOfHours) {
        this.amountOfHours = amountOfHours;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
