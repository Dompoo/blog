# Star Import에 대한 생각

작성일: 2025년 5월 9일 오전 10:36
카테고리: Style

- Star Import를 제거하라는 것이 구글 스타일에 정의되어 있다.
- 그 이유는 대중적으로 다음과 같이 꼽힌다.
    - 명확성이 떨어진다. → 인정
    - 여러 패키지에 동일한 클래스명이 있다면 혼용될 수 있다. → 인정
        
        ```java
        import java.sql.*;
        import java.util.*;
        
        Date date; // 어느 Date?
        ```
        
- 근데, 그러면 명확하고 동일하지 않을 클래스는 괜찮을듯?

![image.png](Star%20Import%EC%97%90%20%EB%8C%80%ED%95%9C%20%EC%83%9D%EA%B0%81%201ee30154715b80ae9cecefca8818df98/image.png)