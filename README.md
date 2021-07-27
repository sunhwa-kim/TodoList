# TodoList
<br>

### project
personal, 2021.07.27

### BE
* gradle
* Spring Boot
  * springboot data jpa
  * springboot data rest
  * springboot test
* JDK 11
* H2DB
* projectlombok

### API
* POST
* GET
* GET(id)
* DELETE(id)
* DELETE

### reference
* [SpringBoot](https://spring.io/guides/gs/spring-boot/)
* [SpringBoot rest](https://spring.io/guides/gs/accessing-data-rest/)
* lombok : maven repository


### [FE 확인](https://todobackend.com/)
* Example Implementation -> url + http://localhost:8080 
<div style="inline;">
<img src="https://user-images.githubusercontent.com/66774973/127161732-6b5f752a-025c-4953-b086-fa127451038f.PNG" width="300">
</div>

### process
#### layerd
* controller, service, repository, model
  * controller : request/response
  * service : request/response - DB
  * model : Object
  * repository : DB
  
* TodoResponse 생성은 어디에서?
  * service가 DB로 보내는 객체와 받아오는 데이터 책임 &
  TodoResponse 객체 생성은 Controller에서 적절하다고 생각
  * How to request is null or the result of DB is null ?
    * Response status가 변화
  
* Test
  * unit test? [참조](https://docs.microsoft.com/ko-kr/dotnet/core/testing/unit-testing-best-practices)
   > 좋은 단위 테스트의 특징
    * Fast. 완성도 높은 프로젝트에서 수천 개의 단위 테스트를 수행하는 것은 드문 일이 아닙니다. 단위 테스트는 실행하는 데 시간이 거의 걸리지 않습니다. 밀리초.
    * Isolated. 독립형 단위 테스트는 독립적으로 실행될 수 있으며, 파일 시스템 또는 데이터베이스와 같은 외부 요인에 종속되지 않습니다.
    * 반복 가능. 단위 테스트를 실행하는 것은 해당 결과와 일치해야 합니다. 즉, 실행 사이에 아무 것도 변경하지 않으면 항상 동일한 결과를 반환합니다.
    * 자체 검사. 테스트는 사람의 개입 없이 통과했는지 여부를 자동으로 검색할 수 있어야 합니다.
    * Timely. 단위 테스트는 테스트 중인 코드에 비해 작성하는 데 불균형적으로 긴 시간이 걸리지 않아야 합니다. 코드를 작성하는 데 비해 많은 시간이 걸리는 코드를 테스트하는 경우 더 많은 테스트가 가능한 디자인을 고려해 보세요.
    ```java
      @ExtendWith(MockitoExtension.class)
      @Mock
      @InjectMocks
    ```
    