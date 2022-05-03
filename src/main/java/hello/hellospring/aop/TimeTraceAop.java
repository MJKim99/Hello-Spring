package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // AOP로 사용할 수 있는 것
@Component // <- 이렇게 해서 스프링 빈으로 등록할 수 있지만 SpringConfig에서 등록해 사용하는 걸 선호함
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") // 아래 공통 관심 사항을 어디에 적용할 것인가? (타겟팅)
    // 패키지명. 그 밑에 있는 거. 파라미터 타입 등등
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
//            Object result = joinPoint.proceed(); // 다음 메소드로 진행
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }

    }
}
