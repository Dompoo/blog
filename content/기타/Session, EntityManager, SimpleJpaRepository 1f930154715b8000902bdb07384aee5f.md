# Session, EntityManager, SimpleJpaRepository

작성일: 2025년 5월 20일 오후 5:46
카테고리: JPA

- `EntityManager`는 JPA에서 영속성 컨텍스트를 담당하는 인터페이스이다.
- `Session`은 하이버네이트의 `EntityManager` 구현체이다.
- `SimpleJpaRepository` 는 Repository의 기본 구현체이며, 내부적으로 `EntityManager`를 사용한다.