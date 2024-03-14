package org.example;

import java.util.List;

class Student implements Comparable<Student>{
    String nume;
    double medie;
    String[] preferinte;
    String curs;

    public Student(String nume) {
        this.nume = nume;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public double getMedie() {
        return medie;
    }

    public String getNume() {
        return nume;
    }

    public int compareTo(Student student) {
        return this.nume.compareTo(student.nume);
    }
}

class StudentLicenta extends Student {
    public StudentLicenta(String nume) { super(nume); }
}

class StudentMaster extends Student {
    public StudentMaster(String nume) { super(nume); }
}
