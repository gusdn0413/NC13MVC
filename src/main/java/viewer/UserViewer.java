package viewer;

import controller.BoardController;
import controller.UserController;
import lombok.Setter;
import model.BoardDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class UserViewer {

    @Setter
    private UserController userController;
    @Setter
    private Scanner scanner;
    @Setter
    private UserDTO loginId;
    @Setter
    private BoardController boardController;

    public void showMenu() {
        String message = "1. 로그인 2. 회원가입 3. 프로그램 종료";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                auth();

                if (loginId != null) {
                    showIndex();
                }
            } else if (userChoice == 2) {
                register();
            } else if (userChoice == 3) {
                System.out.println("사용 감사");
                break;
            }
        }
    }

    public void showIndex() {
        String message = "1. 게시글 쓰기 2. 게시글 보기 3. 게시글 삭제 4. 회원정보 수정 5. 로그아웃";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                writeBoard();
            } else if (userChoice == 2) {
                showBoard();
            } else if (userChoice == 3) {
                message = "삭제할 게시글의 번호를 입력해주세요";
                int boardId = ScannerUtil.nextInt(scanner, message);
                deleteBoard(boardId);
            } else if (userChoice == 4) {
                update(loginId.getId());
                if (this.loginId == null) {
                    break;
                }
            } else if (userChoice == 5) {
                this.loginId = null;
                break;
            }
        }
    }

    private void auth() {
        String message;

        message = "아이디를 입력해주세요";
        String username = ScannerUtil.nextLine(scanner, message);

        message = "비밀번호를 입력해주세요";
        String password = ScannerUtil.nextLine(scanner, message);

        UserDTO loginId = userController.auth(username, password);
        if (loginId == null) {
            System.out.println("잘못 입력하셨습니다. 로그인 정보를 다시 확인해주세요");
        } else {
            this.loginId = loginId;
        }
    }

    public void register() {
        String message;
        message = "아이디를 입력해주세요";
        String username = ScannerUtil.nextLine(scanner, message);
        message = "비밀번호를 입력해주세요";
        String password = ScannerUtil.nextLine(scanner, message);
        message = "닉네임을 입력해주세요";
        String nickname = ScannerUtil.nextLine(scanner, message);

        if (userController.validateUsername(username)) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setNickname(nickname);
            userController.insert(userDTO);
            System.out.println("회원 가입이 완료되었습니다");
        } else {
            System.out.println("중복하는 회원 이름입니다");
        }
    }

    public void delete() {
        String message = "정말 삭제 하시겠습니까? Y/N";
        String answer = ScannerUtil.nextLine(scanner, message);
        if (answer.equalsIgnoreCase("Y")) {
            message = "삭제 하시려면 비밀번호를 입력해주세요";
            String password = ScannerUtil.nextLine(scanner, message);
            if (password.equals(loginId.getPassword())) {
                userController.delete(loginId.getId());
                System.out.println("삭제되었습니다");
                this.loginId = null;
            } else {
                System.out.println("잘못 입력하셨습니다");
            }
        } else if (answer.equalsIgnoreCase("N")) {
            System.out.println("회원 삭제를 취소합니다.");
        }
    }

    public void update(int id) {
        String message;
        message = "1. 회원 정보 수정 2. 삭제";
        int userChoice = ScannerUtil.nextInt(scanner, message);
        if (userChoice == 1) {
            UserDTO userDTO = userController.selectOne(id);

            message = "새로운 비밀번호를 입력해주세요";
            userDTO.setPassword(ScannerUtil.nextLine(scanner, message));

            message = "새로운 닉네임을 입력해주세요";
            userDTO.setNickname(ScannerUtil.nextLine(scanner, message));

            userController.update(userDTO);
        } else if (userChoice == 2) {
            delete();
        }
    }

    public void writeBoard() {
        String message;
        message = "제목을 입력해주세요";
        String topic = ScannerUtil.nextLine(scanner, message);
        message = "내용을 입력해주세요";
        String content = ScannerUtil.nextLine(scanner, message);

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTopic(topic);
        boardDTO.setContent(content);
        boardDTO.setAuthor(loginId.getNickname());

        boardController.insert(boardDTO);
    }


    private void showBoard() {
        ArrayList<BoardDTO> boardDTOS = boardController.selectAll();
        String message;
        message = "1. 전체 보기 2. 특정 게시물 보기 3. 게시글 수정";
        int userChoice = ScannerUtil.nextInt(scanner, message);
        if (userChoice == 1) {
            for (BoardDTO boardDTO : boardDTOS) {
                System.out.println(
                        boardDTO.getId() + "번 게시글의 제목 :" +
                                boardDTO.getTopic() + " 내용 : " +
                                boardDTO.getContent() + " 작가 : " +
                                boardDTO.getAuthor());
                System.out.println("=================================");
            }
        } else if (userChoice == 2) {
            message = "게시글의 번호를 입력해주세요";
            int boardId = ScannerUtil.nextInt(scanner, message);
            BoardDTO boardDTO = boardController.selectOne(boardId);
            if (boardDTO == null) {
                System.out.println("해당 번호의 게시글이 없습니다");
                System.out.println("=================================");
            } else {
                System.out.println(boardId + "번 게시글 제목 : " + boardDTO.getTopic());
                System.out.println(boardId + "번 게시글 내용 : " + boardDTO.getContent());
                System.out.println(boardId + "번 게시글 작가 : " + boardDTO.getAuthor());
                System.out.println("=================================");
            }
        } else if (userChoice == 3) {
            message = "수정할 게시글의 번호를 입력해주세요";
            int boardId = ScannerUtil.nextInt(scanner, message);
            if (boardController.selectOne(boardId) == null) {
                System.out.println("게시글이 존재하지 않습니다");
            } else {
                updateBoard(boardId);
            }
        } else {
            System.out.println("=================================");
            System.out.println("잘못된 번호입니다");
        }
    }

    public void updateBoard(int id) {
        BoardDTO boardDTO = boardController.selectOne(id);
        String message;
        message = "새로운 제목을 입력해주세요";
        boardDTO.setTopic(ScannerUtil.nextLine(scanner, message));
        message = "새로운 내용을 입력해주세요";
        boardDTO.setContent(ScannerUtil.nextLine(scanner, message));

        boardController.update(boardDTO);
    }

    public void deleteBoard(int id) {
        String answer = ScannerUtil.nextLine(scanner, "정말로 삭제하시겠습니까 Y/N");
        if (answer.equalsIgnoreCase("Y")) {
            boardController.delete(id);
            System.out.println(id + "번 게시글이 삭제 되었습니다");
        } else if (answer.equalsIgnoreCase("N")) {
            System.out.println("삭제 하지 않습니다");
        } else {
            System.out.println("잘못 입력하셨습니다");
        }
    }
}
