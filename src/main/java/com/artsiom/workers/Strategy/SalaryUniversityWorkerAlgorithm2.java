package com.artsiom.workers.Strategy;


import com.artsiom.workers.UniversityWorker;
import com.artsiom.workers.Worker;

import java.io.Serializable;

public class SalaryUniversityWorkerAlgorithm2 implements SalaryStrategy, Serializable {
    @Override
    public double countSalary(Worker worker) {
        UniversityWorker w = (UniversityWorker) worker;
        int amountOfHours = w.getAmountOfHours();
        double hourlyRate = w.getHourlyRate();
        int overtimeHours = w.getOvertimeHours();
        boolean knowsForeignLanguage = w.isKnowsForeignLanguage();
        String workingRegime = w.getWorkingRegime();

        double total = 0;
        total += amountOfHours * hourlyRate + overtimeHours * 0.3 * hourlyRate;
        if(knowsForeignLanguage) total *= 1.2;
        if(workingRegime.equals("nigh")) total *= 1.5;
        return total;
    }
}
