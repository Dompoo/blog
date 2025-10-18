> 결론적으로는 아니다.
**비즈니스적으로 식별성이 필요한 경우에만 붙이면 된다.**
> 

```java
public class ReservationTime {

    private static final LocalTime MIN_TIME = LocalTime.of(9, 0);
    private static final LocalTime MAX_TIME = LocalTime.of(23, 0);

    private final LocalTime startTime;

    public ReservationTime(final LocalTime startTime) {
        Objects.requireNonNull(startTime, "시작 시간은 null이 될 수 없습니다.");
        validateStartTimeRange(startTime);
        this.startTime = startTime;
    }

    private static void validateStartTimeRange(final LocalTime startTime) {
        if (startTime.isBefore(MIN_TIME)) {
            throw new IllegalArgumentException("시작 시간은 9시 이후이어야 합니다.");
        }
        if (startTime.isAfter(MAX_TIME)) {
            throw new IllegalArgumentException("시작 시간은 23시 이전이어야 합니다.");
        }
    }

    public LocalTime startTime() {
        return startTime;
    }
}
```

위 객체는 식별성이 필요할까? 아니라고 본다. 예약 시간은 그냥 값만 들고 있으면 된다. 내 예약 시간과 너의 예약 시간이 동일해도 된다. 그래도 아무 문제가 없다. 비즈니스적으로 같은 값의 두 예약 시간을 구분할 필요는 없다.

이에 비해 `예약` 그 자체는 식별성이 필요하다. 방탈출에 입장하려면 해당 예약이 다른 예약들과 식별되어야 한다. 따라서 ID가 필요하다. 비즈니스적으로 같은 값을 갖는 두 예약을 구분해야 한다.