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

⚙️ Como executar o projeto localmente
Clone o repositório:

```bash
git clone https://github.com/gabb-dev/Proz-Tec.git
```

#### Após baixar o projeto basta execultar a classe **Main.java**
