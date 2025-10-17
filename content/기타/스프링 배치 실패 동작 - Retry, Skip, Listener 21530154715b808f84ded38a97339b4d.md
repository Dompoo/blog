# 스프링 배치 실패 동작 - Retry, Skip, Listener

작성일: 2025년 6월 17일 오후 3:05
카테고리: Spring Batch

- 스프링 배치 실패시 기본 동작
    - 3번째 청크에서 예외가 발생하여 실패했다고 가정
    - 3번째 청크에 대해서만 롤백이 진행되고, 4번째 청크부터는 진행되지 않는다.
    - 해당 StepExecution은 실패처리 되며, JobExecution 또한 실패처리된다.
    - 몇번째 청크부터 실패했는지 그 메타데이터가 JobRepository에 저장된다.
- Retry
    - 실패시 트랜잭션을 롤백하지 않고, 같은 작업을 다시 시도하여 성공을 유도한다.
    - 최종시도까지 실패하면 그때 실패처리한다.
    - 재시도시 성공 가능성이 있을 때 사용한다. (ex. 일시적 네트워크 오류, DB Lock에 의한 오류 등)
    
    ```java
    return new StepBuilder("sampleStep", jobRepository)
                .<String, String>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant() // 내결함성 기능 활성화
                .retryLimit(3) // 최대 3번까지 재시도
                .retry(OptimisticLockingFailureException.class) // 이 예외 발생 시 재시도
                .build();
    ```
    
- Skip
    - 실패시 전체 Step을 멈추지 말고, 무시한 채 다음 청크부터 이어가는 방식이다.
    - 재시도해도 절대 성공할 수 없는 경우 사용한다. (ex. 잘못된 데이터, 제약 조건 위배 등)
    
    ```java
    return new StepBuilder("sampleStep", jobRepository)
                .<String, String>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .faultTolerant() // 내결함성 기능 활성화
                .skipLimit(100) // 총 100개까지 Skip 허용
                .skip(ParseException.class) // 이 예외 발생 시 Skip
                .noSkip(FileNotFoundException.class) // 이 예외는 Skip하지 않고 즉시 실패
                .build();
    ```
    
- Retry와 Skip 동시 사용
    - 두 방법의 성질이 다르므로 같이 사용하는 것이 이상적이다.
        - Retry는 재시도시 성공 가능성이 있을 때, Skip은 없을 때 사용
    
    ```java
    return new StepBuilder("processOrdersStep", jobRepository)
                .<OrderCsv, Order>chunk(10, transactionManager)
                .reader(orderCsvReader)
                .processor(new OrderItemProcessor())
                .writer(new OrderItemWriter())
                .faultTolerant() // 내결함성 기능 활성화
    
                // Retry 정책 설정 (일시적 예외)
                .retryLimit(3) // 최대 3번까지 재시도
                .retry(OptimisticLockingFailureException.class) // 이 예외는 재시도
    
                // Skip 정책 설정 (영구적 예외)
                .skipLimit(100) // 총 100개까지 스킵 허용
                .skip(DataParseException.class) // 이 예외는 스킵
    
                .listener(new MySkipLoggingListener()) // 스킵된 아이템 로깅
                .build();
    ```
    
- Listener
    - 직접 동작 흐름을 바꾸지는 않고, 부가 처리를 하는 컴포넌트다. (로깅, 알림 리소스 정리 등)
    - read가 스킵됐을 때, process가 스킵됐을 때, 재시도가 발생했을 때, 예외가 발생했을 때 등 수많은 이벤트에서 사용할 수 있다.