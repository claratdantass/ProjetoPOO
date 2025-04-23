CREATE TABLE animais (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INT,
    sexo VARCHAR(10),
    descricao TEXT,
    saude VARCHAR(50),
    vacinado BOOLEAN DEFAULT FALSE,
    castrado BOOLEAN DEFAULT FALSE,
    imagem BYTEA,
    disponivel_para_adocao BOOLEAN DEFAULT TRUE
);

INSERT INTO animais (nome, idade, sexo, descricao, saude, vacinado, castrado, imagem, disponivel_para_adocao)
VALUES 
    ('Mequetrefe', 3, 'Macho', 'Gato Siames, muito docil e carinhoso', 'Saudável', TRUE, FALSE, NULL, FALSE);