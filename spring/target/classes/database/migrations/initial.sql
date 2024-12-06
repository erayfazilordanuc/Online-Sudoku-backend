DROP TABLE users;
DROP TABLE games;

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(255) UNIQUE,
  email VARCHAR(255) UNIQUE,
  password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS games (
  id SERIAL PRIMARY KEY,
  room INT NOT NULL,
  player1_id INT,
  player2_id INT,
  board VARCHAR(255),
  is_started BOOLEAN,
  is_finished BOOLEAN,
  is_quick BOOLEAN,
  winner_id INT
)