package org.example;

// UTIL
import java.util.ArrayList;
import java.util.List;

class Curs<T extends Student> {
    String nume;
    int capacitate;
    ArrayList<Student> studentiInscrisi;

    public Curs(String nume, int capacitate) {
        this.nume = nume;
        this.capacitate = capacitate;
    }
}

class CursLicenta extends Curs<StudentLicenta> {
    public CursLicenta(String nume, int capacitate) {
        super(nume, capacitate);
    }
}

class CursMaster extends Curs<StudentMaster> {
    public CursMaster(String nume, int capacitate) {
        super(nume, capacitate);
    }
}
