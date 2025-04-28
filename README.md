# 🐾 Projeto de Cadastro e Catalogação de Gatos

Este projeto tem como objetivo criar um sistema para o cadastro e catalogação de gatos abandonados no Campus da UFPB. A aplicação permite que os usuários possam registrar informações sobre os gatos, como nome, cor, idade, localização, se são castrados e se foram adotados. Além disso, o sistema possui uma interface gráfica que facilita a interação com os dados, permitindo que os administradores e usuários gerenciem as informações de forma simples e eficaz.

<div style="display: flex; justify-content: space-around;">
  <img src="https://github.com/user-attachments/assets/0564a393-a16d-425d-bf08-ca5176cfa371" width="22%" />
  <img src="https://github.com/user-attachments/assets/1d1b4106-1d9d-40f0-b187-b94cde9e64de" width="22%" />
  <img src="https://github.com/user-attachments/assets/365c2b3e-7751-4949-ab99-8cfbe51621e8" width="22%" />
  <img src="https://github.com/user-attachments/assets/365c2b3e-7751-4949-ab99-8cfbe51621e8" width="22%" />
</div>

### Funcionalidades Principais

- **Cadastro de Gatos**: Permite o cadastro de gatos com informações detalhadas, como nome, cor, idade, localização, características de adoção e castração.

- **Gerenciamento de Gatos**: Os administradores poderão adicionar, editar e remover gatos do banco de dados.

- **Validação de Dados**: O sistema realiza validações para garantir que as informações inseridas sejam coerentes, como verificar se os campos obrigatórios foram preenchidos.

- **Interface Gráfica**: A interface gráfica do usuário (GUI) foi construída utilizando JavaFX, proporcionando uma experiência intuitiva.

### Tecnologias Utilizadas

- **Linguagem**: Java
- **Frameworks**: JavaFX
- **Banco de Dados**: PostgreSQL
- **Paradigma**: Programação Orientada a Objetos (POO)
- **Controle de Versão**: Git


## 💻 Como Rodar o Projeto

1. Clone o repositório:
   
   ```bash
   git clone [https://github.com/seuusuario/projeto-catalogacao-gatos.git](https://github.com/seuusuario/projeto-catalogacao-gatos.git)´

3. Navegue até a pasta do projeto:

   ```bash
   cd projeto-catalogacao-gatos
   ```

4. Configure o Banco de Dados PostgreSQL
   
- Certifique-se de que o PostgreSQL está instalado e rodando.
- Crie um banco de dados (se ainda não existir):

   ```sql
   CREATE DATABASE catalogo_gatos;
   ```
5. Execute o script SQL para criar e popular a tabela:
   ```bash
   psql -U seu_usuario -d catalogo_gatos -f banco/tabela.sql
   ```
   Isso criará a tabela animais e já incluirá um registro de exemplo.

6. Execute a classe principal (Main) ou a classe com a interface gráfica para iniciar o sistema.

7. Configure a conexão com o banco no código Java
No código Java, ajuste a string de conexão conforme o seu ambiente:

   ```java
   String url = "jdbc:postgresql://localhost:5432/catalogo_gatos";
   String user = "seu_usuario";
   String password = "sua_senha";
   ```

## 🔨 Atalhos para Compilação e Execução

8. Compile o Projeto Java

   Para facilitar, utilize os scripts abaixo (ajuste os caminhos nos arquivos conforme sua instalação):

   ```bash
      ./compile.sh
   ```
9. Execute a Interface Gráfica
   ```bash
      ./run.sh
   ```

## Observações
- O arquivo banco/tabela.sql cria e popula a tabela animais no banco de dados.
- Certifique-se de que o driver JDBC está no classpath ao compilar e executar.
- Se usar IDE (Eclipse, IntelliJ), configure o JavaFX e o JDBC nas propriedades do projeto

## 🤝 Membros da equipe
Este projeto foi desenvolvido como parte da disciplina Programação Orientada à Objetos.

- [Beatriz Pessôa](https://github.com/beapessoas)
- [Emyle Santos](https://github.com/Emysntts)
- [Clara Dantas](https://github.com/claratdantass)
- [Gustavo Lacerda](https://github.com/LacerdaGustavo)
