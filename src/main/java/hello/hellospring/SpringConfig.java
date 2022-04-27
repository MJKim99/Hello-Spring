//package hello.hellospring;
//
//import hello.hellospring.repository.MemberRepository;
//import hello.hellospring.repository.MemoryMemberRepository;
//import hello.hellospring.service.MemberService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration // 어노테이션 추가
///**
// * 스프링이 뜰 때 Configuration을 읽는다.
// */
//public class SpringConfig {
//
//    @Bean // 스프링 빈을 등록할거야
//    /**
//     * 아래 로직을 호출해서 스프링 빈에 등록해준다.
//     * 그럼 MemberService가 스프링 빈에 등록된다.
//     * 근데 MemberService에서는 생성자에 Repository를 넣어주어야 한다. (Ctrl + P)
//     */
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }
//
//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository(); // Memory는 구현체. 인터페이스는 new가 안 되기 때문에 구현체를 new 하는 것.
//    }
//
//}
