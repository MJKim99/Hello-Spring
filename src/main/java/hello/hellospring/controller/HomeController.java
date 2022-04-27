package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // localhost:8080 하면 기본으로 / 가 호출된다.
                    // 해당 매핑이 있는 경우 static 폴더 내 index.html 파일은 무시된다.
    public String home() {
        return "home";
    }
}
