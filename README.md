# 💰 Expense Tracker

> *Know where every coin goes. Helping you track income, expenses and savings with ease.*

---

## 📸 Sneak Peek

### Login / Register
![Login/Register](web_pics/Screenshot%202026-10-29%20134747.png)

### Home Page
![Home Page](web_pics/Screenshot%202026-10-29%20135341.png)
![Home Page - Transactions](web_pics/Screenshot%202026-10-29%20134832.png)

### History Page
![History Page](web_pics/Screenshot%202026-10-29%20134845.png)

### Savings Page
![Savings Page](web_pics/Screenshot%202026-10-29%20134857.png)

---

## ✨ Key Features

- [x] **Authentication:** Create an account and log in securely.
- [x] **Balance Overview:** See your total balance at a glance.
- [x] **Monthly Transactions:** View all transactions from the current month, with a chart filtered by category.
- [x] **Add Transactions:** Create a transaction of type `INCOME`, `EXPENSE` or `SAVINGS`.
- [x] **History & Filtering:** Browse transactions from a specific period (year, or year and month), with a chart based on categories.
- [x] **Savings Management:** See your total savings and withdraw money from them.
- [x] **Yearly Savings Report:** See how much you saved in a specific year (chart by months).

---

## 📁 Project Structure

A high-level overview of the project's architecture:

```text
📦 Expense-Tracker
├── 📂 backend                              # Spring Boot application
│   ├── 📂 src/main/java/.../expense_tracker
│   │   ├── 📂 constants                    # Application constants
│   │   ├── 📂 controller                   # REST API endpoints
│   │   ├── 📂 dto                          # Data Transfer Objects (transaction, user)
│   │   ├── 📂 entity                       # Database entities (JPA)
│   │   ├── 📂 repository                   # Database access layer
│   │   ├── 📂 security                     # Authentication & authorization
│   │   └── 📂 service                      # Business logic (impl/)
│   ├── 📂 src/main/resources               # Configuration & db/migration scripts
│   ├── 📂 src/test                         # Tests
│   └── 📄 pom.xml                          # Maven dependencies
├── 📂 frontend                             # React & Vite application
│   ├── 📂 public                           # Static assets
│   ├── 📂 src                              # Components, pages, hooks
│   ├── 📄 package.json                     # Frontend dependencies and scripts
│   └── 📄 vite.config.js                   # Vite configuration
├── 📂 web_pics                             # Images used for documentation
├── 📄 docker-compose.yml                   # Database container setup
└── 📄 .env.example                         # Environment variables template
```

---

## 🛠️ Tech Stack

### Backend
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/springboot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/apache_maven-%23C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)

### Database & DevOps
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

### Frontend
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)

### Tools
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)

---

## 🚀 Getting Started

### Prerequisites

- [Docker](https://www.docker.com/) (running)
- Java 25
- Node.js and npm

### Run the app

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/expense-tracker.git
   cd expense-tracker
   ```

2. **Create your `.env` file** in the root folder, based on `.env.example`
   ```bash
   cp .env.example .env
   ```
   Then fill in the values.

3. **Start the database**
   ```bash
   docker compose up -d
   ```

4. **Start the backend**: run `ExpenseTrackerApplication` from your IDE
   (`backend/src/main/java/com/alexstefan/expense_tracker`), or from the terminal:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

5. **Start the frontend** (in a new terminal)
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

6. **Open the app** at http://localhost:5173

Now you're good to go: track your future expenses and be more disciplined! 💪

---

## 👤 Author

**Alexandru-Ștefan Ștefănescu** - [GitHub](https://github.com/<your-username>)