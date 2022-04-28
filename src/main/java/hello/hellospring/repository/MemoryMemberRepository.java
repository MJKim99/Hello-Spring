package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// @Repository 어노테이션은 interface가 아닌 구현체에 작성해주어야 함.
//@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // save() 위해 사용. 실무에서는 공유되는 문제 때문에 단순 HashMap 안씀
    private static long sequence = 0L; // 동시성 문제 때문에 실무에서는 단순 long 안쓰고 atomiclong? 씀

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // id는 시스템에서 정하는 것이기 때문에 여기서 지정하는 것임. name 값은 사용자 입력을 통해 넘어왔다고 가정
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
//        return store.get(id); 해당 결과가 null인 경우를 대비해 Optional 설정
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                    .filter(member -> member.getName().equals(name))
                    .findAny(); // findAny() <- 하나라도 찾아지면 반환하는 거
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
