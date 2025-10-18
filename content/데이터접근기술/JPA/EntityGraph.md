- Fetch Join을 하지 않아도 값을 보고 연관된 엔티티를 미리 땡겨오는 기술이다.

```java
@EntityGraph(attributePaths = {"reservations"})
@Query("select u from User u")
List<User> findAllWithReservations();
```

- 위 예시에서는 User를 가져올 때, Reservation도 LEFT JOIN 해서 가져온다.
- `SELECT * FROM users u LEFT JOIN reservation r ON u.id = r.user_id`
- 사용법
    - 단독 사용
    
    ```java
    @EntityGraph(attributePaths = {"reservations"})
    List<User> findAllByUsername(String username);
    ```
    
    - `@Query` 와 사용
    
    ```java
    @EntityGraph(attributePaths = {"reservations"})
    @Query("select u from User u")
    List<User> findAllWithReservations();
    ```
    
    - `@NamedEntityGraph` 와 사용
    
    ```java
    @Entity
    @NamedEntityGraph(name = "User.withReservations", attributeNodes = @NamedAttributeNode("reservations"))
    public class User { ... }
    
    // repository 메서드
    
    @EntityGraph("User.withReservations")
    List<User> findAll();
    ```