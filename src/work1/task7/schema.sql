USE computer_store;

-- Производители и модели
CREATE TABLE Product (
                         maker CHAR(1) NOT NULL,
                         model INT NOT NULL,
                         type VARCHAR(7) NOT NULL,
                         PRIMARY KEY (model)
);

-- ПК
CREATE TABLE PC (
                    code INT NOT NULL AUTO_INCREMENT,
                    model INT NOT NULL,
                    speed INT NOT NULL,
                    ram INT NOT NULL,
                    hd INT NOT NULL,
                    cd VARCHAR(5) NOT NULL,
                    price DECIMAL(10,2) NOT NULL,
                    PRIMARY KEY (code),
                    FOREIGN KEY (model) REFERENCES Product(model) ON DELETE CASCADE
);

-- Ноутбуки
CREATE TABLE Laptop (
                        code INT NOT NULL AUTO_INCREMENT,
                        model INT NOT NULL,
                        speed INT NOT NULL,
                        ram INT NOT NULL,
                        hd INT NOT NULL,
                        screen DECIMAL(3,1) NOT NULL,
                        price DECIMAL(10,2) NOT NULL,
                        PRIMARY KEY (code),
                        FOREIGN KEY (model) REFERENCES Product(model) ON DELETE CASCADE
);

-- Принтеры
CREATE TABLE Printer (
                         code INT NOT NULL AUTO_INCREMENT,
                         model INT NOT NULL,
                         color CHAR(1) NOT NULL,
                         type VARCHAR(6) NOT NULL,
                         price DECIMAL(10,2) NOT NULL,
                         PRIMARY KEY (code),
                         FOREIGN KEY (model) REFERENCES Product(model) ON DELETE CASCADE
);