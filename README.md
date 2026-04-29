# 🐾 PetFlow — API REST com Docker

**Equipe:** PetFlow  
**RM:** 563558  
**Disciplina:** DevOps Tools & Cloud Computing — FIAP  
**Checkpoint:** 2º CP | 1º Semestre

---

## 📋 Sobre o Projeto

API REST desenvolvida em Java com Spring Boot para gerenciamento de Pets,
criada como teste de migração do ambiente de desenvolvimento para containers Docker.
O projeto utiliza dois containers em uma rede Docker:

- **mysql-rm563558** — banco de dados MySQL 8.0 com volume nomeado para persistência
- **app-rm563558** — aplicação Java Spring Boot exposta na porta 8080

---

## 🛠️ Tecnologias

- Java 17
- Spring Boot 4.0.6
- Spring Data JPA + Hibernate 7
- MySQL 8.0
- Docker
- Eclipse Temurin 17 JRE (imagem base da aplicação)

---

## 📁 Estrutura do Projeto

```cmd
petflow/
├── src/
│   └── main/
│       ├── java/br/com/petflow/
│       │   ├── PetflowApplication.java
│       │   ├── controller/PetController.java
│       │   ├── model/Pet.java
│       │   ├── repository/PetRepository.java
│       │   └── service/PetService.java
│       └── resources/
│           └── application.properties
├── scripts/
│   └── setup-docker.sh
├── target/
│   └── petflow-0.0.1-SNAPSHOT.jar
└── pom.xml
```

---

## ✅ Pré-requisitos

- Docker Desktop instalado e rodando
- Java 17+
- Maven (ou usar o wrapper `./mvnw`)
- Git

---

## 🚀 How To — Passo a Passo

### 1. Clonar o repositório

```bash
git clone https://github.com/SEU_USUARIO/petflow.git
cd petflow
```

### 2. Gerar o JAR da aplicação

```bash
./mvnw clean package -DskipTests
```

O JAR será gerado em `target/petflow-0.0.1-SNAPSHOT.jar`.

### 3. Executar o script de setup

> ⚠️ **IMPORTANTE:** Execute pelo **CMD do Windows** (não Git Bash nem PowerShell),
> pois o Git Bash converte caminhos Linux e causa erros no `docker cp` e `docker exec`.

Abra o CMD, navegue até a pasta do projeto e execute:

```cmd
cd C:\caminho\para\petflow
bash scripts/setup-docker.sh
```

Ou execute os comandos manualmente na ordem abaixo.

---

### 4. Execução manual (alternativa ao script)

#### 4.1 Pull das imagens
```cmd
docker pull mysql:8.0
docker pull eclipse-temurin:17-jre
```

#### 4.2 Criar rede Docker
```cmd
docker network create network-rm563558
```

#### 4.3 Criar volume nomeado
```cmd
docker volume create volume-rm563558-mysql
```

#### 4.4 Subir container MySQL
```cmd
docker run -d ^
  --name mysql-rm563558 ^
  --network network-rm563558 ^
  -p 3307:3306 ^
  -e MYSQL_ROOT_PASSWORD=root123 ^
  -e MYSQL_DATABASE=petflow ^
  -e MYSQL_USER=petflow_user ^
  -e MYSQL_PASSWORD=petflow123 ^
  -v volume-rm563558-mysql:/var/lib/mysql ^
  mysql:8.0
```

> Aguarde 25 segundos para o MySQL inicializar completamente.

#### 4.5 Subir container da aplicação
```cmd
docker run -d ^
  --name app-rm563558 ^
  --network network-rm563558 ^
  -p 8080:8080 ^
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql-rm563558:3306/petflow ^
  -e SPRING_DATASOURCE_USERNAME=petflow_user ^
  -e SPRING_DATASOURCE_PASSWORD=petflow123 ^
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update ^
  -e SPRING_JPA_SHOW_SQL=true ^
  -e SERVER_PORT=8080 ^
  eclipse-temurin:17-jre ^
  sleep infinity
```

#### 4.6 Copiar o JAR para o container
```cmd
docker cp target\petflow-0.0.1-SNAPSHOT.jar app-rm563558:/app.jar
```

#### 4.7 Iniciar a aplicação dentro do container
```cmd
docker exec app-rm563558 sh -c "java -jar /app.jar"
```

> Este comando mantém o log visível no terminal. Para rodar em background use `-d` e acompanhe com `docker logs app-rm563558`.

---

### 5. Verificar os containers

```cmd
docker ps
docker image ls
docker volume ls
docker network ls
```

---

### 6. Testar a API

Base URL: `http://localhost:8080/pets`

#### POST — Criar pet
```cmd
curl -X POST http://localhost:8080/pets -H "Content-Type: application/json" -d "{\"nome\":\"Rex\",\"especie\":\"Cao\",\"raca\":\"Labrador\",\"idade\":3,\"peso\":28.5,\"tutorNome\":\"Olavo Silva\",\"tutorEmail\":\"olavo@petflow.com\"}"
```

#### GET — Listar todos
```cmd
curl http://localhost:8080/pets
```

#### GET — Buscar por ID
```cmd
curl http://localhost:8080/pets/1
```

#### PUT — Atualizar
```cmd
curl -X PUT http://localhost:8080/pets/1 -H "Content-Type: application/json" -d "{\"nome\":\"Rex Atualizado\",\"especie\":\"Cao\",\"raca\":\"Labrador\",\"idade\":4,\"peso\":30.0,\"tutorNome\":\"Olavo Silva\",\"tutorEmail\":\"olavo@petflow.com\"}"
```

#### DELETE — Remover
```cmd
curl -X DELETE http://localhost:8080/pets/1
```

#### Verificar no banco após cada operação
```cmd
docker exec -it mysql-rm563558 mysql -u petflow_user -ppetflow123 petflow -e "SELECT * FROM tb_pfw_pet;"
```

---

## 🐳 Objetos Docker criados

| Tipo | Nome |
|---|---|
| Network | network-rm563558 |
| Volume | volume-rm563558-mysql |
| Container | mysql-rm563558 |
| Container | app-rm563558 |

---

## 📎 Links

- 🔗 GitHub: https://github.com/olavoneves/PetFlow
- 🎥 Vídeo: [link do YouTube]
