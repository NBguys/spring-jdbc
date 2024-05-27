# spring-jdbc
테스트에서도 @Slfj4와 같은 애노테이션을 사용하기
build.gradle
```
dependencies {
 //테스트에서 lombok 사용
 testCompileOnly 'org.projectlombok:lombok'
 testAnnotationProcessor 'org.projectlombok:lombok'
}
```

대표적인 커넥션 풀 오픈소스는 commons-dbcp2 , tomcat-jdbc pool , HikariCP 등이 있다.

설정과 사용의 분리
```
설정: DataSource 를 만들고 필요한 속성들을 사용해서 URL , USERNAME , PASSWORD 같은 부분을 입력하는
것을 말한다. 이렇게 설정과 관련된 속성들은 한 곳에 있는 것이 향후 변경에 더 유연하게 대처할 수 있다.
사용: 설정은 신경쓰지 않고, DataSource 의 getConnection() 만 호출해서 사용하면 된다
```

스프링 부트 3.1 이상 - 로그 출력 안되는 문제 해결

src/main/resources/logback.xml
```xml
<configuration>
 <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
 <encoder>
 <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} -%kvp- 
%msg%n</pattern>
 </encoder>
 </appender>
 <root level="DEBUG">
 <appender-ref ref="STDOUT" />
 </root>
</configuration>
```
스프링 부트 3.1 부터 기본 로그 레벨을 INFO 로 빠르게 설정하기 때문에 로그를 확인할 수 없는데, 이렇게하면 기본
로그 레벨을 DEBUG 로 설정해서 강의 내용과 같이 로그를 확인할 수 있다.

트랜잭션 ACID
트랜잭션은 ACID(http://en.wikipedia.org/wiki/ACID)라 하는 원자성(Atomicity), 일관성(Consistency), 격리
성(Isolation), 지속성(Durability)을 보장해야 한다.
```
원자성: 트랜잭션 내에서 실행한 작업들은 마치 하나의 작업인 것처럼 모두 성공 하거나 모두 실패해야 한다.
일관성: 모든 트랜잭션은 일관성 있는 데이터베이스 상태를 유지해야 한다. 예를 들어 데이터베이스에서 정한 무
결성 제약 조건을 항상 만족해야 한다.
격리성: 동시에 실행되는 트랜잭션들이 서로에게 영향을 미치지 않도록 격리한다. 예를 들어 동시에 같은 데이터
를 수정하지 못하도록 해야 한다. 격리성은 동시성과 관련된 성능 이슈로 인해 트랜잭션 격리 수준(Isolation
level)을 선택할 수 있다.
지속성: 트랜잭션을 성공적으로 끝내면 그 결과가 항상 기록되어야 한다. 중간에 시스템에 문제가 발생해도 데이
터베이스 로그 등을 사용해서 성공한 트랜잭션 내용을 복구해야 한다.
```

트랜잭션은 원자성, 일관성, 지속성을 보장한다. 문제는 격리성인데 트랜잭션 간에 격리성을 완벽히 보장하려면 트랜잭
션을 거의 순서대로 실행해야 한다. 이렇게 하면 동시 처리 성능이 매우 나빠진다. 이런 문제로 인해 ANSI 표준은 트랜
잭션의 격리 수준을 4단계로 나누어 정의했다.
```
트랜잭션 격리 수준 - Isolation level
READ UNCOMMITED(커밋되지 않은 읽기)
READ COMMITTED(커밋된 읽기)
REPEATABLE READ(반복 가능한 읽기)
SERIALIZABLE(직렬화 가능) 
```

커밋 설정
```
set autocommit true; //자동 커밋 모드 설정(기본)
set autocommit false; //수동 커밋 모드 설정
```

원자성: 트랜잭션 내에서 실행한 작업들은 마치 하나의 작업인 것처럼 모두 성공 하거나 모두 실패해야 한다.
트랜잭션의 원자성 덕분에 여러 SQL 명령어를 마치 하나의 작업인 것 처럼 처리할 수 있었다. 성공하면 한번에 반영하
고, 중간에 실패해도 마치 하나의 작업을 되돌리는 것 처럼 간단히 되돌릴 수 있다.

조회와 락
데이터를 조회할 때도 락을 획득하고 싶을 때가 있다. 이럴 때는 select for update 구문을 사용하면 된다.
```sql
ex) select * from member where member_id='memberA' for update;
```