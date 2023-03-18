package stander.stander.model.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


@Entity
@RequiredArgsConstructor
@Table(name = "timeboard")
public class TimeBoard {
    @Id
    @Column(name="timeboard_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @OneToMany()
    @Getter
    @Setter
    @JoinColumn(name="timeboard_id")
    private Map<Integer, Member> times;

    @Getter
    @Column(name = "date")
    LocalDateTime date;

    private Map<Integer, Member> makeTimes() {
        Map<Integer, Member> times= new HashMap<>();
        for(int i = 0; i < 24; i++) {
            times.put(i, null);
        }
        return times;
    }

    public TimeBoard(LocalDateTime date) {
        this.date = date;
        this.times = makeTimes();
    }
}
