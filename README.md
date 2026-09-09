 Loan Verification \& Data Integrity Management System



A full-stack web application designed to streamline \*\*loan processing, validation, verification, exception management, and data integrity monitoring.



The system provides a centralized dashboard for managing loan records, identifying validation issues, tracking exceptions, verifying loan records, and detecting unauthorized data modifications using SHA-256 hash-based integrity verification.



\---



\## 🚀 Key Features



\### 📊 Dashboard



\* Total loan count

\* Pending loans

\* Verified loans

\* Rejected loans

\* Modified loans

\* Total exceptions

\* Open exceptions

\* Resolved exceptions



\### 💰 Loan Management



\* Create new loan records

\* View loan details

\* Update loan information

\* Delete loan records

\* Search loans by Loan ID or borrower name

\* Filter loans by status

\* Filter loans by verification status



\### ✅ Loan Validation \& Verification



\* Automatic loan data validation

\* Validation of borrower name

\* Loan amount validation

\* Interest rate validation

\* Loan date validation

\* Loan verification workflow

\* Rejection of invalid loan records



\### 🔐 Data Integrity Verification



\* SHA-256 hash generation for verified loan records

\* Stored hash comparison

\* Detection of modified loan records

\* Automatic status update to `MODIFIED`

\* Automatic exception creation when data modification is detected



\### ⚠️ Exception Management



\* Automatic exception generation for validation failures

\* Exception severity classification

\* Open and resolved exception tracking

\* Search exceptions by Loan ID

\* Filter by exception status

\* Filter by severity

\* Resolve exceptions



\### 📂 CSV Loan Upload



\* Upload loan records through CSV

\* Process and validate uploaded records

\* Generate exceptions for invalid records



\---



\## 🛠️ Technology Stack



\### Backend



\* Java

\* Spring Boot

\* Spring Data JPA

\* Maven

\* REST APIs

\* MySQL

\* SHA-256



\### Frontend



\* React.js

\* JavaScript

\* HTML5

\* CSS3

\* Axios



\### Development Tools



\* Visual Studio Code

\* Spring Tool Suite / Eclipse

\* Git

\* GitHub



\---



\## 🏗️ System Architecture



```text

&#x20;                   ┌──────────────────────┐

&#x20;                   │      React.js        │

&#x20;                   │      Frontend        │

&#x20;                   └──────────┬───────────┘

&#x20;                              │

&#x20;                              │ REST API

&#x20;                              ▼

&#x20;                   ┌──────────────────────┐

&#x20;                   │    Spring Boot       │

&#x20;                   │       Backend        │

&#x20;                   ├──────────────────────┤

&#x20;                   │ Controllers           │

&#x20;                   │ Services              │

&#x20;                   │ Validation            │

&#x20;                   │ Exception Management  │

&#x20;                   │ Hash Verification     │

&#x20;                   └──────────┬───────────┘

&#x20;                              │

&#x20;                              │ JPA

&#x20;                              ▼

&#x20;                   ┌──────────────────────┐

&#x20;                   │       MySQL          │

&#x20;                   │      Database        │

&#x20;                   └──────────────────────┘

```



\---



\## 📁 Project Structure



```text

loan-verification-system/

│

├── loan-verification/

│   ├── src/

│   │   ├── main/

│   │   │   ├── java/com/intain/loanverification/

│   │   │   │   ├── controller/

│   │   │   │   ├── dto/

│   │   │   │   ├── entity/

│   │   │   │   ├── exception/

│   │   │   │   ├── repository/

│   │   │   │   └── service/

│   │   │   └── resources/

│   │   │       └── application.properties

│   │   └── test/

│   │

│   ├── pom.xml

│   └── mvnw

│

├── loan-verification-frontend/

│   ├── public/

│   ├── src/

│   │   ├── App.js

│   │   ├── App.css

│   │   └── LoanList.js

│   ├── package.json

│   └── package-lock.json

│

├── .gitignore

└── README.md

```



\---



\## 🔄 Application Workflow



```text

Loan Creation

&#x20;     │

&#x20;     ▼

Input Validation

&#x20;     │

&#x20;     ├── Invalid ──► Create Exception

&#x20;     │                    │

&#x20;     │                    ▼

&#x20;     │                REJECTED

&#x20;     │

&#x20;     └── Valid

&#x20;          │

&#x20;          ▼

&#x20;     Loan Verification

&#x20;          │

&#x20;          ▼

&#x20;     Generate SHA-256 Hash

&#x20;          │

&#x20;          ▼

&#x20;       VERIFIED

&#x20;          │

&#x20;          ▼

&#x20;    Hash Verification

&#x20;          │

&#x20;     ┌────┴────┐

&#x20;     │         │

&#x20;   Valid    Modified

&#x20;     │         │

&#x20;     ▼         ▼

&#x20;  Valid      MODIFIED

&#x20;  Record     + Exception

```



\---



\## 🔐 Data Integrity



The application uses \*\*SHA-256 hashing\*\* to verify whether a verified loan record has been changed.



When a loan is verified:



```text

Loan Data

&#x20;  │

&#x20;  ▼

SHA-256 Hash

&#x20;  │

&#x20;  ▼

Stored in Database

```



During subsequent verification:



```text

Current Loan Data

&#x20;      │

&#x20;      ▼

Generate New SHA-256 Hash

&#x20;      │

&#x20;      ▼

Compare with Stored Hash

&#x20;      │

&#x20;  ┌───┴────┐

&#x20;  │        │

&#x20;Match    No Match

&#x20;  │        │

&#x20;  ▼        ▼

&#x20;VALID    MODIFIED

&#x20;         Exception

```



This helps identify unauthorized or unexpected modifications to verified loan records.



\---



\## 🌐 REST API Endpoints



\### Loan APIs



| Method | Endpoint                                       | Description                   |

| ------ | ---------------------------------------------- | ----------------------------- |

| GET    | `/api/loans`                                   | Get all loans                 |

| POST   | `/api/loans`                                   | Create a loan                 |

| GET    | `/api/loans/{id}`                              | Get loan by ID                |

| GET    | `/api/loans/loan-id/{loanId}`                  | Get loan by Loan ID           |

| GET    | `/api/loans/summary`                           | Get loan dashboard summary    |

| GET    | `/api/loans/status/{status}`                   | Filter by loan status         |

| GET    | `/api/loans/verification/{verificationStatus}` | Filter by verification status |

| PUT    | `/api/loans/{id}`                              | Update loan                   |

| POST   | `/api/loans/{id}/verify`                       | Verify loan                   |

| GET    | `/api/loans/{id}/verify-hash`                  | Verify data integrity         |

| DELETE | `/api/loans/{id}`                              | Delete loan                   |



\### Exception APIs



| Method | Endpoint                        | Description               |

| ------ | ------------------------------- | ------------------------- |

| GET    | `/api/exceptions`               | Get all exceptions        |

| POST   | `/api/exceptions`               | Create exception          |

| GET    | `/api/exceptions/loan/{loanId}` | Get exceptions for a loan |

| GET    | `/api/exceptions/open`          | Get open exceptions       |

| GET    | `/api/exceptions/summary`       | Get exception summary     |

| PUT    | `/api/exceptions/{id}/resolve`  | Resolve exception         |



\---



\## ⚙️ How to Run the Backend



\### 1. Prerequisites



Install:



\* Java 21 or compatible JDK

\* Maven

\* MySQL



\### 2. Configure MySQL



Create a MySQL database:



```sql

CREATE DATABASE loan\_verification;

```



Update the database configuration in:



```text

loan-verification/src/main/resources/application.properties

```



Example:



```properties

spring.datasource.url=jdbc:mysql://localhost:3306/loan\_verification

spring.datasource.username=root

spring.datasource.password=YOUR\_PASSWORD



spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

```



Do not commit real database passwords to GitHub.



\### 3. Start the Backend



Navigate to the backend:



```powershell

cd loan-verification

```



Run:



```powershell

.\\mvnw.cmd spring-boot:run

```



The backend will run on:



```text

http://localhost:8080

```



\---



\## 💻 How to Run the Frontend



Open a new terminal.



Navigate to:



```powershell

cd loan-verification-frontend

```



Install dependencies:



```powershell

npm install

```



Start the React application:



```powershell

npm start

```



The frontend will normally run on:



```text

http://localhost:3000

```



\---



\## 🧪 Testing the Application



The application can be tested using the following workflow:



1\. Create a loan.

2\. Enter valid loan information.

3\. Verify the loan.

4\. Generate and store the SHA-256 hash.

5\. Modify the loan information.

6\. Run hash verification.

7\. The system detects the modification.

8\. Loan status changes to `MODIFIED`.

9\. A corresponding exception is generated.

10\. Resolve the exception from the Exception Management section.



\---



\## 📸 Screenshots



Screenshots can be added here to demonstrate the application.



\### Dashboard



Add your dashboard screenshot here.
<img width="1917" height="597" alt="Screenshot 2026-09-09 133521" src="https://github.com/user-attachments/assets/09361b3c-19da-4602-b8d7-91e46d9739af" />




\### Loan Management



Add your loan management screenshot here.

<img width="1112" height="763" alt="Screenshot 2026-09-09 133651" src="https://github.com/user-attachments/assets/adc89694-9417-4e47-ba29-7d9b62e4d900" />




\### Data Integrity Verification



Add your modified-loan/hash verification screenshot here.
<img width="1117" height="105" alt="Screenshot 2026-09-09 133729" src="https://github.com/user-attachments/assets/9f65f38c-48b7-425f-843b-08cd91b66468" />




\### Exception Management



Add your exception management screenshot here.

<img width="1123" height="207" alt="Screenshot 2026-09-09 133746" src="https://github.com/user-attachments/assets/0e177230-e52d-4d67-bcf2-25fc54737aaa" />




\---



\## 🔮 Future Enhancements



\* JWT-based authentication and authorization

\* Role-based access control

\* Docker containerization

\* Cloud deployment

\* Automated CI/CD pipeline using GitHub Actions

\* Advanced audit logging

\* Email notifications for critical exceptions

\* Pagination and advanced search

\* Unit and integration test expansion

\* Production database configuration

\* API documentation using Swagger/OpenAPI



\---



\## 🎯 Project Highlights



This project demonstrates practical experience with:



\* Full-stack application development

\* Java and Spring Boot

\* REST API development

\* React.js frontend development

\* MySQL database integration

\* CRUD operations

\* Data validation

\* Exception handling

\* SHA-256 data integrity verification

\* Search and filtering

\* CSV processing

\* Git and GitHub



\---



\## 👨‍💻 Author



Avineni Bharath Kumar



B.Tech – Computer Science \& Engineering



GitHub: \[avinenibharath-05](https://github.com/avinenibharath-05)



\---



\## 📄 License



This project is intended for educational, portfolio, and demonstration purposes.



