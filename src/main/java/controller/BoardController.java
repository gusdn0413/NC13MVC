package controller;

import model.BoardDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardController {
    private ArrayList<BoardDTO> list;
    private int nextId;
    private Scanner scanner;

    public BoardController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public void insert(BoardDTO boardDTO) {
        boardDTO.setId(nextId++);
        list.add(boardDTO);
    }

    public ArrayList<BoardDTO> selectAll() {
        return list;
    }

    public BoardDTO selectOne(int id) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(id);
        if (list.contains(boardDTO)) {
            return list.get(list.indexOf(boardDTO));
        }
        return null;
    }

    public void delete(int id) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(id);
        list.remove(boardDTO);
    }

    public void update(BoardDTO boardDTO) {
        int index = list.indexOf(boardDTO);
        list.set(index, boardDTO);
    }
}
