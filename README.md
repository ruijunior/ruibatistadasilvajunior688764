# SEPLAG - Catálogo de Artistas e Álbuns Musicais

**Inscrição:** 16372  
**Candidato:** Rui Batista da Silva Junior  
**Vaga:** Engenheiro da Computação - Sênior  
**Projeto desenvolvido como parte do processo seletivo para SEPLAG/MT**

Backend em Java para cadastro e consulta de artistas (cantores e bandas) e álbuns, com capas armazenadas em objeto storage, listagem paginada, filtros por tipo de artista e por nome, e sincronização de regionais com uma API externa. A API é protegida por JWT, limita requisições por usuário e envia notificações em tempo real (WebSocket) quando um novo álbum é criado.

---

## Stack Utilizada

Java 21 · Spring Boot 3.2.3 · Gradle · Spring Security (JWT) · Spring Data JPA · Spring WebSocket (STOMP) · Spring Cloud OpenFeign · PostgreSQL 15 · MinIO · Flyway · MapStruct · Bucket4j · SpringDoc OpenAPI · Testcontainers · Docker

---

## Arquitetura Geral

Arquitetura baseada em **Clean Architecture** + **Hexagonal Architecture (Ports & Adapters)**

### Estrutura de Camadas

```
br.com.rbsj.seplag/
  domain/          # Camada de Domínio (regras de negócio puras)
    album/         # Entidades, Value Objects, Interfaces Gateway
    artista/
    regional/
    validation/
  application/     # Camada de Aplicação (Use Cases)
    album/         # create, retrieve, update, upload
    artista/
    regional/
  infrastructure/  # Camada de Infraestrutura (adaptadores)
    api/           # Controllers REST, exception (ApiError, GlobalExceptionHandler)
    album/         # Implementação Gateway (PostgreSQL)
    security/      # JWT, Rate Limit, Filtros
    storage/       # MinIO Gateway
    notification/  # WebSocket Gateway
    config/        # Configurações Spring
```

---

## Backend (API)

### Stack Tecnológica

- **Java 21** (LTS)
- **Spring Boot 3.2.3**
- **Spring Security** (JWT stateless)
- **Spring Data JPA** + **Hibernate**
- **Spring WebSocket** (STOMP)
- **Spring Cloud OpenFeign** (integração externa)
- **PostgreSQL 15** (banco de dados)
- **MinIO** (Object Storage S3-compatible)
- **Flyway** (migrações versionadas)
- **MapStruct** (mapeamento de objetos)
- **Bucket4j** (rate limiting)
- **SpringDoc OpenAPI** (Swagger)
- **Gradle** (build tool)
- **Testcontainers** (testes de integração)

### Arquitetura

**Clean Architecture + Hexagonal Architecture:**

- **Domain:** Entidades puras, Value Objects, Interfaces Gateway (Ports)
- **Application:** Use Cases, Commands, Queries, Outputs
- **Infrastructure:** Implementações de Gateway (Adapters), Controllers, Repositories

**Princípios aplicados:**
- Inversão de Dependência (DIP)
- Separação de Responsabilidades (SRP)
- Testabilidade (Domain testável sem frameworks)
- Flexibilidade (trocar PostgreSQL por MongoDB só muda o Gateway)

### Segurança

- **Autenticação JWT:**
  - Access Token: 5 minutos
  - Refresh Token: 24 horas
  - Endpoint `/api/v1/auth/login` e `/api/v1/auth/refresh`
- **Rate Limit:** 10 requisições por minuto por usuário (Bucket4j)
- **Health Checks:**
  - Liveness: `/actuator/health/liveness`
  - Readiness: `/actuator/health/readiness` (valida DB)
- **CORS:** Configurado para permitir origem específica
- **Swagger:** Documentação automática em `/api/swagger-ui`

### Tratamento global de exceções

Exceções são convertidas em respostas HTTP padronizadas via `@RestControllerAdvice` (`GlobalExceptionHandler`):

- **IllegalArgumentException** → 404 (se mensagem contém "não encontrado"/"not found") ou 400
- **DomainException** → 400 com lista de erros em `details`
- **UsernameNotFoundException** / **AuthenticationException** → 401 ("Credenciais inválidas")
- **RuntimeException** (ex.: MinIO) → 503 (serviço indisponível) ou 500
- **Exception** → 500

Resposta de erro (`ApiError`): `timestamp`, `status`, `error`, `message`, `path`, `details` (opcional).

Exemplo:
```json
{
  "timestamp": "2026-02-04T12:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Álbum não encontrado: id-inexistente",
  "path": "/api/v1/albuns/id-inexistente",
  "details": null
}
```

### Logs (SLF4J)

Logs estratégicos em pontos chave (auth, criação de álbum, sync de regionais, rate limit, erros de storage e exceções tratadas). Níveis: INFO (operações principais), WARN (auth/refresh rejeitado, rate limit, exceções 4xx), ERROR (MinIO, exceções 5xx), DEBUG (notificação WebSocket). Configurável em `application.yaml` (`logging.level.br.com.rbsj.seplag`).

### Funcionalidades Implementadas

#### Requisitos Gerais ✅

- ✅ Segurança: bloqueio de acesso por domínio (CORS configurado)
- ✅ Autenticação JWT com expiração 5 min + renovação
- ✅ POST, PUT, GET implementados
- ✅ Paginação na consulta de álbuns
- ✅ Consultas parametrizadas: álbuns por tipo (CANTOR/BANDA)
- ✅ Consultas por nome do artista com ordenação alfabética (asc/desc)
- ✅ Upload de múltiplas imagens de capa do álbum
- ✅ Armazenamento no MinIO (API S3-compatible)
- ✅ Links pré-assinados com expiração de 30 minutos
- ✅ Versionamento de endpoints (`/api/v1/...`)
- ✅ Flyway Migrations para criar e popular tabelas
- ✅ Documentação OpenAPI/Swagger

#### Requisitos Sênior ✅

- ✅ Health Checks (Liveness/Readiness)
- ✅ Testes unitários (Domain + Use Cases)
- ✅ Testes de integração (Infrastructure com Testcontainers)
- ✅ WebSocket para notificar frontend a cada novo álbum cadastrado
- ✅ Rate Limit: 10 requisições por minuto por usuário
- ✅ Endpoint de regionais:
  - ✅ Importar lista da API externa
  - ✅ Atributo "ativo" (boolean)
  - ✅ Sincronização inteligente:
    - Novo no endpoint → inserir
    - Ausente no endpoint → inativar
    - Atributo alterado → inativar antigo e criar novo

### Estrutura de Dados (Banco)

#### Tabela: `artistas`
```sql
id          VARCHAR(255) PK (UUID)
nome        VARCHAR(200) NOT NULL
tipo        VARCHAR(20) NOT NULL CHECK (tipo IN ('CANTOR', 'BANDA'))
criado_em   TIMESTAMP NOT NULL
atualizado_em TIMESTAMP NOT NULL
```

#### Tabela: `albuns`
```sql
id              VARCHAR(255) PK (UUID)
titulo          VARCHAR(200) NOT NULL
ano_lancamento  INTEGER
criado_em       TIMESTAMP NOT NULL
atualizado_em   TIMESTAMP NOT NULL
```

#### Tabela: `album_capas`
```sql
album_id  VARCHAR(255) FK → albuns.id
url_capa  VARCHAR(500) NOT NULL
```

#### Tabela: `artista_album` (N:N)
```sql
artista_id VARCHAR(255) FK → artistas.id
album_id   VARCHAR(255) FK → albuns.id
PRIMARY KEY (artista_id, album_id)
```

#### Tabela: `regionais`
```sql
id          BIGSERIAL PK
id_externo  INTEGER UNIQUE
nome        VARCHAR(200) NOT NULL
ativo       BOOLEAN NOT NULL DEFAULT true
criado_em   TIMESTAMP NOT NULL
atualizado_em TIMESTAMP NOT NULL
```

### Storage (MinIO)

- **Bucket:** `album-covers`
- **Acesso:** URLs pré-assinadas (PUT para upload, GET para download)
- **Expiração:** 30 minutos (1800 segundos)
- **Console:** http://localhost:9001 (minioadmin/minioadmin)

### WebSocket

- **Endpoint:** `/ws` (com fallback SockJS)
- **Tópico:** `/topic/albuns`
- **Evento:** Disparado automaticamente quando um novo álbum é cadastrado
- **Formato:** Objeto `Album` completo serializado em JSON

### Health Checks

| Tipo | Endpoint | Descrição |
|------|----------|-----------|
| Health | `/actuator/health` | Status geral da aplicação |
| Liveness | `/actuator/health/liveness` | Aplicação está viva (Kubernetes reinicia se falhar) |
| Readiness | `/actuator/health/readiness` | Aplicação pronta (valida DB; Kubernetes só envia tráfego se UP) |

**Exemplo de uso:**
```bash
# Health geral
curl http://localhost:8080/actuator/health

# Liveness
curl http://localhost:8080/actuator/health/liveness

# Readiness
curl http://localhost:8080/actuator/health/readiness
```

---

## Como Executar

### Pré-requisitos

- **Docker** e **Docker Compose** instalados
- **Java 21** (opcional, apenas se rodar localmente sem Docker)
- **Gradle** (opcional, apenas se rodar localmente)

### Opção 1: Docker Compose (Recomendado)

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd ruibatistadasilvajunior688764/backend
   ```

2. **Subir todos os serviços:**
   ```bash
   docker compose up --build
   ```

   Isso irá:
   - Construir a imagem da API (multi-stage build)
   - Subir PostgreSQL 15
   - Subir MinIO
   - Criar bucket `album-covers` automaticamente
   - Subir a API Spring Boot

3. **Aguardar inicialização:**
   - PostgreSQL: ~5 segundos
   - MinIO: ~10 segundos
   - API: ~30 segundos (aplica migrations Flyway)

### Opção 2: Execução Local (sem Docker)

1. **Subir PostgreSQL e MinIO via Docker Compose:**
   ```bash
   docker compose up postgres minio createbuckets
   ```

2. **Configurar variáveis de ambiente** (ou usar `application.yaml`):
   ```bash
   export DB_HOST=localhost
   export DB_PORT=5432
   export DB_NAME=catalogo_db
   export DB_USER=postgres
   export DB_PASSWORD=postgres
   export MINIO_ENDPOINT=http://localhost:9000
   export MINIO_ACCESS_KEY=minioadmin
   export MINIO_SECRET_KEY=minioadmin
   ```

3. **Executar a aplicação:**
   ```bash
   ./gradlew bootRun
   ```

### Acessos

| Serviço | URL | Credenciais |
|---------|-----|-------------|
| **API** | http://localhost:8080 | - |
| **Swagger** | http://localhost:8080/api/swagger-ui | - |
| **MinIO Console** | http://localhost:9001 | minioadmin / minioadmin |
| **PostgreSQL** | localhost:5432 | postgres / postgres |

### Usuário Inicial

O sistema utiliza autenticação em memória (via `SimpleUserDetailsService`):

- **Username:** `admin`
- **Password:** `admin123`
- **Roles:** Sem roles específicas (todos os endpoints autenticados são acessíveis)

**Exemplo de login:**
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

**Resposta:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 300000
}
```

---

## Testes

### Executar Todos os Testes

```bash
./gradlew test
```

### Executar Apenas Testes Unitários

```bash
./gradlew test --tests "*Test" --exclude-task "*IntegrationTest"
```

### Executar Apenas Testes de Integração

**Nota:** Requer Docker em execução (Testcontainers)

```bash
./gradlew test --tests "*IntegrationTest"
```

### Cobertura de Testes

- **Domain:** Entidades, Value Objects, Validators
- **Application:** Use Cases (Create, Update, Retrieve, List)
- **Infrastructure:** Gateway (PostgreSQL), Controllers (API REST)

**Testes de Integração:**
- `AlbumPostgresGatewayIntegrationTest` - Testa persistência com PostgreSQL real
- `AlbumControllerIntegrationTest` - Testa API REST completa (auth + endpoints)

---

## Endpoints da API

### Autenticação

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/api/v1/auth/login` | Login e obtenção de tokens | Não |
| POST | `/api/v1/auth/refresh` | Renovação de tokens | Não |

### Artistas

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/v1/artistas` | Listar (paginação, busca, ordenação) | Sim |
| GET | `/api/v1/artistas/{id}` | Buscar por ID | Sim |
| POST | `/api/v1/artistas` | Criar artista | Sim |
| PUT | `/api/v1/artistas/{id}` | Atualizar artista | Sim |

### Álbuns

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/v1/albuns` | Listar (paginação, busca, filtro por tipo) | Sim |
| GET | `/api/v1/albuns/{id}` | Buscar por ID | Sim |
| POST | `/api/v1/albuns` | Criar álbum | Sim |
| POST | `/api/v1/albuns/{id}/presigned-url` | Gerar URL pré-assinada para upload | Sim |
| POST | `/api/v1/albuns/{id}/capa` | Adicionar capa ao álbum | Sim |

### Regionais

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/v1/regionais` | Listar regionais | Sim |
| POST | `/api/v1/regionais/sync` | Sincronizar com API externa | Sim |

**Documentação completa:** http://localhost:8080/api/swagger-ui

---

## Decisões de Arquitetura

### Clean Architecture

O domínio (entidades, regras, interfaces de persistência e notificação) não depende de frameworks: só de Java. Os casos de uso orquestram o domínio e dependem apenas dessas interfaces. Assim o núcleo da aplicação fica testável sem subir Spring ou banco, e mudanças em tecnologia (trocar banco, storage ou mensageria) ficam restritas à camada de infraestrutura.

### Hexagonal (Ports & Adapters)

As interfaces de persistência, storage e notificação ficam no domínio (portas); as implementações (PostgreSQL, MinIO, WebSocket) ficam na infraestrutura (adaptadores). Isso permite trocar adaptadores (ex.: MinIO por S3, PostgreSQL por outro banco) sem alterar regras de negócio e facilita mocks nos testes.

### Gradle

Build mais rápido com cache e compilação incremental; configuração em Groovy/Kotlin DSL; integração direta com Spring Boot 3 e Java 21.

### MinIO

Compatível com a API S3, roda localmente para desenvolvimento e testes, e permite evoluir para S3 ou outro objeto storage em produção. Upload via URLs pré-assinadas segue o padrão usual em APIs modernas.

### WebSocket (STOMP)

Notificações em tempo real (ex.: novo álbum cadastrado) sem polling. STOMP padroniza mensagens sobre WebSocket e, no futuro, permite acoplar um message broker (RabbitMQ, Redis) para escalar.

### Por que Bucket4j para Rate Limit?

- **Token Bucket:** Algoritmo eficiente e preciso para controlar a taxa de requisições.
- **Por usuário:** Um “bucket” por usuário (ou por IP quando não autenticado), permitindo limite justo por identidade.
- **Performance (O(1) em memória):** Cada verificação “posso consumir um token?” é feita em tempo constante, independente da quantidade de usuários ou do histórico de requisições — o que mantém o rate limit rápido mesmo com muitos usuários simultâneos.
- **Padrão de mercado:** Mesmo tipo de abordagem usada por APIs de grande escala.

---

## O que foi Implementado

### ✅ Requisitos Obrigatórios

- [x] Segurança com bloqueio de domínios externos (CORS)
- [x] Autenticação JWT (5 min) + Refresh Token (24h)
- [x] POST, PUT, GET implementados
- [x] Paginação em álbuns
- [x] Consultas parametrizadas (tipo CANTOR/BANDA)
- [x] Consultas por nome com ordenação (asc/desc)
- [x] Upload múltiplas capas
- [x] MinIO (S3-compatible)
- [x] Links pré-assinados (30 min)
- [x] Versionamento de endpoints
- [x] Flyway Migrations
- [x] OpenAPI/Swagger

### ✅ Requisitos Sênior

- [x] Health Checks (Liveness/Readiness)
- [x] Testes unitários (Domain + Use Cases)
- [x] Testes de integração (Infrastructure)
- [x] WebSocket (notificações em tempo real)
- [x] Rate Limit (10 req/min por usuário)
- [x] Sincronização de Regionais (API externa)

### ✅ Entrega

- [x] Dockerfile (multi-stage build)
- [x] docker-compose completo (API + PostgreSQL + MinIO)
- [x] README.md documentado
- [x] Código como produção (Clean Code, padrões, testes)
- [x] Tratamento global de exceções (`@RestControllerAdvice` + `ApiError`)
- [x] Logs estratégicos (SLF4J) em auth, álbuns, regionais, rate limit e erros

---

## Contato

Rui Batista da Silva Junior · Inscrição 16372 · ruibatistasilvajunior@gmail.com

---

## Licença

Este projeto foi desenvolvido exclusivamente para o processo seletivo da SEPLAG/MT.

---

**Status do Projeto:** ✅ **Concluído e aderente a todos os requisitos do edital para ENGENHEIRO DA COMPUTAÇÃO - SÊNIOR (Backend Java)**
