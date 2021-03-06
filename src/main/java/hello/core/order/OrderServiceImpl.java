package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
//import hello.core.discount.FixDiscountPolicy;
//import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

// @RequiredArgsConstructor 생성자를 자동으로 만들어줌.
@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;

    // 직접 지정하면.. 추상클래스와 구현클래스 둘다 의존하게 되어 OCP 위반이 됨..
    // 추상클래스에만 의존해야함..
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 아래처럼 변경하면 해결될지도..
    private final DiscountPolicy discountPolicy;

    // 의존성 주입방법중 수정자 주입과 필드 주입은 사용하지 않는 것이 좋음
    // 특히 필드 주입은 순수 자바 테스트시에 객체를 넣을 방법이 없게되므로 스프링을 통해서만 올릴 수 있게 되므로
    // 테스트 코드를 구성할 수 없어서 되도록 생성자 주입 방법을 써야 함.
    // 단, 사용가능한 부분은 변경이 거의 없는데만 제한적으로 사용
    // 수정자 주입의 경우도 임의로 객체를 변경하는 경우가 발생할 수 있기 때문에 빈으로 집어넣을 수 없는 경우에만 한정적으로 사용
//    @Autowired(required = false)
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("setDiscountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("setMemberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }

    // @Autowired 매칭 방법
    // 1) 타입매칭 (Quilifier 끼리 매칭)
    // 2) 타입매칭의 결과가 2개 이상일 경우 필드명으로 빈 이름 매칭
    // 단, 그래도 못찾으면 NoSuchBeanDefinitionException 예외 발생
    // Quilifire가 Primary보다 우선순위가 높음.
    @Autowired
    private DiscountPolicy rateDiscountPolicy;

    // 생성자가 하나일경우 자동으로 @Autowired 를 붙여주므로 생략해도 됨.
    // @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy) {
//        System.out.println("OrderServiceImpl.memberRepository = " + memberRepository);
//        System.out.println("OrderServiceImpl.discountPolicy = " + rateDiscountPolicy);
//        this.memberRepository = memberRepository;
//        this.discountPolicy = rateDiscountPolicy;
//    }

    // 애노테이션은 상속개념 없다.
    // 여러 애노테이션을 모아서 사용하는 기능을 스프링이 지원해주는것임.
    // 단, 무분별하게 사용하면 유지보수에 혼란만 가중시킬 수 있음.
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
//    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        System.out.println("OrderServiceImpl.memberRepository = " + memberRepository);
        System.out.println("OrderServiceImpl.discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
