package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; // jpa는 EntityManager로 모든 게 동작한다.
    // gradle에서 data-jpa를 라이브러리로 받았다. 그러면 스프링 부트가 자동으로 EntityManager를 생성해준다. 현재 데이터베이스랑 연결해서.

    // 그래서 그냥 만들어진 걸 injection 해주면 된다.
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // persist() 에서 모든 걸 다 해줌. setId()까지 다 해줌
        em.persist(member); // persist: 영속하다. 영구 저장하다.
        return member; // persist()는 return 값 없음.
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // 조회 시 find 사용. 조회할 타입(Member.class), 식별자(pk 값)(id)
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result =  em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
//        List<Member> result = em.createQuery("select m from Member m", Member.class)
//                .getResultList();
//        return result;
        // Ctrl + Alt + N. result 끝에 커서 두고
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
        /**
         * 보통 테이블 대상으로 쿼리를 날리는데, 그러지 않고 객체를 대상으로(정확히는 Entity를 대상으로) 쿼리를 날리는 것이다.
         * 그럼 SQL로 번역된다.
         * 여기서는 Member를 대상으로 한다.
         * select의 대상이 Member Entity (객체 자체) 인 것이다.
         * 매핑도 다 되어있는 것
         */
    }
}
