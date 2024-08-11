CREATE SCHEMA controle_equipamentos_TI;
USE controle_equipamentos_TI;


CREATE TABLE equipamento (
	 pk_equipamento INT AUTO_INCREMENT PRIMARY KEY, 
	 tipo VARCHAR(30 NOT NULL,
     modelo VARCHAR(30) NOT NULL
);


CREATE TABLE computador (
    fk_equipamento INT NOT NULL,
	pk_computador INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(30) NOT NULL,
    processador VARCHAR(30) NOT NULL,
    memoria VARCHAR(30) NOT NULL,
    windows VARCHAR(30) NOT NULL,
    armazenamento VARCHAR(30) NOT NULL,
    formatacao VARCHAR(1) NOT NULL,
    manutencao VARCHAR(1) NOT NULL,
    
    INDEX idx_fk_equipamento (fk_equipamento),
    
    CONSTRAINT fk_equipamento_computador FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE RESTRICT ON UPDATE RESTRICT
);


CREATE TABLE impressora (
	fk_equipamento INT NOT NULL,
	pk_impressora INT AUTO_INCREMENT PRIMARY KEY,
    revisao VARCHAR(1) NOT NULL,
    
    INDEX idx_fk_equipamento (fk_equipamento),
    
    CONSTRAINT fk_impressora_equipamento FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE RESTRICT ON UPDATE RESTRICT
);


CREATE TABLE outros_equipamentos (
	fk_equipamento INT NOT NULL,
    pk_outros_equipamentos INT AUTO_INCREMENT PRIMARY KEY,
	descricao VARCHAR(80) NOT NULL,
    
	INDEX idx_fk_equipamento (fk_equipamento),
    
    CONSTRAINT fk_equip_genericos FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE RESTRICT ON UPDATE RESTRICT
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
    
	CONSTRAINT fk_equipamento_envio FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_loja_envio FOREIGN KEY (fk_loja) REFERENCES loja(pk_loja) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT equipamento (tipo, modelo) VAlUES
('Computador', 'teste'),
('Computador', 'teste'),
('Computador', 'teste');
INSERT equipamento (tipo) VAlUES
('Impressora', 'teste'),
('Impressora', 'teste'),
('Impressora', 'teste');
INSERT equipamento (tipo) VALUES
('Nobreak', 'teste'),
('Leitor de código de barras', 'teste'),
('Monitor', 'teste');


INSERT INTO computador (fk_equipamento, pk_computador, modelo, processador, memoria, windows, armazenamento, formatacao, manutencao) VALUES
(1, 130, 'teste', 'i3 8100T', '8GB DDR4', '11 Pro', 'SSD 240GB', 'S', 'S'),
(2, 132, 'teste', 'Celeron N5095', '4GB DDR4', '11 Pro', 'SSD NvME 256GB', 'S', 'S'),
(3, 133, 'teste', 'i3 6100T', '4GB DDR4', '11 Pro', 'HDD 500GB', 'S', 'S');

INSERT impressora (fk_equipamento, pk_impressora, revisao) VALUES
(4, 35, 1),
(5, 36, 1),
(6, 37, 1);

INSERT outros_equipaentos (fk_equipamento, pk_outros_equipamentos, especificacao) VALUES
(10, NULL, '600 VA'),
(11, NULL, 'Sem fio'),
(12, NULL, '17 polegadas');

INSERT INTO envio_equipamento (fk_equipamento, fk_loja, data_envio, observacao) VALUES
(1, 100, '2024-08-01', 'BIOS atualizada para tentar corrigir reinício repentino da máquina'),
(2, 27, '2024-08-02', 'Trocar máquina do gerente. A que voltar será destinada ao EFN'),
(3, 27, '2024-08-06', 'Substituir o computador do balcão que foi queimado.');
INSERT INTO envio_equipamento (fk_equipamento, fk_loja, data_envio, observacao) VALUES
(4, 23, '2024-06-20', 'Troca da impressora Brother 1617 (engasgando papel).'),
(5, 0, '2024-07-02', 'Trocar de impressora (engasgando papel).'),
(6, 8, '2024-07-27', 'Trocar de impressora (engasgando papel).');


SELECT * FROM equipamento;
SELECT * FROM computador;
SELECT * FROM impressora;
SELECT * FROM outros_equipamentos;
SELECT * FROM loja;
SELECT * FROM envio_equipamento;

CREATE VIEW view_equipamento_data_envio  AS (
	SELECT e.pk_equipamento, ee.fk_loja, e.modelo, data_envio
	FROM equipamento e
    INNER JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
);

SELECT * FROM view_equipamento_data_envio
LEFT JOIN impressora
ON e.pk_equi

CREATE VIEW view_equipamento_retorno_envio  AS (
	SELECT e.pk_equipamento, ee.fk_loja, e.modelo
	FROM equipamento e
    INNER JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
);

CREATE VIEW equipamento_nao_enviado AS (
	SELECT 
	

);





equipamentos_descartados log
id_equipamento
observacao

TRIGGER descartado (
	before delete

)
;


-- onde usar interface?
drop schema controle_equipamentos_ti;