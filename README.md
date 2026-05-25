# 💻 Proz-Tec
Um sistema desenvolvido para simular o gerenciamento de uma loja online.

🎯 Objetivo do Projeto

O principal objetivo do projeto é servir como gerenciamento de um sistema de loja seguindo o fluxo descrito abaixo.

O sistema funciona no seguinte fluxo:

1. O usuário interage com a interface (View).

2. A requisição é enviada para o Controller, que atua como o cérebro da operação.

3. O Controller solicita os dados necessários ao Model.

4. O Model processa as regras de negócio, consulta o banco de dados e retorna a informação.

5. O Controller envia esses dados formatados de volta para a View, que atualiza a tela para o usuário.

📂 Estrutura de Diretórios

```
/src
├── /controllers  
├── /models        
├── /views         
├── /routes        
└── Main.java      
```
🧩 Como cada camada atua no projeto:
- Model: Aqui vamos ter todas as **regras de negócio** e a lógica do sistema. Por exemplo: Só posso criar um produto se eu for um fornecedor ou só posso comprar se for maior de idade.

- Views: Toda parte de interação com o usuário do sistema estará aqui (neste projeto as views é tudo o que aparece no terminal para visualizar e interagir).

- Controllers: Os controllers são o que vão ligar o model (a lógica/ regra de negócio) com as view (a tela do usuário).

🚀 Tecnologias Utilizadas
- Java 27

- MySQL

🗃️ Modelagem do Banco de Dados

Os modelos de banco de dados foram feitos pela ferramenta **brModelo**, que pode ser baixada [aqui](https://sourceforge.net/projects/brmodelo/).

⚙️ Como executar o projeto localmente

1. Clone o repositório:

```bash
git clone https://github.com/gabb-dev/Proz-Tec.git
```

2. Renomeie o arquivo .env.example para .env e preencha as variáveis de conexão com o banco de dados;

3. Execute as instruções SQL do arquivo `docs/db/modelo-fisico/migration.sql` no seu console MySQL para criar um banco de dados e popular com dados de exemplo;

4. Compile e execute com o comando:

```bash
javac -d bin src/Main.java
java -cp "bin;src/database/mysql-connector-j-8.4.0.jar" Main
```
