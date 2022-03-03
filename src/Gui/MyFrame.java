package Gui;

import Observable.Student;
import Observers.University;
import Tools.Course;
import Workers.Strategy.*;
import Workers.UniversityAcademicWorker;
import Workers.UniversityWorker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


//        panel.add(...);
//        panel.revalidate();
//        panel.repaint(); // sometimes required

public class MyFrame extends JFrame {
    JButton addPerson;
    JButton addCourse;
    JButton showStats;
    JButton addStudentToCourse;
    JLabel showLabel;
    JRadioButton workerRadioButton;
    JRadioButton studentRadioButton;
    JRadioButton salaryAlgorithm1;
    JRadioButton salaryAlgorithm2;
    JButton showAll;
    JButton showStudents;
    JButton showWorkers;
    JCheckBox withScholarship;
    JTable table;
    University university;
    JScrollPane jsp;
    JOptionPane jOptionPane;
    JButton save;
    JLabel publicationLabel;
    JTextField publicationsField;
    JLabel coursesLabel;
    JTextField coursesField;
    JCheckBox knowsForeignLanguageCheckbox;
    JComboBox<String> jComboBox;
    JLabel regimeLabel;
    JButton submitWorkerButton;
    JRadioButton workerSelectRadioButton;
    JRadioButton academicWorkerRadioButton;

    public MyFrame(){
        university = getData();
        jOptionPane = new JOptionPane();
        setResizable(false);

    }

    private void msgbox(String s){
        jOptionPane.showMessageDialog(null, s);
    }

    public void showAllTable(){
        try {
            getContentPane().remove(jsp);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        getContentPane().invalidate();

        String[][] data = university.getAllPeople();
        String[] columnNames = { "Name", "Surname", "Type" };
        table = new JTable(data, columnNames);
        jsp = new JScrollPane(table);
        jsp.setBounds(10, 120, 730, 500);
        add(jsp);
//        getContentPane().validate();
        getContentPane().repaint();
    }


    public void showStudentsTable(){
//        getContentPane().removeAll();
        try {
            getContentPane().remove(jsp);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        getContentPane().invalidate();


        boolean scholarship;
        if(withScholarship.isSelected()){
            scholarship = true;
        }else {
            scholarship = false;
        }

        String[][] data = university.getAllStudentsArray(scholarship);
        String[] columnNames = { "Name", "Surname", "Ects","Avg. grade","Index.","Year","Failed Courses"};
        table = new JTable(data, columnNames);
        jsp = new JScrollPane(table);
        jsp.setBounds(10, 120, 730, 500);
        add(jsp);
        getContentPane().validate();
        getContentPane().repaint();
    }

    public void showWorkersTable(){
        try {
            getContentPane().remove(jsp);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        getContentPane().invalidate();

        SalaryStrategy salaryStrategy;
        String[][] data = null;
        if(salaryAlgorithm1.isSelected()){
            data = university.getAllWorkersArray(new SalaryUniversityWorkerAlgorithm1(),new SalaryAcademicWorkerAlgorithm1());
        }
        if(salaryAlgorithm2.isSelected()){
            data = university.getAllWorkersArray(new SalaryUniversityWorkerAlgorithm2(),new SalaryAcademicWorkerAlgorithm2());
        }

        String[] columnNames = { "Name", "Surname", "Position","Hours","Overtime h.","Hourly Rate","Publications","Courses","Foreign Language","Regime","Salary","Type"};
        table = new JTable(data, columnNames);
        jsp = new JScrollPane(table);
        jsp.setBounds(10, 120, 730, 500);
        add(jsp);


        getContentPane().validate();
        getContentPane().repaint();

    }

    public void addStudentToCourse(){
        getContentPane().removeAll();
        getContentPane().invalidate();


        JLabel indexNumberLabel = new JLabel("Index num.");
        JTextField indexNumberField = new JTextField();


        JComboBox<String> chooseCourseBox = new JComboBox<>(university.arrayOfCourses());
        JLabel courseNameLabel = new JLabel("Course name");


        JLabel gradeLabel = new JLabel("Grade");
        JTextField gradeField = new JTextField();

        JLabel title = new JLabel("Adding student to Course");
        title.setBounds(150,10,200,20);


        JButton submit = new JButton("Submit");

        courseNameLabel.setBounds(10,40,100,20);
        chooseCourseBox.setBounds(100,40,180,20);

        indexNumberLabel.setBounds(10,70,100,20);
        indexNumberField.setBounds(100,70,180,20);

        gradeLabel.setBounds(10,100,100,20);
        gradeField.setBounds(100,100,180,20);

        submit.setBounds(100,130,180,20);

        submit.addActionListener(e -> {
            if(indexNumberField.getText().isEmpty() && gradeField.getText().isEmpty()){
                msgbox("All fields mustn't be empty!");
            }else{
                String indexNum = indexNumberField.getText();
                String courseName = chooseCourseBox.getSelectedItem().toString();

                if(university.containsCourse(courseName)){
                    if(university.containsStudentWithIndexNum(indexNum)){
                        Course course = new Course(courseName,university.ectsOfCourse(courseName));
                        Student student = university.findStudentByIndex(indexNum);
                        student.setUniversity(university);
                        student.addCourse(course,Double.parseDouble(gradeField.getText()));

                        indexNumberField.setText("");
                        gradeField.setText("");

                    }else{
                        msgbox("There is no student with \"" + indexNum + "\" index num.");
                    }
                }else{
                    msgbox("There is no\"" + courseName + "\" course in the university!");
                }


            }
        });

        JButton back = new JButton("BACK =>");
        back.setBounds(410,10,80,20);
        back.addActionListener(e -> {mainView();});
        add(chooseCourseBox);
        add(courseNameLabel);
        add(indexNumberField);
        add(indexNumberLabel);
        add(title);
        add(submit);
        add(back);
        add(gradeField);
        add(gradeLabel);

        getContentPane().validate();
        getContentPane().repaint();

    }

    public void addCourse(){
        getContentPane().removeAll();
        getContentPane().invalidate();

        JLabel courseNameLabel = new JLabel("Course name");
        JTextField courseNameField = new JTextField();

        JLabel courseEctsLabel = new JLabel("ECTS");
        JTextField courseEctsField = new JTextField();

        JLabel title = new JLabel("Adding new Course");
        title.setBounds(180,10,150,20);


        JButton submit = new JButton("Submit");

        courseNameLabel.setBounds(10,40,100,20);
        courseNameField.setBounds(100,40,180,20);

        courseEctsLabel.setBounds(10,70,100,20);
        courseEctsField.setBounds(100,70,180,20);

        submit.setBounds(100,100,180,20);

        submit.addActionListener(e -> {
            if(courseNameField.getText().isEmpty() && courseEctsField.getText().isEmpty()){
                msgbox("All fields musn't be empty!");
            }
            else{
                String courseName = courseNameField.getText();
                int ects = Integer.parseInt(courseEctsField.getText());
                Course course = new Course(courseName,ects);
                university.addCourse(course);
                courseEctsField.setText("");
                courseNameField.setText("");
            }
        });


        JButton back = new JButton("BACK =>");
        back.setBounds(410,10,80,20);
        back.addActionListener(e -> {mainView();});

        add(title);
        add(courseNameField);
        add(courseNameLabel);
        add(courseEctsField);
        add(courseEctsLabel);
        add(submit);
        add(back);

        getContentPane().validate();
        getContentPane().repaint();

    }


    public void mainView(){

        getContentPane().removeAll();
        getContentPane().invalidate();

        setSize(750,700);

        setTitle("University Manager");
        setLayout(null);

        showAllTable();

        addPerson = new JButton("Add Person");
        addPerson.addActionListener(e -> {addPersonView();});
        addPerson.setBounds(10,10,250,20);

        addCourse = new JButton("Add Course");
        addCourse.setBounds(270,10,220,20);
        addCourse.addActionListener(e -> addCourse());

        showStats = new JButton("Statistics");
        showStats.setBounds(500,10,240,20);
        showStats.addActionListener(e -> {
            msgbox(university.showStats());
        });

        save = new JButton("Save");
        save.setBounds(300,620,130,40);
        save.addActionListener(e -> saveData(university));


        workerRadioButton = new JRadioButton("Worker");
        studentRadioButton = new JRadioButton("Student");
        studentRadioButton.setSelected(true);

        salaryAlgorithm1 = new JRadioButton("Salary algo 1");
        salaryAlgorithm2 = new JRadioButton("Salary algo 2");
        salaryAlgorithm1.setSelected(true);


        showLabel = new JLabel("Show:");
        showLabel.setBounds(450,30,100,20);

        showAll = new JButton("All");
        showAll.setBounds(270,50,100,20);
        showAll.addActionListener(e -> showAllTable());

        showStudents = new JButton("Students");
        showStudents.setBounds(380,50,200,20);
        showStudents.addActionListener(e -> showStudentsTable());

        withScholarship = new JCheckBox("Scholarship");
        withScholarship.setBounds(380,80,150,20);

        showWorkers = new JButton("Workers");
        showWorkers.setBounds(590,50,150,20);
        showWorkers.addActionListener(e -> showWorkersTable());


        ButtonGroup group = new ButtonGroup();
        group.add(workerRadioButton);
        group.add(studentRadioButton);

        ButtonGroup group2 = new ButtonGroup();
        group2.add(salaryAlgorithm1);
        group2.add(salaryAlgorithm2);

        salaryAlgorithm1.setBounds(590,70,120,20);
        salaryAlgorithm2.setBounds(590,90,120,20);

        workerRadioButton.setBounds(10,45,100,20);
        studentRadioButton.setBounds(110,45,100,20);


        addStudentToCourse = new JButton("Add student to Course");
        addStudentToCourse.setBounds(10,80,250,20);
        addStudentToCourse.addActionListener(e -> addStudentToCourse());


        add(addPerson);
        add(addStudentToCourse);
        add(addCourse);
        add(showStats);
        add(showStudents);
        add(salaryAlgorithm1);
        add(salaryAlgorithm2);
//        add(jsp);
        add(workerRadioButton);
        add(studentRadioButton);
        add(showLabel);
        add(showAll);
        add(withScholarship);
        add(showWorkers);
        add(save);
        getContentPane().validate();
        getContentPane().repaint();
        setVisible(true);
    }

    public void addStudentView(){
        getContentPane().removeAll();
        getContentPane().invalidate();

        JButton back = new JButton("BACK =>");
        JLabel title = new JLabel("Adding new Student");
        title.setBounds(180,10,150,20);

        JTextField nameField = new JTextField();
        JLabel nameLabel = new JLabel("Name");
        JTextField surnameField = new JTextField();
        JLabel surnameLabel = new JLabel("Surname");
        JTextField studyYear = new JTextField();
        JLabel studyYearLabel = new JLabel("Study year");
        JTextField indexNumberField = new JTextField();
        JLabel indexNumberLabel= new JLabel("Index num");

        JButton submit = new JButton("Submit");

        nameLabel.setBounds(10,40,100,20);
        nameField.setBounds(100,40,180,20);

        surnameLabel.setBounds(10,70,100,20);
        surnameField.setBounds(100,70,180,20);

        studyYearLabel.setBounds(10,100,100,20);
        studyYear.setBounds(100,100,180,20);

        indexNumberLabel.setBounds(10,130,100,20);
        indexNumberField.setBounds(100,130,180,20);

        submit.setBounds(100,160,180,20);
        submit.addActionListener(e -> {

            String name = nameField.getText().toString();
            String surname = surnameField.getText().toString();
            String indexNum = indexNumberField.getText().toString();
            String year = studyYear.getText().toString();

            if(nameField.getText().isEmpty() && surnameField.getText().isEmpty() && indexNumberField.getText().isEmpty() && studyYear.getText().isEmpty()){
                msgbox("All fields musn't be empty!");
            }else{
                Student student = new Student(name,surname,indexNum,Integer.parseInt(year));
                university.addPerson(student);
                student.setUniversity(university);
                nameField.setText("");
                surnameField.setText("");
                indexNumberField.setText("");
                studyYear.setText("");
            }
        });



        back.setBounds(410,10,80,20);
        back.addActionListener(e -> {mainView();});
        add(back);
        add(title);
        add(nameField);
        add(nameLabel);
        add(surnameField);
        add(surnameLabel);
        add(studyYear);
        add(studyYearLabel);
        add(indexNumberField);
        add(indexNumberLabel);
        add(submit);
        getContentPane().validate();
        getContentPane().repaint();
    }

    public void saveData(University university){
        try (OutputStream file = new FileOutputStream("src/Ser/university.ser");
             OutputStream buffer = new BufferedOutputStream(file);
             ObjectOutput output = new ObjectOutputStream(buffer);){

            output.writeObject(university);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public University getData(){
        try(
                InputStream file = new FileInputStream("src/Ser/university.ser");
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream (buffer);
        ){
            return (University) input.readObject();
        } catch (FileNotFoundException e) {
            return new University();
        } catch (IOException e) {
            return new University();
        } catch (ClassNotFoundException e) {
            return new University();
        }

    }

    public void addWorkerView(){
        getContentPane().removeAll();
        getContentPane().invalidate();


//
//        ButtonGroup group2 = new ButtonGroup();
//        group2.add(salaryAlgorithm1);
//        group2.add(salaryAlgorithm2);


        JButton back = new JButton("BACK =>");
        back.setBounds(410,10,80,20);
        back.addActionListener(e -> {mainView();});
        JLabel title = new JLabel("Adding new Worker");
        title.setBounds(180,10,150,20);



        workerSelectRadioButton = new JRadioButton("Worker");
        academicWorkerRadioButton = new JRadioButton("Academic Worker");
        workerSelectRadioButton.setBounds(10,40,200,20);
        academicWorkerRadioButton.setBounds(100,40,200,20);


        ButtonGroup group3 = new ButtonGroup();
        group3.add(workerSelectRadioButton);
        group3.add(academicWorkerRadioButton);

        workerSelectRadioButton.setSelected(true);
        addUniversityWorkerView();

        workerSelectRadioButton.addActionListener(e -> addUniversityWorkerView());
        academicWorkerRadioButton.addActionListener(e -> addAcademicWorkerView());


        JLabel nameLabel = new JLabel("Name");
        JTextField nameField = new JTextField();
        nameLabel.setBounds(10,70,100,20);
        nameField.setBounds(180,70,180,20);

        JLabel surnameLabel = new JLabel("Surname");
        JTextField surnameField = new JTextField();
        surnameLabel.setBounds(10,100,100,20);
        surnameField.setBounds(180,100,180,20);



        JLabel positionLabel = new JLabel("Position");
        JTextField positionField = new JTextField();
        positionLabel.setBounds(10,130,100,20);
        positionField.setBounds(180,130,180,20);


        JLabel hoursLabel = new JLabel("Amount of hours");
        JTextField hoursField = new JTextField();
        hoursLabel.setBounds(10,160,160,20);
        hoursField.setBounds(180,160,180,20);

        JLabel overtimeHoursLabel = new JLabel("Overtime hours");
        JTextField overtimeField = new JTextField();
        overtimeHoursLabel.setBounds(10,190,100,20);
        overtimeField.setBounds(180,190,180,20);

        JLabel hourlyRateLabel = new JLabel("Hourly rate");
        JTextField hourlyRateField = new JTextField();
        hourlyRateLabel.setBounds(10,220,100,20);
        hourlyRateField.setBounds(180,220,180,20);

        submitWorkerButton = new JButton("Submit");
        submitWorkerButton.setBounds(10,310,70,20);

        submitWorkerButton.addActionListener(e -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String position = positionField.getText();
            int hours = Integer.parseInt(hoursField.getText());
            int overtimeHours = Integer.parseInt(overtimeField.getText());
            double hourlyRate = Double.parseDouble(hourlyRateField.getText());

            if(workerSelectRadioButton.isSelected()){
                boolean knowsForeignLanguage = false;
                if(knowsForeignLanguageCheckbox.isSelected()) knowsForeignLanguage = true;

                String workingRegime = jComboBox.getSelectedItem().toString();


                UniversityWorker worker = new UniversityWorker(name,surname,position,hours,overtimeHours,hourlyRate,knowsForeignLanguage,workingRegime);
                university.addPerson(worker);
            }else {

                int amountOfPublication = Integer.parseInt(publicationsField.getText());
                int amountOfCourses = Integer.parseInt(coursesField.getText());

                UniversityAcademicWorker worker = new UniversityAcademicWorker(name,surname,position,hours,overtimeHours,hourlyRate,amountOfPublication,amountOfCourses);
                university.addPerson(worker);
            }


            nameField.setText("");
            surnameField.setText("");
            positionField.setText("");
            hoursField.setText("");
            overtimeField.setText("");
            hourlyRateField.setText("");

            try {
                publicationsField.setText("");
                coursesField.setText("");
            }catch (Exception ex){
                ex.printStackTrace();
            }

        });


        add(back);
        add(title);
        add(nameLabel);
        add(nameField);
        add(surnameLabel);
        add(surnameField);
        add(positionLabel);
        add(positionField);
        add(hoursLabel);
        add(hoursField);
        add(overtimeHoursLabel);
        add(overtimeField);
        add(hourlyRateField);
        add(hourlyRateLabel);
        add(academicWorkerRadioButton);
        add(workerSelectRadioButton);
        add(submitWorkerButton);



        getContentPane().validate();
        getContentPane().repaint();
    }



    public void addUniversityWorkerView(){
        try {
            remove(publicationLabel);
            remove(publicationsField);
            remove(coursesLabel);
            remove(coursesField);
        }catch (Exception e){
            e.printStackTrace();
        }
        getContentPane().invalidate();

        knowsForeignLanguageCheckbox = new JCheckBox("Knows foreign language");
        knowsForeignLanguageCheckbox.setBounds(10,250,200,20);

        regimeLabel = new JLabel("Working regime");
        regimeLabel.setBounds(10,280,100,20);

        String[] optionsToChoose = {"Day","Night"};
        jComboBox = new JComboBox<>(optionsToChoose);
        jComboBox.setBounds(180,280,100,20);

        add(knowsForeignLanguageCheckbox);
        add(jComboBox);
        add(regimeLabel);

        getContentPane().validate();
        getContentPane().repaint();

    }

    public void addAcademicWorkerView(){

        try {
            remove(knowsForeignLanguageCheckbox);
            remove(jComboBox);
            remove(regimeLabel);
        }catch (Exception e){
            e.printStackTrace();
        }
        getContentPane().invalidate();

        publicationLabel = new JLabel("Amount of publications");
        publicationsField = new JTextField();
        publicationLabel.setBounds(10,250,160,20);
        publicationsField.setBounds(180,250,180,20);

        coursesLabel = new JLabel("Amount of courses");
        coursesField = new JTextField();
        coursesLabel.setBounds(10,280,160,20);
        coursesField.setBounds(180,280,180,20);

        add(publicationsField);
        add(publicationLabel);

        add(coursesField);
        add(coursesLabel);

        getContentPane().validate();
        getContentPane().repaint();
    }



    public void addPersonView(){
        if(studentRadioButton.isSelected()) addStudentView();
        else addWorkerView();
    }


    public static void main(String[] args) {
        MyFrame gui = new MyFrame();
        gui.mainView();
    }
}
