CREATE DATABASE IF NOT EXISTS `sis_proz_tec`;
USE `sis_proz_tec`;

/* Logico_1: */

CREATE TABLE cliente (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100),
    genero VARCHAR(20),
    telefone VARCHAR(20),
    email VARCHAR(100),
    senha VARCHAR(255),
    fk_venda_id INTEGER
);

CREATE TABLE fornecedor (
    cnpj VARCHAR(14) PRIMARY KEY,
    nome VARCHAR(100),
    atuacao VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100),
    senha VARCHAR(255),
    fk_venda_id INTEGER,
    fk_cliente_cpf VARCHAR(11)
);

CREATE TABLE produto (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(100),
    quant INTEGER,
    valor INTEGER,
    fornecedor INTEGER
);

CREATE TABLE venda (
    id INTEGER PRIMARY KEY,
    fornecedor VARCHAR(14),
    cliente VARCHAR(11),
    valor INTEGER,
    produto INTEGER,
    fk_produto_id INTEGER
);

CREATE TABLE cadastra (
    fk_fornecedor_cnpj VARCHAR(14),
    fk_produto_id INTEGER
);

CREATE TABLE busca (
    fk_cliente_cpf VARCHAR(11),
    fk_produto_id INTEGER
);
 
ALTER TABLE cliente ADD CONSTRAINT FK_cliente_2
    FOREIGN KEY (fk_venda_id)
    REFERENCES venda (id)
    ON DELETE RESTRICT;
 
ALTER TABLE fornecedor ADD CONSTRAINT FK_fornecedor_2
    FOREIGN KEY (fk_venda_id)
    REFERENCES venda (id)
    ON DELETE RESTRICT;
 
ALTER TABLE fornecedor ADD CONSTRAINT FK_fornecedor_3
    FOREIGN KEY (fk_cliente_cpf)
    REFERENCES cliente (cpf)
    ON DELETE RESTRICT;
 
ALTER TABLE venda ADD CONSTRAINT FK_venda_2
    FOREIGN KEY (fk_produto_id)
    REFERENCES produto (id)
    ON DELETE RESTRICT;
 
ALTER TABLE cadastra ADD CONSTRAINT FK_cadastra_1
    FOREIGN KEY (fk_fornecedor_cnpj)
    REFERENCES fornecedor (cnpj)
    ON DELETE RESTRICT;
 
ALTER TABLE cadastra ADD CONSTRAINT FK_cadastra_2
    FOREIGN KEY (fk_produto_id)
    REFERENCES produto (id)
    ON DELETE RESTRICT;
 
ALTER TABLE busca ADD CONSTRAINT FK_busca_1
    FOREIGN KEY (fk_cliente_cpf)
    REFERENCES cliente (cpf)
    ON DELETE SET NULL;
 
ALTER TABLE busca ADD CONSTRAINT FK_busca_2
    FOREIGN KEY (fk_produto_id)
    REFERENCES produto (id)
    ON DELETE SET NULL;

-- ==========================================
-- SEED DATA (5 registros por tabela)
-- ==========================================

-- Inserindo dados na tabela produto
INSERT INTO produto (id, nome, quant, valor, fornecedor) VALUES
(1, 'Notebook Dell Inspiron', 15, 4500, 1),
(2, 'Mouse Sem Fio Logitech', 50, 120, 2),
(3, 'Teclado Mecânico Redragon', 30, 350, 3),
(4, 'Monitor LG UltraWide 29', 10, 1500, 4),
(5, 'Headset Gamer HyperX', 25, 450, 5);

-- Inserindo dados na tabela venda
INSERT INTO venda (id, fornecedor, cliente, valor, produto, fk_produto_id) VALUES
(1, '12345678000199', '11122233344', 4500, 1, 1),
(2, '23456789000188', '22233344455', 120, 2, 2),
(3, '34567890000177', '33344455566', 350, 3, 3),
(4, '45678901000166', '44455566677', 1500, 4, 4),
(5, '56789012000155', '55566677788', 450, 5, 5);

-- Inserindo dados na tabela cliente
INSERT INTO cliente (cpf, nome, genero, telefone, email, senha, fk_venda_id) VALUES
('11122233344', 'Carlos Silva', 'Masculino', '11999998888', 'carlos.silva@email.com', 'senha123', 1),
('22233344455', 'Ana Costa', 'Feminino', '21988887777', 'ana.costa@email.com', 'ana2026', 2),
('33344455566', 'Bruno Alves', 'Masculino', '31977776666', 'bruno.alves@email.com', 'bruno@pass', 3),
('44455566677', 'Diana Souza', 'Feminino', '41966665555', 'diana.souza@email.com', 'diana_secure', 4),
('55566677788', 'Eduardo Lima', 'Outro', '51955554444', 'eduardo.lima@email.com', 'edu12345', 5);

-- Inserindo dados na tabela fornecedor
INSERT INTO fornecedor (cnpj, nome, atuacao, telefone, email, senha, fk_venda_id, fk_cliente_cpf) VALUES
('12345678000199', 'Tech Distribuidora', 'Tecnologia', '1133334444', 'contato@techdist.com', 'techpass', 1, '11122233344'),
('23456789000188', 'Logitech Brasil', 'Periféricos', '1133335555', 'suporte@logitech.com.br', 'logipass', 2, '22233344455'),
('34567890000177', 'Redragon Imports', 'Periféricos Gamer', '1133336666', 'vendas@redragon.com', 'redpass', 3, '33344455566'),
('45678901000166', 'LG Electronics', 'Monitores e TVs', '1133337777', 'parceiros@lg.com.br', 'lgpartner', 4, '44455566677'),
('56789012000155', 'HyperX Gaming', 'Áudio e Acessórios', '1133338888', 'distribuidor@hyperx.com', 'hyperxpass', 5, '55566677788');

-- Inserindo dados na tabela cadastra
INSERT INTO cadastra (fk_fornecedor_cnpj, fk_produto_id) VALUES
('12345678000199', 1),
('23456789000188', 2),
('34567890000177', 3),
('45678901000166', 4),
('56789012000155', 5);

-- Inserindo dados na tabela busca
INSERT INTO busca (fk_cliente_cpf, fk_produto_id) VALUES
('11122233344', 1),
('22233344455', 2),
('33344455566', 3),
('44455566677', 4),
('55566677788', 5);