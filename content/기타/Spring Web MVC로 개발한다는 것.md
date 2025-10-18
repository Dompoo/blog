# Spring Web MVC로 개발한다는 것

작성일: 2025년 4월 19일 오후 9:58
카테고리: Spring Web MVC

- WAS 자체는 이미 잘 개발되어 있다.
    - Tomcat, Jetty 등
- WAS 내부에 들어가는 서블릿 구현체를 개발해야 한다.
- 하지만 서블릿 구현체 개발은 귀찮고 복잡하다.
- 스프링 웹 mvc는 이 귀찮은 작업을 도와준다.
- 컨트롤러만 개발하면 스프링 웹 mvc의 디스패쳐 서블릿이 컨트롤러로 요청을 매핑한다.
- Client → Tomcat → DispatcherServlet → Controller