package stander.stander.service;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import stander.stander.model.Entity.Member;
import stander.stander.model.Entity.TimeBoard;
import stander.stander.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class ReserveService {

    @Autowired private ReserveRepository reserveRepository;

    public List<TimeBoard> getOneWeek(){
        return reserveRepository.OneWeek();
    }

    public void makeTimeBoard(int i) {
        LocalDateTime time = LocalDateTime.now().withNano(0).plusDays(i);
        reserveRepository.makeTimeBoard(time);
    }

    public void reserveTime(Member member, int afterDay, int start, int end) {
        LocalDateTime reserveDate = LocalDateTime.now().withNano(0).plusDays(afterDay);
        reserveRepository.reserveTime(member, reserveDate, start, end);
    }

    public void cancleTime(int afterDay, Member member){
        reserveRepository.cancleTime(LocalDateTime.now().withNano(0).plusDays(afterDay), member);
    }

    public List<TimeBoard> getAllTime(){
        return reserveRepository.allTimeBoard();
    }


}
