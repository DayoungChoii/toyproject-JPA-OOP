## 소개글
**객체지향적 설계와 JPA를 연습하는 토이 프로젝트입니다.**  
**객체지향 5대 원칙 SOLID를 최대한 지키며 만들려고 노력했습니다.**  
[토이프로젝트 진행과정](https://velog.io/@maxxyoung/series/OOP-PJT)

### 주요 기능
#### 회원
- 로그인
- 회원가입

#### 메시지
- 받은 메시지 수, 보낸 메시지 수
- 메시지요청
- 메시지 작성 전송
- 받은 메시지 목록
- 보낸 메시지 목록
- 요청받은 메시지 목록
- 메시지 단 건 조회

#### 친구
- 친구 목록
- 친구 찾기
- 친구 등록
- 받은 친구 목록
- 보낸 친구 목록

## DB 설계
![image](https://user-images.githubusercontent.com/38481737/201271952-de5cff5c-aa61-4b64-81b8-7ae0fafcea91.png)

## Tech Stacks
- Spring Boot, Java, MySQL, JPA
- Thymleaf, Javascript, JQuery
- Git, Gradle, Intellij, AWS, Jenkins

## MyBatis -> JPA Refactoring
[리팩토링 진행과정](https://velog.io/@maxxyoung/step6-mybatis-JPA-%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81)

## SOLID
#### 단일 책임 원칙(SRP, Single Responsibility Principle)
 - 각각의 모듈(회원, 파일, 메시지, 친구)로 책임을 구분
 - 각 모듈의 메소드는 1개의 기능만으로 구현
 
 #### 개방 폐쇄 원칙(OCP, Open-Closed Principle)
 - 로직이 변경될 수 있을 것 같은 모듈은 모두 인터페이스를 만들고 그 인터페이스를 구현함
 - ex)현재 비밀번호는 SHA256으로 암호화하지만 추 후 비밀번호 알고리즘이 변경될 것을 고려하여 인터페이스와 그 구현체를 사용
 #### 리스코프 치환 원칙(LSP, Liskov Substitution Principle)
 
 #### 인터페이스 분리의 원칙
 - 최대한 인터페이스를 쪼개서 구현함
 - 만약 기존의 기능에서 특정 기능이 필요하다면 해당용으로 인터페이스를 만들어 제공할 예정
 
 #### 의존 역전 원칙 (DIP, Dependency Inversion Principle)
 - 의존주입하는 객체들 중 바뀔 가능성이 있는 로직은 인터페이스에 의존하고 있음 따라서 비밀번호 알고리즘이 바뀌어도 변경 최소화함

## AWS EC2, RDS, Jenkins 배포
![스크린샷 2022-11-11 오후 12 55 53](https://user-images.githubusercontent.com/38481737/201261743-9999519e-be82-410f-b689-549551ddcac8.png)

## 그 외 신경 쓴 사항
공통 예외 처리, 인터셉터, logback, 메시지 처리
