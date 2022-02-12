package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeBeanFind {

    @Test
    void PrototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototype bean1");
        PrototypeBean pb1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototype bean2");
        PrototypeBean pb2 = ac.getBean(PrototypeBean.class);

        System.out.println("pb1 = " + pb1);
        System.out.println("pb2 = " + pb2);

        assertThat(pb1).isNotSameAs(pb2);

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
