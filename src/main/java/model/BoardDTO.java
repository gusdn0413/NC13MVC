package model;

import lombok.Data;

@Data
public class BoardDTO {
    private int id;
    private String topic;
    private String content;
    private String author;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof BoardDTO) {
            BoardDTO boardDTO = (BoardDTO) o;
            return id == boardDTO.id;
        }
        return false;
    }
}
