CREATE TABLE requests
(
    id UUID PRIMARY KEY,
    currency CHAR(3),
    name VARCHAR(32),
    request_date TIMESTAMP,
    value_rate DOUBLE
)