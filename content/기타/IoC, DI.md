# IoC, DI

작성일: 2025년 4월 7일 오후 4:37
카테고리: Spring Core

## IoC

> 제어의 역전
> 
- 기존에는 객체자신에게 필요한 의존성들을 직접 제어했다.
- IoC를 하게되면, 그런 의존성 제어의 역할을 다른 객체에게 위임한다.
    - IoC 객체가 의존성을 주입한다.
    - 심지어 해당 객체를 직접 생성하기까지 한다.

## DI

> 의존성 주입
> 
- 제어의 역전을 하게되면 자연스럽게 DI 형태가 나온다.
- 생성자나 setter 등을 통해서 의존성을 외부에서 넣어주는 것이다.
- 이러면 주입받는 객체에서는 구현체에 의존할 필요없다.
- DIP를 지키는 방법 중 하나이다.

## 스프링에서 만들기

```java
@Configuration
public class AppConfig {
	
	@Bean
	public MemberService memberService() {
		return new MemberServiceImpl(memberRepository());
	}
	
	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl(
			memberRepository(),
			discountPolicy());
	}
	
	@Bean
	public MemberRepository memberRepository() {
		return new MemoryMemberRepository();
	}
	
	@Bean
	public DiscountPolicy discountPolicy() {
		return new RateDiscountPolicy();
	}
}
```

```java
public static void main(String[] args) {
	ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
	// ...
}
```