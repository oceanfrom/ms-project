INSERT INTO categories (name, description)
VALUES
    ('Electronics', 'Devices and gadgets for everyday use'),
    ('Clothing', 'Apparel and fashion items'),
    ('Home Appliances', 'Appliances for home use'),
    ('Books', 'Printed or digital publications for reading'),
    ('Sports', 'Equipment and accessories for physical activities');

INSERT INTO products (name, description, available_quantity, price, category_id)
VALUES
    ('Smartphone', 'A high-tech smartphone with a large screen', 100, 499.99, 1), -- Electronics
    ('Laptop', 'A portable personal computer', 50, 799.99, 1), -- Electronics
    ('Smartwatch', 'A wristwatch that integrates with your smartphone', 150, 199.99, 1), -- Electronics
    ('Bluetooth Headphones', 'Wireless headphones with noise cancellation', 75, 129.99, 1), -- Electronics
    ('TV', 'A high-definition television for home entertainment', 30, 599.99, 1), -- Electronics

    ('T-shirt', 'A cotton t-shirt with a cool design', 200, 19.99, 2), -- Clothing
    ('Jeans', 'Denim jeans for casual wear', 150, 39.99, 2), -- Clothing
    ('Jacket', 'A warm jacket for winter weather', 100, 59.99, 2), -- Clothing
    ('Dress', 'A formal dress for special occasions', 80, 49.99, 2), -- Clothing
    ('Sneakers', 'Comfortable sneakers for everyday use', 120, 69.99, 2), -- Clothing

    ('Refrigerator', 'A fridge with energy-efficient features', 40, 799.99, 3), -- Home Appliances
    ('Washing Machine', 'A washing machine with multiple modes', 60, 399.99, 3), -- Home Appliances
    ('Microwave', 'A microwave oven for fast cooking', 80, 149.99, 3), -- Home Appliances
    ('Coffee Maker', 'A coffee maker to brew your favorite coffee', 150, 79.99, 3), -- Home Appliances
    ('Air Conditioner', 'An air conditioner for cooling your space', 50, 499.99, 3), -- Home Appliances

    ('Fiction Novel', 'A thrilling mystery novel', 200, 14.99, 4), -- Books
    ('Science Book', 'A book on scientific discoveries and theories', 100, 29.99, 4), -- Books
    ('History Book', 'A detailed history of ancient civilizations', 150, 19.99, 4), -- Books
    ('Cookbook', 'A collection of delicious recipes', 180, 24.99, 4), -- Books
    ('Children’s Book', 'A colorful and educational book for children', 250, 9.99, 4), -- Books

    ('Tennis Racket', 'A professional racket for tennis players', 50, 89.99, 5), -- Sports
    ('Football', 'A durable football for outdoor games', 100, 39.99, 5), -- Sports
    ('Yoga Mat', 'A comfortable mat for yoga sessions', 200, 19.99, 5), -- Sports
    ('Baseball Glove', 'A leather glove for baseball players', 60, 49.99, 5), -- Sports
    ('Dumbbells', 'A set of weights for home workouts', 120, 29.99, 5); -- Sports
