- Redis와의 연결, 쿼리 등을 추상화해서 다룰 수 있게 해준다.
- 다른 여타 `Spring Data` 계층들처럼 `RedisTemplate`을 제공해준다. 이를 통해 접근하면 된다.
- 필요해지면 더 찾아보고 사용하면 될 듯!
### RedisTemplate 사용 예시
```java
@Service
@RequiredArgsConstructor
public class RedisStringService {
    
    private final RedisTemplate<String, Object> redisTemplate;
    
    public void setValue(String key, String value) {
	    redisTemplate.opsForValue().set(key, value);
    }

    public Boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }
    
    public String getValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
```