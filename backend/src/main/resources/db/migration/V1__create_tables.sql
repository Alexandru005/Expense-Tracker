CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(128) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    balance NUMERIC(12,2) DEFAULT 0 NOT NULL CHECK (balance >= 0),
    savings NUMERIC(12,2) DEFAULT 0 NOT NULL CHECK (savings >= 0)
);

CREATE TABLE transactions(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT REFERENCES users NOT NULL,
    title VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    category VARCHAR(50) NOT NULL,
    amount NUMERIC(12,2) NOT NULL CHECK (amount > 0),
    date DATE NOT NULL
);

CREATE INDEX idx_transactions_user_type_date ON transactions(user_id, type, date);