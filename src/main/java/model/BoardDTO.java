package model;

import lombok.Data;

import java.util.Date;

@Data
public class BoardDTO {
    private int id;
    private String title;
    private String content;
    private int writerId;
    private Date entryDate;
    private Date modifyDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof BoardDTO boardDTO) {
            return id == boardDTO.id;
        }
        return false;
    }
}
