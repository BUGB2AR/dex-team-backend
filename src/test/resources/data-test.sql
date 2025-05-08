-- Limpa as tabelas (atenção à ordem para manter integridade referencial)
DELETE FROM composicao_time;
DELETE FROM time;
DELETE FROM integrante;

-- Insere Integrantes
INSERT INTO integrante (id, nome, franquia, funcao) VALUES 
(1, 'Jogador Teste', 'Franquia Teste', 'Função Teste'),
(2, 'Jogador 1', 'Franquia A', 'Atacante'),
(3, 'Jogador 2', 'Franquia B', 'Zagueiro'),
(4, 'Jogador por ID', 'Franquia X', 'Função Y'),
(5, 'Jogador Integração', 'Franquia Integração', 'Função Integração'),
(6, 'Jogador Composição', 'Franquia Comp', 'Função Comp');

-- Insere Times
INSERT INTO time (id, data) VALUES 
(1, '2023-01-01'),
(2, '2023-01-10'),
(3, '2023-01-15'),
(4, '2023-01-20'),
(5, '2023-02-01'),
(6, CURRENT_DATE),
(7, '2023-05-10');

-- Insere Composição dos Times
INSERT INTO composicao_time (id, id_time, id_integrante) VALUES 
(1, 2, 2), -- Time 2023-01-10 com Jogador 1
(2, 2, 3), -- Time 2023-01-10 com Jogador 2
(3, 4, 2), -- Time 2023-01-20 com Jogador 1
(4, 6, 6),
(5, 7, 1); -- Time atual com Jogador Composição


