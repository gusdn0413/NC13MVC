package viewer;

import controller.ReplyController;
import controller.UserController;
import lombok.Setter;
import model.BoardDTO;
import model.ReplyDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class ReplyViewer {

    @Setter
    private Scanner scanner;
    @Setter
    private UserDTO logIn;
    @Setter
    private ReplyController replyController;
    @Setter
    private UserController userController;

    //    public void showMenu() {
    public void showMenu(int boardId) {
        String message = "1. 댓글 달기 2. 댓글 목록 보기 3. 뒤로 가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                insert(boardId);
            } else if (userChoice == 2) {
                printList(boardId);
            } else if (userChoice == 3) {
                System.out.println("게시판으로 돌아갑니다");
                break;
            }
        }
    }

    public void insert(int boardId) {
//    public void insert() {
        ReplyDTO replyDTO = new ReplyDTO();

        String message = "댓글을 입력하세요";
        String content = ScannerUtil.nextLine(scanner, message);

        replyDTO.setContent(content);
        replyDTO.setWriterId(logIn.getId());
        replyDTO.setBoardId(boardId);
        replyController.insert(replyDTO);
    }

    public void printList(int boardId) {
//    public void printList() {
        ArrayList<ReplyDTO> list = replyController.selectAll();
        for (ReplyDTO replyDTO : list) {
            System.out.println("작성자 id : " + replyDTO.getWriterId());
            System.out.println("작성자 닉네임 : " + userController.selectNicknameById(replyDTO.getWriterId()));
            System.out.println("게시글 id : " + replyDTO.getBoardId());
        }
        if (list.isEmpty()) {
            System.out.println("등록된 댓글이 없습니다");
        } else {
            String message = "댓글을 자세히 보시려면 id를 입력하시거나 뒤로가시려면 0 을 입력하세요";
            int userChoice = ScannerUtil.nextInt(scanner, message);
            while (!replyController.validateInput(userChoice)) {
                System.out.println("잘못 입력하셨습니다");
                userChoice = ScannerUtil.nextInt(scanner, message);
            }

            if (userChoice != 0) {
                printOne(userChoice, boardId);
            }
        }
    }

    private void printOne(int id, int boardId) {
        ReplyDTO replyDTO = replyController.selectOne(id);
        System.out.println("==========================");
        System.out.println("댓글 작성자 닉네임 : " + userController.selectNicknameById(replyDTO.getWriterId()));
        System.out.println("댓글 id : " + replyDTO.getId());
        System.out.println("==========================");
        System.out.println("댓글 내용 : " + replyDTO.getContent());
        System.out.println("==========================");

        if (logIn.getId() == replyDTO.getWriterId()) {
            String message = "1. 수정 2. 삭제 3. 뒤로가기";
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                update(replyDTO.getId());
            } else if (userChoice == 2) {
                delete(replyDTO.getId());
            } else if (userChoice == 3) {
                printList(boardId);
            }
        } else {
            String message = "1. 뒤로 가기";
            ScannerUtil.nextInt(scanner, message, 1, 1);
            printList(boardId);
        }
    }

    public void update(int replyId) {
        String message = "새로운 댓글을 입력해주세요";
        String newContent = ScannerUtil.nextLine(scanner, message);
        ReplyDTO replyDTO = replyController.selectOne(replyId);
        replyDTO.setContent(newContent);
        replyController.update(replyDTO);
    }

    public void delete(int replyId) {
        String message = "정말 삭제하시겠습니까? Y/N";
        String answer = ScannerUtil.nextLine(scanner, message);
        if (answer.equalsIgnoreCase("Y")) {
            message = "비밀번호를 입력해주세요";
            String password = ScannerUtil.nextLine(scanner, message);
            if (password.equals(logIn.getPassword())) {
                replyController.delete(replyId);
                System.out.println("댓글이 삭제되었습니다");
            } else {
                System.out.println("비밀번호가 틀렸습니다");
            }
        } else {
            System.out.println("취소하셨습니다");
        }
    }
}
