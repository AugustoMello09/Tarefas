INSERT INTO tb_tarefa (id, name, cost, due_date, position, favorite) VALUES (1, 'Teste prático - lista de tarefas', 0, '2024-11-30', 0, false);

UPDATE tb_tarefa SET usuario_id = (SELECT id FROM tb_usuario WHERE email = 'jose@gmail.com') WHERE id = 1;