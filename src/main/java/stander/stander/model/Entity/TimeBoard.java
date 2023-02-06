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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @OneToMany
    @Getter
    @Setter
    @Column(name = "times")
    private Map<Integer, Member> times= makeTimes();

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
    }
}
