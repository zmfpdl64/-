package stander.stander.repository;

import stander.stander.model.Entity.TimeBoard;

import java.util.List;

public interface ReserveInterface {

    public List<TimeBoard> OneWeek();

    public void makeTimeBoard();

    public void reserveTime();

    public void cancleTime();
}
