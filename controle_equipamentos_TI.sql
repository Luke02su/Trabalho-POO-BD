/*Este banco de dados tem como objetivo ser um sanar, futuramente, uma deficiência no setor de TI de uma empresa X quanto aos auxiliares de TI
que são responsáveis pelo controle de envios de equipamentos relaionados à TI para as suas demais filiais. 
O objetivo principal é os utilizadores desse sistema, integrado à uma linguagem de programação, ter um controle interno dos equipamentos a serem enviados, que foram nviados,
que ainda não foram enviados.*/

-- Criação do banco de dados 'controle_equipamentos_ti'; definindo caracteres especiais e case-sensitive padrões; utilizando o banco.
CREATE SCHEMA controle_equipamentos_TI
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_bin;

-- Criação do usuário; atribuição de papel criado com seus respectivos privilégios relativos somente ao CRUD. (Criou-se o papel para caso haja outros usuários que contemplem os mesmos privilégios.)
CREATE USER 'auxiliar01_ti'@'%' IDENTIFIED BY 'Nac@2024';
CREATE ROLE aux_ti;
GRANT INSERT, SELECT, UPDATE, DELETE 
ON controle_equipamentos_ti.*
TO aux_ti;
GRANT aux_ti TO 'auxiliar01_ti'@'%';
SET DEFAULT ROLE 'aux_ti' TO 'auxiliar_ti'@'%';

-- Mostrando os privilégios da ROLE 'aux_ti'.
SHOW GRANTS FOR aux_ti;
-- Mostrando os priviégios do USER 'auxiliar_ti'@'%'
SHOW GRANTS FOR 'auxiliar01_ti'@'%';

-- Criação da tabela de equipamento, com a qual se relacionará a classe abstrata 'equipamento', em Java, ou seja, não poderá ser instanciada, servindo, desse como, como herança.
CREATE TABLE equipamento (
	 pk_equipamento INT AUTO_INCREMENT PRIMARY KEY, 
	 tipo VARCHAR(30) NOT NULL,
     modelo VARCHAR(30) NOT NULL
) ENGINE=InnoDB;

-- Criação da tabela de computador, com a qual se relacionará a classe 'computadorDAO', em Java, instanciada.
CREATE TABLE computador (
    fk_equipamento INT NOT NULL,
	pk_computador INT AUTO_INCREMENT PRIMARY KEY,
    processador VARCHAR(30) NOT NULL,
    memoria VARCHAR(30) NOT NULL,
    windows VARCHAR(30) NOT NULL,
    armazenamento VARCHAR(30) NOT NULL,
    formatacao VARCHAR(1) NOT NULL,
    manutencao VARCHAR(1) NOT NULL,
    
    INDEX idx_fk_equipamento (fk_equipamento),
    
    CONSTRAINT fk_equipamento_computador FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Criação da tabela de computador, com a qual se relacionará a classe 'impressoraDAO', em Java, instanciada.
CREATE TABLE impressora (
	fk_equipamento INT NOT NULL,
	pk_impressora INT AUTO_INCREMENT PRIMARY KEY,
    revisao VARCHAR(1) NOT NULL,
    
    INDEX idx_fk_equipamento (fk_equipamento),
    
    CONSTRAINT fk_impressora_equipamento FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Criação da tabela de computador, sobre a qual se relacionará a classe 'OutrosEquipamentosDAO', em Java, instanciada.
CREATE TABLE outros_equipamentos (
	fk_equipamento INT NOT NULL,
    pk_outros_equipamentos INT AUTO_INCREMENT PRIMARY KEY,
	descricao VARCHAR(80) NOT NULL,
    
	INDEX idx_fk_equipamento (fk_equipamento),
    
    CONSTRAINT fk_equip_genericos FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Criação da tabela de computador, sobre a qual se relacionará a classe 'LojaDAO', em Java, instanciada.
CREATE TABLE loja (
	pk_loja INT AUTO_INCREMENT PRIMARY KEY,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    cidade VARCHAR(20) NOT NULL,
    telefone VARCHAR(20) NOT NULL
) ENGINE=InnoDB;

-- Criação da tabela de computador, sobre a qual se relacionará a classe 'EnvioEquipamento', em Java, instanciada.
CREATE TABLE envio_equipamento (
	pk_envio INT AUTO_INCREMENT PRIMARY KEY,
	fk_equipamento INT NOT NULL,
    fk_loja INT NOT NULL,
    data_envio DATE NOT NULL,
    observacao TEXT NOT NULL,
    
	INDEX idx_fk_equipamento (fk_equipamento),
	INDEX idx_fk_loja (fk_loja),
    
	CONSTRAINT fk_equipamento_envio FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_loja_envio FOREIGN KEY (fk_loja) REFERENCES loja(pk_loja) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Habilitando o respectivo banco de dados para o uso.
USE controle_equipamentos_TI;
-- Mostrando as tabelas do banco controle_equipamentos_TI.
SHOW TABLES;

-- Povoamento das tabelas de equipamento. Tais atributos da tabela serão herdadas, via FOREIGN KEY, para as tabelas computador, impressora e outros_equipamentos. (Em Java iso é visto mais facilmente.)
INSERT equipamento (tipo, modelo) VALUES
('Microcomputador', 'Dell Optiplex 3060'),
('Microcomputador', 'Bematech RC-8400'),
('Microcomputador', 'Dell Optiplex 3050');
INSERT equipamento (tipo, modelo) VAlUES
('Multifuncional a laser', 'HP M1132'),
('Multifuncional a laser', 'Brother 2540'),
('Fotográfica a jato de tinta', 'Epson L805');
INSERT equipamento (tipo, modelo) VALUES
('Nobreak', 'SMS'),
('Leitor de código de barras', 'Bematech'),
('Monitor', 'AOC');

-- Povoamento da  tabela de computador.
INSERT INTO computador (fk_equipamento, pk_computador, processador, memoria, windows, armazenamento, formatacao, manutencao) VALUES
(1, 130, 'i3 8100T', '8GB DDR4', '11 Pro', 'SSD 240GB', 'S', 'S'),
(2, NULL, 'Celeron N5095', '4GB DDR4', '11 Pro', 'SSD NvME 256GB', 'S', 'S'),
(3, NULL, 'i3 6100T', '4GB DDR4', '11 Pro', 'HDD 500GB', 'S', 'N');

-- Povoamento das tabela de impressora.
INSERT INTO impressora (fk_equipamento, pk_impressora, revisao) VALUES
(4, 35, 'S'),
(5, NULL, 'S'),
(6, NULL, 'S');

-- Povoamento das tabela de outros_equipamentos.
INSERT INTO outros_equipamentos (fk_equipamento, pk_outros_equipamentos, descricao) VALUES
(7, 203, '600 VA'),
(8, NULL, 'Sem fio'),
(9, NULL, '17 polegadas');

-- Povoamento da tabela de loja.
INSERT INTO loja (pk_loja, cnpj, cidade, telefone) VALUES 
(NULL, '22.222.998/0923-40', 'Patrocínio', '(34) 9 98222-343'),
(NULL, '22.222.998/0922-10', 'Patos de Minas', '(34) 9 9912-7876'),
(NULL, '22.222.998/083-98', 'Paracatu', '(34) 9 97192-931');

-- Povoamento da tabela envio_equipamento, correlacionando com equipamento e loja de forma agregativa. (Em Java isso é visto como associação de agregação.)
INSERT INTO envio_equipamento (fk_equipamento, fk_loja, data_envio, observacao) VALUES
(1, 1, '2024-08-01', 'BIOS atualizada para tentar corrigir reinício repentino da máquina.'),
(2, 2, '2024-08-02', 'Trocar máquina do gerente. A que voltar será destinada ao EFN.'),
(3, 3, '2024-08-06', 'Substituir o computador do balcão que foi queimado.');
INSERT INTO envio_equipamento (fk_equipamento, fk_loja, data_envio, observacao) VALUES
(4, 1, '2024-06-20', 'Troca da impressora Brother 1617, engasgando papel).'),
(5, 3, '2024-07-02', 'Troca de impressora, não puxa papel.'),
(6, 2, '2024-07-27', 'Troca de impressora, borrando papel.');
INSERT INTO envio_equipamento (fk_equipamento, fk_loja, data_envio, observacao) VALUES
(7, 3, '2024-06-14', 'Troca de nobreak queimado.'),
(8, 2, '2024-07-01', 'Faltando leitor s/ fio na loja'),
(9, 1, '2024-07-20', 'Troca de monitor de 15 polegadas.');

-- Selecionando todos os atributos das respectivas tabelas.
SELECT * FROM equipamento;
SELECT * FROM computador;
SELECT * FROM impressora;
SELECT * FROM outros_equipamentos;
SELECT * FROM loja;
SELECT * FROM envio_equipamento;

-- Criando uma VIEW para o envio de computador, na qual pode-se ver os que foram e os que não foram enviados.
CREATE VIEW view_computador_envio AS (
	SELECT c.pk_computador AS id_computador, ee.fk_loja AS loja, e.modelo, ee.data_envio, ee.observacao
	FROM equipamento e
    INNER JOIN computador c
    ON e.pk_equipamento = c.fk_equipamento
    LEFT JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
);

-- Criando uma VIEW para o envio de impressora, na qual pode-se ver os que foram e os que não foram enviadas.
CREATE VIEW view_impressora_envio AS (
	SELECT pk_impressora AS id_impressora, e.modelo, ee.data_envio, ee.observacao
    FROM equipamento e
    INNER JOIN impressora i
    ON e.pk_equipamento = i.fk_equipamento
	LEFT JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
);

-- Criando uma VIEW para o envio de ouyros_equipamentos, na qual pode-se ver os que foram e os que não foram enviados.
CREATE VIEW view_outros_equip_envio AS (
	SELECT pk_outros_equipamentos AS id_outros, e.tipo, e.modelo, o.descricao, ee.data_envio, ee.observacao
    FROM equipamento e
    INNER JOIN outros_equipamentos o
    ON e.pk_equipamento = o.fk_equipamento
	LEFT JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
);

-- Selecionando todos os atributos das respectivas VIEWS.
SELECT * FROM view_computador_envio;
SELECT * FROM view_impressora_envio;
SELECT * FROM view_outros_equip_envio;

-- Criando tabela de LOG para equipamentos descartados após o acionamento da TRIGGER 'trg_descarte_equipamento'
CREATE TABLE log_equipamentos_descartados (
	pk_descarte INT AUTO_INCREMENT PRIMARY KEY,
    fk_equipamento INT NOT NULL,
    tipo VARCHAR (30) NOT NULL,
    modelo VARCHAR(30) NOT NULL,
    motivo VARCHAR(30) NOT NULL,
	horario TIMESTAMP NOT NULL
);

-- Criação da TRIGGER da tabela 'equipamento', em que, ao deletar algum dado desta, insere-se na tabela de 'log_equipamentos_descartados' os respectivos dados dessa.
DELIMITER &&
CREATE TRIGGER trg_descarte_equipamento BEFORE DELETE
ON equipamento
FOR EACH ROW
BEGIN
	INSERT INTO log_equipamentos_descartados (pk_descarte, fk_equipamento, tipo, modelo, motivo, horario) VALUES (NULL, OLD.pk_equipamento, OLD.tipo, OLD.modelo, 'Velho ou estragado', NOW());
END&&
DELIMITER ;

-- Croaçãode PROCEDURE a fim de automatizar o processo, via banco, de DELETE de um determinado equipamento. (Ideal fazer pelo Java.)
DELIMITER %%
CREATE PROCEDURE proc_deletar_equipamento (IN id_equipamento INT)
BEGIN
	DELETE FROM equipamento WHERE pk_equipamento = id_equipamento;
END%%
DELIMITER ;

-- Chamando a PROCEDURE e passando o respectivo valor referente ao ID da tabela 'equipamento'.
CALL proc_deletar_equipamento (9);

-- Selecionando os atributos da tabela 'log_equipamentos_descartados'.
SELECT * FROM log_equipamentos_descartados;

-- DROP SCHEMA controle_equipamentos_ti; -- Caso seja necessário resetar o banco de dados apague-o.
