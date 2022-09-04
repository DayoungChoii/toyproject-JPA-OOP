**객체지향적 설계를 연습하는 토이 프로젝트입니다.**  
**객체지향 5대 원칙 SOLID를 최대한 지키며 만들려고 노력했습니다.**

## 단일 책임 원칙(SRP, Single Responsibility Principle)
 - 각각의 모듈(회원, 파일, 메시지, 친구)로 책임을 구분
 - 각 모듈의 메소드는 1개의 기능만으로 구현
 
 ## 개방 폐쇄 원칙(OCP, Open-Closed Principle)
 - 로직이 변경될 수 있을 것 같은 모듈은 모두 인터페이스를 만들고 그 인터페이스를 구현함
 - ex)Repository들은 현재는 mybatis로 구현되어 있지만 향후 JPA로 다시 기능을 변경할 것이기에 인터페이스를 구현하여 클래스를 생성함
 
 ## 리스코프 치환 원칙(LSP, Liskov Substitution Principle)
 
 ## 인터페이스 분리의 원칙
 - 최대한 인터페이스를 쪼개서 구현함
 - 만약 기존의 기능에서 특정 기능이 필요하다면 해당용으로 인터페이스를 만들어 제공할 예정
 
 ## 의존 역전 원칙 (DIP, Dependency Inversion Principle)
 - 의존주입하는 객체들은 대부분 인터페이스에 의존하고 있음 따라서 비밀번호 알고리즘이 변경되거나 디비에 접근하는 방법이 바뀌어도 변경 최소화함
 
## 그 외 신경 쓴 사항
공통 예외 처리, 인터셉터, logback, 메시지 처리

진행 과정은 https://velog.io/@maxxyoung/series/%EC%82%B0%EC%82%BC%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8 참고!
