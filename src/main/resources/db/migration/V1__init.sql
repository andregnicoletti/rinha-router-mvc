CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    correlation_id VARCHAR(100),
    amount DOUBLE PRECISION NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT FALSE,
    retry_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE payment_transactions (
    id SERIAL PRIMARY KEY,
    correlation_id VARCHAR(100),
    endpoint_type VARCHAR(20),
    amount DOUBLE PRECISION NOT NULL,
    processed_at TIMESTAMP NOT NULL
);