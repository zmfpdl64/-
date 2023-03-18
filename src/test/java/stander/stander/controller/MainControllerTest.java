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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import stander.stander.model.Entity.Member;
import stander.stander.model.Entity.Seat;
import stander.stander.service.MemberService;
import stander.stander.service.SeatService;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
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

        int before = sitService.findAll().size();
        Seat sit1 = new Seat();
        sit1.setSeat_num("1");
        sit1.setMember(member);
        sitService.save(sit1);
        int after = sitService.findAll().size();
        Seat sit = sitService.findMember(member);
        assertThat(before+1).isEqualTo(after);
        log.info("sit.member_id = {}", sit.getMember());
//        Assertions.assertThat(1).isEqualTo(2);

    }
}