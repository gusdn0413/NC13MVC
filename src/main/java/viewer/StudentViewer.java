package viewer;

// 실제 사용자가 보개될 UI를 담당할 뷰어

import controller.StudentController;
import model.StudentDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

import static util.ScannerUtil.*;

public class StudentViewer {
    private final Scanner SCANNER = new Scanner(System.in);
    private final StudentController studentController;

    public StudentViewer(StudentController studentController) {
        this.studentController = studentController;
    }

    public void showMenu() {
        String message = "1. 입력 2. 출력 3. 수정 4. 종료";
        while (true) {
            int userChoice = nextInt(SCANNER, message);
            if (userChoice == 1) {
                insertStudent();
            } else if (userChoice == 2) {
                showStudent();
            } else if (userChoice == 3) {
                updateStudent();
            } else if (userChoice == 4) {
                System.out.println("사용해주셔서 감사");
                break;
            }
        }
    }

    private void insertStudent() {
        StudentDTO studentDTO = new StudentDTO();
        String message;

        message = "학생의 이름을 입력해주세요";
        studentDTO.setName(nextLine(SCANNER, message));

        message = "학생의 국어 점수를 입력해주세요";
        studentDTO.setKorean(nextInt(SCANNER, message));

        message = "학생의 영어 점수를 입력해주세요";
        studentDTO.setEnglish(nextInt(SCANNER, message));

        message = "학생의 수학 점수를 입력해주세요";
        studentDTO.setMath(nextInt(SCANNER, message));

        studentController.insert(studentDTO);
    }

    private void showStudent() {
        ArrayList<StudentDTO> list = studentController.selectAll();

        if (list.isEmpty()) {
            System.out.println("등록된 학생이 없습니다");
        } else {
            String message;
            message = "1. 전체 학생 조회 2. 특정 학생 조회";
            int userChoice = nextInt(SCANNER, message);
            if (userChoice == 1) {
                for (StudentDTO studentDTO : list) {
                    System.out.println(studentDTO.getId() + "번 학생의 정보 : = " + studentDTO);
                    System.out.println(studentDTO.getId() + "번 학생의 총점 : " + studentController.calculateSum(studentDTO));
                    System.out.println(studentDTO.getId() + "번 학생의 평균 : " + studentController.calculateAverage(studentDTO));
                }
            } else if (userChoice == 2) {
                message = "번호를 입력해주세요";
                int id = nextInt(SCANNER, message);
                StudentDTO studentDTO = studentController.selectOne(id);
                if (studentDTO == null) {
                    System.out.println("해당 번호의 학생이 없습니다");
                } else {
                    System.out.println(id + "번 학생의 정보 : " + studentDTO);
                    System.out.println(id + "번 학생의 총점 : " + studentController.calculateSum(studentDTO));
                    System.out.println(id + "번 학생의 평균 : " + studentController.calculateAverage(studentDTO));
                }
            } else {
                System.out.println("잘못입력하셨습니다");
            }
        }
    }

    public void updateStudent() {
        String message = "1. 수정 2. 삭제";
        int userChoice = ScannerUtil.nextInt(SCANNER, message);
        if (userChoice == 1) {
            message = "수정할 학생 번호를 입력해주세요";
            int studentId = nextInt(SCANNER, message);
            update(studentId);
        }
        if (userChoice == 2) {
            message = "삭제할 학생의 번호를 입력해주세요";
            int studentId = nextInt(SCANNER, message);
            delete(studentId);
        }
    }

    public void update(int id) {
        StudentDTO studentDTO = studentController.selectOne(id);
        String message;

        message = "새로운 이름을 입력해주세요";
        studentDTO.setName(ScannerUtil.nextLine(SCANNER, message));

        message = "새로운 국어 점수를 입력해주세요";
        studentDTO.setKorean(ScannerUtil.nextInt(SCANNER, message));

        message = "새로운 영어 점수를 입력해주세요";
        studentDTO.setEnglish(ScannerUtil.nextInt(SCANNER, message));

        message = "새로운 수학 점수를입력해주세요";
        studentDTO.setMath(ScannerUtil.nextInt(SCANNER, message));

        studentController.update(studentDTO);
    }

    private void delete(int id) {
        String answer = ScannerUtil.nextLine(SCANNER, "정말로 삭제하시겠습니까 Y/N");
        if (answer.equalsIgnoreCase("Y")) {
            studentController.delete(id);
            System.out.println(id + "번 학생이 삭제 되었습니다");
        } else if (answer.equalsIgnoreCase("N")) {
            System.out.println("삭제 하지 않습니다");
        } else {
            System.out.println("잘못 입력하셨습니다");
        }
    }
}
