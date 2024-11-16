# ✂️ Projeto Backend - Dom Roque

## 📝 Descrição
Este projeto tem como objetivo fornecer o backend para o sistema de gerenciamento do salão de cabeleireiro **Dom Roque**. O sistema é responsável por gerenciar os dados dos clientes, funcionários e agendamentos de serviços, com integração com a **API Gerencia Net** para criação de planos de pagamento e com a **API ViaCep** para consulta de endereços.

## 🚀 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot** (Framework principal)
- **Banco de Dados**:
  - **MySQL** (Local)
  - **SQL Server na Azure** (Nuvem)
- **APIs Externas**:
  - **Gerencia Net** (Integração para criação de planos)
  - **ViaCep** (Consulta de endereços)

## 🔧 Funcionalidades Principais
- 📋 **Cadastro de Clientes**: Gerencie os dados dos clientes.
- 📅 **Agendamento de Serviços**: Realize o agendamento dos serviços prestados.
- 💳 **Integração com Gerencia Net**: Criação e gerenciamento de planos de pagamento.
- 🏠 **Consulta de Endereços**: Busca de endereços via API ViaCep.
- 📊 **Conexão com Bancos de Dados**: Suporte para banco local (MySQL) e banco na nuvem (SQL Server).

## ⚙️ Requisitos de Instalação
Antes de rodar o projeto, verifique se você possui os seguintes requisitos:
- **Java 17** ou superior
- **Maven** para gerenciar dependências
- **MySQL** e **SQL Server** configurados

## 🛠️ Como Rodar o Projeto
1. Clone o repositório:
   ````
   git clone https://github.com/seu-usuario/seu-repositorio.git
Acesse a pasta do projeto:

cd seu-repositorio
Compile o projeto usando Maven:

mvn clean install
Execute a aplicação:


mvn spring-boot:run
🌐 Rotas Principais da API
- **/clientes**: Endpoints para gerenciamento de clientes.
- **/funcionarios**: Endpoints para gerenciamento de funcionários.
- **/agendamentos**: Endpoints para agendamento de serviços.
- **/planos**: Integração com a API Gerencia Net para criação de planos.
- **/enderecos**: Consultas de endereços via ViaCep.
## 📂 Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── com.dominio.domroque
│   │       ├── controller      # Controladores da aplicação (responsáveis pelos endpoints)
│   │       ├── service         # Serviços responsáveis pela lógica de negócios
│   │       ├── model           # Modelos de dados e entidades JPA
│   │       └── repository      # Repositórios para acesso aos dados no banco
│   └── resources
│       ├── application.properties  # Arquivo de configurações do Spring Boot
│       └── data.sql              # Arquivo de inicialização do banco de dados (se necessário)
└── test
    └── java
        └── com.dominio.domroque
            ├── controller      # Testes dos controladores
            ├── service         # Testes dos serviços
            └── repository      # Testes dos repositórios
````
🔒 Segurança
A aplicação pode ser configurada com Spring Security para controle de autenticação e autorização.

🤝 Contribuições
Contribuições são bem-vindas! Para contribuir, siga os seguintes passos:

Faça um fork do repositório.
Crie uma nova branch: git checkout -b minha-feature.
Realize as alterações e commit: git commit -m 'Adicionando nova feature'.
Envie para o repositório remoto: git push origin minha-feature.
Abra um Pull Request.
📬 Contato
Para dúvidas ou sugestões, entre em contato via e-mail: domroque@empresa.com.

Obrigado por conferir o projeto! 😊✨




### Explicação das Seções:
1. **Descrição**: Explica o que o projeto faz e suas integrações.
2. **Tecnologias**: Lista as tecnologias que estão sendo usadas.
3. **Funcionalidades**: Destaca os principais recursos do sistema.
4. **Requisitos de Instalação**: Informa os pré-requisitos para rodar o projeto.
5. **Como Rodar o Projeto**: Instruções passo a passo para rodar o projeto localmente.
6. **Rotas Principais da API**: Descreve as rotas mais importantes que o backend oferece.
7. **Estrutura do Projeto**: Mostra a organização dos arquivos no repositório.
8. **Segurança**: Indica que a aplicação pode ser configurada com **Spring Security**.
9. **Contribuições**: Orientações para quem deseja contribuir com o código.
10. **Contato**: Detalhes de contato.
