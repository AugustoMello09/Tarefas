INSERT INTO tb_usuario (id, name, email, password, notification) VALUES (UUID(), 'Augusto', 'jose@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', false);
INSERT INTO tb_usuario_cargo (usuario_id, cargo_id) SELECT id, 2 FROM tb_usuario WHERE email = 'jose@gmail.com';