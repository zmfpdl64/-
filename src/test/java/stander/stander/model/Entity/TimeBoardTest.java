package stander.stander.model.Entity;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TimeBoard 도메인 테스트")
@Transactional
class TimeBoardTest {

    @Test
    public void GenerateTimeBoard() {
        //Given
        Calendar calendar = Calendar.getInstance();
        LocalDateTime date= LocalDateTime.now().withNano(0).plusDays(1);
        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        date.plusYears(1);
        //When
        TimeBoard board = new TimeBoard(date);

        //Then
        System.out.println(board.date);
        System.out.println(calendar.get(Calendar.YEAR) + "년 "+ calendar.get(Calendar.MONTH) + "월 "+ calendar.get(Calendar.DATE)+"일" );
        System.out.println(board.getTimes().size());
        assertThat(board.getTimes().size()).isEqualTo(24);
    }

}