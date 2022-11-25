package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testEntity() throws Exception {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        // when

        List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }

        // then
    }

    @Test
    public void doublePKPersist() throws Exception {
        // given
        Member member = new Member();
        //member.setId(100L);
        em.persist(member);

        em.flush();
        System.out.println("----- 1 -----");
        em.clear();
        System.out.println("----- 2 -----");
        System.out.println("member.getId(): " + member.getId());

        Member newMember = new Member();
        newMember.setId(member.getId());
        em.persist(newMember);
        System.out.println("----- 3 -----");
        // when

        // then
    }

    @Test
    public void testMergeMember() throws Exception {
        // given
        Member member = new Member();
        member.setId(100L);

        Member mergeMember = em.merge(member);
        System.out.println("same? " + (member == mergeMember));
        // when

        // then
    }

}