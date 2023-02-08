package stander.stander.service;

import org.apache.tomcat.jni.Local;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import stander.stander.model.Entity.Member;
import stander.stander.model.Entity.TimeBoard;
import stander.stander.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("좌석 미리 예약 서비스 테스트")
class ReserveServiceTest {

    @InjectMocks
    private ReserveService sut;

    @Mock
    private ReserveRepository rep;

    @BeforeEach
    public void before(){
        List<TimeBoard> times = getTimeBoards();
        given(rep.OneWeek()).willReturn(times);
    }

    @Test
    void getOneWeek() {
        ///Given

//        List<TimeBoard> times = getTimeBoards();
//        given(rep.OneWeek()).willReturn(times);
        //When
        List<TimeBoard> oneWeek = sut.getOneWeek();

        //Then
        then(rep).should().OneWeek();
        assertThat(oneWeek.size()).isEqualTo(7);
    }

    private static List<TimeBoard> getTimeBoards() {
        List<TimeBoard> times = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            times.add(new TimeBoard(LocalDateTime.now().withNano(0).plusDays(i)));
        }
        return times;
    }

    @Test
    void reserveTime() {
        //Given
        int start = 10, end = 14;
        int afterday = 1;
        LocalDateTime day = LocalDateTime.now().withNano(0).withNano(0).plusDays(afterday);
        Member member = getMember();
        willDoNothing().given(rep).reserveTime(member, day, start, end);

        //When
        sut.reserveTime(member, afterday, start, end);
//        rep.reserveTime(member, day, start, end);
        List<TimeBoard> oneWeek = sut.getOneWeek();

        //Then
        then(rep).should().reserveTime(member, day, start, end);
        assertThat(oneWeek.get(afterday).getTimes().get(start).getUsername()).isEqualTo("woojin");
    }

    private static Member getMember() {
        Member member = new Member();
        member.setUsername("woojin");
        member.setAge(25L);
        member.setPassword("1234");
        return member;
    }

    @Test
    void cancleTime() {
    }

    @Test
    void getAllTime() {
    }
}