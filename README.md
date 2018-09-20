# Transactions
Simple transaction managment system

## Requirements
  * Maven 3.3+
  * OpenJDK 1.8+
  * Postgres 8+
## Installation
1 Install postgress, maven and openJDK if needed

*In Ubuntu system:*
```
$ sudo apt update
$ sudo apt install postgresql postgresql-contrib maven openjdk-9-jdk 
```

2 Add a new user into postgreSQL with creditionals dbadmin/dbadmin by `psql` [`Instruction`](https://www.postgresql.org/docs/9.3/static/app-createuser.html)

3 Create database tables by `psql` [`Instruction`](https://www.postgresql.org/docs/9.1/static/sql-createdatabase.html)


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
4 Compile source code of the project with `maven`
```
$ mvn compile
```

5 Now you can build a WAR archive with `maven`
```
$ maven package
```

6 Congratulations, you can deploy built war to your's application server

