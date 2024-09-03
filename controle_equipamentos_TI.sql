/*Este banco de dados tem como objetivo ser um sanar, futuramente, uma deficiência no setor de TI de uma empresa X quanto aos auxiliares de TI, os
quais são responsáveis pela manutenção e controle de envios de equipamentos relacionados à TI para as suas demais filiais. 
O objetivo principal é auxiliá-los com esse controle desse sistema, que até então era feita de forma não muito intuitiva: usando planilhas, 
integrado este banco MySQL à uma linguagem de programação, no caso, o Java, a fim de integrar o CRUD.
Desse modo, facilita-se a inserção, listagem, atualização e deleção de dados.
Como é um protótipo, pode-se haver equívocos nesta primeira versão. Entretanto, para os fins da empresa, um dos integrantes do trabalho 
que atua na empresa definiu que tal modelo é o mais ideal a ser utilizado.*/

-- Criação do banco de dados 'controle_equipamentos_ti'; definindo caracteres especiais e case-sensitive padrões; utilizando o banco.
CREATE SCHEMA controle_equipamentos_ti
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_bin;
USE controle_equipamentos_ti;

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
    gerente VARCHAR(30) NOT NULL,
    cidade VARCHAR(20) NOT NULL,
    telefone VARCHAR(20) NOT NULL
) ENGINE=InnoDB;

-- Criação da tabela de computador, sobre a qual se relacionará a classe 'EnvioEquipamento', em Java, instanciada.
CREATE TABLE envio_equipamento (
	fk_equipamento INT NOT NULL,
    fk_loja INT NOT NULL,
    data_envio DATE NOT NULL,
    observacao TEXT NOT NULL,
    
	INDEX idx_fk_equipamento (fk_equipamento),
	INDEX idx_fk_loja (fk_loja),
    
	CONSTRAINT fk_equipamento_envio FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_loja_envio FOREIGN KEY (fk_loja) REFERENCES loja(pk_loja) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT pk_envio PRIMARY KEY (fk_equipamento, fk_loja)
) ENGINE=InnoDB;

-- Mostrando as tabelas do banco controle_equipamentos_TI.
SHOW TABLES;

-- Povoamento das tabelas de equipamento. Tais atributos da tabela serão herdadas, via FOREIGN KEY, para as tabelas computador, impressora e outros_equipamentos. (Em Java iso é visto mais facilmente.)
START TRANSACTION;
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
INSERT INTO loja (pk_loja, cnpj, gerente, cidade, telefone) VALUES 
(NULL, '22.222.998/0923-40', 'Marcelo', 'Patrocínio', '(34) 9 98222-343'),
(NULL, '22.222.998/0922-10', 'José', 'Patos de Minas', '(34) 9 9912-7876'),
(NULL, '22.222.998/083-98', 'Kely', 'Paracatu', '(34) 9 97192-931');

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
-- ROLLBACK; -- Caso deseje voltar para estado anterior antes das inserções.
COMMIT;

-- Selecionando todos os atributos das respectivas tabelas.
SELECT * FROM equipamento;
SELECT * FROM computador;
SELECT * FROM impressora;
SELECT * FROM outros_equipamentos;
SELECT * FROM loja;
SELECT * FROM envio_equipamento;

-- Criando uma VIEW detalhada, em que há os atributios modelo e tipo advindos de 'equipamento', para o envio de equipamento, que será usada para listar os dados de 'Outros_EquipamentosDAO', no Java.
CREATE VIEW view_equipamento_envio_detalhado AS (
	SELECT eq.fk_equipamento, q.tipo, q.modelo, eq.fk_loja, l.gerente, DATE_FORMAT(eq.data_envio, "%d/%m/%Y") AS data_envio,  eq.observacao
    FROM envio_equipamento eq
    INNER JOIN equipamento q
    ON eq.fk_equipamento = q.pk_equipamento
    INNER JOIN loja l
    ON eq.fk_loja = l.pk_loja
);

-- Criando uma VIEW para o envio de computador, na qual pode-se ver os que foram e os que não foram enviados.
CREATE VIEW view_computador_enviado_nao_enviado AS (
	SELECT c.pk_computador AS id_computador, e.modelo
	FROM equipamento e
    INNER JOIN computador c
    ON e.pk_equipamento = c.fk_equipamento
    LEFT JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
    WHERE ee.fk_equipamento IS NULL
);

-- Criando uma VIEW para o envio de impressora, na qual pode-se ver os que foram e os que não foram enviadas.
CREATE VIEW view_impressora_enviada_nao_enviada AS (
	SELECT pk_impressora AS id_impressora, e.modelo
    FROM equipamento e
    INNER JOIN impressora i
    ON e.pk_equipamento = i.fk_equipamento
	LEFT JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
    WHERE ee.fk_equipamento IS NULL IS NULL
);

-- Criando uma VIEW para o envio de outros_equipamentos, na qual pode-se ver os que foram e os que não foram enviados.
CREATE VIEW view_outros_equip_enviado_nao_enviado AS (
	SELECT pk_outros_equipamentos AS id_outros, e.modelo
    FROM equipamento e
    INNER JOIN outros_equipamentos o
    ON e.pk_equipamento = o.fk_equipamento
	LEFT JOIN envio_equipamento ee
    ON ee.fk_equipamento = e.pk_equipamento
	WHERE ee.fk_equipamento IS NULL
);

-- Selecionando todos os atributos das respectivas VIEWS. (A primeira é destinada ao READ do 'Envio_Equipamento'.) 
SELECT * FROM view_equipamento_envio_detalhado;
SELECT * FROM view_computador_enviado_nao_enviado;
SELECT * FROM view_impressora_enviada_nao_enviada;
SELECT * FROM view_outros_equip_enviado_nao_enviado;

-- Criando tabela de LOG para equipamentos descartados após o acionamento da TRIGGER 'trg_descarte_equipamento'
CREATE TABLE log_equipamentos_descartados (
    pk_descarte INT AUTo_INCREMENT PRIMARY KEY,
    fk_equipamento INT NOT NULL,
    tipo VARCHAR (30) NOT NULL,
    modelo VARCHAR(30) NOT NULL,
    motivo VARCHAR(30) NOT NULL,
	data DATE NOT NULL,
    usuario VARCHAR(25) NOT NULL
    
    -- INDEX idx_fk_equipamento (fk_equipamento),
    
    -- CONSTRAINT pk_equipamento_descarte FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON UPDATE RESTRICT ON DELETE RESTRICT -- Erro ao acinonar a TRIGGER.
)ENGINE=InnoDB;

CREATE TABLE log_envios_equipamentos_descartados (
	pk_descarte INT AUTO_INCREMENT PRIMARY KEY,
    fk_equipamento INT NOT NULL,
    fk_loja INT NOT NULL,
    motivo VARCHAR(30) NOT NULL,
	data DATE NOT NULL,
    usuario VARCHAR(25) NOT NULL,
    
	INDEX idx_fk_equipamento (fk_equipamento),
    INDEX idx_fk_loja (fk_loja),
    
    CONSTRAINT pk_envio_equipamento_descarte FOREIGN KEY (fk_equipamento) REFERENCES equipamento(pk_equipamento) ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT pk_envio_loja_descarte FOREIGN KEY (fk_loja) REFERENCES loja(pk_loja) ON UPDATE RESTRICT ON DELETE RESTRICT
)ENGINE=InnoDB;

/*Criação do usuário; atribuição de papel criado com seus respectivos privilégios relativos somente ao CRUD. Como
não se pode selecionar mais de uma tabela a serem concedidas os privilégios, deu-se a todos e depois revogou aquelas relativas a
a um papael de DBA. (Criou-se o papel para caso haja outros usuários que contemplem os mesmos privilégios.)*/
CREATE USER 'auxiliar01_ti'@'%' IDENTIFIED BY 'Nac@2000';
CREATE ROLE aux_ti;

GRANT aux_ti TO 'auxiliar01_ti'@'%';
SET DEFAULT ROLE aux_ti TO 'auxiliar01_ti'@'%';

GRANT USAGE
ON contole_equipamentos_ti.*
TO aux_ti;
GRANT INSERT, SELECT, UPDATE, DELETE 
ON controle_equipamentos_ti.equipamento
TO aux_ti;
GRANT INSERT, SELECT, UPDATE, DELETE 
ON controle_equipamentos_ti.computador
TO aux_ti;
GRANT INSERT, SELECT, UPDATE, DELETE 
ON controle_equipamentos_ti.impressora
TO aux_ti;
GRANT INSERT, SELECT, UPDATE, DELETE 
ON controle_equipamentos_ti.outros_equipamentos
TO aux_ti;
GRANT INSERT, SELECT, UPDATE, DELETE 
ON controle_equipamentos_ti.loja
TO aux_ti;
GRANT INSERT, SELECT, UPDATE, DELETE 
ON controle_equipamentos_ti.envio_equipamento
TO aux_ti;
GRANT SELECT
ON controle_equipamentos_ti.log_equipamentos_descartados
TO aux_ti;
GRANT SELECT
ON controle_equipamentos_ti.log_envios_equipamentos_descartados
TO aux_ti;
GRANT SELECT
ON controle_equipamentos_ti.view_equipamento_envio_detalhado
TO aux_ti;
GRANT SELECT
ON controle_equipamentos_ti.view_computador_enviado_nao_enviado
TO aux_ti;
GRANT SELECT
ON controle_equipamentos_ti.view_impressora_enviada_nao_enviada
TO aux_ti;
GRANT SELECT
ON controle_equipamentos_ti.view_outros_equip_enviado_nao_enviado
TO aux_ti;
GRANT USAGE ON *.* TO auxiliar01_ti;
FLUSH PRIVILEGES; -- Garantindo a atualização dos privilégios.

-- Mostrando os privilégios da ROLE 'aux_ti'.
SHOW GRANTS FOR aux_ti;
-- Mostrando os priviégios do USER 'auxiliar_ti'@'%'
SHOW GRANTS FOR 'auxiliar01_ti'@'%';

-- Criação da TRIGGER da tabela 'equipamento', em que, ao deletar algum dado desta, insere-se na tabela de 'log_equipamentos_descartados' os respectivos dados dessa. Mesma coisa ocorre na TRIGGER log_equipamentos_descartados', inserindo na TABLE 'log_envios_equipamentos_descartados'.
DELIMITER &&
CREATE TRIGGER trg_descarte_equipamento BEFORE DELETE
ON equipamento
FOR EACH ROW
BEGIN
	INSERT INTO log_equipamentos_descartados (pk_descarte, fk_equipamento, tipo, modelo, motivo, data, usuario) VALUES (NULL, OLD.pk_equipamento, OLD.tipo, OLD.modelo, 'Velho ou estragado', NOW(), USER());
END&&
DELIMITER ;

DELIMITER &&
CREATE TRIGGER trg_descarte_envio BEFORE DELETE
ON envio_equipamento
FOR EACH ROW
BEGIN
	INSERT INTO log_envios_equipamentos_descartados (pk_descarte, fk_equipamento, fk_loja, motivo, data, usuario) VALUES (NULL, OLD.fk_equipamento, OLD.fk_loja, 'Equipamento devolvido', NOW(), USER());
END&&
DELIMITER ;

-- Criação de PROCEDURE a fim de automatizar o processo, via banco, de DELETE de um determinado equipamento. (Ideal fazer pelo Java.)
DELIMITER %%
CREATE PROCEDURE proc_deletar_equipamento (IN id_equipamento INT)
BEGIN
	DELETE FROM equipamento WHERE pk_equipamento = id_equipamento;
END%%
DELIMITER ;

-- Criação de PROCEDURE a fim de automatizar o processo, via banco, de DELETE de um determinado envio de equipamento. (Ideal fazer pelo Java.)
DELIMITER %%
CREATE PROCEDURE proc_deletar_envio_equipamento (IN id_equipamento INT, IN id_loja INT)
BEGIN
	DELETE FROM envio_equipamento WHERE fk_equipamento = id_equipamento AND fk_loja = id_loja;
END%%
DELIMITER ;

-- Atribuindo os privilégios restantes.
GRANT TRIGGER
ON controle_equipamentos_ti.*
TO aux_ti;
GRANT EXECUTE
ON PROCEDURE controle_equipamentos_ti.proc_deletar_equipamento
TO aux_ti;
GRANT EXECUTE
ON PROCEDURE controle_equipamentos_ti.proc_deletar_envio_equipamento 
TO aux_ti;
FLUSH PRIVILEGES;

-- Chamando a PROCEDURE e passando o respectivo valor referente ao ID da tabela 'equipamento; já na segunda, além desse, passa-se também o de 'loja'.
CALL proc_deletar_equipamento(9);
CALL proc_deletar_envio_equipamento(1, 1);

-- Selecionando os atributos da tabela 'log_equipamentos_descartados' e 'log_envios_equipamentos_descartados'.
SELECT * FROM log_equipamentos_descartados;
SELECT * FROM log_envios_equipamentos_descartados;

-- DROP SCHEMA controle_equipamentos_ti; -- Caso seja necessário resetar o banco de dados apague-o.
-- DROP USER auxiliar01_ti; -- Caso seja necessário excluir o usuário.
-- DROP ROLE aux_ti; -- Caso seja necessário excluir o papel atribupido ao usuário.
