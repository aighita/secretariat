package org.example;

// IO
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

// NIO
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Path;
import java.nio.file.Paths;

// UTIL
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

class Secretariat {
    String pathIn;
    String pathOut;
    String notePath;

    Set<Student> studenti = new HashSet<>();
    Set<Curs> cursuri = new HashSet<>();

    String buildAntetResources() {
        File file = new File("src/main/resources");
        if (file.exists()) return file.getPath();
        file = new File("../resources");
        return file.getPath();
    }

    void buildPaths(String fileName) {
        String resourcesPath = this.buildAntetResources();
        this.pathIn = new String(resourcesPath +  "/" + fileName + "/" + fileName + ".in");
        this.pathOut = new String(resourcesPath + "/" + fileName + "/" + fileName + ".out");
        this.notePath = new String(resourcesPath + "/" + fileName + "/note_");
        this.createMyPath(pathOut);
    }

    void createMyPath(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void cleanOut() {
        PrintWriter pw = newPrinter(this.pathOut);
        pw.write("");
        pw.close();
    }

    BufferedReader newBufferedReader(String path) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return br;
    }

    PrintWriter newPrinter(String path) {
        PrintWriter out = null;
        try {
            FileWriter fw = new FileWriter(path, false);
            BufferedWriter bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    PrintWriter newPrinterAppend(String path) {
        PrintWriter out = null;
        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            out = new PrintWriter(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    ArrayList<String> citesteFisier(String file) {
        BufferedReader br = newBufferedReader(file);
        ArrayList<String> lines = new ArrayList<>();
        String line = new String();

        try {
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    void studentDuplicat(Student student) {
        PrintWriter pw = newPrinterAppend(pathOut);
        pw.println("***");
        pw.println("Student duplicat: " + student.nume);
        pw.close();
    }

    void adaugaStudentMaster(StudentMaster studentMaster) {
        for (Student cautaStudent : studenti) {
            if (cautaStudent.nume.equals(studentMaster.nume)) {
                this.studentDuplicat(studentMaster);
                return;
            }
        }
        studenti.add(studentMaster);
    }

    void adaugaStudentLicenta(StudentLicenta studentLicenta) {
        for (Student cautaStudent : studenti) {
            if (cautaStudent.nume.equals(studentLicenta.nume)) {
                this.studentDuplicat(studentLicenta);
                return;
            }
        }
        studenti.add(studentLicenta);
    }

    void adaugaStudent(String studii, String nume) {
        switch(studii) {
            case "master":
                this.adaugaStudentMaster(new StudentMaster(nume));
                break;
            case "licenta":
                this.adaugaStudentLicenta(new StudentLicenta(nume));
                break;
        }
    }

    void adaugaCurs(String studii, String nume, int capacitate) {
        switch(studii) {
            case "master":
                this.cursuri.add(new CursMaster(nume, capacitate));
                break;
            case "licenta":
                this.cursuri.add(new CursLicenta(nume, capacitate));
                break;
        }
    }

    void posteazaCurs(String nume) {
        PrintWriter pw = newPrinterAppend(pathOut);
        pw.println("***");
        for (Curs curs : cursuri) {
            if  (curs.nume.equals(nume)) {
                pw.println(curs.nume + " (" + curs.capacitate + ")");
                String line = "";
                for (Object stud : curs.studentiInscrisi) {
                    Student s = (Student) stud;
                    line = line + s.nume + " - " + s.medie + "\n";
                }
                pw.printf(line);
            }
        }
        pw.close();
    }

    void posteazaStudent(String nume) {
        PrintWriter pw = newPrinterAppend(pathOut);
        pw.println("***");
        for (Object s : studenti) {
            if (s instanceof StudentMaster) {
                StudentMaster stud = (StudentMaster) s;
                if (stud.nume.equals(nume)) {
                    pw.println("Student Master: " + stud.nume + " - "
                    + stud.medie + " - " + stud.curs);
                }
            }
            if (s instanceof StudentLicenta) {
                StudentLicenta stud = (StudentLicenta) s;
                if (stud.nume.equals(nume)) {
                    pw.println("Student Licenta: " + stud.nume + " - "
                            + stud.medie + " - " + stud.curs);
                }
            }
        }
        pw.close();
    }

    void adaugaPreferinte(String[] args) {
        for (Student s : studenti) {
            if (s.nume.equals(args[1])) {
                s.preferinte = new String[args.length - 2];
                for (int i = 2; i < args.length; i++) {
                    s.preferinte[i - 2] = args[i];
                }
            }
        }
    }

    void inroleazaStudentMaster(Object stud) {
        StudentMaster student = (StudentMaster) stud;
        for (String pref : student.preferinte) {
            for (Object c : cursuri) {
                CursMaster curs;
                if (c instanceof CursMaster)
                    curs = (CursMaster) c;
                else continue;
                if (curs.nume.equals(pref) && (curs instanceof CursMaster)) {
                    if (curs.studentiInscrisi == null) {
                        curs.studentiInscrisi = new ArrayList<>();
                        curs.studentiInscrisi.add(student);
                        student.curs = curs.nume;
                        return;
                    }
                    if (curs.capacitate > curs.studentiInscrisi.size()) {
                        curs.studentiInscrisi.add(student);
                        student.curs = curs.nume;
                        return;
                    } else {
                        Object u = curs.studentiInscrisi.get(curs.capacitate - 1);
                        StudentMaster ultim = (StudentMaster) u;
                        if (ultim.medie == student.medie) {
                            curs.studentiInscrisi.add(student);
                            student.curs = curs.nume;
                            return;
                        }
                    }
                }
            }
        }
    }

    void inroleazaStudentLicenta(Object stud) {
        StudentLicenta student = (StudentLicenta) stud;
        for (String pref : student.preferinte) {
            for (Object c : cursuri) {
                CursLicenta curs;
                if (c instanceof CursLicenta)
                    curs = (CursLicenta) c;
                else continue;
                if (curs.nume.equals(pref) && (curs instanceof CursLicenta)) {
                    if (curs.studentiInscrisi == null) {
                        curs.studentiInscrisi = new ArrayList<>();
                        curs.studentiInscrisi.add(student);
                        student.curs = curs.nume;
                        return;
                    }
                    if (curs.capacitate > curs.studentiInscrisi.size()) {
                        curs.studentiInscrisi.add(student);
                        student.curs = curs.nume;
                        return;
                    } else {
                        Object u = curs.studentiInscrisi.get(curs.capacitate - 1);
                        StudentLicenta ultim = (StudentLicenta) u;
                        if (ultim.medie == student.medie) {
                            curs.studentiInscrisi.add(student);
                            student.curs = curs.nume;
                            return;
                        }
                    }
                }
            }
        }
    }

    void repartizeaza() {
        List<Student> studentiList = new ArrayList<>(studenti);
        Collections.sort(studentiList, Comparator.comparingDouble(Student::getMedie).reversed()
                .thenComparing(Student::getNume, String.CASE_INSENSITIVE_ORDER));

        for (Object s : studentiList) {
            if (s instanceof StudentMaster) this.inroleazaStudentMaster(s);
            else this.inroleazaStudentLicenta(s);
        }

        for (Curs c : cursuri) {
            if (c.studentiInscrisi == null) continue;
            Collections.sort(c.studentiInscrisi);
        }
    }

    void actualizeazaMedieStudent(String nume, double medie) {
        Iterator<Student> iterator = studenti.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.nume.equals(nume)) {
                student.setMedie(medie);
            }
        }
    }

    void citesteMediile() {
        ArrayList<String> medii = new ArrayList<>();
        int i = 0;
        medii = citesteFisierNote(String.valueOf(++i));

        while (medii != null) {
            for (String line : medii) {
                actualizeazaMedieStudent(line.replace(" ", "").split("-")[0], Double.parseDouble(line.replace(" ", "").split("-")[1]));
            }
            medii = citesteFisierNote(String.valueOf(++i));
        }
    }

    void posteazaMediile() {
        PrintWriter pw = newPrinterAppend(pathOut);
        List<Student> studentiList = new ArrayList<>(studenti);
        Collections.sort(studentiList, Comparator.comparingDouble(Student::getMedie).reversed()
                .thenComparing(Student::getNume, String.CASE_INSENSITIVE_ORDER));

        pw.println("***");
        for (Student s : studentiList) {
            pw.println(s.nume + " - " + s.medie);
        }
        pw.close();
    }

    ArrayList<String> citesteFisierNote(String nr) {
        String notePathSpecific = this.notePath + nr + ".txt";
        File file = new File(notePathSpecific);
        if (!file.exists()) return null;
        ArrayList<String> note = citesteFisier(notePathSpecific);
        return note;
    }

    void ruleaza(String file) {
        this.buildPaths(file);
        this.cleanOut();
        ArrayList<String> lines = this.citesteFisier(pathIn);

        for (String line : lines) {
            String[] args = line.replace(" ", "").split("-");
            switch(args[0]) {
                case "adauga_student":
                    this.adaugaStudent(args[1], args[2]);
                    break;
                case "citeste_mediile":
                    this.citesteMediile();
                    break;
                case "posteaza_mediile":
                    this.posteazaMediile();
                    break;
                case "adauga_curs":
                    this.adaugaCurs(args[1], args[2], Integer.parseInt(args[3]));
                    break;
                case "posteaza_curs":
                    this.posteazaCurs(args[1]);
                    break;
                case "posteaza_student":
                    this.posteazaStudent(args[1]);
                    break;
                case "contestatie":
                    this.actualizeazaMedieStudent(args[1], Double.parseDouble(args[2]));
                    break;
                case "adauga_preferinte":
                    this.adaugaPreferinte(args);
                    break;
                case "repartizeaza":
                    this.repartizeaza();
                    break;
                default:
                    System.out.println("Actiune inexistenta!");
                    break;
            }
        }
    }
}
