package stander.stander.repository;

import stander.stander.model.Entity.Member;
import stander.stander.model.Entity.TimeBoard;

import java.time.LocalDateTime;
import java.util.List;

public interface ReserveInterface {

    public List<TimeBoard> OneWeek();


    public void makeTimeBoard(LocalDateTime localDateTime);


    void reserveTime(Member member, LocalDateTime dateTime, int start, int end);

    public void cancleTime(LocalDateTime localDateTime, Member member);
}
