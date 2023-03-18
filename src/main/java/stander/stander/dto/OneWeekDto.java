package stander.stander.dto;

import lombok.Getter;
import stander.stander.model.Entity.TimeBoard;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OneWeekDto {
    List<TimeBoardDto> times = new ArrayList<>();

    public OneWeekDto(List<TimeBoard> timeBoards) {
        for(TimeBoard timeBoard : timeBoards){
            times.add(TimeBoardDto.of(timeBoard));
        }
    }
    public static OneWeekDto of(List<TimeBoard> timeBoards){
        return new OneWeekDto(timeBoards);
    }
}
