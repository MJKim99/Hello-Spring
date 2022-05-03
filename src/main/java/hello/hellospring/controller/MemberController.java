package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    //    private final MemberService memberService = new MemberService();
    // new MemberService 해도 되긴하는데 스프링에서 관리하게 되면
    // 다 스프링 컨테이너에 등록하고, 스프링 컨테이너에 있는 걸 받아서 써야 된다? 바다소스?
    // 왜냐하면 new해서 하면 MemberController 말고 다른 Controller들이 memberService를 받아서 쓸 수 있게 된다.
    // 주문Controller 같은 곳에서도 Member 정보가 필요할지도 모르니 쓸 수도 있게 되는 것
    // 근데 Service 내에 다른 데서 쓸만한 큰 기능이 없어서 굳이 new로 새로운 인스턴스를 생성할 필요가 없다?
    // 한 개의 인스턴스만 생성하고 공용으로 쓰는 게 더 좋음.
    // 그래서 스프링 컨테이너에 등록함. 스프링 컨테이너에 등록하면 딱 한 개만 되기 때문
    // 그 외에 부가적인 효과를 볼 수 있음
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass());
    }
    // 스프링 컨테이너가 뜰 때 MemberController를 생성하는데 그때 생성자를 호출한다.
    // 생성자에 @Autowired 어노테이션이 붙어있으면
    // 스프링 컨테이너에 들어있는 MemberService를 가지고 와서 연결시켜줌
    // 이때 Service에 @Service 어노테이션이 필요한 것. 어노테이션 쓰면 스프링 빈에 등록됨
    // 어노테이션으로 명시해주지 않으면 스프링 컨테이너가 Service가 뭔지 찾지 못함
    // @Repository도 같음
    // @Autowired를 주입해주는 거라고 함.
    // 이게 붙어있으면 Controller가 생성될 때 스프링 빈에 등록되어 있는 MemberService를
    // 객체를 가져다 넣어줌. 이게 바로 DI(Dependency Injection). 의존관계를 주입해주는 것.


    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/"; //회원가입이 끝났으니 home 화면으로
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
