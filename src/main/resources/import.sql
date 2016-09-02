# Создадим пять тестовый пользователей с разным достатком.
Insert into shop_users (id, score) values (1, 10000000.0);
Insert into shop_users (id, score) values (2, 1000000.0);
Insert into shop_users (id, score) values (3, 100000.0);
Insert into shop_users (id, score) values (4, 10000.0);
Insert into shop_users (id, score) values (5, 1000.0);

#Создадим 15 продуктов разной цены.
Insert into shop_products (id, name, price, vendorCode) values (1, 'Дом', 1000000.0, 'house' );
Insert into shop_products (id, name, price, vendorCode) values (2, 'Автомобиль', 500000.0, 'car' );
Insert into shop_products (id, name, price, vendorCode) values (3, 'Мотоцикл', 300000.0, 'bike' );
Insert into shop_products (id, name, price, vendorCode) values (4, 'Скутер', 100000.0, 'pit-bike' );
Insert into shop_products (id, name, price, vendorCode) values (5, 'Велосипед', 50000.0, 'mini-bike' );

Insert into shop_products (id, name, price, vendorCode) values (6, 'Мобильный телефон', 40000.0, 'phone' );
Insert into shop_products (id, name, price, vendorCode) values (7, 'Игровая консоль', 30000.0, 'console' );
Insert into shop_products (id, name, price, vendorCode) values (8, 'Электронная книга',15000.0, 'book' );
Insert into shop_products (id, name, price, vendorCode) values (9, 'Плеер', 8000.0, 'player' );
Insert into shop_products (id, name, price, vendorCode) values (10, 'Наушники', 4000.0, 'headphones' );

Insert into shop_products (id, name, price, vendorCode) values (11, 'Каньяк', 2000.0, 'cognac' );
Insert into shop_products (id, name, price, vendorCode) values (12, 'Вино', 500.0, 'wine' );
Insert into shop_products (id, name, price, vendorCode) values (13, 'Печеньки', 125.0, 'biscuit' );
Insert into shop_products (id, name, price, vendorCode) values (14, 'Мороженое', 25.4, 'ice-cream' );
Insert into shop_products (id, name, price, vendorCode) values (15, 'Жвачка', 5.5, 'gum' );
