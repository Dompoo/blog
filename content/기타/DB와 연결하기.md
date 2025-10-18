# DB와 연결하기

작성일: 2025년 9월 24일 오전 11:26

- `DriverManager`
    - 초창기 방식, DB 연결을 ‘생성’한다.
    
    ```java
    Connection conn = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/testdb", "user", "password"
    );
    ```
    
    - 매번 새로운 DB 연결을 생성하므로 비용이 크다.
- `DataSource`
    - 커넥션 풀링등을 지원하여 성능 최적화를 할 수 있다.
    
    ```java
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/testdb");
    dataSource.setUsername("user");
    dataSource.setPassword("password");
    Connection conn = dataSource.getConnection();
    ```
    
    - `JdbcConnectionPool` 구현체 코드
    
    ![[DB와 연결하기_image.png]]