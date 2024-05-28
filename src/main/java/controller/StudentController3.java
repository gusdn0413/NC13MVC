package controller;

import model.StudentDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentController3 {


    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/board";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "Qkr1593574!s";
    private Connection connection;

    public StudentController3() {
        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<StudentDTO> selectAll() {
        ArrayList<StudentDTO> list = new ArrayList<>();
        String query = "SELECT * FROM student";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StudentDTO studentDTO = new StudentDTO();
                studentDTO.setId(resultSet.getInt("id"));
                studentDTO.setName(resultSet.getString("name"));
                studentDTO.setKorean(resultSet.getInt("korean"));
                studentDTO.setEnglish(resultSet.getInt("english"));
                studentDTO.setMath(resultSet.getInt("math"));

                list.add(studentDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public StudentDTO selectOne(int id) {
        StudentDTO studentDTO = null;

        String query = "SELECT * FROM student WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentDTO = new StudentDTO();
                studentDTO.setId(resultSet.getInt("id"));
                studentDTO.setName(resultSet.getString("name"));
                studentDTO.setKorean(resultSet.getInt("korean"));
                studentDTO.setEnglish(resultSet.getInt("english"));
                studentDTO.setMath(resultSet.getInt("math"));
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentDTO;
    }

    public void insert(StudentDTO studentDTO) {
        String query = "INSERT INTO student(name, korean, english, math) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, studentDTO.getName());
            preparedStatement.setInt(2, studentDTO.getKorean());
            preparedStatement.setInt(3, studentDTO.getEnglish());
            preparedStatement.setInt(4, studentDTO.getMath());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(StudentDTO studentDTO) {
        String query = "UPDATE student SET name = ?, korean = ?, english = ?, math = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentDTO.getName());
            preparedStatement.setInt(2, studentDTO.getKorean());
            preparedStatement.setInt(3, studentDTO.getEnglish());
            preparedStatement.setInt(4, studentDTO.getMath());
            preparedStatement.setInt(5, studentDTO.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM student WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
