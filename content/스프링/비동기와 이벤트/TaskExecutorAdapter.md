- 비동기 관련 실행기들은 다음 의존관계를 갖는다.
[[Executor]] <- TaskExecutor <- [[AsyncTaskExecutor]]
↑
[[ExecutorService]]
- 스프링은 `AsyncTaskExecutor`를 사용하여 [[@Async]]를 처리하게 된다.