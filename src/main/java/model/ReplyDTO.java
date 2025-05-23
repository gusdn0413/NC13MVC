package model;

import lombok.Data;

@Data
public class ReplyDTO {
    private int id;
    private String content;
    private int writerId;
    private int boardId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ReplyDTO) {
            ReplyDTO replyDTO = (ReplyDTO) o;
            return id == replyDTO.id;
        }
        return false;
    }
}
