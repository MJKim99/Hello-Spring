package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}

/**
 * Data Jpa는 이렇게만 구현하면 끝난다.
 * JpaRepository를 갖고있으면 스프링 데이터 jpa가 자동으로 구현체를 만들어주고, 스프링 빈도 자동으로 등록해준다.
 * 그래서 SpringConfig에서도 그냥 가져다쓰면 된다.
 */