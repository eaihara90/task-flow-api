# TaskFlow

API REST para gerenciamento de tarefas, desenvolvida como projeto de estudo de Java e Spring Boot. O projeto explora a organização em camadas, o uso de DTOs e a construção de endpoints HTTP.

## Tecnologias

- Java 25
- Spring Boot 4.1.2 e Spring Web MVC
- Maven

## Funcionalidades

- Criar uma tarefa com título e descrição.
- Listar todas as tarefas.
- Buscar uma tarefa pelo ID.

As tarefas são armazenadas em memória e são perdidas quando a aplicação é encerrada. Novas tarefas recebem o status `pending`. Os campos `title` e `description` não podem ser nulos ou estar em branco.

## Como executar

É necessário ter o JDK 25 e o Maven instalados. Na pasta do projeto, execute:

```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## Endpoints

| Método | Caminho | Descrição |
| --- | --- | --- |
| POST | `/tasks` | Cria uma tarefa |
| GET | `/tasks` | Lista as tarefas |
| GET | `/tasks/{id}` | Busca uma tarefa pelo ID |

### Criar uma tarefa

```bash
curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d '{"title":"Estudar Spring Boot","description":"Criar uma API REST"}'
```

Exemplo de resposta (`201 Created`):

```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "description": "Criar uma API REST",
  "status": "pending"
}
```

### Consultar tarefas

```bash
curl http://localhost:8080/tasks
curl http://localhost:8080/tasks/1
```

## Estrutura do projeto

O código da funcionalidade de tarefas está organizado em `src/main/java/com/taskflow/task`:

- `controller`: recebe as requisições HTTP.
- `service`: aplica as regras da aplicação.
- `repository`: define o acesso aos dados e a implementação em memória.
- `entity`: representa a tarefa e seus estados.
- `dto`: define os dados de entrada e saída da API.
- `mapper`: converte entre entidades e DTOs.
