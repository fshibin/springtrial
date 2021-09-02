DROP TABLE order_details IF EXISTS;
DROP TABLE orders IF EXISTS;
DROP TABLE shopping_carts IF EXISTS;
DROP TABLE user_addrs IF EXISTS;
DROP TABLE user_cart IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE administrators IF EXISTS;
DROP TABLE product_supply IF EXISTS;
DROP TABLE product_pricing IF EXISTS;
DROP TABLE product_stock IF EXISTS;
DROP TABLE product_stats IF EXISTS;
DROP TABLE product_variants IF EXISTS;
DROP TABLE products IF EXISTS;
DROP TABLE product_brands IF EXISTS;
DROP TABLE product_categories IF EXISTS;
DROP TABLE product_families IF EXISTS;

CREATE TABLE product_brands (
  id     INTEGER IDENTITY PRIMARY KEY,
  brand  VARCHAR(32) NOT NULL,
  vendor VARCHAR(32)
);

CREATE TABLE product_families (
  id          INTEGER IDENTITY PRIMARY KEY,
  family      VARCHAR(32) UNIQUE,
  description VARCHAR(64)
);

CREATE TABLE product_categories (
  id          INTEGER IDENTITY PRIMARY KEY,
  category    VARCHAR(32) UNIQUE,
  fam_id      INTEGER NOT NULL,
  description VARCHAR(64)
);
ALTER TABLE product_categories ADD CONSTRAINT fk_product_cat_fam FOREIGN KEY (fam_id) REFERENCES product_families (id);

CREATE TABLE products (
  id           INTEGER IDENTITY PRIMARY KEY,
  name         VARCHAR(255) NOT NULL,
  cat_id       INTEGER NOT NULL,
  brand_id     INTEGER NOT NULL,
  series       VARCHAR(32),
  model        VARCHAR(32),
  description  VARCHAR(256) NOT NULL,
  opt1name     VARCHAR(32),
  opt1list     VARCHAR(256),
  opt2name     VARCHAR(32),
  opt2list     VARCHAR(256),
  opt3name     VARCHAR(32),
  opt3list     VARCHAR(256),
  opt4name     VARCHAR(32),
  opt4list     VARCHAR(256),
  opt5name     VARCHAR(32),
  opt5list     VARCHAR(256),
  status       VARCHAR(16)
);
CREATE INDEX prod_cat ON products (cat_id);
CREATE INDEX prod_brand ON products (brand_id);
ALTER TABLE products ADD CONSTRAINT fk_products_cat FOREIGN KEY (cat_id) REFERENCES product_categories (id);
ALTER TABLE products ADD CONSTRAINT fk_products_brand FOREIGN KEY (brand_id) REFERENCES product_brands (id);

CREATE TABLE product_variants (
  prod_id      INTEGER NOT NULL,
  var_id       INTEGER NOT NULL,
  barcode      VARCHAR(32) NOT NULL, 
  opt1value    VARCHAR(16),
  opt2value    VARCHAR(16),
  opt3value    VARCHAR(16),
  opt4value    VARCHAR(16),
  opt5value    VARCHAR(16),
  length       VARCHAR(8),
  width        VARCHAR(8),
  height       VARCHAR(8),
  weight       VARCHAR(8),
  packing_list VARCHAR(128),
  status       VARCHAR(16),
  PRIMARY KEY (prod_id, var_id)
);
ALTER TABLE product_variants ADD CONSTRAINT fk_product_variants_prod FOREIGN KEY (prod_id) REFERENCES products (id);

CREATE TABLE product_stats (
  prod_id   INTEGER NOT NULL,
  var_id    INTEGER NOT NULL,
  sold      INTEGER NOT NULL,
  browsed   INTEGER NOT NULL,
  shared    INTEGER NOT NULL,
  commented INTEGER NOT NULL,
  stars     INTEGER NOT NULL,
  PRIMARY KEY (prod_id, var_id)
);
ALTER TABLE product_stats ADD CONSTRAINT fk_product_stats_pv FOREIGN KEY (prod_id, var_id) REFERENCES product_variants (prod_id, var_id);

CREATE TABLE product_stock (
  prod_id   INTEGER NOT NULL,
  var_id    INTEGER NOT NULL,
  price     INTEGER NOT NULL,
  quantity  INTEGER NOT NULL,
  PRIMARY KEY (prod_id, var_id)
);
ALTER TABLE product_stock ADD CONSTRAINT fk_product_stock_pv FOREIGN KEY (prod_id, var_id) REFERENCES product_variants (prod_id, var_id);

CREATE TABLE product_supply (
  id         INTEGER IDENTITY PRIMARY KEY,
  prod_id    INTEGER NOT NULL,
  var_id     INTEGER NOT NULL,
  quantity   INTEGER NOT NULL,
  active_at  DATETIME NOT NULL,
  created_at DATETIME NOT NULL,
  created_by INTEGER NOT NULL,
  status     VARCHAR(15)
);
CREATE INDEX prod_supply_pv ON product_supply (prod_id, var_id);
ALTER TABLE product_supply ADD CONSTRAINT fk_product_supply_pv FOREIGN KEY (prod_id, var_id) REFERENCES product_variants (prod_id, var_id);

CREATE TABLE product_pricing (
  id         INTEGER IDENTITY PRIMARY KEY,
  prod_id    INTEGER NOT NULL,
  var_id     INTEGER NOT NULL,
  price      INTEGER NOT NULL,
  start_at   DATETIME NOT NULL,
  stop_at    DATETIME NOT NULL,
  created_at DATETIME NOT NULL,
  created_by INTEGER NOT NULL,
  status     VARCHAR(15)
);
CREATE INDEX prod_pricing_pv ON product_pricing (prod_id, var_id);
ALTER TABLE product_pricing ADD CONSTRAINT fk_product_pricing_pv FOREIGN KEY (prod_id, var_id) REFERENCES product_variants (prod_id, var_id);

CREATE TABLE users (
  id         INTEGER IDENTITY PRIMARY KEY,
  email      VARCHAR(64) NOT NULL,
  name       VARCHAR(64),
  password   VARCHAR(128) NOT NULL
);
  
CREATE TABLE user_cart (
  user_id  INTEGER NOT NULL,
  prod_id  INTEGER NOT NULL,
  var_id   INTEGER NOT NULL,
  quantity INTEGER NOT NULL,
  PRIMARY KEY (user_id, prod_id, var_id)
);
ALTER TABLE user_cart ADD CONSTRAINT fk_user_cart_users FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE user_cart ADD CONSTRAINT fk_user_cart_pv FOREIGN KEY (prod_id, var_id) REFERENCES product_variants (prod_id, var_id);

CREATE TABLE user_addrs (
  user_id      INTEGER NOT NULL,
  addr_id      INTEGER NOT NULL,
  address      VARCHAR(128) NOT NULL,
  recipient    VARCHAR(64) NOT NULL,
  phone_number VARCHAR(32) NOT NULL,
  PRIMARY KEY (user_id, addr_id)
);
ALTER TABLE user_addrs ADD CONSTRAINT fk_user_addrs_users FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE administrators (
  id       INTEGER IDENTITY PRIMARY KEY,
  email    VARCHAR(64) NOT NULL,
  name     VARCHAR(64),
  password VARCHAR(128) NOT NULL,
  roles    VARCHAR(64)
);

CREATE TABLE orders (
  id         INTEGER IDENTITY PRIMARY KEY,
  user_id    INTEGER NOT NULL,
  addr_id    INTEGER NOT NULL,
  created_at DATETIME NOT NULL,
  price      INTEGER NOT NULL,
  paid_at    DATETIME NOT NULL,
  status     VARCHAR(16)
);
ALTER TABLE orders ADD CONSTRAINT fk_orders_addrs FOREIGN KEY (user_id, addr_id) REFERENCES user_addrs (user_id, addr_id);

CREATE TABLE order_details (
  order_id INTEGER NOT NULL,
  prod_id  INTEGER NOT NULL,
  var_id   INTEGER NOT NULL,
  quantity INTEGER NOT NULL,
  price    INTEGER NOT NULL
);
ALTER TABLE order_details ADD CONSTRAINT fk_order_details_orders FOREIGN KEY (order_id) REFERENCES orders (id);
ALTER TABLE order_details ADD CONSTRAINT fk_order_details_pv FOREIGN KEY (prod_id, var_id) REFERENCES product_variants (prod_id, var_id);

