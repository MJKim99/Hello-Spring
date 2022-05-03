package hello.hellospring;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration // 어노테이션 추가
/**
 * 스프링이 뜰 때 Configuration을 읽는다.
 */
public class SpringConfig {

//    @Autowired DataSource dataSource; // 필드 주입 방법

//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    @PersistenceContext // 생성자 주입하는 대신 이거만 사용해도 되긴 함
//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    /**
     * Spring Data-Jpa
     * 이렇게만 하면 스프링 데이터 JPA가 만든 구현체가 자동으로 등록됨
     */
    private final MemberRepository memberRepository;

    /**
     *
     */
    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean // 스프링 빈을 등록할거야
    /**
     * 아래 로직을 호출해서 스프링 빈에 등록해준다.
     * 그럼 MemberService가 스프링 빈에 등록된다.
     * 근데 MemberService에서는 생성자에 Repository를 넣어주어야 한다. (Ctrl + P)
     */
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }

    // Spring Data-jpa
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }


    // 컴포넌트 스캔 방식이 아닌 직접 빈을 등록하는 방식을 사용하면 => 스프링의 DI를 사용하면 기존 코드 손대지 않고 설정만 수정만으로 구현 클래스 변경 가능
    // 아래처럼 Memory~Re()를 사용하다가 바로 Jdbc~Re()로 변경할 수 있다는 이점 존재
    // 이 부분에서 스프링 컨테이너가 다형성을 잘 지원해준다고 말할 수 있다.
    // 또한 이 방법을 사용함으로써 개방-폐쇄 원칙(OCP, Oepn-Closed Principle)을 지킬 수 있다.
    // 확장에는 열려있고, 수정(,변경)에는 닫혀있다. <- 사실상 말이 되지 않지만 객체지향에서 말하는 다형성을 잘 활용하면 이렇게 가능.
//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository(); // Memory는 구현체. 인터페이스는 new가 안 되기 때문에 구현체를 new 하는 것.
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);

//    }


}
