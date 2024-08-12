CREATE SCHEMA controle_equipamentos_TI;
USE controle_equipamentos_TI;


CREATE TABLE equipamento (
	 pk_equipamento INT AUTO_INCREMENT PRIMARY KEY, 
	 tipo VARCHAR(30) NOT NULL,
     modelo VARCHAR(30) NOT NULL
);


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
);


CREATE TABLE impressora (
	fk_equipamento INT NOT NULL,
	pk_impressora INT AUTO_INCREMENT PRIMARY KEY,
    revisao VARCHAR(1) NOT NULL,
    
    INDEX idx_fk_equipamento (fk_equipamento),
    
    CONSTRAINT fk_impressora_equipamento FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE outros_equipamentos (
	fk_equipamento INT NOT NULL,
    pk_outros_equipamentos INT AUTO_INCREMENT PRIMARY KEY,
	descricao VARCHAR(80) NOT NULL,
    
	INDEX idx_fk_equipamento (fk_equipamento),
    
    CONSTRAINT fk_equip_genericos FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE loja (
	pk_loja INT AUTO_INCREMENT PRIMARY KEY,
    cnpj INT UNIQUE NOT NULL,
    cidade VARCHAR(30) NOT NULL,
    telefone VARCHAR(20) NOT NULL
);


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
);


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

INSERT INTO computador (fk_equipamento, pk_computador, processador, memoria, windows, armazenamento, formatacao, manutencao) VALUES
(1, 130, 'i3 8100T', '8GB DDR4', '11 Pro', 'SSD 240GB', 'S', 'S'),
(2, NULL, 'Celeron N5095', '4GB DDR4', '11 Pro', 'SSD NvME 256GB', 'S', 'S'),
(3, NULL, 'i3 6100T', '4GB DDR4', '11 Pro', 'HDD 500GB', 'S', 'N');

INSERT INTO impressora (fk_equipamento, pk_impressora, revisao) VALUES
(4, 35, 'S'),
(5, NULL, 'S'),
(6, NULL, 'S');

INSERT INTO outros_equipamentos (fk_equipamento, pk_outros_equipamentos, descricao) VALUES
(7, 203, '600 VA'),
(8, NULL, 'Sem fio'),
(9, NULL, '17 polegadas');

INSERT INTO loja (pk_loja, cnpj, cidade, telefone) VALUES 
(NULL, 283492323, 'Patrocínio', 998222343),
(NULL, 287472722, 'Patos de Minas', 99127876),
(NULL, 289019112, 'Paracatu', 997192931);

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

SELECT * FROM equipamento;
SELECT * FROM computador;
SELECT * FROM impressora;
SELECT * FROM outros_equipamentos;
SELECT * FROM loja;
SELECT * FROM envio_equipamento;

CREATE VIEW view_computador_data_envio AS (
	SELECT c.pk_computador AS id_computador, ee.fk_loja AS loja, e.modelo, ee.data_envio, ee.observacao
	FROM equipamento e
    INNER JOIN computador c
    ON e.pk_equipamento = c.fk_equipamento
    LEFT JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
);

CREATE VIEW view_outros_equip_envio AS (
	SELECT pk_outros_equipamentos AS id_outros, e.tipo, e.modelo, o.descricao, ee.data_envio, ee.observacao
    FROM equipamento e
    INNER JOIN outros_equipamentos o
    ON e.pk_equipamento = o.fk_equipamento
	LEFT JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
);

CREATE VIEW view_impressora_envio AS (
	SELECT pk_impressora AS id_impressora, e.modelo, ee.data_envio, ee.observacao
    FROM equipamento e
    INNER JOIN impressora i
    ON e.pk_equipamento = i.fk_equipamento
	LEFT JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
);


SELECT * FROM view_computador_envio;
SELECT * FROM view_impressora_envio;
SELECT * FROM view_outros_equip_envio;


CREATE TABLE log_equipamentos_descartados (
	pk_descarte INT AUTO_INCREMENT PRIMARY KEY,
    fk_equipamento INT NOT NULL,
    motivo VARCHAR(30) NOT NULL,
	horario TIMESTAMP NOT NULL
);

DELIMITER &&
CREATE TRIGGER trg_descarte_equipamento BEFORE DELETE
ON equipamento
FOR EACH ROW
BEGIN
	INSERT INTO log_equipamentos_descartados (pk_descarte, fk_equipamento, motivo, horario) VALUES (NULL, OLD.pk_equipamento, 'Velho ou estragado', NOW());
END&&
DELIMITER ;

DELIMITER %%
CREATE PROCEDURE proc_deletar_equipamento (IN id_equipamento INT)
BEGIN
	DELETE FROM equipamento WHERE pk_equipamento = id_equipamento;
END%%
DELIMITER ;

CALL proc_deletar_equipamento (9);

SELECT * FROM log_equipamentos_descartados;

-- DROP SCHEMA controle_equipamentos_ti;