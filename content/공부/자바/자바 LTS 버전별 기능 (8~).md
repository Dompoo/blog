## 8버전 (LTS)
[[람다 표현식]]
[[스트림 API]]
[[Optional]]
**새로운 Date/Time API (java.time)**
: 기존에는 `java.util.Date`와 `java.util.Calendar`를 날짜 계산에 사용했다.
다음 문제가 있었다. : 가변 객체, 0월 부터 시작, 타임존 처리 복잡, 날짜 계산 복잡
새로운 API에서는 이 문제들이 해결된다. (`LocalDate`, `LocalTime`, `LocalDateTime`, `Instant` 등)

**디폴트 메서드**
: 인터페이스를 수정하면 모든 구현체를 수정해야 했다. 이는 엔터프라이즈 코드와 라이브러리 코드의 유지보수를 아주 힘들게 했다.
따라서 기존 인터페이스 구현체들을 수정하지 않으면서도 새로운 유틸 메서드나 공통적인 기능을 추가하는 방법을 제공하기 위해 **디폴트 메서드**가 탄생했다.
## 9버전
**Try-With-Resources 개선**
: final 또는 effectively final 변수를 try 괄호 안에서 직접 사용할 수 있다.
```java
BufferedReader reader = new BufferedReader(...);
try (reader) {
    // 사용
}
```

**쉬운 불변 컬렉션 생성**
: `List.of()`, `Set.of()`, `Map.of()

**인터페이스의 private 메서드**
: default 메서드에서의 중복 코드를 뽑아낼 수 있다.
## 10버전
[[var 키워드]]

**Optional.orElseThrow()**
: 매개 변수 없이 사용 가능, `NoSuchElementException`이 터진다.

**List.copyOf, Set.copyOf(), Map.copyOf()**
: 불변 복사본 생성

**Collectors.toUnmodifiableList() / Set / Map**
: stream에서 불변 컬렉션 응답
## 11버전 (LTS)
### HTTP Client API (java.net.http)
기존에는 HTTP 통신을 위하여 복잡한 `HttpURLConnection`를 쓰거나, 외부 라이브러리에 의존해야 했다.
```java
URL url = new URL("https://api.example.com/users");
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod("GET");
conn.setRequestProperty("Content-Type", "application/json");

int responseCode = conn.getResponseCode();
BufferedReader in = new BufferedReader(
    new InputStreamReader(conn.getInputStream()));
String inputLine;
StringBuilder response = new StringBuilder();
while ((inputLine = in.readLine()) != null) {
    response.append(inputLine);
}
in.close();
conn.disconnect();
```
**HTTP Client API**는 표준 HTTP 클라이언트를 제공하고, 네이티브로도 표현력 있는 코드를 작성하는 것을 목표로 한다.
```java
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://api.example.com/users"))
    .header("Content-Type", "application/json")
    .GET()
    .build();

HttpResponse<String> response = client.send(
    request, 
    HttpResponse.BodyHandlers.ofString()
);
String body = response.body();
```
비동기/웹소컷 통신도 제공하며, HTTP/2 도 지원한다.

**String 추가 지원**
: 문자열을 다루기 위한 추가 기능을 제공한다.
`isBlank()`, `lines()`, `strip()`, `repeat()` 등

**Files API 개선**
: 파일 읽기/쓰기를 간단하게 처리할 수 있게 되었다.
```java
String content = Files.readString(Path.of("file.txt"))`
Files.writeString(Path.of("output.txt"), "Hello World", StandardOpenOption.CREATE);
```
## 12버전

## 17버전 (LTS)
## 21버전 (LTS)
## 25버전 (LTS)