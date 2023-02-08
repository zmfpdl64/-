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
import static org.junit.jupiter.api.Assertions.assertNull;
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

    @DisplayName("1주일 정도 타임보드 가져오기 테스트")
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

    @DisplayName("다음날 10시 부터 14시까지의 예약 잡기 테스트")
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
    }

    private static Member getMember() {
        Member member = new Member();
        member.setUsername("woojin");
        member.setAge(25L);
        member.setPassword("1234");
        return member;
    }

    @DisplayName("10시부터 14시 예약한것 취소하기")
    @Test
    void cancleTime() {
        //Given
        int start = 10, end = 14;
        int afterday = 1;
        LocalDateTime day = LocalDateTime.now().withNano(0).withNano(0).plusDays(afterday);
        Member member = getMember();
        willDoNothing().given(rep).reserveTime(member, day, start, end);
        willDoNothing().given(rep).cancleTime(day, member);

        //When
        sut.reserveTime(member, afterday, start, end);
        sut.cancleTime(afterday, member);
        List<TimeBoard> oneWeek = sut.getOneWeek();

        //Then
        then(rep).should().cancleTime(day, member);
        assertNull(oneWeek.get(1).getTimes().get(1));
    }

    @DisplayName("모든 타임보드 불러오기")
    @Test
    void getAllTime() {
        //Given
        given(rep.allTimeBoard()).willReturn(getTimeBoards());
        //When
        List<TimeBoard> allTime = sut.getAllTime();
        sut.getOneWeek();

        //Then
        then(rep).should().allTimeBoard();
        assertThat(allTime.size()).isEqualTo(7);

    }
}