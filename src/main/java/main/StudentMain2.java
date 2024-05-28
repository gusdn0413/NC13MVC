package main;

import controller.StudentController2;

import util.ScannerUtil;
import viewer.StudentViewer2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class StudentMain2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentController2 studentController2 = new StudentController2();
        StudentViewer2 studentViewer2 = new StudentViewer2();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/board";
            String username = "root";
            String password = "Qkr1593574!s";

            Connection connection = DriverManager.getConnection(url, username, password);

            studentViewer2.setStudentController2(studentController2);
            studentViewer2.setConnection(connection);
            studentViewer2.setScanner(scanner);

            System.out.println("connection 성공");

            studentViewer2.showMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
