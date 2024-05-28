package viewer;

import controller.StudentController2;
import lombok.Setter;
import model.StudentDTO;
import util.ScannerUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentViewer2 {
    @Setter
    private StudentController2 studentController2;
    @Setter
    private Scanner scanner;

    public void showMenu() throws SQLException {
        String message = "1. 입력 2. 전체 조회 3. 하나 조회 4. 업데이트 5. 삭제 6. 종료";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);

            if (userChoice == 1) {
                insert();
            } else if (userChoice == 2) {
                selectAll();
            } else if (userChoice == 3) {
                selectOne();
            } else if (userChoice == 4) {
                update();
            } else if (userChoice == 5) {
                delete();
            } else if (userChoice == 6) {
                studentController2.initialize();
                break;
            }
        }
    }

    private void insert() throws SQLException {

        scanInfo result = getStudentScanInfo();
        StudentDTO studentDTO = saveStudentInfo(result);

        studentController2.insert(studentDTO);
    }

    private void selectAll() throws SQLException {
        ArrayList<StudentDTO> list = studentController2.selectAll();
        for (StudentDTO studentDTO : list) {
            System.out.println("studentDTO = " + studentDTO);
        }
    }

    private void selectOne() throws SQLException {
        int id = getStudentId();
        StudentDTO studentDTO = studentController2.selectOne(id);
        System.out.println("studentDTO = " + studentDTO);
    }

    private void update() throws SQLException {
        int id = getStudentId();
        scanInfo scanInfo = getStudentScanInfo();
        StudentDTO studentDTO = saveStudentInfo(scanInfo);
        studentDTO.setId(id);
        studentController2.update(studentDTO);
    }

    private void delete() throws SQLException {
        String message = "삭제할 학생의 번호 : ";
        int id = ScannerUtil.nextInt(scanner, message);
        studentController2.delete(id);
    }

    private scanInfo getStudentScanInfo() {
        String message;
        message = "이름을 입력하시오 : ";
        String name = ScannerUtil.nextLine(scanner, message);
        message = "국어 점수 : ";
        int korean = ScannerUtil.nextInt(scanner, message);
        message = "영어 점수 : ";
        int english = ScannerUtil.nextInt(scanner, message);
        message = "수학 점수 : ";
        int math = ScannerUtil.nextInt(scanner, message);
        return new scanInfo(name, korean, english, math);
    }

    private record scanInfo(String name, int korean, int english, int math) {
    }

    private int getStudentId() {
        String message = "수정할 학생의 번호 : ";
        return ScannerUtil.nextInt(scanner, message);
    }

    private static StudentDTO saveStudentInfo(scanInfo result) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(result.name());
        studentDTO.setKorean(result.korean());
        studentDTO.setEnglish(result.english());
        studentDTO.setMath(result.math());
        return studentDTO;
    }
}