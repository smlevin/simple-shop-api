CREATE USER 'checkout'@'localhost' IDENTIFIED BY 'checkout';
GRANT ALL PRIVILEGES ON *.* TO 'checkout'@'localhost';
FLUSH PRIVILEGES;
CREATE DATABASE checkout_shop;

