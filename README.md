# Importador de Dados (CSV para SQL)
Este sistema foi desenvolvido como parte do desafio prático para o cargo de Desenvolvedor Júnior. Ele realiza a leitura, processamento e persistência de dados de escolas a partir de um arquivo CSV em um banco de dados PostgreSQL.

## 🛠️ Tecnologias Utilizadas
Linguagem: Java 25

Banco de Dados: PostgreSQL 17

Driver JDBC: PostgreSQL JDBC Driver

Arquitetura: Camadas (Model, Service, DAO)

## 🚀 Como Configurar e Executar
### 1. Requisitos do Banco de Dados
Certifique-se de ter o PostgreSQL instalado. Crie o banco de dados e a tabela executando o script "escolas_DDL" na pasta docs.
### 2. Configuração do Projeto
Importe o projeto em sua IDE (Eclipse, IntelliJ, VS Code).

Adicione o driver JDBC do PostgreSQL (postgresql-42.7.8.jar) ao seu Build Path.

No arquivo bd.Conexao, verifique se as credenciais (USUARIO e SENHA) correspondem ao seu ambiente local.

Certifique-se de que o arquivo escolas122022.csv está localizado na raiz do projeto.

### 3. Execução
Execute a classe escolasTestePratico.Main. O console exibirá o progresso e confirmará o sucesso da importação.

## 🧠 Decisões de Design e Implementação
Para atender aos critérios de funcionalidade, clareza e tratamento de erros, as seguintes decisões foram tomadas:

### 1. Arquitetura em Camadas
O sistema foi dividido para facilitar a manutenção:

Model (Escola): Representação simples dos dados.

Service (EscolaService): Contém a lógica de negócio, conversão de tipos e limpeza de strings do CSV.

DAO (EscolasDao): Focado puramente na interação com o banco via JDBC.

### 2. Tratamento de Erros e Resiliência
Robustez no Parse: O sistema identifica caracteres de preenchimento do CSV (como -) e os converte adequadamente para null, evitando erros de conversão numérica.

Continuidade: Caso uma linha específica apresente erro de mapeamento, o sistema registra o erro no log (System.err) informando o código da escola, mas permite que o processo continue para os demais registros.

Atomicidade (Rollback): Foi implementado o controle manual de transação (setAutoCommit(false)). Caso ocorra um erro crítico durante a inserção no banco, um Rollback é executado para garantir que o banco não fique com dados parciais.

Além disso, outros erros mais simples também foram devidamente tratados.

### 3. Performance com Batch Processing
Em vez de realizar um comando INSERT para cada linha, o sistema utiliza o Batch Update do JDBC. Os dados são enviados ao banco em lotes de 500 registros, reduzindo drasticamente o tráfego de rede e o tempo total de processamento.

### 4. Precisão Geográfica
Campos de Latitude e Longitude foram mapeados como BigDecimal no Java e NUMERIC no SQL. Isso evita erros de arredondamento, garantindo que a coordenada exata do CSV seja preservada.

## 📝 Observação sobre o Dataset

Datas: O sistema utiliza SimpleDateFormat para converter as strings de data do CSV em objetos java.util.Date compatíveis com o banco de dados.

**Candidato:** Caio Silveira Figueiredo

**Data:** 05/02/2026
