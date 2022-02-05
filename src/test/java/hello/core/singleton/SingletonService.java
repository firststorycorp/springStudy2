package hello.core.singleton;

// 스프링은 기본 싱글톤으로 만들어줘(스프링 컨테이너)서 별도로 만들어줄 필요는 없음
// 성능향상에 도움됨..
public class SingletonService {

    // 자신을 static + private변수를 두게되면 클래스에 딱 하나만 존재하게 됨..
    private static final SingletonService instance = new SingletonService();

    // 생성자 접근제한
    private SingletonService() {

    }

    // public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용
    public static SingletonService getInstance() {
        return instance;
    }

    public void login() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
