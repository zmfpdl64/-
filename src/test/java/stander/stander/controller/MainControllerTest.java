package stander.stander.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import stander.stander.model.Entity.Member;
import stander.stander.model.Entity.Seat;
import stander.stander.service.MemberService;
import stander.stander.service.SeatService;

import java.util.Comparator;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
//@WebMvcTest(controllers = MainController.class)
class MainControllerTest {

//    @Autowired
//    private MockMvc mvc;

    Logger log = LoggerFactory.getLogger(MainControllerTest.class);

    @Autowired
    MemberService memberService;
    @Autowired
    SeatService sitService;

    @Test
    @Transactional
    public void post_join() {
        Member member = newMember();
        memberService.join(member);

        Member findmember = memberService.findById(member.getId());
        Assertions.assertThat(member.getUsername()).isEqualTo(findmember.getUsername());
        Assertions.assertThat(member.getPassword()).isEqualTo(findmember.getPassword());
    }

    private Member newMember() {
        Member member = new Member();
        memberService.join(member);
        member.setUsername("이우진");
        member.setPassword("1234");
        return member;
    }

    @Test
    @Transactional
    public void reserve() {
        Member member = newMember();
        memberService.join(member);

        Seat sit1 = new Seat();
        sit1.setId(1L);
        sit1.setSeat_num("1");
        sit1.setMember(member);
        sitService.save(sit1);
        Seat sit = sitService.findMember(member);
        Assertions.assertThat(sit.getId()).isEqualTo(1L);
        log.info("sit.member_id = {}", sit.getMember());

        sitService.clearOne(member);
        Seat cleansit = sitService.findById(1L);
        Assertions.assertThat(cleansit.getMember()).isEqualTo(null);
        log.info("sit.member_id = {}", cleansit.getMember());

//        Assertions.assertThat(1).isEqualTo(2);

    }
}