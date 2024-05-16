package model;

// 모델 : 사용자가 입력한 값을 자바 객채의 형태로 바꾸거나
// 아니면 데이터 베이스로부터 받은 값을 자바 객체의 형태로 바꿀 때 사용되는 클래스의 종류
// DTO  :Data Transfer Object
// VO : Value Object

import lombok.Data;
// lombok 은 게터/세터, toString() 등 모델 클래스들에게 필요한 기본 메서드를 자동으로 생성해주는 라이브러리
// 필요에 따라서는 @Getter @Setter 등 필요한 것을 사용 가능

// 모델 클래스에는 해당 데이터를 객체화 시킬때 필요한 필드, 겟터/셋터, equals() 정도의 메소드만 들어간다.

@Data
public class StudentDTO {
    private int id;
    private String name;
    private int korean;
    private int english;
    private int math;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof StudentDTO) {
            StudentDTO s = (StudentDTO) o;
            return id == s.id;
        }
        return false;
    }
}














