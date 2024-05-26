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