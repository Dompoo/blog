# ELK, LG

작성일: 2025년 6월 6일 오후 9:51
카테고리: Log

- 로그 수집, 저장, 분석, 시각화를 위한 기술 스택이다.
- ELK Stack
    - Log Stash : 로그 수집 / 필터링 / 전송
        - 로그 수집 파이프라인이다.
        - 다양한 입력 소스에서 로그를 수집할 수 있다.
        - 수집 후에 Elastic Search로 보낸다.
    - Elastic Search : 로그 저장 / 인덱싱 / 쿼리 기능 제공
        - NoSQL 기반의 분산형 문서 저장소이다.
    - Kibana : 로그 시각화
    - Beats : 데이터 수집 모듈 (FileBeat : 로그, HeartBeat, …)
        - Beats를 사용하게 되면 Beats가 데이터를 수집하여 Logstash로 보내는 구조가 된다.
- LG Stack
    - Promtail : 로그 수집기
        - 파일 로그를 읽고 라벨을 붙인 후 Loki로 보낸다.
    - Loki : 로그 저장소
        - 라벨 기반 메타데이터만 인덱싱한다.
    - Grafana : 시각화 및 탐색 도구
- ELK 스택이 성능이 좀 더 좋고 수평확장에 유리하며 리소스를 많이 잡아먹는다.
- 따라서 ELK → 로그 분석 및 대규모 상황 / LG → 로그를 매트릭처럼 빠르게 보고 싶을 때