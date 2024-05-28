package main;

import model.BoardDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MySqlMain {
    public static void main(String[] args) {
        try {
            // 우리가 메소드를 선언할 때 종종 메소드 이름과 파라미터 뒤에 throws Exception 이라고 적어준다
            // 해당 코드의 의미는 이 메소드를 실행할 때 어떠한 오류가 발생할 수 있다 허고 미리 경고를 하는 것
            // 추후에 같이 해당 메소드를 사용할 때에는 아래와 같이 try/catch 구조를 사용하여
            // 해당 오류가 발생했을 때 어떻게 처리할 것인지를 적어준다.
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/board";
            String username = "root";
            String password = "Qkr1593574!s";
            Connection connection = DriverManager.getConnection(url, username, password);

            System.out.println("connection 성공");

            // 1. 현재 board 테이블의 내용을 전부 뽑아서 어레이리스트 객체에 추가하고
            // 추가하고 나서 리스트를 출력
            ArrayList<BoardDTO> list = new ArrayList<>();
            String query = "SELECT * FROM board";

            // 위에서 만든 Connection 객체를 통해서 쿼리를 보낼 때에는
            // 우리가 preparedStatement 객체를 통해서 스트링 쿼리를 보내게 된다.
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // SELECT 쿼리와 같이 PreparedStatement 의 결과가 존재하는 경우
            // 그 결과를 ResultSet 에 담게 된다
            ResultSet resultSet = preparedStatement.executeQuery();

            // resultSet 은 우리가 결과를 추출하기 위해서는 반드시 resultSet.next() 라는 것을 통하여
            // 커서의 위치를 while 문을 통해서 옮긴다.
            while (resultSet.next()) {
                BoardDTO boardDTO = new BoardDTO();
                // ResultSet 객체의 현재 위치의 값을 꺼낼 때에는 데이터 타입에 맞추어서 다음 메소드를 실행하면 된다.
                boardDTO.setId(resultSet.getInt("id"));
                boardDTO.setTitle(resultSet.getString("title"));
                boardDTO.setContent(resultSet.getString("content"));
                boardDTO.setWriterId(resultSet.getInt("writer_id"));
                boardDTO.setEntryDate(resultSet.getTimestamp("entry_date"));
                boardDTO.setModifyDate(resultSet.getTimestamp("modify_date"));
                list.add(boardDTO);
            }
            for (BoardDTO boardDTO : list) {
                System.out.println("boardDTO = " + boardDTO);
            }

            // 2. SELECT
            query = "SELECT * FROM board WHERE id =?";
            preparedStatement = connection.prepareStatement(query);

            // ? 자리에 우리가 원하는 값을 넣어주자
            // 이 때에는 데이터타입에 맞추어 메소드를 불러주면 된다.
            // 1번 자리에 물음표에 3을 넣어라
            int index = 1;

            preparedStatement.setInt(index, 3);

            // 위의 쿼리를 실행시킨 값을 resultSet 에 저장해라
            resultSet = preparedStatement.executeQuery();

            // while 문을 통하여 쿼리의 결과값을 BoardDTO 객체에 저장해보자
            BoardDTO temp = new BoardDTO();
            while (resultSet.next()) {
                temp.setId(resultSet.getInt("id"));
                temp.setTitle(resultSet.getString("title"));
                temp.setContent(resultSet.getString("content"));
                temp.setWriterId(resultSet.getInt("writer_id"));
            }
            System.out.println("temp = " + temp);

            // 3. INSERT
            query = "INSERT INTO board(title, content, writer_id) VALUES (?,?,?)";

            preparedStatement = connection.prepareStatement(query);

            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setTitle("지금 시간은 11시 18분");
            boardDTO.setContent("귀찮아");
            boardDTO.setWriterId(1);

            preparedStatement.setString(1, boardDTO.getTitle());
            preparedStatement.setString(2, boardDTO.getContent());
            preparedStatement.setInt(3, boardDTO.getWriterId());
            
            // INSERT, UPDATE, DELETE 와 같이 데이터를 입력 수정 삭제하는 쿼리는
            // executeUpdate()를 한다
//            preparedStatement.executeUpdate();

            // 4. UPDATE
            // 4번 글의 내용을 수정해보자
            BoardDTO boardDTO2 = new BoardDTO();
            boardDTO2.setId(4);
            boardDTO2.setTitle("수정된 제목은 4번글");
            boardDTO2.setContent("이 4번 글은 수정되었습니다.");

            query = "UPDATE board SET title = ?, content = ?, modify_date = NOW() WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, boardDTO2.getTitle());
            preparedStatement.setString(2, boardDTO2.getContent());
            preparedStatement.setInt(3, boardDTO2.getId());

//            preparedStatement.executeUpdate();

            // 5. DELETE
            int id = 2;
            query = "DELETE FROM board WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            // 만약 오류가 발생하면 그 오류의 내역을 출력해라
            e.printStackTrace();
        }
    }
}
