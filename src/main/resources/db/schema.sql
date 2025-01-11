CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(32) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(35) NOT NULL
    is_banned BOOLEAN NOT NULL
);

INSERT INTO users (username, password, email, role, isBanned) VALUES
('john', '12345678', 'john@example.com', 'USER', FALSE),
('alice', '5f4dcc3b5aa765d61d8327deb882cf99', 'alice@example.com', 'USER', FALSE),
('bob', 'e99a18c428cb38d5f260853678922e03', 'bob@example.com', 'ADMIN', FALSE),
('charlie', '9bf31c7ff062936a96d3c8bd1f1a1a2f', 'charlie@example.com', 'USER', TRUE),
('david', '098f6bcd4621d373cade4e832627b4f6', 'david@example.com', 'LEADER', FALSE),
('eve', 'c4ca4238a0b923820dcc509a6f75849b', 'eve@example.com', 'ADMIN', FALSE),
('frank', '98f13708210194c475687be6106a3b84', 'frank@example.com', 'USER', FALSE),
('grace', 'b109f3bbbc244eb82441917edf3d6c93', 'grace@example.com', 'USER', TRUE),
('hannah', 'e4d909c290d0fb1ca068ffaddf22cbd0', 'hannah@example.com', 'USER', FALSE),
('ian', 'd2d2d2d2d2d2d2d2d2d2d2d2d2d2d2d2', 'ian@example.com', 'USER', TRUE),
('jack', 'c81e728d9d4c2f636f067f89cc14862c', 'jack@example.com', 'LEADER', FALSE);