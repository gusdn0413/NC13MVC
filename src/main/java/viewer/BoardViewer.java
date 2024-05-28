package viewer;

import controller.BoardController;
import controller.UserController;
import lombok.Setter;
import model.BoardDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardViewer {

    @Setter
    private BoardController boardController;
    @Setter
    private UserController userController;
    @Setter
    private Scanner scanner;
    @Setter
    private UserDTO logIn;
    @Setter
    private ReplyViewer replyViewer;

    public void showMenu(int loginId) {
        String message = "1. 글 작성하기 2. 글 목록 보기 3. 뒤로 가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                insert(loginId);
            } else if (userChoice == 2) {
                replyViewer.setLogIn(logIn);
                printList(loginId);
            } else if (userChoice == 3) {
                System.out.println("메인 화면으로 돌아갑니다");
                break;
            }
        }
    }

    private void insert(int loginId) {
        BoardDTO boardDTO = new BoardDTO();

        String message = "글의 제목을 입력해주세요";
        boardDTO.setTitle(ScannerUtil.nextLine(scanner, message));

        message = "글의 내용을 입력해주세요";
        boardDTO.setContent(ScannerUtil.nextLine(scanner, message));

        boardDTO.setWriterId(loginId);
        boardController.insert(boardDTO);
    }

    private void printList(int loginId) {
        ArrayList<BoardDTO> list = boardController.selectAll();
        for (BoardDTO boardDTO : list) {
            System.out.println("작성자 id : " + boardDTO.getWriterId());
            System.out.println("작성자 닉네임 : " + userController.selectNicknameById(boardDTO.getWriterId()));
            System.out.println("글 제목 : " + boardDTO.getTitle());
        }
        if (list.isEmpty()) {
            System.out.println("등록된 게시글이 없습니다");
        } else {
            String message = "상세보기할 글의 번호나 뒤로 가실려면 0을 입력해주세요";
            int userChoice = ScannerUtil.nextInt(scanner, message);
            while (!boardController.validateInput(userChoice)) {
                System.out.println("잘못 입력하셨습니다.");
                userChoice = ScannerUtil.nextInt(scanner, message);
            }
            if (userChoice != 0) {
                printOne(userChoice, loginId);
            }
        }
    }

    private void printOne(int id,int loginId) {
        BoardDTO boardDTO = boardController.selectOne(id);
        System.out.println("============================");
        System.out.println("글 제목 : " + boardDTO.getTitle());
        System.out.println("글 번호 : " + boardDTO.getId());
        System.out.println("글 작성자 : " + boardDTO.getWriterId());
        System.out.println("============================");
        System.out.println(boardDTO.getContent());
        System.out.println("============================");

//        nowBoard = boardDTO;
//        replyViewer.setNowBoard(nowBoard);
//
//        if (logIn.getId() == nowBoard.getWriterId()) {
        replyViewer.setLogIn(logIn);
        if (loginId == boardDTO.getWriterId()) {
            String message = "1. 수정 2. 삭제 3. 댓글 4. 뒤로가기";
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 4);
            if (userChoice == 1) {
                update(boardDTO.getId());
            } else if (userChoice == 2) {
                delete(boardDTO.getId());
            } else if (userChoice == 3) {
                replyViewer.showMenu(loginId);
            } else if (userChoice == 4) {
                printList(loginId);
            }
        } else {
            String message = "1. 댓글 2. 뒤로 가기";
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
            if (userChoice == 1) {
//                replyViewer.showMenu();
                replyViewer.showMenu(boardDTO.getId());
            } else if (userChoice == 2) {
                printList(loginId);
            }
        }
    }

    private void update(int loginId) {
        String message = "새로운 제목을 입력해주세요";
        String newTitle = ScannerUtil.nextLine(scanner, message);
        message = "새로운 내용을 입력해주세요";
        String newContent = ScannerUtil.nextLine(scanner, message);

        BoardDTO boardDTO = boardController.selectOne(loginId);
        boardDTO.setTitle(newTitle);
        boardDTO.setContent(newContent);
        boardController.update(boardDTO);
    }

    public void delete(int loginId) {
        String message = "정말 삭제하시겠습니까? Y/N";
        String answer = ScannerUtil.nextLine(scanner, message);
        if (answer.equalsIgnoreCase("Y")) {
            message = "비밀번호를 입력해주세요";
            String password = ScannerUtil.nextLine(scanner, message);
            if (password.equals(logIn.getPassword())) {
                boardController.delete(loginId);
            }
        }
    }
}
