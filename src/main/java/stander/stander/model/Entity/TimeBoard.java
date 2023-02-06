package stander.stander.model.Entity;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@RequiredArgsConstructor
@Table(name = "timeboard")
public class TimeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @OneToMany
    @Column(name = "times")
    private Map<Integer, Member> times= makeTimes();

    @Column(name = "date")
    LocalDateTime date = LocalDateTime.now();

    private Map<Integer, Member> makeTimes() {
        Map<Integer, Member> times= new HashMap<>();
        for(int i = 0; i < 24; i++) {
            times.put(i, null);
        }
        return times;
    }
}
