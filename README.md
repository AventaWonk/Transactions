# Transactions
Simple transaction managment system

## Requirements
  * Maven 3.3+
  * OpenJDK 1.8+
  * Postgres 8+
## Installation
1 Install postgress, maven and openJDK if needed 
**In Ubuntu system
```
$ sudo apt update
$ sudo apt install postgresql postgresql-contrib maven openjdk-9-jdk 
```

2 Add a new user into postgreSQL with creditionals dbadmin/dbadmin
3 Create database tables

```sql
CREATE TABLE public.store_transaction
(
    id serial PRIMARY KEY,
    shop_id int NOT NULL,
    product_id int NOT NULL,
    amount numeric(12,2),
    quantity int NOT NULL,
    date timestamp NOT NULL,
    CONSTRAINT store_transaction_shop_id_fk FOREIGN KEY (shop_id) REFERENCES public.shop (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT store_transaction_product_id_fk FOREIGN KEY (product_id) REFERENCES public.product (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.shop
(
    id serial PRIMARY KEY,
    address varchar(255)
);

CREATE TABLE public.product
(
    id serial PRIMARY KEY,
    name varchar(255)
);
```
4 Compile the source code of the project by maven
```
$ mvn compile
```

5 Build an executable WAR  by maven
```
$ maven package
```

6 Now you can deploy your's built war to application server
