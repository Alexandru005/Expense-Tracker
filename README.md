# 💰 Expense Tracker

> A web application that helps users manage their money: track income, expenses and savings, and see where every coin goes.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?logo=react&logoColor=61DAFB)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)

<!-- Add a screenshot or GIF here -->
<!-- ![Dashboard](docs/dashboard.png) -->

## ✨ Features

- 👤 Create an account and log in
- 💵 See your total balance
- 📅 See all transactions from the current month, with a chart filtered by category
- ➕ Add a transaction (`INCOME` / `EXPENSE` / `SAVINGS`)
- 🔎 Browse transactions from a specific period (year, or year and month), with a chart based on categories
- 🏦 See your total savings and withdraw money from them
- 📊 See how much you saved in a specific year (chart by months)

## 🛠️ Tech Stack

| Layer    | Technologies                                   |
|----------|------------------------------------------------|
| Backend  | Java 25, Spring Boot, Maven, PostgreSQL        |
| Frontend | JavaScript, HTML, CSS, React, Vite, npm        |
| DevOps   | Docker, Docker Compose                         |

## 📁 Project Structure

```
expense-tracker/
├── backend/                
│   └── src/
│       ├── main/
│       │   ├── java/com/alexstefan/expense_tracker/
│       │   │   ├── constants/    # application constants
│       │   │   ├── controller/   # REST endpoints
│       │   │   ├── dto/          # data transfer objects (transaction, user)
│       │   │   ├── entity/       # JPA entities
│       │   │   ├── repository/   # database access
│       │   │   ├── security/     # authentication & authorization
│       │   │   └── service/      # business logic (impl/)
│       │   └── resources/
│       │       └── db/migration/ # database migrations
│       └── test/                 # tests
├── frontend/                 
├── .env.example              
├── docker-compose.yml        
└── README.md
```

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


5. **Start the frontend** (in a new terminal)
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

6. **Open the app** at http://localhost:5173

Now you're good to go: track your future expenses and be more disciplined! 💪

## 👤 Author

**Alexandru-Ștefan Ștefănescu** - [GitHub](https://github.com/<your-username>)