# 💰 Expense Tracker

A simple **Expense Tracker API** built with **Spring Boot** that helps you manage your income and expenses efficiently.  
This project allows users to **add, update, delete, and view expenses**, with secure API endpoints using **JWT authentication**.

---

## 🚀 Features

- ✏️ Create, update, and delete expenses
- 💵 Track total income and expenses
- 👤 User registration and login with JWT
- 🧾 View list of all transactions
- 🧰 Built with clean architecture using service and repository layers

---

## 🧑‍💻 Tech Stack

- **Java 22**
- **Spring Boot 3**
- **Spring Security (JWT)**
- **Spring Data JPA**
- **SQL Server**
- **Maven**
- **Lombok**

## ⚙️ Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/kelopaksalak/expense-tracker.git
cd expense-tracker
```

### 2.Configure Database
This project uses an .env file to hold sensitive information such as database credentials.
Before running the app, create a new file named .env in the root directory of the project.

🧩 Example .env file

```bash
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=your_databaseName
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

## 📘 API Documentation (Swagger)
After running the project, you can open the Swagger page to see and test all API endpoints:

```bash
http://localhost:8080/swagger-ui/index.html#/
```
Swagger helps you check your API easily without using external tools like Postman.