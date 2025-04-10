CREATE TABLE books
(
    id          BIGSERIAL PRIMARY KEY,
    vendor_code VARCHAR(255) NOT NULL UNIQUE,
    title       VARCHAR(255) NOT NULL,
    year        BIGINT,
    brand       VARCHAR(255),
    stock       INTEGER,
    price       NUMERIC(10, 2),
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

