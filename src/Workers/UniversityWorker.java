package Workers;

import Workers.Strategy.SalaryStrategy;
import Workers.Strategy.SalaryUniversityWorkerAlgorithm1;

public class UniversityWorker extends Worker{
    boolean knowsForeignLanguage;
    String workingRegime;


    public UniversityWorker(String name, String surname, String position, int amountOfHours, int overtimeHours, double hourlyRate, boolean knowsForeignLanguage, String workingRegime) {
        super(name, surname, position, amountOfHours, overtimeHours, hourlyRate);
        setSalaryStrategy(new SalaryUniversityWorkerAlgorithm1());
        this.knowsForeignLanguage = knowsForeignLanguage;

        this.workingRegime = workingRegime;


    }

    public void setSalaryStrategy(SalaryStrategy salaryStrategy) {
        this.salaryStrategy = salaryStrategy;
    }

    @Override
    public double getSalary() {
        return salaryStrategy.countSalary(this);
    }


    public boolean isKnowsForeignLanguage() {
        return knowsForeignLanguage;
    }

    public void setKnowsForeignLanguage(boolean knowsForeignLanguage) {
        this.knowsForeignLanguage = knowsForeignLanguage;
    }

    public String getWorkingRegime() {
        return workingRegime;
    }

    public void setWorkingRegime(String workingRegime) {
        this.workingRegime = workingRegime;
    }

    public SalaryStrategy getSalaryStrategy() {
        return salaryStrategy;
    }
}
