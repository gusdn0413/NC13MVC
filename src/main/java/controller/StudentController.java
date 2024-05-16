package controller;

import model.StudentDTO;
import model.UserDTO;

import java.util.ArrayList;

// 컨트롤러 : 주로 사용자의 요청을 받아서 데이터를 가공하여 다시 사용자에게 보내주는 역할을한다
// 우리에게 데이터베이스가 있으면 컨트롤러가 데이터베이스로 값을 전송하거나 전송받은 값을 다시 보내주는 역할을 하지만
// DB가 없으므로 ArrayList 를 유사 DB로 사용
public class StudentController {
    private ArrayList<StudentDTO> list;
    private int nextId;

    public StudentController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    // 1. studentDTO 추가
    public void insert(StudentDTO studentDTO) {
        studentDTO.setId(nextId++);
        list.add(studentDTO);
    }

    // 2. 전체 리스트 보기
    public ArrayList<StudentDTO> selectAll() {
        return list;
    }

    // 3. 특정 DTO 가져오기 없으면 Null 반환
    public StudentDTO selectOne(int id) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(id);

        if (list.contains(studentDTO)) {
            return list.get(list.indexOf(studentDTO));
        }
        return null;
    }

    // 4. 사용자가 보낸 객체로 list 의 특정 요소 수정
    public void update(StudentDTO studentDTO) {
        int index = list.indexOf(studentDTO);
        list.set(index, studentDTO);
    }

    // 5. 특정 id 값을 가진 객체를 list 에서 삭제하는 delete()
    public void delete(int id) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(id);
        list.remove(studentDTO);
    }

    // 6. 파라미터로 들어온 학생 객체의 총점을 계산 하는 메소드
    public int calculateSum(StudentDTO studentDTO) {
        return studentDTO.getKorean() + studentDTO.getEnglish() + studentDTO.getMath();
    }

    // 7. 파라미터로 들어온 학생 객체의 평군을 계산하는 메소드
    public double calculateAverage(StudentDTO studentDTO) {
        return (double) calculateSum(studentDTO) / 3;
    }
}
