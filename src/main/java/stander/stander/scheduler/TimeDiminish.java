package stander.stander.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stander.stander.model.Entity.Member;
import stander.stander.model.Entity.Seat;
import stander.stander.service.MemberService;
import stander.stander.service.SeatService;
import stander.stander.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class TimeDiminish {

    @Autowired
    private SeatService seatService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ReserveRepository reserveRepository;

    @Scheduled(cron = "0/1 * * * * *")
    public void timeDiminish() {
        List<Seat> useSeat = seatService.findUseSeat();
        if(!Objects.isNull(useSeat)) {
            for (Seat seat : useSeat) {
                int use_time = ((int) new Date().getTime() - (int) seat.getCheck_in().getTime()) / 1000;
//                log.info("use_time = {}", use_time);

                int time = seat.getMember().getTime() - use_time;
//                log.info("time = {}", time);

                if (seat.getMember().getTime() == 0) {
                    Member member = seat.getMember();
                    member.setQr(null);
                    seatService.clearOne(member);
                    memberService.modify(member);

                } else {
                    seat.getMember().setTime(seat.getMember().getTime() - 1);
                    memberService.modify(seat.getMember());
                }

            }
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // 초, 분, 시, 일, 달, 요일
    public void createReserveTable() {
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0).plusDays(1);
        reserveRepository.makeTimeBoard(localDateTime);
    }

}
