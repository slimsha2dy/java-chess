USE chess;

CREATE TABLE move
(
    move_id     INT AUTO_INCREMENT PRIMARY KEY,
    source      VARCHAR(12) NOT NULL,
    destination VARCHAR(12) NOT NULL
);
