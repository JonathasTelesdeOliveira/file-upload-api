# Transaction Input API 🚀

API desenvolvida em **Spring Boot** utilizando **Java 21** para o processamento e gerenciamento de arquivos de transações (CSV e Excel/XLSX), integrada com **Apache Kafka** e documentada via **Swagger/OpenAPI**.

---

## ⚙️ Configurações da Aplicação (`application.properties`)

As configurações principais da aplicação estão definidas da seguinte forma:

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

## 🛠️ Tecnologias e Ferramentas

* **Java 21**
* **Spring Boot**
* **Spring Web MVC** (Gerenciamento de rotas e requisições HTTP)
* **Spring Kafka** (Mensageria e envio de dados transacionais)
* **SpringDoc OpenAPI (Swagger UI 3.1.0)** (Documentação interativa da API)
* **Thymeleaf** (Renderização de views/modelos)
* **EasyExcel (Alibaba 3.2.1)** (Processamento eficiente de arquivos Excel)
* **OpenCSV (5.12.0)** (Leitura e manipulação de arquivos CSV)
* **Jackson Databind** (Serialização/Deserialização JSON)
* **Commons IO (2.20.0)** (Utilitários para manipulação de arquivos)
* **Lombok** (Redução de código boilerplate)
* **Docker Compose** (Orquestração de ambiente)

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

    <!-- Jackson Databind (Tools) -->
    <dependency>
        <groupId>tools.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <scope>compile</scope>
    </dependency>

    <!-- Jackson Databind (Com.fasterxml) -->
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

Abaixo encontram-se as rotas disponíveis para o upload de arquivos de transações, agrupadas sob a tag **Binance** (Gerenciamento da carteira cripto).

### 1. Upload de Arquivo CSV (`CsvController`)
* **URL:** `POST /csv/Enviar-Csv`
* **Consumes:** `multipart/form-data`
* **Descrição:** Lê e processa um arquivo CSV contendo dados de transações.

| Parâmetro | Tipo | Obrigatório | Descrição |
| :--- | :--- | :--- | :--- |
| `file` | MultipartFile | Sim | Arquivo CSV com as transações |

* **Respostas:**
  * `200 OK`: Leitura realizada com sucesso. Retorna uma lista de `TransacaoKafkaDTO`.
  * `400 Bad Request`: Erro na requisição.
  * `401 Unauthorized`: Arquivo não enviado.

---

### 2. Upload de Arquivo Excel (`ExelController`)
* **URL:** `POST /exel/Enviar-Exel`
* **Consumes:** `multipart/form-data`
* **Descrição:** Lê e processa um arquivo Excel (`.xlsx`) ou CSV por meio da biblioteca EasyExcel.

| Parâmetro | Tipo | Obrigatório | Descrição |
| :--- | :--- | :--- | :--- |
| `file` | MultipartFile | Sim | Arquivo XLSX/CSV com as transações |

* **Respostas:**
  * `200 OK`: Leitura realizada com sucesso. Retorna uma lista de `TransacaoKafkaDTO`.
  * `400 Bad Request`: Erro na requisição.
  * `401 Unauthorized`: Arquivo não enviado.

---

## 💡 Como Acessar a Documentação Interativa (Swagger UI)

Como a porta foi configurada para `8081` e o caminho do Swagger foi customizado, você pode acessar a interface de testes diretamente pelo navegador através do link:
👉 **`http://localhost:8081/swagger-ui.html`**