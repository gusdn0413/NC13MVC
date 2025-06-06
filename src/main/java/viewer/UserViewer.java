package viewer;

import controller.UserController;
import lombok.Setter;
import model.UserDTO;
import util.ScannerUtil;

import java.util.Scanner;

public class UserViewer {
    @Setter
    private UserController userController;
    @Setter
    private Scanner scanner;
    @Setter
    private UserDTO logIn;
    @Setter
    private BoardViewer boardViewer;

    public void showIndex() {
        String message = "1. 로그인 2. 회원가입 3. 프로그램 종료";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                auth();
                if (logIn != null) {
                    // 회원 메뉴 실행
                    boardViewer.setLogIn(logIn);
                    showMenu();
                }
            } else if (userChoice == 2) {
                register();
            } else if (userChoice == 3) {
                System.out.println("사용해주셔서 감사합니다.");
                break;
            }
        }
    }

    private void auth() {
        String message;
        message = "아이디를 입력해주세요.";
        String username = ScannerUtil.nextLine(scanner, message);

        message = "비밀번호를 입력해주세요.";
        String password = ScannerUtil.nextLine(scanner, message);

        logIn = userController.auth(username, password);

        if (logIn == null) {
            System.out.println("잘못 입력하셨습니다. 로그인 정보를 다시 확인해주세요.");
        }
    }

    public void register() {

        String message = "사용하실 아이디를 입력해주세요";
        String username = ScannerUtil.nextLine(scanner, message);
        if (userController.validateUsername(username)) {

            message = "사용할 비밀번호를 입력해주세요";
            String password = ScannerUtil.nextLine(scanner, message);

            message = "사용할 닉네임을 입력해주세요";
            String nickname = ScannerUtil.nextLine(scanner, message);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setNickname(nickname);

            userController.insert(userDTO);
        } else {
            System.out.println("중복 된 아이디는 사용할 수 없습니다.");
        }
    }

    private void showMenu() {
        String message = "1. 게시판으로 2. 회원 정보 수정 3. 로그 아웃";
        while (logIn != null) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                boardViewer.showMenu(logIn.getId());
            } else if (userChoice == 2) {
                printInfo();

            } else if (userChoice == 3) {
                logIn = null;
                System.out.println("성공적으로 로그아웃");
                break;
            }
        }
    }

    private void printInfo() {
        System.out.println("============================");
        System.out.println(logIn.getNickname() + " 회원님의 정보");

        System.out.println("============================");
        System.out.println("아이디 : " + logIn.getId());
        System.out.println("닉네임 : " + logIn.getNickname());

        System.out.println("============================");
        String message = "1. 회원 정보 수정 2. 회원 탈퇴 3. 뒤로 가기";

        int userChoice = ScannerUtil.nextInt(scanner, message);
        if (userChoice == 1) {
            update();
        } else if (userChoice == 2) {
            delete();

        } else if (userChoice == 3) {

        }
    }

    private void update() {
        String message = "새로운 닉네임을 입력해주세요";
        String newNickname = ScannerUtil.nextLine(scanner, message);
        message = "새로운 비밀번호를 입력해주세요";
        String newPassword = ScannerUtil.nextLine(scanner, message);
        message = "기존 비밀번호를 입력해주세요";
        String oldPassword = ScannerUtil.nextLine(scanner, message);

        if (oldPassword.equals(logIn.getPassword())) {
            logIn.setNickname(newNickname);
            logIn.setPassword(newPassword);

            userController.update(logIn);
        } else {
            System.out.println("기존 비밀번호와 달라서 수정할 수 없습니다");
        }
    }

    private void delete() {
        String message = "정말로 탈퇴하시겠습니가? Y/N";
        String answer = ScannerUtil.nextLine(scanner, message);
        if (answer.equalsIgnoreCase("Y")) {
            message = "비밀번호를 입력해주세요";
            String password = ScannerUtil.nextLine(scanner, message);
            if (password.equals(logIn.getPassword())) {
                userController.delete(logIn.getId());
                logIn = null;
            }
        }
    }
}