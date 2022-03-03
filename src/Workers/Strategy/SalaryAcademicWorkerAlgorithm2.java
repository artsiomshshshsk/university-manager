package Workers.Strategy;

import Workers.UniversityAcademicWorker;
import Workers.Worker;

import java.io.Serializable;

public class SalaryAcademicWorkerAlgorithm2 implements SalaryStrategy, Serializable {
    @Override
    public double countSalary(Worker worker) {

        UniversityAcademicWorker w = (UniversityAcademicWorker) worker;
        int amountOfHours = w.getAmountOfHours();
        double hourlyRate = w.getHourlyRate();
        int overtimeHours = w.getOvertimeHours();
        int amountOfPublications = w.getAmountOfPublications();
        int amountOfCourses = w.getAmountOfCourses();

        double total = 0;
        total += amountOfPublications * 40;
        total += amountOfCourses * 90;
        total += amountOfHours * hourlyRate + overtimeHours * 0.25;
        return total;
    }
}
