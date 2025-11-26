
CREATE TABLE IF NOT EXISTS task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    expiration_date DATE NULL,
    creation_date DATETIME NOT NULL,
    priority ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL DEFAULT 'MEDIUM',
    done_status ENUM('DONE', 'NOT_DONE') NOT NULL DEFAULT 'NOT_DONE'
);

