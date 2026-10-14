# Lendo e Eviando via Kafka, Trades Spot da Binacne em Exel, xlsx ou CSV 🚀

API desenvolvida em **Spring Boot** utilizando **Java 21** focada na **ingestão e recepção de arquivos de transações de Trade Spot(`CSV` e `Excel`/`.xlsx`)** da **Binance**. O objetivo principal deste microsserviço é validar, processar e repassar os dados de transações para o microsserviço/API de **Upload BRL** utilizando **Apache Kafka** para mensageria assíncrona.

---

## ⚙️ Configurações da Aplicação (`application.properties`)

```properties
spring.application.name=transaction_input_api
server.port=8081

spring.docker.compose.enabled=false

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs

spring.kafka.bootstrap-servers=localhost:9092
logging.level.org.springframework.kafka=DEBUG
```

---

## 🛠️ Arquitetura e Fluxo de Dados

1. **Recepção:** O cliente faz o envio (*upload*) do arquivo `.csv` ou `.xlsx` através dos endpoints REST.
2. **Processamento Inicial:** A API realiza o parse do arquivo recebido (via **OpenCSV** ou **EasyExcel**).
3. **Publicação/Repasse:** As transações convertidas são publicadas no tópico do **Apache Kafka** para serem consumidas e processadas pela **API Upload BRL**.
4. **Resposta:** O cliente recebe uma resposta rápida de confirmação de recebimento (`200 OK`), desacoplando a leitura do processamento final de dados.

---

## 🛠️ Tecnologias e Ferramentas

* **Java 21**
* **Spring Boot**
* **Spring Web MVC** (Rotas e recepção de requisições multipart/form-data)
* **Spring Kafka** (Publicação das transações para a API Upload BRL)
* **SpringDoc OpenAPI (Swagger UI 3.1.0)** (Documentação interativa da API)
* **Alibaba EasyExcel (3.2.1)** (Leitura e processamento eficiente de arquivos Excel)
* **OpenCSV (5.12.0)** (Leitura e conversão de arquivos CSV)
* **Jackson Databind** (Serialização e manipulação JSON/Payloads)
* **Commons IO (2.20.0)** (Manipulação e suporte a arquivos)
* **Lombok** (Produtividade e redução de código boilerplate)
* **Docker Compose** (Gerenciamento do ambiente local)

---

## 📦 Dependências do Projeto (`pom.xml`)

```xml
<properties>
    <java.version>21</java.version>
</properties>

<dependencies>
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Spring Web MVC -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc</artifactId>
    </dependency>

    <!-- Spring Boot DevTools -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- SpringDoc OpenAPI (Swagger) -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>3.1.0</version>
    </dependency>

    <!-- Spring Boot Docker Compose -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-docker-compose</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- Thymeleaf -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <!-- Spring Kafka -->
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
    </dependency>

    <!-- OpenCSV -->
    <dependency>
        <groupId>com.opencsv</groupId>
        <artifactId>opencsv</artifactId>
        <version>5.12.0</version>
        <scope>compile</scope>
    </dependency>

    <!-- Jackson Databind -->
    <dependency>
        <groupId>tools.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <scope>compile</scope>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <scope>compile</scope>
    </dependency>

    <!-- Commons IO -->
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.20.0</version>
        <scope>compile</scope>
    </dependency>

    <!-- Alibaba EasyExcel -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>easyexcel</artifactId>
        <version>3.2.1</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

---

## 📄 Endpoints da API

Endpoints para recepção de arquivos de transações da carteira cripto (**Binance**).

### 1. Upload e Ingestão de Arquivo CSV (`CsvController`)

* **URL:** `POST /csv/Enviar-Csv`
* **Content-Type:** `multipart/form-data`
* **Descrição:** Recebe o arquivo CSV contendo dados de transações e dispara os registros para a API Upload BRL através do Kafka.

| Parâmetro | Tipo | Obrigatório | Descrição |
| :--- | :--- | :--- | :--- |
| `file` | `MultipartFile` | Sim | Arquivo CSV com as transações da Binance |

* **Respostas:**
  * `200 OK`: Arquivo recebido e encaminhado para processamento com sucesso.
  * `400 Bad Request`: Formato de requisição inválido ou parâmetro ausente.
  * `401 Unauthorized`: Arquivo não enviado ou sem autorização.

---

### 2. Upload e Ingestão de Arquivo Excel (`ExelController`)

* **URL:** `POST /exel/Enviar-Exel`
* **Content-Type:** `multipart/form-data`
* **Descrição:** Recebe o arquivo Excel (`.xlsx`) com dados de transações, converte e publica as mensagens no Kafka para a API Upload BRL.

| Parâmetro | Tipo | Obrigatório | Descrição |
| :--- | :--- | :--- | :--- |
| `file` | `MultipartFile` | Sim | Arquivo XLSX com as transações da Binance |

* **Respostas:**
  * `200 OK`: Arquivo recebido e encaminhado para processamento com sucesso.
  * `400 Bad Request`: Formato de requisição inválido ou parâmetro ausente.
  * `401 Unauthorized`: Arquivo não enviado ou sem autorização.

---

## 💡 Como Acessar a Documentação Interativa (Swagger UI)

Acesse a interface interativa da API no navegador para testar os envios:

👉 **`http://localhost:8081/swagger-ui.html`**