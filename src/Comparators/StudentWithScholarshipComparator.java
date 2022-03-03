package Comparators;

import Observable.Student;

import java.util.Comparator;

public class StudentWithScholarshipComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        if(o1.getYearOfStudy() > o2.getYearOfStudy()){
            return 1;
        }
        if(o1.getYearOfStudy() < o2.getYearOfStudy()){
            return -1;
        }
        return 0;
    }
}
