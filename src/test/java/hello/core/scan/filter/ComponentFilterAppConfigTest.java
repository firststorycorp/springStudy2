package hello.core.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();

        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class));
    }

    @Configuration
    @ComponentScan(
            // FilterType
            // ANNOTATION (디폴트): 어노테이션 인식 동작
            // ASSIGNABLE_TYPE: 지정한 타입과 자식타입 인식(클래스를 직접 지정 가능)
            // ASSPECTJ: AsspectJ 패턴으로 찾아오는것
            // REGEX: 정규식 사용
            // CUSTOM: TypeFilter 라는 인터페이스를 구현해서 처리

            // includeFilter 를 사용할일이 거의 없음.
            // excludeFilter는 사용하는경우 간혹 있음.
            // 스프링은 기본적으로 ComponentScan을 제공해서 별도로 사용할일 거의 없음.

            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {

    }
}
