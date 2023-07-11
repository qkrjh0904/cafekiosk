## 0. 개요
### 목표
```text
- 프로덕션 코드의 품질을 보장하는 탄탄한 테스트 코드 작성법 공부하기
- 스프링 & JPA 기반 프로젝트에서의 테스트 코드 작성법 공부하기
- 테스트 프레임워크 JUnit5 다뤄보기
- Mock 프레임워크 Mockito 다뤄보기
- 테스트 코드를 통한 문서 자동화 도구 Spring REST Docs 다뤄보기
```

### 테스트는 왜 필요할까?
```text
프로젝트가 커지면 커질수록
- 커버할 수 없는 영역이 발생한다.
- 경험과 감에 의존하게 된다.
- 유지보수가 어렵다.
- 신뢰도가 하락한다.
- 피드백이 늦다.

우리는 이런걸 필요로한다.
- 빠른 피드백
- 자동화
- 안정감

테스트 코드를 작성하지 않는다면
- 변화가 생기는 매순간마다 발생할 수 있는 모든 케이스를 고려해야한다.
- 변화가 생기는 매순간마다 모든 팀원이 동일한 고민을 해야 한다.
- 빠르게 변화하는 소프트웨어의 안정성을 보장할 수 없다.

하지만 잘못 도입해 병목이 된다면
- 안정성을 제공하기 힘들다.
- 또다른 짐이 된다.
- 잘못된 검증이 이뤄질 수 있다.

올바른 테스트 코드는
- 자동화 테스트로 비교적 빠른 시간 안에 버그를 발견할 수 있고, 
	수동 테스트에 드는 비용을 크게 절약할 수 있다.
- 소프트웨어의 빠른 변화를 지원한다.
- 팀원들의 집단 지성을 팀 차원의 이익으로 승격시킨다.
- 가까이 보면 느리지만, 멀리 보면 가장 빠르다.
```

### 테스트는
귀찮다.
귀찮지만 해야한다.

---

## 1. 요구사항

### 관리자 요구사항
```text
1. 메뉴 추가 기능
2. 메뉴 일부/전체 삭제 기능
3. 주문 목록 총 금액 계산하기
4. 주문 생성하기
```

### 사용자 요구사항
```text
1. 주문 가능한 상품 목록 조회할 수 있다.
2. 판매 상태 : 판매중, 판매보류, 판매중지 (판매중, 판매 보류인 상태의 상품을 화면에 보여줌)
3. 상품구성요소 : id, 상품 번호, 상품 타입, 판매 상태, 상품 이름, 가격
4. 상품 번호 리스트를 받아 주문 생성할 수 있다.
5. 주문은 주문 상태, 주문 등록 시간을 가진다.
6. 주문의 총 금액을 계산할 수 있어야한다.
7. 주문 생성 시 재고 확인 및 개수 차감 후 생성할 수 있다.
8. 재고는 상품 번호를 가진다.
9. 재고와 관련 있는 상품 타입은 병음료, 베이커리 이다.
```

---

## 2. 단위테스트
#### 단위테스트
```text
- 클래스 또는 메서드의 작은 코드 단위를 네트워크 등 외부요인 없이 독립적으로 검증하는 테스트
- 검증속도가 빠르고, 안정적이다.
```

#### JUnit5
```text
- 단위 테스트를 위한 테스트 프레임워크
- XUnit - Kent Beck
```

#### AssertJ
```text
- 테스트 코드 작성을 원활하게 돕는 테스트 라이브러리
- 풍분한 API, 메서드 체이닝 지원
```

#### 테스트 케이스 세분화 하기
```text
- 해피 케이스
  - 정상 플로우
- 예외 케이스
  - 아메리카노 0잔 혹은 -1잔?
- 경계값 테스트
  - 이상, 이하, 초과, 미만, 구간, 날짜 등등..
```

#### 테스트 하기 어려운 영역을 분리하기
```text
- 가게 운영시간(10:00 ~ 22:00) 외에는 주문을 생성할 수 없다는 조건이 있을 때
- 현재 시간에 따라서 테스트가 성공하거나 실패하는 코드를 짜면 안된다.
- 검증하고자 하는 대상은 시간 범위 안에 잘 주문이 되는지이다.
- 그렇기 때문에 외부로 분리할수록 테스트 가능한 코드는 많아진다.
- 관측할 때마다 다른 값에 의존하는 코드(현재 날짜/시간, 랜덤 값, 전역 변수/함수, 사용자 입력 등)
- 외부 세계에 영향을 주는 코드(표준 출력, 이메일 발송, 데이터베이스에 기록하기 등)
```
---
## 3. TDD
#### TDD: Test Driven Development
```text
테스트 코드를 프로덕션 코드보다 먼저 작성하여 테스트가 구현 과정을 주도하도록 하는 방법론.  

TDD 기본 과정 (레드-그린-리팩토링)
- RED : 실패하는 테스트 작성한다.
- GREEN : 테스트 통과하는 최소한의 코딩. 엉터리 코드여도 괜찮다. 초록불을 보기 위한 코딩이다. 
- REFACTOR : 구현 코드를 개선하고 테스트 통과를 유지시킨다.

TDD의 핵심 가치는 피드백이다.  
과감한 리팩토링을 가능하게 해준다.
내가 구현하는 프로덕션 코드에 대한 보장을 해준다.

테스트를 먼저 작성을 한다면 테스트 하기 좋은 구조로 구현할 수 있도록 생각하게 된다. 
```

---

## 4. 테스트는 []다.
#### 테스트는 ?? 이다.
```text
테스트는 문서다.
- 프로덕션 기능을 설명하는 테스트 코드 문서
- 다양한 테스트 케이스를 통해 프로덕션 코드를 이해하는 시각과 관점을 보완
- 어느 한 사람이 과거에 경험했던 고민의 결과물을 팀 차원으로 승격시켜서, 모두의 자산으로 공유할 수 있다.
```

#### DisplayName 을 섬세하게
```text
- 테스트 하고자 하는 행위로 테스트코드 메서드명을 짓는다.
- 명사의 나열보다는 문장으로 짓자.
- 테스트 행위에 대한 결과까지 명시해주면 좋다.
- 테스트의 현상을 중점으로 기술하지 말자.(주문 테스트에 실패한다 -> 주문을 생성할 수 없다.)
```

#### BDD : Behavior Driven Development
```text
- TDD 에서 파생된 개발 방법
- 함수 단위의 테스트에 집중하기 보다, 시나리오에 기반한 테스트케이스 자체에 집중하여 테스트한다.
- 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준을 권장한다.

Given/When/Then
- Given : 시나리오 진행에 필요한 모든 준비 과정(객체, 값, 상태 등)
- When : 시나리오 행동 진행
- Then : 시나리오 진행에 대한 결과 명시, 검증
```

---

## 5. Spring & JPA 기반 테스트

#### Layered Architecture
```text
Layered Architecture 는 보통 Presentation Layer, Business Layer, Persistence Layer 로 구성된다.
이런 형태가 테스트 하기 어렵게 느껴질 수 있다.

여러 객체가 협력해서 하나의 동작을 한다거나, A 모듈과 B 모듈이 합쳐져 어떤 동작을 하는지에 대한 테스트가 필요하다.
그래서 통합테스트가 필요하다.

통합테스트
- 여러 모듈이 협력하는 기능을 통합적으로 검증하는 테스트
- 일반적으로 작은 범위의 단위 테스트만으로는 기능 전체의 신뢰성을 보장할 수 없다
- 풍부한 단위 테스트 & 큰 기능 단위를 검증하는 통합 테스트
```

#### Spring & JPA
```text
Library vs Framework
- 라이브러리는 내 코드가 주체가 되어 외부에서 끌어와 사용한다.
- 프레임워크는 이미 갖춰진 환경이 있고, 그 프레임 안에 내 코드가 들어가 동작을 한다.

Spring
- IoC(Inversion of Control)
- DI(Dependency Injection)
- AOP(Aspect Oriented Programming)

ORM
- 객체지향 패러다임과 관계형 DB 패러다임의 불일치
- 이전에는 개발자가 객체의 데이터를 한땀한땀 매핑하여 DB에 저장 및 조회
- ORM을 사용함으로써 개발자는 단순 작업을 줄이고, 비즈니스 로직에만 집중

JPA
- Java 진영의 ORM 기술 표준
- 인터페이스이고, 여러 구현체가 있지만 보통 Hibernate 를 많이 사용한다.
- 반복적인 CRUD SQL 을 생성 및 실행해주고 여러 부가 기능들을 제공한다.
- 편리하지만 쿼리를 직접 작성하지 않기 때문에 어떤 식으로 쿼리가 만들어지고 실행되는지 명확하게 알아야한다.
- Spring 진영에서는 JPA 를 한번 더 추상화한 Spring Data JPA 제공
- QueryDSL 과 조합하여 많이 사용한다.
```

#### Persistence Layer
```text
- Data Access 의 역할
- 비즈니스 가공 로직이 포함되어서는 안된다. Data 에 대한 CRUD 에만 집중한 레이어.
```

#### Business Layer
```text
- 비즈니스 로직을 구현하는 역할
- Persistence Layer 와의 상호작용을 통해 비즈니스 로직을 전개시킨다.
- 트랜잭션을 보장해야한다.
```

