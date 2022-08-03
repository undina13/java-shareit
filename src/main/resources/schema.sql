drop table if exists users,items;

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     name VARCHAR(255) NOT NULL,
                                     email VARCHAR(512) NOT NULL,
                                     CONSTRAINT pk_user PRIMARY KEY (id),
                                     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
                                     name VARCHAR(255) NOT NULL,
                                     description VARCHAR(255) NOT NULL,
                                     is_available BOOLEAN,
                                     owner_id BIGINT NOT NULL,
                                     request_id BIGINT,
                                     CONSTRAINT pk_item PRIMARY KEY (id),
                                     CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE
);