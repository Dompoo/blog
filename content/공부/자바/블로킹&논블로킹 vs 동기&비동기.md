- 두 개념이 매우 비슷해서 헷갈리기 쉽다.
- 블로킹&논블로킹은 '제어 흐름의 위치'에 대한 개념이며
  동기&비동기는 '작업 완료 확인 방법'에 대한 개념이다.
- **블로킹** : 호출된 메서드가 자기 작업이 끝나기 전까지 제어권을 가진다.
- **논블로킹** : 호출된 메서드의 작업 여부와 상관없이 호출한 메서드가 제어권을 이어간다.
- **동기** : 작업의 결과 확인을 위해서는 작업이 완료될 때까지 기다린다.
- **비동기** : 작업의 결과를 콜백/이벤트/퓨쳐 등으로 능동적으로 받거나 풀링한다.
### 예시
- **블로킹 동기** : 일반적이고, 직관적이며 고전적인 방식
```java
String content = fileInput.read(); // 읽을 때까지 기다린다.
System.out.println(content);
```
- **논블로킹 비동기** : 자원을 가장 효율적으로 사용하는 현대적 방식
```java
fetch('https://api.example.com')
	.then(response => console.log(response)); // 가져오면 추후에 실행된다.
```
- **블로킹 비동기** : 비동기의 이점을 사용하지 못하는 비효율적인 상황
```java
Future<String> future = executor.submit(() -> "비동기 작업 결과");

String result = future.get(); // 비동기 결과를 얻는 과정에서 스레드가 블로킹 된다.
```
- **논블로킹 동기** : 
```java
while(true) { // 즉시 리턴됨(Non-blocking). 하지만 데이터가 없으면 null 반환 String data = socket.read(); if (data != null) { 처리(data); break; } // 데이터가 올 때까지 계속 반복해서 확인(Sync) }
```