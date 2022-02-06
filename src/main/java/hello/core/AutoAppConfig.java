package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // @Configuration 어노테이션을 제외하는 이유는 별도로 제외범위(excludeFilter)를 지정하지 않으면
        // @Component 어노테이션 범주에 들어가는 @Configuration 어노테이션이 붙은 자바파일도 탐색하여 포함시켜버리기 때문에 반드시 제외해야함.

        // @ComponentScan 은 @Component 가 붙은 객체를 탐색한다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)

        // basePackages 는 탐색할 패키지의 시작위치를 지정하는 것.
        // 해당 패키지를 포함 하위 패키지까지 모두 탐색하고, 여러 패키지를 지정할수도 있음
//        basePackages = "hello.core.member"

        // basePackageClasses 는 지정한 클래스가 소속된 패키지부터 하위패키지까지 모두 탐색한다.
        // 지정하지 않으면 디폴트는 @ComponentScan 가 붙은 설정 정보 클래스가 소속된 패키지가 시작위치가 됨.
//        basePackageClasses = AutoAppConfig.class

        // 결론
        // basePackages, basePackageClasses 를 안붙이는 것이 좋다.
        // 기본적으로 프로젝트 루트 hello.core 에서 시작하게 하는 것이 좋음.
        // AppConfig 를 따로 두지 않아도 @SpringBootApplication 어노테이션이 붙은 파일이 실행될때 @ComponentScan 을 하므로
        // 똑같은 작업을 또 할필요가 없음.

        // @ComponentScan 대상
        // @Component, @Controller, @Service, @Repository, @Configuration
        // 해당 어노테이션들에 기본적으로 @Component 가 포함되어 있기 때문..

        // 어노테이션은 상속관계가 없음
        // 어노테이션이 특정 어노테이션을 들고 있는지 아는 것은 자바기능이 아니고 스프링 기능임.

        // @Controller: Spring MVC
        // @Repository: 스프링 데이터 접근 계층으로 인식, 데이터 계층의 예외를 스프링 예외로 변환
        // @Configuration: 설정정보로 인식, 스프링 빈이 싱글톤을 유지하도록 추가처리를 함.
        // @Service: 특별한 처리를 하지는 않고, 대신 개발자들이 핵심 비즈니스 로직이 여기에 있겠구나라고 비즈니스 계층을 인식하도록 하는 역할


)
public class AutoAppConfig {

    // 빈을 수동등록하면 자동등록빈보다 우선권을 가짐.
    // -> 수동빈이 자동빈을 오버라이딩 함.
    // 오버라이딩하는 경우 논리적 오류(에러가 나지는 않는 경우..)를 잡기 어려울 수 있기 때문에..
    // 최근 스프링은 실행시 bean이름이 중첩되는 경우 오버라이딩 하지 않고, 오류를 발생시킴
    // 명확하지 않은것은 하면 안됨.

//    @Bean("memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
