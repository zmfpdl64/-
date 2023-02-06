package stander.stander.repository;

import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Repository;
import stander.stander.model.Entity.TimeBoard;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
public class ReserveRepository implements ReserveInterface{

    private final EntityManager em;

    public ReserveRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<TimeBoard> OneWeek() {
        List<TimeBoard> resultList = em.createQuery("select m from TimeBoard m order by m.date desc", TimeBoard.class)
                .getResultList();
        return resultList;
    }

    @Override
    public void makeTimeBoard() {
        TimeBoard timeBoard = new TimeBoard();
        em.persist(timeBoard);
        em.flush();
    }

    @Override
    public void reserveTime() {

    }

    @Override
    public void cancleTime() {

    }

    public List<TimeBoard> allTimeBoard() {
        List<TimeBoard> all = em.createQuery("select m from TimeBoard m", TimeBoard.class).getResultList();
        return all;
    }
}
