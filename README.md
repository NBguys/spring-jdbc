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