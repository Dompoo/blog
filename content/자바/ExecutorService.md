- 자바의 동시성 프레임워크에서 스레드 풀을 관리하고 비동기 작업을 실행하는 핵심 인터페이스이다.
- `Executors`  팩토리 클래스를 통해 다양한 구현체를 생성해볼 수 있다. (직접 생성보다 권장된다.)
```java
ExecutorService executor = Executors.newFixedThreadPool(5);

// Runnable 제출 (결과 없음)
executor.execute(() -> System.out.println("Task"));

// Callable 제출 (결과 반환)
Future<String> future = executor.submit(() -> {
    return "결과";
});
```
- 이외에도 수많은 구현체들이 존재한다.