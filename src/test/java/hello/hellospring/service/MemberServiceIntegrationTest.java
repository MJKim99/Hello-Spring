package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 부트를 사용해 Test 하기. (DB 정보를 스프링에서 가지고 있음)
                // 스프링 컨테이너와 테스트를 함께 실행. 진짜 스프링을 띄워서 테스트 함

@Transactional // 테스트 케이스에 이거 쓰면 테스트 시작 전에 트랜잭션을 시작하고 테스트 완료 후에 항상 롤백함
                // DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않음
class MemberServiceIntegrationTest { // MemberServiceIntTest

    /// Test에서는 필드 주입 방식 써도 상관은 없음. 편하게
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // 첫 실행 시 DB에 저장한 데이터가 남아있기 때문에 오류 발생.
    // DB에 저장된 name이 spring인 회원 삭제해줘야 함
    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        // when
        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // then
    }
}