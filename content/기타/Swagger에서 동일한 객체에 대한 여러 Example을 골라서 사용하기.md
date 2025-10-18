# Swagger에서 동일한 객체에 대한 여러 Example을 골라서 사용하기

작성일: 2025년 6월 7일 오후 7:38
카테고리: API Docs

## 환경

- springboot 3.5.0
- openapi 3.1.0
- springdoc 2.8.8
- swagger ui

## 고민

- 동일한 `ErrorResponse` 객체에 대하여 매번 example을 직접 적어줘도 되지만, 말도 안된다. 엄청난 리소스 손실이다.
- 나의 경우는 `ErrorCode` 열거형이 이미 잘 정의되어 있는데, 동적으로 해당 `ErrorCode`에 대한 `Example`을 가져와서 사용하고 싶었다.
- 그러기 위해서는 먼저 Example을 등록해야 한다.

### Example 등록하기

```java
@Bean
public OpenApiCustomizer exampleInjector() {
    return openApi -> {
        Components components = openApi.getComponents();

        Arrays.stream(BusinessErrorCode.values()).forEach(errorCode -> {
            Example example = new Example()
                    .value(Map.of(
                            "errorCode", errorCode.name(),
                            "message", errorCode.message(),
                            "timestamp", LocalDateTime.now()
                    ));
            components.addExamples(errorCode.name(), example);
        });

        Arrays.stream(SecurityErrorCode.values()).forEach(errorCode -> {
            Example example = new Example()
                    .value(Map.of(
                            "errorCode", errorCode.name(),
                            "message", errorCode.clientMessage(),
                            "timestamp", LocalDateTime.now()
                    ));
            components.addExamples(errorCode.name(), example);
        });
    };
}
```

- 위와 같은 설정 빈을 등록하도록 했다. (그룹이 있는 경우에는 각 그룹에 `addOpenApiCustomizer()` 을 통해 위 빈을 넣어줘야 한다.)
- 위 빈 덕분에 이제 발생 가능한 모든 예외에 대한 예외 응답 json이 openapi에 등록된다.
    
    ![image.png](Swagger%EC%97%90%EC%84%9C%20%EB%8F%99%EC%9D%BC%ED%95%9C%20%EA%B0%9D%EC%B2%B4%EC%97%90%20%EB%8C%80%ED%95%9C%20%EC%97%AC%EB%9F%AC%20Example%EC%9D%84%20%EA%B3%A8%EB%9D%BC%EC%84%9C%20%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0%2020b30154715b800c9c61e935bad81c1e/image.png)
    

### Example 사용하기

- 이 부분에서 상당한 애를 먹었다.
- `$ref` 를 통해서 각 response에 example을 매칭시켜줘야 한다.
- 원하는 최종 형태는 다음과 같다.
    
    ![image.png](Swagger%EC%97%90%EC%84%9C%20%EB%8F%99%EC%9D%BC%ED%95%9C%20%EA%B0%9D%EC%B2%B4%EC%97%90%20%EB%8C%80%ED%95%9C%20%EC%97%AC%EB%9F%AC%20Example%EC%9D%84%20%EA%B3%A8%EB%9D%BC%EC%84%9C%20%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0%2020b30154715b800c9c61e935bad81c1e/image%201.png)
    
- 직접 json 또는 yml 파일을 만들어서 지정해줘도 되지만 나는 springdoc을 통해 openapi 명세를 만들고 있었기 때문에, 애노테이션 기반으로 설정해주었다.
    
    ```java
    @Operation(summary = "회원가입")
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400", content = @Content(examples = {
            @ExampleObject(
                name = "이메일이 중복된 경우",
                ref = "#/components/examples/EMAIL_DUPLICATED"
            ),
            @ExampleObject(
                name = "이름이 중복된 경우",
                ref = "#/components/examples/NAME_DUPLICATED"
            ),
        })),
    })
    ResponseEntity<MemberRegisterResponse> register(
        @RequestBody(required = true) MemberRegisterRequest request
    );
    ```
    
- ref만 지정하면 될 줄 알았는데, name도 지정해야 한다. 이 사실을 몰라서 2일 동안 고생했다.

## 최종 결과물

![image.png](Swagger%EC%97%90%EC%84%9C%20%EB%8F%99%EC%9D%BC%ED%95%9C%20%EA%B0%9D%EC%B2%B4%EC%97%90%20%EB%8C%80%ED%95%9C%20%EC%97%AC%EB%9F%AC%20Example%EC%9D%84%20%EA%B3%A8%EB%9D%BC%EC%84%9C%20%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0%2020b30154715b800c9c61e935bad81c1e/image%202.png)