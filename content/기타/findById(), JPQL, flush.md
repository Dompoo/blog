# findById(), JPQL, flush

작성일: 2025년 5월 15일 오후 5:13
카테고리: JPA

- JPA는 1차 캐시를 운영한다.
- 따라서 `findById(1L)` 을 두번 실행하면 첫번째 실행에만 SELECT 쿼리가 발생한다.
    - 첫번째 요청에서 SELECT 후에 1차 캐시에 저장하고, 두번째 요청은 1차 캐시의 값을 가져다 쓴다.
- 하지만, `findById(1L), findByName(”Dompoo”)` 를 실행하면 둘 다 SELECT 쿼리가 발생한다.
    - 첫번째 요청에서 SELECT 후에 1차 캐시에 저장하고, 두번째 요청은…?
- 1차 캐시는 객체의 식별자를 기준으로 운영된다. (`Map<Id, Entity>` 느낌)
    - 따라서 `findById()` 가 아닌 경우 1차 캐시를 사용하지 못한다.
    - 바로 JPQL(SQL)을 사용하게 되는 것이다.
- JPQL은 1차 캐시를 사용할 수 없기 때문에 당연하게 SQL이 바로 실행된다.
    - 이때, 정합성을 보장받기 위해 변경한 내용을 반영해야 한다.
    - 따라서 JPQL을 실행하면 `flush()`도 같이 실행된다.
- 물론 커밋하지는 않는다. 롤백시 정상적으로 롤백된다.
- 결론
    - `findById() 2번`  : SELECT → 1차 캐싱 → HIT
    - `findById(), findByName()` : SELECT → 1차 캐싱 → flush() → SELECT