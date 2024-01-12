-- Criando o banco de dados cofrinho de moedas;
create DATABASE cofrinho_de_moedas;

-- Selecionando ele para ser usado;
USE cofrinho_de_moedas;

-- Criando a tabela cambio e definindo "moeda" como chave primária;
CREATE TABLE cambio (
    moeda VARCHAR(50) PRIMARY KEY,
    valor DECIMAL(10, 2)
);

-- Criando a tabela cofrinho com a coluna moeda como chave estrangeira;
-- Coloquei um id como chave primária pois pretendia deixar o usuário excluir as moedas por id, mas não consegui fazer isso;
CREATE TABLE cofrinho (
    id INT AUTO_INCREMENT PRIMARY KEY,
    moeda VARCHAR(50),
    valor DECIMAL(10, 2),
    FOREIGN KEY (moeda) REFERENCES cambio(moeda)
);

-- Adicionando o cambio das moedas, com seu nome e valor(cotação de agosto/2023);
INSERT INTO cambio (moeda, valor) VALUES ('Dolar', 4.91);
INSERT INTO cambio (moeda, valor) VALUES ('Euro', 5.41);
INSERT INTO cambio (moeda, valor) VALUES ('Iene', 0.034);
INSERT INTO cambio (moeda, valor) VALUES ('Libra', 6.24);
INSERT INTO cambio (moeda, valor) VALUES ('Real', 1);
