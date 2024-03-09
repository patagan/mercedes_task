CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS car_feature (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    feature_type INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_configuration (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    class_id BIGINT,
    type_id BIGINT,
    motor_id BIGINT,
    color_id BIGINT,
    price FLOAT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (class_id) REFERENCES car_feature(id),
    FOREIGN KEY (type_id) REFERENCES car_feature(id),
    FOREIGN KEY (motor_id) REFERENCES car_feature(id),
    FOREIGN KEY (color_id) REFERENCES car_feature(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS user_configuration_car_feature (
    user_configuration_id BIGINT,
    car_feature_id BIGINT,
    PRIMARY KEY (user_configuration_id, car_feature_id),
    FOREIGN KEY (user_configuration_id) REFERENCES user_configuration(id),
    FOREIGN KEY (car_feature_id) REFERENCES car_feature(id)
);