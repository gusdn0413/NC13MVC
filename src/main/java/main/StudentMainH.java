package main;

import controller.StudentControllerH;

import viewer.StudentViewerH;

import java.sql.SQLException;
import java.util.Scanner;

public class StudentMainH {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        StudentControllerH studentController2 = new StudentControllerH();
        StudentViewerH studentViewer2 = new StudentViewerH();

        studentViewer2.setStudentController2(studentController2);
        studentViewer2.setScanner(scanner);

        System.out.println("connection 성공");

        studentViewer2.showMenu();
    }
}
