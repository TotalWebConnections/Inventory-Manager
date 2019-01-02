-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id


-- :name create-product! :! :n
-- :doc creates a new product
INSERT INTO products
(name, sku, purchase_price, quantity, est_shipping_cost, categories, status)
VALUES (:name, :sku, :purchase_price, :quantity, :est_shipping_cost, :categories, "N/A")

-- :name get-products :? :0
-- :doc retrieves all products
SELECT * FROM products
