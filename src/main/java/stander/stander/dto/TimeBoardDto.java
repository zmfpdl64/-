package stander.stander.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import stander.stander.model.Entity.Member;
import stander.stander.model.Entity.TimeBoard;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class TimeBoardDto {

    private String localDateTime;
    private List<Member> members = new ArrayList<>();

    public TimeBoardDto(TimeBoard timeBoard) {
        localDateTime = new SimpleDateFormat("%MM-%dd").format(timeBoard.getDate());
        extractTimes(timeBoard);
    }
    public static TimeBoardDto of(TimeBoard timeBoard) {
        return new TimeBoardDto(timeBoard);
    }

    private void extractTimes(TimeBoard timeBoard) {
        for(int i = 0; i < 24; i ++ ){
            Member member = timeBoard.getTimes().get(i);
            if(Objects.isNull(member)){
                members.add(null);
            }else{
                members.add(member);
            }
        }
    }
}
