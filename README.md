<div align="center">
    <h1>Tema 2 <br>Programare orientată pe obiecte</h1>
    <p>Sistem informatic pentru gestiunea si repartizarea studentiilor la materiile optionale din facultate</p>
    <a href="Tema2Poo2023.pdf" target="_blank">ENUNȚUL TEMEI</a>
</div>

## Clase
1. <b>Secretariat</b>
- gestionează întreg fluxul
- conține baze de date cu studenți și cursuri
- răspunde la interogări și realizează repartizarea: Student și Curs.
2. <b>Student</b>
- clasa părinte pentru StudentMaster si StudentLicenta
3. <b>Curs</b>
- parametrizată folosind genericitate (nu permite asignarea unui student de master în colecția unui curs ce admite 
studenți de licență)

<div align="center">
    <h2>Rularea și testarea programului</h2>
</div>

## Main
Primește un argument (numele folderului de test [ex: 12-toate_functionalitatile]), creează o instanță "s" de tipul secretariat
și apelează metoda "ruleaza" a clasei "Secretariat".

## Secretariat
Este apelată metoda rulează care primește ca unic argument numele folderului de test. Se apelează metoda "buildPaths()"
care inițializează variabilele "pathIn", "pathOut" si "notePath" cu căile catre fișierele .in, .out si note.txt din folderul
resources ale testului aferent.

Scrierea și citirea în și din fișiere se face cu cu ajutorul metodelor programului: "PrintWriter newPrinterAppend(String path)",
"BufferedReader newBufferedReader(String path)", "ArrayList<String> citesteFisier(String file)" folosite cu scopul de a evita
duplicarea codului.

Se citește conținutul fișierului de input apoi se parcurge linie cu linie pentru a rula comenzile primite. Se realizeaza
apelarea metodelor folosind "switch".

Adaugarea unui student este efectuată apelând metoda "void adaugaStudent(String studii, String nume)" care în funcție de
programul de studii al studentului ce urmeaza sa fie adaugat, va pune in colecția "studenti" de tipul "Set" un nou obiect
StudentMaster sau StudentLicenta. Pentru fiecare student nou adăugat se face o verificare daca acest student există deja in 
datele secretariatului si se va apela metoda "void studentDuplicat(Student student)" in caz afirmativ.

Adaugarea unui nou curs va apela metoda "void adaugaCurs(String studii, String nume, int capacitate)" ce va adauga un nou
obiect de tipul CursMaster/CursLicenta in set-ul de cursuri.

Citirea mediilor va apela metoda "void citesteMediile()". Folosind metoda "citesteFisierNote(String nr)" vor fi citite notele
din fisierele denumite "note_<nr>.txt" pana se va incerca citirea dintr-un fisier inexistent. Pentru fiecare fisier citit se
parcurge continutul acestuia si se asigneaza media studentului folosiind metoda "actualizeazaMedieStudent(String nume, double medie)".

Postarea mediilor se face folosind metoda "posteazaMediile()". Se sorteaza lista de studenti descrescator in functie de medie
folosind "Collections.sort" apoi se afiseaza mediile in pathOut.

Contestatiile se efectueaza folosind metoda "actualizeazaMedieStudent(String nume, double medie)".

Preferintele studentului se adauga intr-un String[] apartinand clasei Student pentru fiecare student in parte.

Repartizarea: studentii sunt sortati descrescator dupa medie. Se parcurge set-ul de studenti. Pentru fiecare student se parcurge
String[] preferinte. Se incearca inrolarea studentului la primul curs "preferinta" tinand cont de conditiile impuse in enunt, iar
mai apoi pentru celelalte cursuri din "preferinte" daca pentru primul nu a reusit.
Dupa inrolarea obiectul Student este adaugat in colectia de studentiInrolati a cursului iar variabilei "curs" din clasa student ii este atribuita cursul la care a fost inrolat.

Postarea cursului: studentii inrolati sunt sortati alfabetic dupa nume iar detaliile cursului sunt postate in pathOut. Pentru sortarea alfabetica
se foloseste "Collections.sort()" si metoda suprascrisa in clasa Student: "compareTo(Student student)".

Postarea detaliilor unui student se efectueaza folosind metoda "void posteazaStudent(String nume)".
