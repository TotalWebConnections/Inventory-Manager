CREATE TABLE products
(id int KEY NOT NULL AUTO_INCREMENT, 
 name VARCHAR(30),
 sku VARCHAR(30),
 purchase_price DECIMAL(8,2),
 list_price DECIMAL(8,2),
 quantity INTEGER(20),
 image VARCHAR(30),
 est_shipping_cost DECIMAL(8,2),
 categories VARCHAR(30),
 purchase_date TIMESTAMP,
 list_date TIMESTAMP DEFAULT '1970-01-01 00:00:01',
 sold_date TIMESTAMP DEFAULT '1970-01-01 00:00:01',
 status VARCHAR(30));
