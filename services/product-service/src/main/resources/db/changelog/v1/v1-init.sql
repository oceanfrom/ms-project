CREATE TABLE categories
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE products
(
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name               VARCHAR(255) NOT NULL,
    description        TEXT,
    available_quantity DOUBLE PRECISION,
    price              DECIMAL(19, 2),
    category_id        BIGINT,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id)
        REFERENCES categories (id) ON DELETE CASCADE
);
