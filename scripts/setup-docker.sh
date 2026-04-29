#!/bin/bash

echo ">>> [1/6] Pull das imagens do Docker Hub..."
docker pull mysql:8.0
docker pull eclipse-temurin:17-jre

echo ">>> [2/6] Criando rede Docker..."
docker network create network-rm563558

echo ">>> [3/6] Criando volume nomeado para persistencia do banco..."
docker volume create volume-rm563558-mysql

echo ">>> [4/6] Subindo container MySQL..."
docker run -d \
  --name mysql-rm563558 \
  --network network-rm563558 \
  -p 3307:3306 \
  -e MYSQL_ROOT_PASSWORD=root123 \
  -e MYSQL_DATABASE=petflow \
  -e MYSQL_USER=petflow_user \
  -e MYSQL_PASSWORD=petflow123 \
  -v volume-rm563558-mysql:/var/lib/mysql \
  mysql:8.0

echo ">>> Aguardando MySQL inicializar (25s)..."
sleep 25

echo ">>> [5/6] Subindo container da aplicacao Java..."
docker run -d \
  --name app-rm563558 \
  --network network-rm563558 \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql-rm563558:3306/petflow \
  -e SPRING_DATASOURCE_USERNAME=petflow_user \
  -e SPRING_DATASOURCE_PASSWORD=petflow123 \
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
  -e SPRING_JPA_SHOW_SQL=true \
  -e SERVER_PORT=8080 \
  eclipse-temurin:17-jre \
  sleep infinity

echo ">>> [6/6] Copiando JAR para o container..."
: '
IMPORTANTE

- Adicionei caminho relativo no copy,
- Comando deve ser executado dentro da pasta raiz do projeto, onde existe a pasta target/
'
docker cp target/petflow-0.0.1-SNAPSHOT.jar app-rm563558:/app.jar

echo ">>> Iniciando aplicacao dentro do container..."
docker exec -d app-rm563558 sh -c "java -jar /app.jar > /app.log 2>&1"

echo ">>> Aguardando aplicacao inicializar (20s)..."
sleep 20

echo ">>> Containers em execucao:"
docker ps

echo ""
echo ">>> Setup concluido! API disponivel em: http://localhost:8080/pets"