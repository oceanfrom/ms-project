CREATE TABLE orders
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id    UUID           NOT NULL,
    reference      TEXT           NOT NULL,
    amount         DECIMAL(15, 2) NOT NULL,
    payment_method TEXT           NOT NULL,
    created_at     TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_line
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id   UUID             NOT NULL,
    product_id UUID          NOT NULL,
    quantity   DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);