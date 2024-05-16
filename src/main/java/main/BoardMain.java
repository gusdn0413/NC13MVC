package main;

import controller.BoardController;
import controller.UserController;
import viewer.UserViewer;

import java.util.Scanner;

public class BoardMain {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UserController userController = new UserController();

        BoardController boardController = new BoardController();

        UserViewer userViewer = new UserViewer();

        userViewer.setScanner(scanner);
        userViewer.setUserController(userController);
        userViewer.setBoardController(boardController);

        userViewer.showMenu();
    }
}
