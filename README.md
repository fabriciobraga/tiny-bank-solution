Here's a well-structured README file for your Tiny Bank application:

---

# **Tiny Bank Application**

## **Overview**
Tiny Bank is a minimalistic banking application developed in Java using Spring Boot. It allows users to perform basic banking operations, such as:
- Depositing money.
- Withdrawing money.
- Viewing account balance.
- Viewing transaction history.

This application uses:
- **H2 Database** (in-memory) for persistence.
- **Springdoc OpenAPI** for API documentation and Swagger UI.

---

## **Technical Information**
- **Java Version:** 21
- **Spring Boot Version:** 3.1.x
- **H2 Database:** 2.x
- **Springdoc OpenAPI Starter:** 2.2.0

---

## **How to Run the Application**

### **Prerequisites**
1. **Java 21:** Ensure you have Java 21 installed on your system. Verify by running:
   ```bash
   java -version
   ```
2. **Maven:** Ensure Maven is installed. Verify by running:
   ```bash
   mvn -version
   ```

### **Steps to Run**
1. Clone the repository:
   ```bash
   git clone https://github.com/fabriciobraga/tiny-bank-solution.git
   cd tiny-bank
   ```
2. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. The application will start on port `8080`.

---

## **Accessing the Application**

### **Swagger UI (API Documentation)**
Access the Swagger UI for exploring and testing APIs:
- URL: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### **H2 Database Console**
Access the H2 database console for inspecting application data:
- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL:** `jdbc:h2:mem:tinybankdb`
- **Username:** `sa`
- **Password:** (Leave blank)

---

## **API Endpoints**
### **Account Operations**
1. **Deposit Money:**
   - **Endpoint:** `POST /api/account/deposit`
   - **Query Parameter:** `amount`
   - **Example:** `/api/account/deposit?amount=100.00`

2. **Withdraw Money:**
   - **Endpoint:** `POST /api/account/withdraw`
   - **Query Parameter:** `amount`
   - **Example:** `/api/account/withdraw?amount=50.00`

3. **View Balance:**
   - **Endpoint:** `GET /api/account/balance`

4. **View Transaction History:**
   - **Endpoint:** `GET /api/account/transactions`

---

## **Project Structure**
```
src/main/java/com/tinybank
├── controller        // REST controllers
├── model             // JPA entities
├── repository        // Spring Data JPA repositories
├── service           // Business logic services
├── exception         // Custom exceptions and handlers
├── config            // Configuration files
└── TinyBankApplication.java // Main application entry point
```

---
