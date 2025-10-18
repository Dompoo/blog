# OpenAPI, Swagger

작성일: 2025년 6월 6일 오후 9:35
카테고리: API Docs

- 원래 Swagger가 짱짱맨이었다.
    - API를 명세하는 기준 : Swagger Specification
    - 해당 명세를 시각화 : Swagger UI
- Swagger Sepecification이 리눅스 진영으로 기부(?)되면서 **Open API Specification(OAS)** 로 명칭이 바뀌었다.
- 따라서 현재는 OAS가 명세, Swagger UI가 시각화 도구 라고 분리하여 이해하는게 맞다.
- OAS를 시각화하는 도구로는 다음과 같은 것들이 있다.
    - Swagger UI
        - 가장 많이 사용한다.
        - OAS가 Swagger 출신이라 그런지 간편하다.
    - ReDoc
        - 깔끔한 정적 문서 스타일
    - RapiDoc
        - 빠른 렌더링, 높은 커스터마이징
- 우리는 springdoc이라는 도구를 통해 OAS에 맞춰 명세를 만들고, 여러 시각화 도구들 중 원하는 것을 골라 API 문서를 만들 수 있다.