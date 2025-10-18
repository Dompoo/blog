# ViewResolver VS HttpMessageConverter

작성일: 2025년 4월 15일 오후 9:10
카테고리: Spring Web MVC

> `@ResponseBody` 의 구체적인 역할이 궁금해졌다.
ChatGPT를 통해 학습!
> 
- 공통점
    - 둘다 Spring Web MVC 에서 **응답을 처리**한다.
- 차이점
    - 대상
        - `ViewResolver`는 뷰 이름을 뷰로 매핑한다.
        - `HttpMessageConverter` 는 객체를 Http 메시지로 변환한다.
    - 대표 구현체
        - `ThymeleafViewResolver`
        - `MappingJackson2HttpMessageConverter` , `StringHttpMessageConverter`
    - 처리 상황
        - `@ResponseBody` 가 아닌 상황
        - `@ResponseBody` 인 상황

## `ViewResolver` 동작

- Spring Web MVC 의존성만 추가해도 뷰 이름을 반환할 수 있다.
- 하지만 해당 뷰 이름이 뷰로 매핑되지는 못한다.
- 적절한 뷰 리졸버가 없기 때문이다.
- 타임리프 의존성을 추가하면 `ThymeleafViewResolver` 가 빈으로 자동 등록된다. (스프링부트 자동구성)
- 등록된 해당 뷰 리졸버를 통해 뷰 이름이 적절한 뷰로 매핑된다.

## `HttpMessageConverter` 동작

- `@ResponseBody` 를 통해 반환값이 객체인 상황에서 객체를 Http 메시지로 변환해야 한다.
- 객체의 타입을 통해 적절한 컨버터를 선택하게 된다.
- `Jackson` 을 사용하는 `MappingJackson2HttpMessageConverter` 를 통해 응답객체들이 json으로 변환될 수 있다.