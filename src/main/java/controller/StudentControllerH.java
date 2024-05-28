package controller;

import model.StudentDTO;

import java.sql.*;
import java.util.ArrayList;

public class StudentControllerH {
    private String query;
    private final Connection connection;

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/board";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "Qkr1593574!s";

    public StudentControllerH() throws SQLException, ClassNotFoundException {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void insert(StudentDTO studentDTO) throws SQLException {

        query = "INSERT INTO student(name, korean, english, math) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, studentDTO.getName());
        preparedStatement.setInt(2, studentDTO.getKorean());
        preparedStatement.setInt(3, studentDTO.getEnglish());
        preparedStatement.setInt(4, studentDTO.getMath());
        preparedStatement.executeUpdate();
    }

    public ArrayList<StudentDTO> selectAll() throws SQLException {
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

    public StudentDTO selectOne(int id) throws SQLException {

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

    public void update(StudentDTO studentDTO) throws SQLException {

        query = "UPDATE student SET name = ?, korean = ?, english = ?, math = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, studentDTO.getName());
        preparedStatement.setInt(2, studentDTO.getKorean());
        preparedStatement.setInt(3, studentDTO.getEnglish());
        preparedStatement.setInt(4, studentDTO.getMath());
        preparedStatement.setInt(5, studentDTO.getId());

        preparedStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {

        query = "DELETE FROM student WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
    }

    public void initialize() throws SQLException {

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
}
