package Observers;

import Comparators.StudentWithScholarshipComparator;
import Observable.Person;
import Observable.Student;
import Observable.Subject;
import Tools.Course;
import Workers.Strategy.SalaryStrategy;
import Workers.UniversityAcademicWorker;
import Workers.UniversityWorker;
import Workers.Worker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class University implements Observer, Serializable {
    private static final long serialVersionUID = 1L;
    ArrayList<Person> university;
    HashMap<Course,Integer> courses;
    ArrayList<Student> studentsWithScholarship;
    int amountOfStudents;

    public University() {
        this.university = new ArrayList<>();
        this.courses = new HashMap<>();
        this.studentsWithScholarship = new ArrayList<>();
    }

    public void addPerson(Person person){
        university.add(person);
    }

    public void removePerson(Person person){
        university.remove(person);
    }

    public ArrayList<Person> getUniversity() {
        return university;
    }

    public void setUniversity(ArrayList<Person> university) {
        this.university = university;
    }

    public HashMap<Course,Integer> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.put(course,0);
    }

    public String[] arrayOfCourses(){
        String[] arrayCourses = new String[courses.size()];
        int i = 0;
        for(Course course: courses.keySet()){
            arrayCourses[i++] = course.getName();
        }
        return arrayCourses;
    }

    public boolean containsCourse(String name){
        System.out.println("_");
        System.out.println(name);
        System.out.println("_");
        for(Course course: courses.keySet()){
            String courseName = course.getName();
            System.out.println(courseName);
            if(courseName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public int ectsOfCourse(String name){
        for(Course course: courses.keySet()){
            String courseName = course.getName();
            if(courseName.equals(name)){
                return course.getEcts();
            }
        }
        return 0;
    }

    public Student findStudentByIndex(String index){
        for(Student student: getAllStudents()){
            String num = student.getIndexNumber();
            if(num.equals(index)) return student;
        }
        return null;

    }

    public boolean containsStudentWithIndexNum(String indexNum){
        for(Student student: getAllStudents()){
            String num = student.getIndexNumber();
            if(num.equals(indexNum)) return true;
        }
        return false;
    }


    public String[][] getAllPeople(){

        String[][] people = new String[university.size()][3];

        for(int i = 0; i < university.size();i++){
            Person person = university.get(i);
            String type = "";
            if(person instanceof Student){
                type = "Student";
            }
            if(person instanceof UniversityWorker){
                type = "University Worker";
            }
            if(person instanceof UniversityAcademicWorker){
                type = "Academic Worker";
            }
            people[i] = new String[3];
            people[i][0] = person.getName();
            people[i][1] = person.getSurname();
            people[i][2] = type;
        }
        return people;
    }

    public String[][] getAllStudentsArray(boolean scolarship){
        ArrayList<Student> st;
        if(scolarship == true){
            st = new ArrayList<>(new HashSet<>(studentsWithScholarship));
            Collections.sort(st,new StudentWithScholarshipComparator());
        }
        else {
            st = getAllStudents();
        }

        String[][] students = new String[st.size()][];
        for(int i = 0; i < st.size();i++){
            Student student = st.get(i);
            String name = student.getName();
            String surname = student.getSurname();
            String ectsSum = String.valueOf(student.getEctsSum());
            String avgGrade = String.format("%.2f",student.averageGrade());
            String indexNum = student.getIndexNumber();
            String studyYear = String.valueOf(student.getYearOfStudy());
            students[i] = new String[7];
            students[i][0] = name;
            students[i][1] = surname;
            students[i][2] = ectsSum;
            students[i][3] = avgGrade;
            students[i][4] = indexNum;
            students[i][5] = studyYear;
            students[i][6] = String.valueOf(student.getNumberOfFailedCourses());
        }
        return students;
    }

    public String[][] getAllWorkersArray(SalaryStrategy salaryStrategyWorker, SalaryStrategy salaryStrategyAcademic){
        ArrayList<Worker> w = getAllWorkers();
        String[][] workers = new String[w.size()][];

        for(int i =0; i < w.size();i++){
            Worker worker = w.get(i);
            String name = worker.getName();
            String surname = worker.getSurname();
            String position = worker.getPosition();
            String amountOfHours = String.valueOf(worker.getAmountOfHours());
            String overtimeHours = String.valueOf(worker.getOvertimeHours());
            String hourlyRate = String.valueOf(worker.getHourlyRate());

            String amountOfPublication = "-";
            String amountOfCourses = "-";
            String type = "";
            String salary = "";

            if(worker instanceof UniversityAcademicWorker){
                type = "Academic Worker";
                UniversityAcademicWorker ww = (UniversityAcademicWorker) worker;
                ww.setSalaryStrategy(salaryStrategyAcademic);

                amountOfPublication = String.valueOf(ww.getAmountOfPublications());
                amountOfCourses = String.valueOf(ww.getAmountOfCourses());
                salary = String.format("%.2f",ww.getSalary());
            }

            String knowsForeignLanguage = "-";
            String workingRegime = "-";

            if(worker instanceof UniversityWorker){
                type = "University Worker";
                UniversityWorker ww = (UniversityWorker) worker;
                ww.setSalaryStrategy(salaryStrategyWorker);

                if(ww.isKnowsForeignLanguage()){
                    knowsForeignLanguage = "yes";
                }else {
                    knowsForeignLanguage = "no";
                }
                workingRegime = ww.getWorkingRegime();
                salary = String.format("%.2f",ww.getSalary());
            }

            String[] data = new String[12];
            data[0] = name;
            data[1] = surname;
            data[2] = position;
            data[3] = amountOfHours;
            data[4] = overtimeHours;
            data[5] = hourlyRate;
            data[6] = amountOfPublication;
            data[7] = amountOfCourses;
            data[8] = knowsForeignLanguage;
            data[9] = workingRegime;
            data[10] = salary;
            data[11] = type;
            workers[i] = data;
        }

        return workers;

    }

    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> students = new ArrayList<>();
        for(Person person:university){
            if(person instanceof Student){
                Student student = (Student) person;
                students.add(student);
            }
        }
        return students;
    }

    public ArrayList<Worker> getAllWorkers(){
        ArrayList<Worker> workers = new ArrayList<>();
        for(Person person: university){
            if(person instanceof Worker){
                Worker worker = (Worker) person;
                workers.add(worker);
            }
        }
        return workers;
    }

    public void printAllStudents(){
        System.out.println("________________________________________________________________________________");
        int posNum = 1;
        System.out.println("All students:");
        ArrayList<Student> students = getAllStudents();
        for(Student student: students){
            System.out.println(posNum++ + " " + student);
        }
        System.out.println("________________________________________________________________________________");
    }

    public void findStudentByIndexNumber(String indexNumber){
        ArrayList<Student> students = getAllStudents();
        for(Student student:students){
            if(indexNumber.equals(student.getIndexNumber())){
                System.out.println(student);
            }
        }
    }

    public void updateCoursesCounter(){
        for(Course course:courses.keySet()){
            courses.put(course,0);
        }
    }

    public String showStats(){
        String output = "";
        output += "________________________________________________________________________________\n";
        output += "Numbers of students on each course:\n";

        HashMap<String,Integer> css = new HashMap<>();

        ArrayList<Student> students = getAllStudents();
        for(Student student:students){
            for(Course course:student.getCourses().keySet()){
                if(css.containsKey(course.getName())){
                    css.put(course.getName(), css.get(course.getName()) + 1);
                }else{
                    css.put(course.getName(),1);
                }
            }
        }

        for(Course course:courses.keySet()){
            if(!css.containsKey(course.getName())){
                css.put(course.getName(),0);
            }
        }

        for(String course:css.keySet()){
            output += (course + ":" + css.get(course) + "\n");
        }

        output += "________________________________________________________________________________\n";
        output += ("Total workers:" + getAllWorkers().size() + "\n");
        output += ("Total students:" + getAllStudents().size() + "\n");
        return output;
    }


    @Override
    public void update(Subject subject) {
        Student student = (Student) subject;
        if(student.getNumberOfFailedCourses() > 2) {
            System.out.println(student + " is removed from the students list(3 failed courses)");
            university.remove(student);
        }

        if(student.averageGrade() > 5.0){
            studentsWithScholarship.add(student);
        }else{
            if (studentsWithScholarship.contains(student)) {
                studentsWithScholarship.remove(student);
            }
        }

        System.out.println(showStats());
    }
}
