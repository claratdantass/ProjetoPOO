# Projeto de Cadastro e Catalogação de Gatos

Este projeto tem como objetivo criar um sistema para o cadastro e catalogação de gatos disponíveis para adoção. A aplicação permite que os usuários possam registrar informações sobre os gatos, como nome, cor, idade, localização, se são castrados, se estão vermifugados e outras características relevantes. Além disso, o sistema possui uma interface gráfica que facilita a interação com os dados, permitindo que os administradores e usuários gerenciem as informações de forma simples e eficaz.

### Funcionalidades Principais

- **Cadastro de Gatos**: Permite o cadastro de gatos com informações detalhadas, como nome, cor, idade, localização, características de adoção (castrado, vermifugado, etc.).
  
- **Login de Usuário**: A aplicação possui uma página de login para que apenas usuários autorizados possam acessar a funcionalidade de cadastro e gerenciamento dos gatos.

- **Gerenciamento de Gatos**: Após o login, os administradores poderão adicionar, editar e remover gatos do banco de dados.

- **Validação de Dados**: O sistema realiza validações para garantir que as informações inseridas sejam coerentes, como verificar se os campos obrigatórios foram preenchidos.

- **Persistência de Dados**: Os dados dos gatos são armazenados em um banco de dados simulado (pode ser um ArrayList ou banco de dados real, dependendo da implementação).

- **Interface Gráfica**: A interface gráfica do usuário (GUI) foi construída utilizando [insira aqui a tecnologia utilizada, como JavaFX ou Swing], proporcionando uma experiência intuitiva.

### Tecnologias Utilizadas

- **Linguagem**: Java
- **Frameworks**: [Se estiver usando JavaFX, Swing ou outros, mencione aqui]
- **Banco de Dados**: [Simulação com ArrayList ou banco de dados real como MySQL, SQLite, etc.]
- **Paradigma**: Programação Orientada a Objetos (POO)
- **Controle de Versão**: Git


## Organização das Pastas
```markdown
```
```
├── com.catalogogatos
│   ├── model           → Contém as classes que representam os dados (POJOs).
│   ├── controller      → Regras de negócio, validações e controle de fluxo.
│   ├── service         → Serviços auxiliares (upload de imagem, autenticação).
│   ├── view            → Interface gráfica (JavaFX, Swing ou outra).
│   ├── repository      → Acesso a dados (simulação com ArrayList ou banco).
│   ├── exception       → Definição de exceções personalizadas.
│   ├── interfaces      → Interfaces e classes abstratas.
└── .gitignore          → Arquivo que especifica os arquivos e pastas a serem ignorados pelo Git.
```
```
## Como Rodar o Projeto

1. Clone o repositório:
   ```bash
   git clone [https://github.com/seuusuario/projeto-catalogacao-gatos.git](https://github.com/seuusuario/projeto-catalogacao-gatos.git)´

2.Navegue até a pasta do projeto:

 ´´cd projeto-catalogacao-gatos

3 - Abra o projeto em sua IDE preferida (como IntelliJ IDEA, Eclipse, etc.).

4 - Execute a classe principal (Main) ou a classe com a interface gráfica para iniciar o sistema.