# WebApplicationContext

작성일: 2025년 4월 16일 오전 11:38
카테고리: Spring Web MVC

> 스프링이 실행될 때 로그에 모르는 객체가 있어서 ChatGPT를 통해 검색하며 학습
> 
- 스프링은 `ApplicationContext` 인터페이스를 통해 DI 기능을 제공한다.
- 스프링 코어만 사용하면 `AnnotationConfigApplicationContext` 같은 일반적인 구현체가 사용된다.
- Spring Web MVC를 사용하면 `WebApplicationContext` 가 사용된다.

# WebApplicationContext

- `DispatcherServlet` 에 의해서 생성된다.
- `@Controller` , `ViewResolver` 와 같은 웹 관련 컴포넌트를 인식하고, 초기화한다.
- 일반 `singleton`, `prototype` 외에 웹 전용 스코프를 지원한다.
    - `request`, `session`, `application`, `websocket`