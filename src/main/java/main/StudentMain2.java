package main;

import controller.StudentController2;

import util.ScannerUtil;
import viewer.StudentViewer2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentMain2 {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        StudentController2 studentController2 = new StudentController2();
        StudentViewer2 studentViewer2 = new StudentViewer2();

        studentViewer2.setStudentController2(studentController2);
        studentViewer2.setScanner(scanner);

        System.out.println("connection 성공");

        studentViewer2.showMenu();
    }
}
