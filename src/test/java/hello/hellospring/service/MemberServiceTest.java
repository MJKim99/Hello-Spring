package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

//    MemberService memberService = new MemberService();
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // memberService에 있는 memberRepository와 위 memberRepository는 다른 객체이기 때문에 애매하다.
    // 어쨌든 new 해서 생성했으니 각각 다른 인스턴스이므로 내용물이 달라질 수도 있음
    // 같은 인스턴스를 사용하기 위해 MemberService.java 수정 및 beforeEach() 메서드 생성
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    // 테스트는 독립적으로 실행되어야 하기 때문에 테스트 실행 때마다 new 해줌
    // 위처럼 하면 같은 MemoryMemberRepository 사용하는 것

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given - 이런 상황이 주어짐 - 이 데이터를 기반
        Member member = new Member();
        member.setName("spring");

        // when - 이걸 실행했을 때 - 이걸 검증(테스트)
        Long saveId = memberService.join(member);

        // then - 결과가 이게 나와야 됨
        // 우리가 저장한 게 repository께 맞냐
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    /**
     * 위 회원가입 테스트는 반쪽 자리다.
     * join 핵심은 회원 저장이 되는가도 중요하지만,
     * 중복 회원 검증을 제대로 타서 예외(throw)가 제대로 나오는지도 중요하다.
     */

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        // when
        memberService.join(member1);

        // 이걸 try~catch 문으로 테스트 하는 게 좀 애매하긴 함.
/*        try {
            memberService.join(member2);
            fail(); // 위 단계에서 예외가 발생하지 않은 경우 제대로 된 게 아님
        } catch (IllegalStateException e) {
            // join()에서 예외가 발생해서 정상적으로 테스트를 통과한 것
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123");
        }*/
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        /*memberService.join(member2) 로직을 실행하면
        IllegalStateException 예외가 터져야 함*/

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}