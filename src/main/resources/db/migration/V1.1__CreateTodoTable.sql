CREATE TABLE todo (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description text,
    is_done BOOL DEFAULT FALSE
);
