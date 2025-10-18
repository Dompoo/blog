```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/base.xml"/>
    <appender name="JSON_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>/var/log/app/app.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>/var/log/app/app-%d{yyyy-MM-dd}.log.gz</fileNamePattern>
            <maxHistory>7</maxHistory>
        </rollingPolicy>
        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp>
                    <timeZone>Asia/Seoul</timeZone>
                </timestamp>
                <pattern>
                    <pattern>
                        {
                        "timestamp":"%d{yyyy-MM-dd'T'HH:mm:ss.SSSZ}",
                        "level":"%level",
                        "thread":"%thread",
                        "logger":"%logger",
                        "message":"%msg",
                        "exception":"%ex"
                        }
                    </pattern>
                </pattern>
            </providers>
        </encoder>
    </appender>
    <root level="INFO">
        <appender-ref ref="JSON_FILE"/>
    </root>
</configuration>
```

- 크게 appender를 정의하는 부분과 해당 appender를 사용하는 부분으로 나눌 수 있다.
- **Appender**
    - 로그를 남기는 주체이다.
    - 콘솔에 남길 수 있고, 파일 하나에, 파일 여러 개에, 또는 TCP로 전송하는 등의 appender가 존재한다.
    - 위에서는 Json File로 남기는 Appender가 정의되었다.
- **Appender 사용**
    - info 레벨 이상의 로그(info, warn, error)를 Json File Appender를 통해 남긴다는 뜻이다.
    - 원하면 spring의 profile과 연동하여 특정 프로필에서만 로그를 남기도록 할 수도 있다.
- 참고 : log4j2를 사용한다면?
    - 일단 비슷한 흐름으로 설정하지만, 굳이 XML이 아니어도 된다.
    - log4j2는 yml, json 등 많은 형식을 지원한다.
    
    ```yaml
    Configuration:
      status: warn
      monitorInterval: 30
      Properties:
        LOG_PATH: /var/log/app
        LOG_FILE_NAME: app
        TimeZone: Asia/Seoul
    
      Appenders:
        RollingFile:
          name: JsonFileAppender
          fileName: "${LOG_PATH}/${LOG_FILE_NAME}.log"
          filePattern: "${LOG_PATH}/${LOG_FILE_NAME}-%d{yyyy-MM-dd}.log.gz"
          JsonLayout:
            complete: false
            compact: true
            eventEol: true
            timeZone: "${TimeZone}"
          Policies:
            TimeBasedTriggeringPolicy:
              interval: 1
              modulate: true
          DefaultRolloverStrategy:
            max: 7
    
      Loggers:
        Root:
          level: info
          AppenderRef:
            ref: JsonFileAppender
    
    ```