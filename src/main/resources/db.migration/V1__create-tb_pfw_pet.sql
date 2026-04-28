CREATE TABLE TB_PFW_PET (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome          VARCHAR(100)   NOT NULL,
    especie       VARCHAR(50)    NOT NULL,
    raca          VARCHAR(100),
    idade         INT,
    peso          DECIMAL(5,2),
    tutor_nome    VARCHAR(150)   NOT NULL,
    tutor_email   VARCHAR(150),
    data_cadastro DATE
);