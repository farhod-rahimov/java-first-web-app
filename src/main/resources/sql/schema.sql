-- table "users"

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    firstName VARCHAR(70) NOT NULL,
    lastName VARCHAR(70) NOT NULL,
    email VARCHAR(70) NOT NULL UNIQUE,
    phoneNumber VARCHAR(20) NOT NULL,
    password VARCHAR(80) NOT NULL
);

-- table "authentications"

DROP TABLE IF EXISTS authentications CASCADE;

CREATE TABLE IF NOT EXISTS authentications(
    authId BIGSERIAL PRIMARY KEY,
    authDate DATE NOT NULL,
    authTime TIME NOT NULL,
    authIp VARCHAR(40) NOT NULL,
    userId BIGINT REFERENCES users(id)
);

-- table "images"

DROP TABLE IF EXISTS images CASCADE;

CREATE TABLE IF NOT EXISTS images(
     id BIGSERIAL PRIMARY KEY,
     originalName VARCHAR NOT NULL,
     uniqueName VARCHAR NOT NULL,
     imagePath VARCHAR NOT NULL,
     imageSize REAL NOT NULL,
     MIME VARCHAR NOT NULL,
     userId BIGINT REFERENCES users(id)
);