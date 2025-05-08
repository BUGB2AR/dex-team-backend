Instruções Para rodar o projeto

Utilizando a ide Spring tool suit importe o projeto para sua workspace, certifique-se que você tem instalado o mysql server na maquina local para poder rodar o projeto corretamente

1. Passo:

- Baixar e instalar o mysql localmente no seguinte site => https://dev.mysql.com/downloads/installer/

2. Passo:
- Ide Utilizado foi Eclipse com java 17 basta ter a versão em sua maquina ou usando o Spring Tool Suit já podemos usar diretamente apenas importando o projeto para a workspace normalmente.

3. Passo
- Ao inicializar com o maven usando o comando mvn spring-boot:run, com o devido comando já é possivel rodar o projeto, o mesmo possui bootstrap para criar a tabela com auto ddl update então ao inicializar já irá criar as tabelas necessárias
- obs: utilize o clean install antes de executar o mvn, de via de regra é interessante usar dessa forma podemos fazer um clean e um install seguro dessa forma também irá rodar as suits de testes da nossa aplicação em spring boot.

4 Passo
- Um extra podemos também rodar o projeto apenas clicando no botão direito do mouse em cima do projeto na workspace do eclipse e indo até covarege podemos executar o covarege de testes do backend
- Segundo extra podemos executar também diretamente via dev tools ferramenta ao qual facilita a execução do projeto em spring boot.

Obs: configure o seguinte usuário mysql ao instalar workbanch + server

usuario: root
password: root
porta: 3306 (padrão)
