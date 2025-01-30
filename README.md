# Banco JAVER API Documentation

## Versão 1.0 

A API do Banco JAVER oferece acesso para gerenciar informações de clientes, incluindo operações CRUD (Create, Read, Update, Delete) e a consulta do score de crédito. A arquitetura do sistema é baseada em duas aplicações distintas:

1. **Primeira Aplicação** (API na porta 8081):  
   Esta aplicação é responsável por atuar como intermediária entre o usuário e a segunda aplicação, oferecendo acesso para realizar as operações CRUD e também consultando o score de crédito com base no saldo de conta corrente do cliente. Ela consome a segunda aplicação (API na porta 8080) para realizar o processamento dessas informações.

2. **Segunda Aplicação** (API na porta 8080):  
   Esta aplicação é responsável pelo armazenamento de dados em um banco de dados H2 e realiza operações CRUD e de recuperação do score de crédito diretamente sobre essa base de dados.

### Endereço Base
- **API na porta 8081 - Primeira Aplicação**: http://localhost:8081  
  (Pasta: `projeto-final-p1`)

- **API na porta 8080 - Segunda Aplicação**: http://localhost:8080  
  (Pasta: `projeto-final`)

- **Documentação Swagger**: [http://localhost:8081/projeto-final.html](http://localhost:8081/projeto-final.html)

---

## Endpoints

### 1. **GET /api/v1/clientes/{id}**
#### Descrição
Recupera as informações de um cliente baseado no seu ID.

#### Parâmetros
| Nome  | Descrição       | Tipo     |
|-------|-----------------|----------|
| `id`  | ID do cliente   | integer  |

#### Respostas

- **200 OK**  
  Cliente encontrado com sucesso.  
  Exemplo:
  ```json
  {
    "id": 9007199254740991,
    "nome": "string",
    "telefone": 9007199254740991,
    "correntista": true,
    "scoreCredito": 0.1,
    "saldoCc": 0.1
  }
  ```

- **404 Not Found**  
  Cliente não encontrado.  
  Exemplo:
  ```json
  {
    "message": "Cliente id={id} não encontrado no sistema"
  }
  ```

---

### 2. **PUT /api/v1/clientes/{id}**
#### Descrição
Atualiza as informações de um cliente baseado no seu ID.

#### Parâmetros
| Nome  | Descrição       | Tipo     |
|-------|-----------------|----------|
| `id`  | ID do cliente   | integer  |

#### Corpo da Requisição
```json
{
  "nome": "string",
  "telefone": 9007199254740991,
  "correntista": true,
  "saldoCc": 0.1
}
```

#### Respostas

- **200 OK**  
  Cliente atualizado com sucesso.  
  Exemplo:
  ```json
  {
    "id": 9007199254740991,
    "nome": "string",
    "telefone": 9007199254740991,
    "correntista": true,
    "scoreCredito": 0.1,
    "saldoCc": 0.1
  }
  ```

- **400 Bad Request**  
  Dados inválidos fornecidos ou cliente não correntista tentando inserir saldo.  
  Exemplo:
  ```json
  {
    "message": "erro_especifico"
  }
  ```

- **404 Not Found**  
  Cliente não encontrado.  
  Exemplo:
  ```json
  {
    "message": "Cliente id={id} não encontrado no sistema"
  }
  ```

- **409 Conflict**  
  Telefone já cadastrado para outro cliente.  
  Exemplo:
  ```json
  {
    "message": "Cliente com número de telefone '{telefone}' não pode ser cadastrado, pois já existe no sistema"
  }
  ```

---

### 3. **DELETE /api/v1/clientes/{id}**
#### Descrição
Exclui um cliente baseado no seu ID.

#### Parâmetros
| Nome  | Descrição       | Tipo     |
|-------|-----------------|----------|
| `id`  | ID do cliente   | integer  |

#### Respostas

- **204 No Content**  
  Cliente excluído com sucesso.  
  
- **404 Not Found**  
  Cliente não encontrado.  
  Exemplo:
  ```json
  {
    "message": "Cliente id={id} não encontrado no sistema"
  }
  ```

---

### 4. **POST /api/v1/clientes**
#### Descrição
Cria um novo cliente no sistema.

#### Corpo da Requisição
```json
{
  "nome": "string",
  "telefone": 9007199254740991,
  "correntista": true,
  "saldoCc": 0.1
}
```

#### Respostas

- **201 Created**  
  Cliente criado com sucesso.  
  Exemplo:
  ```json
  {
    "id": 9007199254740991,
    "nome": "string",
    "telefone": 9007199254740991,
    "correntista": true,
    "scoreCredito": 0.1,
    "saldoCc": 0.1
  }
  ```

- **400 Bad Request**  
  Dados inválidos fornecidos ou cliente não correntista tentando inserir saldo.  
  Exemplo:
  ```json
  {
    "message": "erro_especifico"
  }
  ```

- **409 Conflict**  
  Telefone já registrado para outro cliente.  
  Exemplo:
  ```json
  {
    "message": "Cliente com número de telefone '{telefone}' não pode ser cadastrado, pois já existe no sistema"
  }
  ```

---

### 5. **GET - Score Crédito /api/v1/clientes/score-credito/{id}**
#### Descrição
Recupera o score de crédito de um cliente com base no seu ID. O score de crédito é atribuído com um valor padrão de 100,00 para clientes não correntistas ou para aqueles que, sendo correntistas, possuem saldo inferior a 1.000,00 em conta. Para os clientes com saldo negativo, o score de crédito será zerado. Para os demais clientes, o score corresponde a 10% do saldo disponível em conta.

#### Parâmetros
| Nome  | Descrição       | Tipo     |
|-------|-----------------|----------|
| `id`  | ID do cliente   | integer  |

#### Respostas

- **200 OK**  
  Score de crédito recuperado com sucesso.  
  Exemplo:
  ```json
  {
    "scoreCredito": 100
  }
  ```

- **404 Not Found**  
  Cliente não encontrado.  
  Exemplo:
  ```json
  {
    "message": "Cliente id={id} não encontrado no sistema"
  }
  ```

---

## Schemas

### ClienteResponseDto
```json
{
  "id": 9007199254740991,
  "nome": "string",
  "telefone": 9007199254740991,
  "correntista": true,
  "scoreCredito": 0.1,
  "saldoCc": 0.1
}
```

### ErrorMessage
```json
{
  "message": "erro"
}
```

### ClienteCreateDto
```json
{
  "nome": "string",
  "telefone": 9007199254740991,
  "correntista": true,
  "saldoCc": 0.1
}
```
