CREATE TYPE transaction_type AS ENUM ('buy', 'sell');

CREATE TABLE wallet 
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY
);

CREATE TABLE bank 
(
    stock_name VARCHAR(255) PRIMARY KEY,
    quantity BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE wallet_stock 
(
    wallet_id BIGINT NOT NULL,
    stock_name VARCHAR(255) NOT NULL,
    quantity BIGINT NOT NULL DEFAULT 1,
    PRIMARY KEY (wallet_id, stock_name),
    CONSTRAINT fk_wallet_id_wallet_stock FOREIGN KEY(wallet_id) REFERENCES wallet(id),
    CONSTRAINT fk_stock_name FOREIGN KEY(stock_name) REFERENCES bank(stock_name)
);

CREATE TABLE audit_log 
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    wallet_id BIGINT NOT NULL,
    stock_name VARCHAR(255) NOT NULL,
    type transaction_type NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wallet_id_audit_log FOREIGN KEY(wallet_id) REFERENCES wallet(id)
);

