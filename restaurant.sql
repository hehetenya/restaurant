DROP DATABASE IF EXISTS restaurant;
CREATE DATABASE restaurant;
USE restaurant;


DROP TABLE IF EXISTS category ;
CREATE TABLE IF NOT EXISTS category (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(16) NOT NULL UNIQUE
  );


DROP TABLE IF EXISTS dish ;
CREATE TABLE IF NOT EXISTS dish (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  price INT UNSIGNED NOT NULL,
  weight INT UNSIGNED NOT NULL,
  category_id INT NOT NULL,
  description VARCHAR(500) NULL,

    FOREIGN KEY (category_id)
    REFERENCES category (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


DROP TABLE IF EXISTS role ;
CREATE TABLE IF NOT EXISTS role (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(16) NOT NULL
);


DROP TABLE IF EXISTS user ;
CREATE TABLE IF NOT EXISTS user (
  id INT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(32) NOT NULL,
  role_id INT NOT NULL DEFAULT 1,

    FOREIGN KEY (role_id)
    REFERENCES role (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


DROP TABLE IF EXISTS status ;
CREATE TABLE IF NOT EXISTS status (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(30) NOT NULL
  );


DROP TABLE IF EXISTS receipt ;
CREATE TABLE IF NOT EXISTS receipt (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  status_id INT NOT NULL DEFAULT 1,
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    FOREIGN KEY (status_id)
    REFERENCES status (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


DROP TABLE IF EXISTS receipt_has_dish ;
CREATE TABLE IF NOT EXISTS receipt_has_dish (
  receipt_id INT NOT NULL,
  dish_id INT NOT NULL,
  amount INT UNSIGNED NOT NULL DEFAULT 1,

	UNIQUE KEY (receipt_id, dish_id),

    FOREIGN KEY (receipt_id)
    REFERENCES receipt (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    FOREIGN KEY (dish_id)
    REFERENCES dish (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


DROP TABLE IF EXISTS cart ;
CREATE TABLE IF NOT EXISTS cart (
  user_id INT NOT NULL,
  dish_id INT NOT NULL,
  amount INT UNSIGNED NOT NULL DEFAULT 1,
  
  UNIQUE KEY (user_id, dish_id),
  
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    FOREIGN KEY (dish_id)
    REFERENCES dish (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
    INSERT INTO role (name) VALUE ('client');
    INSERT INTO role (name) VALUE ('manager');
    
    INSERT INTO user (login, password, role_id) VALUE ('admin', 'admin', 2);
    INSERT INTO user (login, password, role_id) VALUE ('user', 'user', 1);
    INSERT INTO user (login, password, role_id) VALUE ('client', 'client', 1);
    
    INSERT INTO status (name) VALUE ('new');
    INSERT INTO status (name) VALUE ('approved');
    INSERT INTO status (name) VALUE ('cancelled');
    INSERT INTO status (name) VALUE ('cooking');
    INSERT INTO status (name) VALUE ('delivering');
    INSERT INTO status (name) VALUE ('received');
    
    INSERT INTO category (name) VALUE ('Pizza');
    INSERT INTO category (name) VALUE ('Sushi');
    INSERT INTO category (name) VALUE ('Burger');
    INSERT INTO category (name) VALUE ('Drink');
    INSERT INTO category (name) VALUE ('Salad');
    INSERT INTO category (name) VALUE ('Dessert');
    
    INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ('New York-Style pizza', 1, 139, 450, 'With its characteristic large, foldable slices and crispy outer crust, New York-style pizza is one of America’s most famous regional pizza types.');
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Classic Philadelphia roll", 2, 129, 320, 'Filled with smoked salmon, cream cheese and cucumber, then rolled in nori seaweed sheet and sushi rice.');
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ('All-American cheeseburger', 3, 249, 490, 'American cheese is the main star of the night. Dill pickle chips, sliced red onion and tomato, crisp lettuce leaves, ketchup and mayo are also involved.');
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Sparkling mineral water", 4, 29, 200, "Fresh and cold bubbly drink");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Coleslaw", 5, 119, 360, "A true American staple and a side dish served with fried chicken.");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Apple ice cream pie", 6, 89, 330, "A crust of finely ground gingersnap cookie crumbs add delicious crunch to a pie of caramel-drizzled, maple-apple ice cream.");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Hawaiian pizza", 1, 124, 450, "Tomato base, mozzarella, ham & pineapple");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Margherita pizza", 1, 109, 450, "Classic tomato base & mozzarella");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Pepperoni Feast pizza", 1, 135, 450, "Tomato base, mozzarella, pepperoni, pepperoni, and more pepperoni");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Meat Fest pizza", 1, 144, 450, "Tomato base, mozzarella, pepperoni, ham, beef & chicken");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("BBQ Americano pizza", 1, 134, 450, "BBQ base, mozzarella, loads of chicken, bacon, sweetcorn & BBQ drizzle");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Vegan Hot'N'Spicy pizza", 1, 122, 450, "Tomato base, vegan ch**se, mushrooms, peppers, red onions & Jalapeños");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Strawberry-Banana smoothie", 4, 64, 400, "The classic strawberry banana smoothie is hard to beat. It’s sweet, it’s fresh, it’s good for you, and it’s always tasty.");
     INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Mojito lemonade", 4, 49, 400, "Lemon, lime, and mint? The most refreshing flavors of the summer! And you get them all in this delicious Mojito lemonade.");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Ice tea", 4, 46, 300, "A cold drink consisting of tea, sugar, and lemon, served over ice.");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Fresh orange juice", 4, 78, 300, "Orange juice is a favorite beverage high in antioxidants and micronutrients like vitamin C, folate, and potassium");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Fresh apple juice", 4, 58, 300, "Delectably fruity with a perfect balance of sweet and tart flavors.");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Mushroom Swiss Burger", 3, 320, 500, "Sauteed portabello mushroom strips, caramelized onion, swiss cheese, dijon mustard");
    INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Chocolate power", 6, 98, 400, "Chocolate shake with hot chocolate cookie and chocolate granola. Oh, and some more chocolate");
    INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Lava cake", 6, 107, 270, "Chocolate cake with smooth, velvety chocolate inside. Served with a ball of ice cream.");    
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Strawberries under the crumble", 6, 76, 250, "Served with a ball of ice cream.");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Apples under the crumble", 6, 76, 250, "Served with a ball of ice cream.");
    INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Soft Shell Crab Tempura roll", 2, 279, 350, "Crab meat fried in crispy tempura, avocado, tamagoyaki, spicy sauce");    
	 INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Rock Lobster Tempura roll", 2, 312, 360, "Lobster tempura with tobiko caviar, avocado and sweet sauce");
      INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Prawn Tempura roll", 2, 309, 320, "Perfectly crisp tempura prawns with avocado and Japanese mayo");
       INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("BBQ Burger", 3, 312, 500, "Crispy onions, cheddar, bacon and of course juicy meat inside of a pretzel bun");
	INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Mushroom Garlic salad", 5, 95, 300, "Mushrooms in creamy garlic sauce");
      INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Dreamy Veggie salad", 5, 103, 300, "Spinach, iceberg lettuce, cucumber, colored pepper and red onion");
      INSERT INTO dish (name, category_id, price, weight, description)
		VALUE ("Italian salad", 5, 136, 350, "Spinach, arugula, ripening ham, mozzarella, sun-dried tomatoes, olives, cherry tomatoes, pumpkin seeds, and sun-dried tomato sauce. Served with garlic bread.");  
      
INSERT INTO receipt (user_id) VALUE (2);
INSERT INTO receipt_has_dish (receipt_id, dish_id, amount) VALUE (1, 2, 2);
INSERT INTO receipt_has_dish (receipt_id, dish_id, amount) VALUE (1, 3, 1);

INSERT INTO receipt (user_id) VALUE (3);
INSERT INTO receipt_has_dish (receipt_id, dish_id, amount) VALUE (2, 5, 1);
INSERT INTO receipt_has_dish (receipt_id, dish_id, amount) VALUE (2, 6, 3);

INSERT INTO cart (user_id, dish_id, amount) VALUE (2, 3, 4);
INSERT INTO cart (user_id, dish_id, amount) VALUE (2, 2, 1);

