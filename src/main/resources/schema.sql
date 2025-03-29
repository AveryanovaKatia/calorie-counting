DROP TABLE IF EXISTS users, dishes, meals, meal_dishes;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    age INT NOT NULL DEFAULT 0,
    gender VARCHAR(30) NOT NULL CHECK (gender IN ('MAN', 'WOMAN')),
    weight DOUBLE NOT NULL DEFAULT 0,
    height DOUBLE NOT NULL DEFAULT 0,
    goal VARCHAR(30) NOT NULL CHECK (goal IN ('WEIGHT_LOSS', 'SUPPORT', 'MASS_GAIN')),
    dailyCalorieNorm DOUBLE NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS dishes (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    caloriesCount DOUBLE NOT NULL,
    proteins DOUBLE NOT NULL,
    fats DOUBLE NOT NULL,
    carbs DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS meals (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS meal_dishes (
    meal_id BIGINT NOT NULL,
    dish_id BIGINT NOT NULL,
    PRIMARY KEY (meal_id, dish_id),
    FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE,
    FOREIGN KEY (dish_id) REFERENCES dishes(id) ON DELETE CASCADE
);
