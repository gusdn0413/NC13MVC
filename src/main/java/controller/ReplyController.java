package controller;

import model.BoardDTO;
import model.ReplyDTO;

import java.util.ArrayList;

public class ReplyController {
    private ArrayList<ReplyDTO> list;
    private int nextId;

    public ReplyController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public void insert(ReplyDTO replyDTO) {
        replyDTO.setId(nextId++);
        list.add(replyDTO);
    }

    public ArrayList<ReplyDTO> selectAll() {
        return list;
    }

    public ReplyDTO selectOne(int id) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setId(id);
        return list.get(list.indexOf(replyDTO));
    }


    public void update(ReplyDTO replyDTO) {
        list.set(list.indexOf(replyDTO), replyDTO);
    }

    public boolean validateInput(int input) {
        if (input == 0) {
            return true;
        }

        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setId(input);
        return list.contains(replyDTO);
    }

    public void delete(int id) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setId(id);

        list.remove(replyDTO);
    }
}
