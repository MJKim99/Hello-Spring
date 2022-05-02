package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
// 서비스에선 보통 비즈니스 로직에서 가져온 이름을 사용한다.
@Transactional // jpa 사용 시 항상 Transactional이 있어야 한다. (데이터를 변경하고 저장할 때에 항상 @Transactional이 있어야 함)
public class MemberService {

    // Test 작성을 위해 코드 수정
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // 이런 걸 DI라고 함. 내가 직접 new 하는 게 아닌, 외부에서 넣어주는 것
//    @Autowired // MemberService도 MemberRepository가 필요하기 때문에 해당 어노테이션 사용해서 주입해줌
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 X
//        Optional<Member> result = memberRepository.findByName(member.getName()); // 자료형이 Optional로 바로 반환되는 건 좋지 않음. 그래서 아래처럼 작성
//        result.ifPresent(m -> { // ifPresent : 값이 있으면(null이 아니면) 로직 동작
//            // 기존 코드였다면 if(null)로 체크했겠지만 Optional로 감쌌기 때문에 사용 가능
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
