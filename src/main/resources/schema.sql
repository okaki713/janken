CREATE TABLE users (
  id IDENTITY,
  name CHAR NOT NULL
);
CREATE TABLE matches (
  id IDENTITY,
  user1 CHAR,
  user2 CHAR,
  user1Hand CHAR NOT NULL,
  user2Hand CHAR NOT NULL
);
