package stander.stander.repository;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import stander.stander.model.Entity.TimeBoard;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@Transactional
@SpringBootTest
class ReserveRepositoryTest {

    public ReserveRepositoryTest(@Autowired  ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }

    ReserveRepository reserveRepository;

    @BeforeEach
    public void before(){
        for(int i = 0; i < 7; i++) {
            reserveRepository.makeTimeBoard();
        }
    }

    @Test
    void oneWeek() {
        List<TimeBoard> timeBoards = reserveRepository.OneWeek();
        assertThat(timeBoards.size()).isEqualTo(7);
    }

    @Test
    void makeTimeBoard() {
        //Given
        int size = reserveRepository.allTimeBoard().size();
        //When
        reserveRepository.makeTimeBoard();
        int update = reserveRepository.allTimeBoard().size();
        //Then
        assertThat(size+1).isEqualTo(update);
    }

    @Test
    void reserveTime() {
    }

    @Test
    void cancleTime() {
    }

    @Test
    void findAll() {
        //Given

        //When
        List<TimeBoard> timeBoards = reserveRepository.allTimeBoard();

        //Then
        assertThat(timeBoards.size()).isEqualTo(7);
    }
}