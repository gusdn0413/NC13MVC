package main;

import java.text.SimpleDateFormat;
import java.util.Date;

// 자바에서 시간 날짜 다루기
// 자바에서의 시간은 그리니치 표준 시간대 1970년 1월 1일 0시 0분 0초를 기준으로 하여
// 이후 시간대는 +, 이전 시간대는 -로 Long 데이터타입을 기준으로 표시한다
// java.util.Date, java.util.Calendar 를 사용하여 현재 시간을 저장 변겅 하고
// SimpleDateFormat 을 사용하여 출력하는 방법을 알아보자
public class DateTimeMain {
    public static void main(String[] args) {

        Date date = new Date();
        System.out.println("date = " + date);
        date.setHours(20);
        System.out.println("date = " + date);

        // SimpleDateFormat :
        // y : 연도 M : 월 d : 일 h : 12시간 체제의 시각 H : 24시간 체제의 시각 m : 분 s : 초 S : 밀리초
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd E요일 HH:mm:ss");
        System.out.println("simpleDateFormat.format(date) = " + simpleDateFormat.format(date));

        simpleDateFormat = new SimpleDateFormat("yyMMdd HHmmss");
        simpleDateFormat.setLenient(false);
//        String time = "981235 233030";
        String time = "981231 233030";
        try {

            date = simpleDateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("date = " + date);
    }
}
