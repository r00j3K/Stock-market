CREATE TYPE transaction_type AS ENUM ('BUY', 'SELL');

CREATE TABLE wallets 
(
    wallet_id VARCHAR(255) PRIMARY KEY
);

CREATE TABLE bank_stocks 
(
    stock_name VARCHAR(255) PRIMARY KEY,
    quantity BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE wallet_stocks
(
    wallet_id VARCHAR(255) NOT NULL,
    stock_name VARCHAR(255) NOT NULL,
    quantity BIGINT NOT NULL,
    PRIMARY KEY (wallet_id, stock_name),
    CONSTRAINT fk_wallet_id_wallet_stock FOREIGN KEY(wallet_id) REFERENCES wallets(wallet_id),
    CONSTRAINT fk_stock_name_wallet_stock FOREIGN KEY(stock_name) REFERENCES bank_stocks(stock_name)
);

CREATE TABLE audit_logs 
(
    log_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    wallet_id VARCHAR(255) NOT NULL,
    stock_name VARCHAR(255) NOT NULL,
    transaction_type transaction_type NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

