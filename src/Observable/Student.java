package Observable;

import Observers.Observer;
import Observers.University;
import Tools.Course;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Student extends Person implements Subject{
    private ArrayList<Observer> observerList;
    private HashMap<Course,Double> courses;
    private String indexNumber;
    private int ectsSum;
    private int numberOfFailedCourses;
    private int yearOfStudy;
    private boolean scholarship;
    private University university;


    public Student(String name, String surname, String indexNumber,int yearOfStudy) {
        super(name, surname);
        this.indexNumber = indexNumber;
        this.yearOfStudy = yearOfStudy;
        this.scholarship = false;

        observerList = new ArrayList<>();

        courses = new HashMap<>();
        ectsSum = 0;
        numberOfFailedCourses = 0;
    }

    //   3 Failed courses ====> remove from university
    public void addCourse(Course course, double grade){
        if(university.containsCourse(course.getName())){
            courses.put(course, grade);
            if(grade < 3) numberOfFailedCourses++;
            else ectsSum += course.getEcts();

            notifyObservers();
        }else{
            System.out.println("There is no \"" + course.getName() + "\" course at the university");
        }
    }

    public void setUniversity(University university) {
        this.university = university;
        registerObserver(university);
    }

    public boolean isScholarship() {
        return scholarship;
    }

    public void setScholarship(boolean scholarship) {
        this.scholarship = scholarship;
    }

    public double averageGrade(){
        double sum = 0;
        for(double grade: courses.values()) sum += grade;
        return courses.size() == 0? 0: sum / (double) courses.size();
    }

    public HashMap<Course, Double> getCourses() {
        return courses;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public int getEctsSum() {
        return ectsSum;
    }

    public int getNumberOfFailedCourses() {
        return numberOfFailedCourses;
    }

    public University getUniversity() {
        return university;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer: observerList){
            observer.update(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if(student.getIndexNumber().equals(getIndexNumber())) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int result = observerList != null ? observerList.hashCode() : 0;
        result = 31 * result + (courses != null ? courses.hashCode() : 0);
        result = 31 * result + (indexNumber != null ? indexNumber.hashCode() : 0);
        result = 31 * result + ectsSum;
        result = 31 * result + numberOfFailedCourses;
        result = 31 * result + yearOfStudy;
        result = 31 * result + (scholarship ? 1 : 0);
        result = 31 * result + (university != null ? university.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + indexNumber;
    }
}
