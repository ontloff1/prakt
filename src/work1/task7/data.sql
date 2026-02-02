USE computer_store;

-- Отключаем проверку внешних ключей для безопасной очистки
SET FOREIGN_KEY_CHECKS = 0;

-- Очищаем таблицы в правильном порядке (сначала дочерние)
DELETE FROM PC;
DELETE FROM Laptop;
DELETE FROM Printer;
DELETE FROM Product;

-- Включаем проверку обратно
SET FOREIGN_KEY_CHECKS = 1;

-- Вставляем данные
INSERT INTO Product VALUES
                        ('A', 1001, 'PC'),
                        ('A', 1002, 'PC'),
                        ('A', 2001, 'Laptop'),
                        ('B', 1003, 'PC'),
                        ('B', 2002, 'Laptop'),
                        ('B', 3001, 'Printer'),
                        ('C', 1004, 'PC'),
                        ('C', 2003, 'Laptop'),
                        ('D', 3002, 'Printer'),
                        ('E', 1005, 'PC'),
                        ('E', 1006, 'PC'),
                        ('E', 1007, 'PC'),
                        ('F', 2004, 'Laptop'),
                        ('G', 3003, 'Printer');

INSERT INTO PC (model, speed, ram, hd, cd, price) VALUES
                                                      (1001, 450,  64,  40, '12x',  400.00),
                                                      (1002, 500, 128,  50, '24x',  550.00),
                                                      (1003, 750, 128,  80, '40x',  700.00),
                                                      (1004, 500,  64,  50, '12x',  480.00),
                                                      (1005, 800, 256, 100, '48x',  950.00),
                                                      (1006, 900, 512, 120, '52x', 1200.00),
                                                      (1007, 450,  32,  40, '16x',  350.00);

INSERT INTO Laptop (model, speed, ram, hd, screen, price) VALUES
                                                              (2001, 400,  64,   5, 12.1,  950.00),
                                                              (2002, 750, 128, 100, 14.1, 1150.00),
                                                              (2003, 600, 128,  15, 15.4, 1300.00),
                                                              (2004, 450,  64,  10, 13.3, 1450.00);

INSERT INTO Printer (model, color, type, price) VALUES
                                                    (3001, 'y', 'Laser', 200.00),
                                                    (3002, 'n', 'Jet',   150.00),
                                                    (3003, 'y', 'Laser', 300.00);