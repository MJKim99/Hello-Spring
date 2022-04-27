package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// 실행을 빠르게, 반복 실행, 여러 테스트를 한 번에 실행하기 위해 Test case 작성
class MemoryMemberRepositoryTest { // 다른 곳에서 가져다 쓸 게 아니니 public 삭제함

    MemoryMemberRepository repository = new MemoryMemberRepository();

//    @AfterEach
//    public void afterEach() {
//        repository.clearStore();
//    }

    @Test // @Test 어노테이션을 붙여서 해당 메서드를 그냥 실행시킬 수 있음
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get(); // 원래 .get() 바로 쓰면 안 좋은데  Test라 그냥 작성
//        System.out.println("result = " + (result == member));
//        Assertions.assertEquals(member, result); <- member와 result 위치가 반대인가?
        assertThat(member).isEqualTo(result);// Assertions을 static으로 함
        // 실무에서는 test 통과 못하면 다음 단계로 못 넘어가게 함
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1"); // spring1 회원 가입
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2"); // spring2 회원 가입
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }
    
    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1"); // spring1 회원 가입
        repository.save(member1);
        
        Member member2 = new Member();
        member2.setName("spring2"); // spring1 회원 가입
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
