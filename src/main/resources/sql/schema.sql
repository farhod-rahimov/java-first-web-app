-- table "users"

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    firstName VARCHAR(70) NOT NULL,
    lastName VARCHAR(70) NOT NULL,
    email VARCHAR(70) NOT NULL UNIQUE,
    phoneNumber VARCHAR(20) NOT NULL,
    password VARCHAR(80) NOT NULL
);

-- table "authentications"

DROP TABLE IF EXISTS authentications;

CREATE TABLE IF NOT EXISTS authentications(
    authId BIGSERIAL,
    authDate DATE,
    authTime TIME,
    authIp VARCHAR(40),
    userId BIGINT REFERENCES users(id)
);

-- table "images"

DROP TABLE IF EXISTS images;

CREATE TABLE IF NOT EXISTS images(
     id BIGSERIAL,
     originalName VARCHAR,
     uniqueName VARCHAR,
     imagePath VARCHAR,
     userId BIGINT REFERENCES users(id)
);