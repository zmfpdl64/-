package stander.stander.repository;

import net.bytebuddy.asm.MemberRemoval;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import stander.stander.model.Entity.Member;
import stander.stander.model.Entity.TimeBoard;
import stander.stander.repository.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ReserveRepositoryTest {

    public ReserveRepositoryTest(@Autowired  ReserveRepository reserveRepository,
                                 @Autowired JpaRepository jpaRepository) {
        this.reserveRepository = reserveRepository;
        this.jpaRepository = jpaRepository;
    }

    ReserveRepository reserveRepository;
    JpaRepository jpaRepository;

    @BeforeEach
    public void before(){
        for(int i = 1; i <= 7; i++) {
            reserveRepository.makeTimeBoard(LocalDateTime.now().plusDays(i));
        }
    }

    @DisplayName("1주 단위로 타임테이블 불러오기")
    @Test
    void oneWeek() {
        //Given

        //When
        List<TimeBoard> timeBoards = reserveRepository.OneWeek();

        //Then
        assertThat(timeBoards.size()).isEqualTo(7);
    }

    @DisplayName("타임테이블 생성하기")
    @Test
    void makeTimeBoard() {
        //Given
        int size = reserveRepository.allTimeBoard().size();
        //When
        reserveRepository.makeTimeBoard(LocalDateTime.now());
        int update = reserveRepository.allTimeBoard().size();
        //Then
        assertThat(size+1).isEqualTo(update);
    }

    @DisplayName("타임테이블에 시간 예약하기")
    @Test
    void reserveTime() {
        //Given
        LocalDateTime localDateTime = LocalDateTime.now();
        Member member = (Member)jpaRepository.findById(1L);

        //When
        reserveRepository.reserveTime(member, localDateTime, 4, 6);
        TimeBoard updateBoard = reserveRepository.getTimeBoard(localDateTime);

        //Then
        assertThat(updateBoard.getTimes().get(4).getName()).isEqualTo(member.getName());
    }

    @DisplayName("특정 날짜 타임테이블 조회하기")
    @Test
    void getTimeBoard() {
        //Given
        LocalDateTime localDateTime = LocalDateTime.now();
        //When
        TimeBoard getTimeBoard = reserveRepository.getTimeBoard(localDateTime);

        //Then
        assertThat(getTimeBoard).isEqualTo(getTimeBoard);
    }

    @Test
    void cancleTime() {
    }

    @DisplayName("모든 타임테이블 불러오기")
    @Test
    void findAll() {
        //Given

        //When
        List<TimeBoard> timeBoards = reserveRepository.allTimeBoard();

        //Then
        assertThat(timeBoards.size()).isEqualTo(7);
    }
}