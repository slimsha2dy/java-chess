USE chess;

CREATE TABLE move
(
    move_id INT AUTO_INCREMENT PRIMARY KEY,
    `from`  VARCHAR(12) NOT NULL,
    `to`    VARCHAR(12) NOT NULL
);
