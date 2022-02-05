package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // @Bean memberService -> new MemoryMemberRepository() 호출
    // @Bean orderService -> new MemoryMemberRepository() 호출
    // 싱글톤이 깨지는가????
    // @Configuration 어노테이션을 넣을 경우 CGLIB이 사용되서 이미 존재하는 객체일 경우 반환하고, 존재하지 않으면 바이트코드를 변경해서 인스턴트 객체 반환
    // 만약, @Configuration 어노테이션이 없다면 CGLIB이 사용되지 않아서 빈 선언은 되지만, 매번 객체를 새롭게 생성(new)해서 반환해버림.
    @Bean
    public MemberService memberService() {
        System.out.println("★call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("★call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("★call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        System.out.println("★call AppConfig.discountPolicy");
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
