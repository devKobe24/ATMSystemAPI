# ATM System API

간단한 계좌 관리 및 거래 기능을 제공하는 Spring Boot 기반의 ATM 시스템 REST API입니다.

[](https://www.oracle.com/java/)
[](https://spring.io/projects/spring-boot)
[](https://gradle.org/)

## ✨ 주요 기능

  - **계좌 잔액 조회**: 특정 계좌의 현재 잔액을 확인합니다.
  - **입금**: 계좌에 현금을 입금하고, 거래 내역을 기록합니다.
  - **출금**: PIN 번호 인증 후 계좌에서 현금을 출금하고, 거래 내역을 기록합니다.
  - **거래 내역 관리**: 모든 입출금 내역을 데이터베이스에 저장합니다.
  - **예외 처리**: 계좌 불일치, 잔액 부족 등 다양한 예외 상황에 대한 처리를 지원합니다.

## 🛠️ 기술 스택

| 기술 | 버전/종류 | 용도 |
| :--- | :--- | :--- |
| **Java** | 17 | 백엔드 언어 |
| **Spring Boot** | 3.5.5 | 애플리케이션 프레임워크 |
| **Spring Data JPA** | - | 데이터베이스 연동 및 ORM |
| **H2 Database** | - | 인메모리 관계형 데이터베이스 |
| **Lombok** | - | 보일러플레이트 코드 자동 생성 |
| **Gradle** | 8.14.3 | 빌드 및 의존성 관리 도구 |

## 🚀 빠른 시작

### 사전 요구사항

  - Java 17 이상
  - Gradle 8.14.3 이상

### 설치 및 실행

1.  **프로젝트 복제**

    ```bash
    git clone [저장소 URL]
    cd ATMSystem
    ```

2.  **프로젝트 빌드**

    ```bash
    ./gradlew clean build
    ```

3.  **애플리케이션 실행**

    ```bash
    ./gradlew bootRun
    ```

4.  **H2 데이터베이스 콘솔 접속**

      - 애플리케이션 실행 후, 웹 브라우저에서 `http://localhost:8080/h2-console`로 접속합니다.
      - **JDBC URL**: `jdbc:h2:mem:atmdb` 로 설정 후 연결하면, 초기 데이터를 확인할 수 있습니다.

## 📖 API 문서

**Base URL**: `http://localhost:8080/api/atm`

### 1\. 잔액 조회

**`GET /{accountNumber}/balance`**

계좌 번호에 해당하는 계좌의 현재 잔액을 조회합니다.

  - **요청 예시 (`curl`)**:

    ```bash
    curl -X GET http://localhost:8080/api/atm/111-222-3333/balance
    ```

  - **성공 응답 예시 (200 OK)**:

    ```json
    {
      "accountNumber": "111-222-3333",
      "balance": 100000,
      "timestamp": "2025-09-19T06:15:35.123456"
    }
    ```

### 2\. 입금

**`POST /deposit`**

지정된 계좌에 금액을 입금합니다.

  - **요청 본문**:

    ```json
    {
      "accountNumber": "111-222-3333",
      "amount": 50000
    }
    ```

  - **성공 응답 예시 (200 OK)** (입금 후 잔액 반환):

    ```json
    {
      "accountNumber": "111-222-3333",
      "balance": 150000,
      "timestamp": "2025-09-19T06:16:00.123456"
    }
    ```

### 3\. 출금

**`POST /withdraw`**

지정된 계좌에서 PIN 번호 인증 후 금액을 출금합니다.

  - **요청 본문**:

    ```json
    {
      "accountNumber": "111-222-3333",
      "pin": "1234",
      "amount": 20000
    }
    ```

  - **성공 응답 예시 (200 OK)** (출금 후 잔액 반환):

    ```json
    {
      "accountNumber": "111-222-3333",
      "balance": 80000,
      "timestamp": "2025-09-19T06:17:00.123456"
    }
    ```

### 예외 처리 응답

| HTTP 상태 코드 | 상황 | 응답 메시지 예시 |
| :--- | :--- | :--- |
| **400 Bad Request** | 계좌번호를 찾을 수 없거나 PIN이 틀린 경우 | `"계좌를 찾을 수 없습니다: 123-456-7890"` |
| **400 Bad Request** | 요청 값 유효성 검증 실패 시 | `{"amount": "최소 출금액은 1,000원입니다."}` |
| **402 Payment Required** | 잔액이 부족한 경우 | `"잔액이 부족합니다."` |
| **500 Internal Server Error** | 그 외 서버 내부 오류 | `"서버 내부 오류가 발생했습니다."` |

## 📁 프로젝트 구조

```
src/
├── main/
│   ├── java/com/kobe/atmsystem/
│   │   ├── AtmSystemApplication.java    # 메인 애플리케이션
│   │   ├── controller/
│   │   │   └── AtmController.java       # API 엔드포인트 정의
│   │   ├── service/
│   │   │   ├── AtmService.java          # 입출금, 잔액조회 등 핵심 서비스
│   │   │   └── AccountService.java      # 계좌 조회 관련 서비스
│   │   ├── model/                       # JPA 엔티티 (도메인 모델)
│   │   │   ├── Account.java
│   │   │   ├── Transaction.java
│   │   │   └── TransactionType.java
│   │   ├── repository/                  # Spring Data JPA 리포지토리
│   │   │   ├── AccountRepository.java
│   │   │   └── TransactionRepository.java
│   │   ├── dto/                         # 데이터 전송 객체 (Request/Response)
│   │   │   ├── WithdrawRequest.java
│   │   │   ├── DepositRequest.java
│   │   │   └── BalanceResponse.java
│   │   └── exception/                   # 커스텀 예외 및 전역 핸들러
│   │       ├── InsufficientFundsException.java
│   │       ├── AccountNotFountException.java
│   │       └── GlobalExceptionHandler.java
│   └── resources/
│       ├── application.yml              # 애플리케이션 설정
│       └── data.sql                     # 초기 데이터 스크립트
└── test/
```