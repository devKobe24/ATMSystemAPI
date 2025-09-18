# ATM System API

<div align="center">
  <p>
    <img src="https://img.shields.io/badge/Java-17-orange" alt="Java">
    <img src="https://img.shields.io/badge/Spring_Boot-3.5.5-brightgreen" alt="Spring Boot">
    <img src="https://img.shields.io/badge/Gradle-8.14.3-blue" alt="Gradle">
    <img src="https://img.shields.io/badge/H2_Database-2.x-lightblue" alt="H2">
  </p>
</div>

간단한 계좌 관리 및 거래 기능을 제공하는 **Spring Boot 기반의 ATM 시스템 REST API**입니다.

## 📋 목차

- [주요 기능](#-주요-기능)
- [기술 스택](#️-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [빠른 시작](#-빠른-시작)
- [API 문서](#-api-문서)
- [데이터베이스 스키마](#-데이터베이스-스키마)
- [예외 처리](#-예외-처리)
- [테스트](#-테스트)
- [개발 환경 설정](#️-개발-환경-설정)

## ✨ 주요 기능

### 🔍 계좌 관리
- **잔액 조회**: 특정 계좌의 현재 잔액을 실시간으로 확인
- **계좌 인증**: PIN 번호를 통한 보안 인증 시스템

### 💰 거래 기능
- **입금**: 계좌에 현금 입금 및 거래 내역 자동 기록
- **출금**: PIN 인증 후 계좌에서 현금 출금 및 잔액 검증
- **거래 내역**: 모든 입출금 거래를 데이터베이스에 영구 저장

### 🛡️ 보안 및 예외 처리
- PIN 번호 기반 계좌 인증
- 잔액 부족 시 출금 차단
- 계좌 번호 검증 및 오류 처리
- 전역 예외 핸들러를 통한 일관된 에러 응답

## 🛠️ 기술 스택

| **카테고리** | **기술** | **버전** | **용도** |
|:------------|:---------|:---------|:---------|
| **언어** | Java | 17 | 백엔드 개발 언어 |
| **프레임워크** | Spring Boot | 3.5.5 | 애플리케이션 프레임워크 |
| **ORM** | Spring Data JPA | 포함됨 | 데이터 접근 계층 |
| **데이터베이스** | H2 Database | 인메모리 | 개발/테스트용 DB |
| **검증** | Bean Validation | 포함됨 | 입력값 유효성 검사 |
| **유틸리티** | Lombok | - | 보일러플레이트 코드 생성 |
| **빌드 도구** | Gradle | 8.14.3 | 의존성 관리 및 빌드 |

## 📁 프로젝트 구조

```
src/main/java/com/kobe/atmsystem/
├── AtmSystemApplication.java           # Spring Boot 메인 클래스
├── controller/
│   └── AtmController.java             # REST API 엔드포인트
├── service/
│   ├── AtmService.java                # 핵심 비즈니스 로직 (입출금, 잔액조회)
│   └── AccountService.java            # 계좌 관련 서비스
├── model/                             # JPA 엔티티 (도메인 모델)
│   ├── Account.java                   # 계좌 엔티티
│   ├── Transaction.java               # 거래 내역 엔티티
│   └── TransactionType.java           # 거래 유형 Enum
├── repository/                        # 데이터 접근 계층
│   ├── AccountRepository.java         # 계좌 리포지토리
│   └── TransactionRepository.java     # 거래 내역 리포지토리
├── dto/                              # 데이터 전송 객체
│   ├── BalanceResponse.java          # 잔액 조회 응답
│   ├── DepositRequest.java           # 입금 요청
│   ├── WithdrawRequest.java          # 출금 요청
│   └── ErrorResponse.java            # 에러 응답
└── exception/                        # 예외 처리
    ├── AccountNotFountException.java  # 계좌 미발견 예외
    ├── InsufficientFundsException.java # 잔액 부족 예외
    ├── InvalidPinException.java       # PIN 오류 예외
    └── GlobalExceptionHandler.java    # 전역 예외 핸들러
```

## 🚀 빠른 시작

### 📋 사전 요구사항

- **Java**: 17 이상
- **Gradle**: 8.14.3 이상 (또는 wrapper 사용)

### 🔧 설치 및 실행

1. **프로젝트 클론**
   ```bash
   git clone [저장소 URL]
   cd ATMSystem
   ```

2. **프로젝트 빌드**
   ```bash
   ./gradlew clean build
   ```

3. **애플리케이션 실행**
   ```bash
   ./gradlew bootRun
   ```
   
   또는 JAR 파일 실행:
   ```bash
   java -jar build/libs/ATMSystem-0.0.1-SNAPSHOT.jar
   ```

4. **서비스 확인**
   - 애플리케이션: http://localhost:8080
   - H2 콘솔: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:atmdb`
     - Username: `sa`
     - Password: (빈값)

## 📖 API 문서

**Base URL**: `http://localhost:8080/api/atm`

### 💰 입금 (Deposit)

**`POST /deposit`**

지정된 계좌에 금액을 입금합니다.

**Request Body:**
```json
{
  "accountNumber": "111-222-3333",
  "amount": 50000
}
```

**Response (200 OK):**
```json
{
  "accountNumber": "111-222-3333",
  "balance": 150000,
  "timestamp": "2025-09-19T06:16:00.123456"
}
```

**cURL 예시:**
```bash
curl -X POST http://localhost:8080/api/atm/deposit \
  -H "Content-Type: application/json" \
  -d '{"accountNumber": "111-222-3333", "amount": 50000}'
```

### 💸 출금 (Withdraw)

**`POST /withdraw`**

PIN 번호 인증 후 지정된 계좌에서 금액을 출금합니다.

**Request Body:**
```json
{
  "accountNumber": "111-222-3333",
  "pin": "1234",
  "amount": 20000
}
```

**Response (200 OK):**
```json
{
  "accountNumber": "111-222-3333",
  "balance": 80000,
  "timestamp": "2025-09-19T06:17:00.123456"
}
```

**cURL 예시:**
```bash
curl -X POST http://localhost:8080/api/atm/withdraw \
  -H "Content-Type: application/json" \
  -d '{"accountNumber": "111-222-3333", "pin": "1234", "amount": 20000}'
```

### 🔍 잔액 조회 (Balance Inquiry)

**`GET /{accountNumber}/balance`**

계좌 번호에 해당하는 계좌의 현재 잔액을 조회합니다.

**Response (200 OK):**
```json
{
  "accountNumber": "111-222-3333",
  "balance": 100000,
  "timestamp": "2025-09-19T06:15:35.123456"
}
```

**cURL 예시:**
```bash
curl -X GET http://localhost:8080/api/atm/111-222-3333/balance
```

## 📊 데이터베이스 스키마

### 🏦 계좌 테이블 (accounts)
| 컬럼명 | 타입 | 제약조건 | 설명 |
|:-------|:-----|:---------|:-----|
| id | BIGINT | PK, AUTO_INCREMENT | 계좌 ID |
| account_number | VARCHAR | UNIQUE, NOT NULL | 계좌 번호 |
| pin | VARCHAR | NOT NULL | PIN 번호 |
| holder_name | VARCHAR | NOT NULL | 예금주명 |
| balance | DECIMAL | NOT NULL | 계좌 잔액 |

### 📋 거래 테이블 (transactions)
| 컬럼명 | 타입 | 제약조건 | 설명 |
|:-------|:-----|:---------|:-----|
| id | BIGINT | PK, AUTO_INCREMENT | 거래 ID |
| account_id | BIGINT | FK, NOT NULL | 계좌 ID |
| type | VARCHAR | NOT NULL | 거래 유형 (DEPOSIT/WITHDRAWAL) |
| amount | DECIMAL | NOT NULL | 거래 금액 |
| balance_after | DECIMAL | NOT NULL | 거래 후 잔액 |
| timestamp | TIMESTAMP | AUTO | 거래 시간 |

### 📂 초기 데이터
```sql
-- 테스트 계좌 데이터
INSERT INTO accounts (account_number, pin, holder_name, balance) 
VALUES ('111-222-3333', '1234', '김철수', 100000);

INSERT INTO accounts (account_number, pin, holder_name, balance) 
VALUES ('444-555-6666', '5678', '이영희', 50000);
```

## ⚠️ 예외 처리

### HTTP 상태 코드별 응답

| **상태 코드** | **상황** | **응답 예시** |
|:-------------|:---------|:-------------|
| **200 OK** | 정상 처리 | 잔액 정보 반환 |
| **400 Bad Request** | 계좌 미발견, PIN 오류 | `"계좌를 찾을 수 없습니다: 123-456-7890"` |
| **400 Bad Request** | 입력값 검증 실패 | `{"amount": "최소 출금액은 1,000원입니다."}` |
| **402 Payment Required** | 잔액 부족 | `"잔액이 부족합니다."` |
| **500 Internal Server Error** | 서버 오류 | `"서버 내부 오류가 발생했습니다."` |

### 입력값 검증 규칙

- **계좌번호**: 필수값, 빈 문자열 불가
- **PIN**: 필수값 (출금 시), 빈 문자열 불가
- **금액**: 필수값, 최소 1,000원 이상

## 🧪 테스트

### 단위 테스트 실행
```bash
./gradlew test
```

### 통합 테스트 실행
```bash
./gradlew integrationTest
```

### 테스트 커버리지 확인
```bash
./gradlew jacocoTestReport
```

## ⚙️ 개발 환경 설정

### IDE 설정 (IntelliJ IDEA)
1. **Lombok 플러그인 설치**
2. **Annotation Processing 활성화**
   - Settings → Build → Compiler → Annotation Processors
   - "Enable annotation processing" 체크

### 환경별 설정

#### 개발 환경 (application-dev.yml)
```yaml
spring:
  jpa:
    show-sql: true
    hibernate:
      format_sql: true
  h2:
    console:
      enabled: true
```

#### 운영 환경 (application-prod.yml)
```yaml
spring:
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: validate
  h2:
    console:
      enabled: false
```

## 📝 API 사용 예시

### Postman Collection

**환경 변수:**
- `BASE_URL`: `http://localhost:8080`

**테스트 시나리오:**
1. 잔액 조회 → 입금 → 잔액 재조회
2. 출금 (정상) → 잔액 조회
3. 출금 (잔액 부족) → 에러 확인
4. 잘못된 PIN으로 출금 시도 → 에러 확인

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 [MIT License](LICENSE) 하에 배포됩니다.

## 📞 문의

프로젝트 관련 문의사항이 있으시면 이슈를 등록해주세요.

---

<div align="center">
  Made with ❤️ by <strong>Kobe</strong>
</div>