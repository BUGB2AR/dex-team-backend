# 🏆 Desafio de Desenvolvimento - Sistema de Escalação de Times

## 📌 Sobre o Projeto

Este projeto tem como objetivo fornecer uma API para gerenciar a escalação de times de esportes tradicionais e eSports. Através de um backend construído com **Spring Boot**, é possível cadastrar integrantes, montar times semanais e gerar estatísticas com base na composição dos times ao longo do tempo.


## ✅ Funcionalidades Implementadas

Todas as funcionalidades descritas no desafio foram completamente implementadas:

### 1. Tratamento de Dados

Foram implementados os métodos no `ApiService` conforme solicitado, sem uso de `count`, procedures ou funções SQL para processamento. Todo o tratamento de dados é feito em Java.

- `timeDaData(data)`
- `integranteMaisUsado(dataInicial, dataFinal)`
- `integrantesDoTimeMaisComum(dataInicial, dataFinal)`
- `funcaoMaisComum(dataInicial, dataFinal)`
- `franquiaMaisFamosa(dataInicial, dataFinal)`
- `contagemPorFranquia(dataInicial, dataFinal)`
- `contagemPorFuncao(dataInicial, dataFinal)`

### 2. API de Cadastro

Endpoints criados para cadastrar e gerenciar entidades:

- Cadastro de **Integrantes e listagem**
- Cadastro de **Times e listagem**
- Associação de integrantes a times (Composição do Time)

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- JUnit
- Eclipse / Spring Tool Suite (STS)

---

## 🚀 Instruções Para Rodar o Projeto

### ✅ Pré-requisitos

- Java 17 instalado
- Maven instalado
- MySQL Server e Workbench instalados localmente
- IDE: Eclipse ou Spring Tool Suite (STS)

### 1️⃣ Instalação do MySQL

Faça o download e instalação do MySQL localmente:

🔗 [Download MySQL Installer](https://dev.mysql.com/downloads/installer/)

**Configurações recomendadas:**

- Usuário: `root`
- Senha: `root`
- Porta: `3306` (padrão)

### 2️⃣ Importar o Projeto

Abra o **Eclipse** ou **Spring Tool Suite**:

- Vá em `File > Import > Existing Maven Projects`
- Selecione o diretório do projeto e importe para a workspace

### 3️⃣ Executar o Projeto

#### Via Maven CLI:

- mvn clean install
- mvn spring-boot:run


### 🧪 Testes

Todos os métodos de serviço foram cobertos com testes unitários, garantindo a confiabilidade e estabilidade das funcionalidades implementadas.


### 🛠️ Outras Considerações

- O código está devidamente comentado, especialmente em trechos mais complexos.
- Foram realizados commits frequentes para registrar toda a evolução da solução.







