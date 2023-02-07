package stander.stander.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stander.stander.repository.*;
import org.springframework.transaction.annotation.Transactional;
import stander.stander.model.Entity.Member;
import stander.stander.model.Entity.Seat;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Transactional
@SpringBootTest
public class JpaSitRepositoryTest {

    @Autowired private JpaRepository jpaRepository;



    Logger log = LoggerFactory.getLogger(JpaRepositoryTest.class);

    @BeforeEach
    public void before() {
        Member member = new Member();
        Member member1 = new Member();
        member.setUsername("woojin");
        member.setId(1L);
        member.setPassword("1234");
        member.setAge(25L);
        member.setGender("male");

        member1.setUsername("jihyun");
        member1.setPassword("12345");
        member1.setId(2L);
        member1.setAge(24L);
        member1.setGender("male");
        jpaRepository.save(member);
        jpaRepository.save(member1);
    }

//    @Test
//    public void save() {
//    }

    @Test
    public void findByMember() {

        //Given
        Long id = 1L;
        String name = "woojin";
        //When
        Member findmem = jpaRepository.findById(id);

        //Then
        assertThat(findmem.getUsername()).isEqualTo(name);

    }

    @Test
    public void merge() {
        //Given
        Long id = 1L;
        Member mem = jpaRepository.findById(id);
        String updateName = "woojjang";
        mem.setUsername(updateName);
        //When
        Member update = jpaRepository.merge(mem);

        //Then
        assertThat(updateName).isEqualTo(update.getUsername());
    }


    @Test
    public void findById() {
        //Given
        Member member = jpaRepository.findById(1L);
        //When

        //Then
        assertThat(member.getUsername()).isEqualTo("woojin");
    }

    @Test
    public void clearById() {
        
    }



    @Test
    public void deleteMember() {
    }

    @Test
    public void findAll() {
        //Given
        int count = jpaRepository.findAll().size();
        //When
        Member member = new Member();
        jpaRepository.save(member);
        int updatesize = jpaRepository.findAll().size();
        //Then
        assertThat(count+1).isEqualTo(updatesize);


    }
}