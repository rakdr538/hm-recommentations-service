CREATE TABLE IF NOT EXISTS country (
   country_id SERIAL PRIMARY KEY,
   country_name VARCHAR (255) UNIQUE NOT NULL,
   -- ISO-3166 Alpha-3
   country_code VARCHAR(5) UNIQUE NOT NULL
);

-- ref : https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3
INSERT INTO country (country_name, country_code)
VALUES
    ('Sweden', 'SWE'),
    ('United States of America', 'USA'),
    ('British Indian Ocean Territory', 'IOT');

CREATE TABLE IF NOT EXISTS promotions (
   promotion_id SERIAL PRIMARY KEY,
   name VARCHAR (255),
   description TEXT,
   start_timestamp TIMESTAMP NOT NULL,
   end_timestamp TIMESTAMP NOT NULL,
   discount SMALLINT CHECK (discount >= 0)
);

CREATE TABLE IF NOT EXISTS demographics (
   demographic_id SERIAL PRIMARY KEY,
   gender VARCHAR (25) NOT NULL,
   from_age SMALLINT NOT NULL CHECK (from_age >= 0),
   to_age SMALLINT NOT NULL CHECK (to_age >= 0),
   promotion_id INT,
   CONSTRAINT fk_demographics_promotion_id FOREIGN KEY (promotion_id) REFERENCES promotions(promotion_id)
);

INSERT INTO demographics (gender, from_age, to_age)
VALUES
    ('female', 5, 10),
    ('male', 13, 18),
    ('female', 13, 18),
    ('female', 20, 50),
    ('male', 18, 60),
    ('all', 20, 200);

CREATE TABLE IF NOT EXISTS category (
   category_id SERIAL PRIMARY KEY,
   name VARCHAR (255) UNIQUE NOT NULL,
   description TEXT,
   image_url TEXT,
   thumbnail_url TEXT,
   demographic_id INT,
   promotion_id INT,
   CONSTRAINT fk_category_promotion_id FOREIGN KEY (promotion_id) REFERENCES promotions(promotion_id),
   CONSTRAINT fk_category_demographic_id FOREIGN KEY (demographic_id) REFERENCES demographics(demographic_id)
);

CREATE TABLE IF NOT EXISTS occasion (
   occasion_id SERIAL PRIMARY KEY,
   type VARCHAR (255) UNIQUE NOT NULL,
   name TEXT,
   description TEXT,
   promotion_id INT,
   occasion_start TIMESTAMP,
   occasion_end TIMESTAMP,
   season VARCHAR (255),
   country_id INT,
   CONSTRAINT fk_occasion_promotion_id FOREIGN KEY (promotion_id) REFERENCES promotions(promotion_id),
   CONSTRAINT fk_occasion_country_id FOREIGN KEY (country_id) REFERENCES country(country_id)
);

CREATE TABLE IF NOT EXISTS variations (
   variation_id SERIAL PRIMARY KEY,
   product_size VARCHAR (5) NOT NULL,
   product_code VARCHAR(255) UNIQUE NOT NULL,
   product_color VARCHAR(255),
   product_images TEXT [],
   product_thumbnails TEXT [],
   sku BIGINT NOT NULL,
   brand VARCHAR (5) NOT NULL,
   original_price DECIMAL NOT NULL,
   name VARCHAR(255)
);

INSERT INTO variations (product_size, product_code, product_color, sku, brand, original_price, name)
VALUES
    ('S', 'HM1234', 'black', 1000, 'HM', 199, 'socks'),
    ('M', 'HM12345', 'red', 1000, 'HM', 199, 'socks'),
    ('S', 'HM234', 'black', 10, 'HM', 599, 'pants'),
    ('L', 'HM2345', 'red', 10, 'HM', 599, 'pants'),
    ('S', 'HM342', 'black', 20, 'HM', 599, 'shirt'),
    ('M', 'HM3421', 'red', 200, 'HM', 599, 'shirt'),
    ('S', 'HM3422', 'black', 200, 'HM', 1599, 'Monster Dress'),
    ('S', 'HM3423', 'red', 200, 'HM', 1599, 'Spider-man Dress');

CREATE TABLE IF NOT EXISTS products (
   product_id SERIAL PRIMARY KEY,
   category_id INT NOT NULL,
   occasion_id INT,
   promotion_id INT,
   variation_id INT NOT NULL,
   discounted_price DECIMAL NOT NULL,
   description TEXT,
   inStock Boolean NOT NULL,
   CONSTRAINT fk_products_category_id FOREIGN KEY (category_id) REFERENCES category(category_id),
   CONSTRAINT fk_products_occasion_id FOREIGN KEY (occasion_id) REFERENCES occasion(occasion_id),
   CONSTRAINT fk_products_promotion_id FOREIGN KEY (promotion_id) REFERENCES promotions(promotion_id),
   CONSTRAINT fk_products_variation_id FOREIGN KEY (variation_id) REFERENCES variations(variation_id)
);

CREATE TABLE IF NOT EXISTS recipes (
   recipe_id SERIAL PRIMARY KEY,
   products INT [],
   recipe_original_price DECIMAL NOT NULL,
   recipe_discounted_price DECIMAL NOT NULL,
   promotion_id INT,
   occasion_id INT,
   CONSTRAINT fk_recipes_occasion_id FOREIGN KEY (occasion_id) REFERENCES occasion(occasion_id),
   CONSTRAINT fk_recipes_promotion_id FOREIGN KEY (promotion_id) REFERENCES promotions(promotion_id)
);