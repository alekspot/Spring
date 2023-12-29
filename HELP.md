# Spring
## Настройка проекта

---
Установить:

* Lombok
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#web)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#appendix.configuration-metadata.annotation-processor)
* Spring Data JPA
* PostgreSQL Driver
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#web.security)

в файле **application.properties** прописать настройки для подключения к БД:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=test
spring.datasource.password=test

spring.jpa.properties.hibernate.default_schema=app
```

## Добавление SSL в проект

- создаем хранилище ключей
 - создаем тестовый центр сертификации, который будет заверять сертификаты (rootca.cer)
 - выпускаем сертификат подписанный через rootca (app-ssl.cer)
 - добавляем сертификаты в OC
 - выпускаем ключи для приложения через app-ssl.cer (app_ssl.pfx)
 - переносим ключи для приложения в resources/ssl
 - в файле **application.properties** прописать настройки для SSL
```
server.ssl.enabled=true
server.ssl.key-store=classpath:ssl/app_ssl.pfx
server.ssl.key-store-type=PKCS12
server.ssl.key-store-password=test
```

## Маппинг БД

- панель Persistence -> Assign Data Sources (выбираем бд, схему)
- Generete Persistence Mapping -> by databse schema

---

## Структура проекта

---
### controller

Логика по обработке http запростов, обращение к сервисам

```java
@RestController
@RequestMapping("event")


@GetMapping("/{id}")

@PathVariable("id")
@RequestBody
```

---
### repo

Интерфейсы репозиториев.

Определяются дополнительные методы поиска сущностей
```java
@Repository
@Query("SELECT e FROM Event")
@Param("title")
```