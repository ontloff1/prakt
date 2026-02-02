USE computer_store;

-- Задание: 1
SELECT model, speed, hd FROM PC WHERE price < 500;

-- Задание: 2
SELECT DISTINCT maker FROM Product WHERE type = 'Printer';

-- Задание: 3
SELECT model, ram, screen FROM Laptop WHERE price > 1000;

-- Задание: 4
SELECT * FROM Printer WHERE color = 'y';

-- Задание: 5
SELECT model, speed, hd FROM PC WHERE cd IN ('12x', '24x') AND price < 600;

-- Задание: 6
SELECT p.maker, l.speed
FROM Laptop l
         JOIN Product p ON l.model = p.model
WHERE l.hd >= 100;

-- Задание: 7
SELECT pr.model, pr.price
FROM Product p
         JOIN (
    SELECT model, price FROM PC
    UNION ALL
    SELECT model, price FROM Laptop
    UNION ALL
    SELECT model, price FROM Printer
) pr ON p.model = pr.model
WHERE p.maker = 'B';

-- Задание: 8
SELECT maker FROM Product WHERE type = 'PC'
EXCEPT
SELECT maker FROM Product WHERE type = 'Laptop';

-- Задание: 9
SELECT DISTINCT p.maker
FROM PC pc
         JOIN Product p ON pc.model = p.model
WHERE pc.speed >= 450;

-- Задание: 10
SELECT model, price FROM Printer WHERE price = (SELECT MAX(price) FROM Printer);

-- Задание: 11
SELECT AVG(speed) FROM PC;

-- Задание: 12
SELECT AVG(speed) FROM Laptop WHERE price > 1000;

-- Задание: 13
SELECT AVG(pc.speed)
FROM PC pc
         JOIN Product p ON pc.model = p.model
WHERE p.maker = 'A';

-- Задание: 14
SELECT speed, AVG(price) FROM PC GROUP BY speed;

-- Задание: 15
SELECT hd FROM PC GROUP BY hd HAVING COUNT(*) >= 2;

-- Задание: 16
SELECT DISTINCT
    CASE WHEN p1.code > p2.code THEN p1.model ELSE p2.model END,
    CASE WHEN p1.code < p2.code THEN p1.model ELSE p2.model END,
    p1.speed,
    p1.ram
FROM PC p1
         JOIN PC p2 ON p1.speed = p2.speed AND p1.ram = p2.ram AND p1.code < p2.code
ORDER BY 1 DESC, 2 DESC;

-- Задание: 17
SELECT 'Laptop', model, speed FROM Laptop WHERE speed < (SELECT MIN(speed) FROM PC);

-- Задание: 18
SELECT p.maker, pr.price
FROM Printer pr
         JOIN Product p ON pr.model = p.model
WHERE pr.color = 'y' AND pr.price = (SELECT MIN(price) FROM Printer WHERE color = 'y');

-- Задание: 19
SELECT p.maker, AVG(l.screen)
FROM Laptop l
         JOIN Product p ON l.model = p.model
GROUP BY p.maker;

-- Задание: 20
SELECT p.maker, COUNT(DISTINCT p.model)
FROM Product p
WHERE p.type = 'PC'
GROUP BY p.maker
HAVING COUNT(DISTINCT p.model) >= 3;

-- Задание: 21
SELECT p.maker, MAX(pc.price)
FROM PC pc
         JOIN Product p ON pc.model = p.model
GROUP BY p.maker;

-- Задание: 22
SELECT speed, AVG(price) FROM PC WHERE speed > 600 GROUP BY speed;

-- Задание: 23
SELECT p1.maker
FROM PC pc
         JOIN Product p1 ON pc.model = p1.model
WHERE pc.speed >= 750
INTERSECT
SELECT p2.maker
FROM Laptop l
         JOIN Product p2 ON l.model = p2.model
WHERE l.speed >= 750;

-- Задание: 24
SELECT model FROM (
                      SELECT model, price FROM PC
                      UNION ALL
                      SELECT model, price FROM Laptop
                      UNION ALL
                      SELECT model, price FROM Printer
                  ) t
WHERE price = (
    SELECT MAX(price) FROM (
                               SELECT price FROM PC
                               UNION ALL
                               SELECT price FROM Laptop
                               UNION ALL
                               SELECT price FROM Printer
                           ) t2
);

-- Задание: 25
SELECT DISTINCT p.maker
FROM Product p
WHERE p.type = 'Printer'
  AND p.maker IN (
    SELECT p2.maker
    FROM PC pc
             JOIN Product p2 ON pc.model = p2.model
    WHERE pc.ram = (SELECT MIN(ram) FROM PC)
      AND pc.speed = (
        SELECT MAX(speed) FROM PC WHERE ram = (SELECT MIN(ram) FROM PC)
    )
);