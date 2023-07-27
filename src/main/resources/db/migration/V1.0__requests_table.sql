CREATE TABLE requests
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    currency CHAR(3),
    name VARCHAR(32),
    request_date TIMESTAMP,
    value_rate DOUBLE
)