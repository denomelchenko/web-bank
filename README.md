# Web Bank

A Spring Boot 3.x based web banking application implementing core banking operations with JWT-based authentication, role-based access control, and a comprehensive REST API.

##  Tech Stack

- **Java 21** - Modern Java LTS
- **Spring Boot 3.2.3** - Latest Spring Boot 3.x
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Database abstraction
- **Hibernate** - ORM framework
- **MySQL** - Primary database
- **JWT (jjwt 0.12.3)** - Token-based authentication
- **Lombok** - Boilerplate reduction
- **Maven** - Build tool

## 📁 Project Structure

```
src/main/java/com/domelchenko/webbank/
├── WebBankApplication.java          # Main entry point
├── config/
│   ├── ProjectSecurityConfig.java   # Security configuration (JWT, CSRF, CORS)
│   └── WebBankUsernamePwdAuthenticationProvider.java  # Custom auth provider
├── controller/
│   ├── AccountController.java       # Account management
│   ├── BalanceController.java       # Transaction history
│   ├── CardsController.java         # Credit/debit cards
│   ├── ContactController.java       # Contact form
│   ├── LoansController.java         # Loan management
│   ├── LoginController.java         # Registration & user info
│   └── NoticesController.java       # Bank notices
├── filter/
│   ├── CsrfCookieFilter.java        # CSRF token handling
│   ├── JwtTokenGeneratorFilter.java # JWT token generation
│   └── JwtTokenValidatorFilter.java # JWT token validation
├── model/
│   ├── Accounts.java                # Bank accounts
│   ├── AccountTransactions.java     # Transaction history
│   ├── Authority.java               # User roles/permissions
│   ├── Cards.java                   # Credit/debit cards
│   ├── Contact.java                 # Contact messages
│   ├── Customer.java                # User entity
│   ├── Loans.java                   # Loans
│   └── Notice.java                  # Bank notices
└── repository/                      # Spring Data JPA repositories
    ├── AccountTransactionsRepository.java
    ├── AccountsRepository.java
    ├── CardsRepository.java
    ├── ContactRepository.java
    ├── CustomerRepository.java
    ├── LoanRepository.java
    └── NoticeRepository.java
```

## 🔐 Security Architecture

### Authentication Flow

1. **User Registration** (`POST /register`) - Creates new customer with BCrypt password encoding
2. **Login** - Uses Spring Security's form login + HTTP Basic
3. **JWT Generation** - `JwtTokenGeneratorFilter` creates token after successful auth
4. **JWT Validation** - `JwtTokenValidatorFilter` validates token on subsequent requests
5. **Custom Auth Provider** - `WebBankUsernamePwdAuthenticationProvider` validates against database

### Role-Based Access Control

| Endpoint | Roles Allowed |
|----------|---------------|
| `/myAccount` | USER |
| `/myBalance` | USER, ADMIN |
| `/loans` / `/myLoans` | USER |
| `/myCards` | USER |
| `/user` | Authenticated |
| `/notices`, `/contact`, `/register` | Public |

### Security Features

- **Stateless Session Management** - No server-side sessions
- **JWT Tokens** - Short-lived (3 seconds expiry in current config)
- **CSRF Protection** - Cookie-based CSRF tokens
- **CORS Configuration** - Allows `http://localhost:4200` (Angular default)
- **Password Encoding** - BCrypt with configurable strength

## 🗄️ Database Schema

### Core Entities

```
Customer (1) ──────< (N) Authority
    │
    ├──< (N) Accounts
    │       │
    │       └──< (N) AccountTransactions
    ├──< (N) Cards
    ├──< (N) Loans
    └──< (N) Contact

Notice (standalone - bank announcements)
```

### Key Tables

| Table | Description |
|-------|-------------|
| `customer` | User accounts with email, hashed password, role |
| `authorities` | Role mappings (ROLE_USER, ROLE_ADMIN) |
| `accounts` | Bank accounts linked to customers |
| `account_transactions` | Transaction history per account |
| `cards` | Credit/debit cards per customer |
| `loans` | Loan records per customer |
| `contact_messages` | Contact form submissions |
| `notice_details` | Bank notices/announcements |

##  API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/register` | Register new user |
| GET | `/user` | Get authenticated user details |

### Account Management
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/myAccount?id={id}` | Get account by customer ID | USER |

### Balance & Transactions
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/myBalance?id={id}` | Get transaction history (descending) | USER, ADMIN |

### Cards
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/myCards?id={id}` | Get cards by customer ID | USER |

### Loans
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/myLoans?id={id}` | Get loans by customer ID | USER |

### Contact
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/contact` | Submit contact inquiry | Public |

### Notices
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/notices` | Get active notices (cached 60s) | Public |

##  Configuration

### Required Environment Variables / Properties

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/web_bank
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Security
security.secret=your-super-secret-key-min-256-bits
security.header=Authorization

# Server
server.port=8080
```

### Application Properties (application.properties)

Create `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/web_bank?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration
security.secret=your-very-long-secret-key-at-least-256-bits-long-for-security
security.header=Authorization

# Server
server.port=8080
server.servlet.context-path=/api

# CORS (adjust for your frontend)
spring.mvc.cors.allowed-origins=http://localhost:4200
spring.mvc.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.mvc.cors.allowed-headers=*
spring.mvc.cors.allow-credentials=true
```

## 🛠️ Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+
- MySQL 8.0+

### Installation

```bash
# Clone the repository
git clone https://github.com/denomelchenko/web-bank.git
cd web-bank

# Create MySQL database
mysql -u root -p -e "CREATE DATABASE web_bank;"

# Configure application.properties (see Configuration section)

# Build and run
./mvnw spring-boot:run
```

Or build JAR and run:
```bash
./mvnw clean package
java -jar target/web-bank-0.0.1-SNAPSHOT.jar
```

### Verify Installation
```bash
# Health check
curl http://localhost:8080/notices

# Register a user
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","mobileNumber":"1234567890","pwd":"password123","role":"USER"}'

# Login (form-based)
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=john@example.com&password=password123"
```

##  Testing

```bash
# Run tests
./mvnw test

# Run with coverage
./mvnw test jacoco:report
```

##  Building for Production

```bash
# Package as JAR
./mvnw clean package -DskipTests

# Run with production profile
java -jar -Dspring.profiles.active=prod target/web-bank-0.0.1-SNAPSHOT.jar
```

##  Development

### Adding New Features

1. Create entity in `model/`
2. Create repository in `repository/`
3. Create controller in `controller/`
4. Add security rules in `ProjectSecurityConfig.java`
5. Add database migration if needed

### Code Style

- Uses Lombok for getters/setters/builders
- Follows Spring Boot conventions
- RESTful API design
- Proper HTTP status codes

##  License

MIT License - see [LICENSE](LICENSE) file for details.

Copyright (c) 2023 Denys Omelchenko

##  Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

##  Known Issues / TODO

- [ ] JWT token expiry is only 3 seconds (configurable via `security.secret`)
- [ ] No refresh token implementation
- [ ] No password reset flow
- [ ] No API documentation (Swagger/OpenAPI)
- [ ] No integration tests
- [ ] CORS hardcoded to localhost:4200
- [ ] SQL injection risk in some queries (uses parameterized queries mostly)

##  Related Projects

- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JJWT Library](https://github.com/jwtk/jjwt)
