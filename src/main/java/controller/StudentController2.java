package controller;

import model.StudentDTO;
import util.ScannerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentController2 {
    private String query;

    public StudentController2() {
    }

    public void insert(Connection connection, StudentDTO studentDTO) throws SQLException {

        query = "INSERT INTO student(name, korean, english, math) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, studentDTO.getName());
        preparedStatement.setInt(2, studentDTO.getKorean());
        preparedStatement.setInt(3, studentDTO.getEnglish());
        preparedStatement.setInt(4, studentDTO.getMath());
        preparedStatement.executeUpdate();
    }

    public ArrayList<StudentDTO> selectAll(Connection connection) throws SQLException {
        ArrayList<StudentDTO> list = new ArrayList<>();
        query = "SELECT * FROM student";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(resultSet.getInt("id"));
            saveInfo(studentDTO, resultSet);
            list.add(studentDTO);
        }
        return list;
    }

    public StudentDTO selectOne(Connection connection, int id) throws SQLException {

        query = "SELECT * FROM student WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, id);
        ResultSet student = preparedStatement.executeQuery();

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(id);
        while (student.next()) {
            saveInfo(studentDTO, student);
        }
        return studentDTO;
    }

    public void update(Connection connection, StudentDTO studentDTO) throws SQLException {

        query = "UPDATE student SET name = ?, korean = ?, english = ?, math = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, studentDTO.getName());
        preparedStatement.setInt(2, studentDTO.getKorean());
        preparedStatement.setInt(3, studentDTO.getEnglish());
        preparedStatement.setInt(4, studentDTO.getMath());
        preparedStatement.setInt(5, studentDTO.getId());

        preparedStatement.executeUpdate();
    }

    public void delete(Connection connection, int id) throws SQLException {


        query = "DELETE FROM student WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
    }

    public void initialize(Connection connection) throws SQLException {

        query = "DELETE FROM student";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.executeUpdate();

        query = "ALTER TABLE student AUTO_INCREMENT = 1";
        preparedStatement = connection.prepareStatement(query);

        preparedStatement.executeUpdate();
    }

    private static void saveInfo(StudentDTO studentDTO, ResultSet student) throws SQLException {

        studentDTO.setName(student.getString("name"));
        studentDTO.setKorean(student.getInt("korean"));
        studentDTO.setEnglish(student.getInt("english"));
        studentDTO.setMath(student.getInt("math"));
    }

    private static Result getInfo(Scanner scanner) {
        String message;
        message = "이름을 입력하시오 : ";
        String name = ScannerUtil.nextLine(scanner, message);

        message = "국어 점수 : ";
        int korean = ScannerUtil.nextInt(scanner, message);

        message = "영어 점수 : ";
        int english = ScannerUtil.nextInt(scanner, message);

        message = "수학 점수 : ";
        int math = ScannerUtil.nextInt(scanner, message);

        return new Result(name, korean, english, math);
    }

    private record Result(String name, int korean, int english, int math) {
    }
}
