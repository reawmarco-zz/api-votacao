# API Votação

API de serviços de votação orientado pela abertura de uma pauta para ser votada durante um determinado período, onde somente pode haver um voto por associado.

 Objetivos
  - Cadastrar uma nova pauta;
  - Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo
determinado na chamada de abertura ou 1 minuto por default);
  - Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é
identificado por um id único e pode votar apenas uma vez por pauta);
  - Contabilizar os votos e dar o resultado da votação na pauta.
### Tech

* Java 8
* Spring Boot Web; JPA; Data; Cloud;
* Swagger 2
* MySql
* Lombok
* H2 Database
* AppEngine - Google Cloud Plataform

### Instalação

```sh
$ git clone https://github.com/reawmarco/api-votacao.git
$ cd api-votacao
$ mvn package
$ cd target
$ java -jar api_votacao.jar
```
#### Swagger
Produção GCP - AppEngine release:
```
https://api-votacao.appspot.com/swagger-ui.html#/
```
Desenvolvimento:
```
http://localhost:8080/swagger-ui.html#/
```
### Google Cloud Plataform - AppEngine

```
https://api-votacao.appspot.com/api/v1/
```

### Versionamento
    - Os endpoints são versionados pelo número major da versão (v1) diretamente na URL de acesso.


