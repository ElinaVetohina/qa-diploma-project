# Дипломный проект по профессии «Тестировщик»

## Документы:
* [План автоматизации тестирования.](./documents/plan.md)
* [Отчет по итогам тестирования](./documents/report.md)
* [Отчет по итогам автоматизации](./documents/summary.md)

## Описание приложения


### Бизнес-часть

Приложение — это веб-сервис, который предлагает купить тур по определённой цене двумя способами:
1. Обычная оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.

![](./documents/pic/service.png)

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей, далее Payment Gate;
* кредитному сервису, далее Credit Gate.

Приложение в собственной СУБД должно сохранять информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом сохранять не допускается.

### СУБД

Заявлена поддержка двух СУБД.:

* MySQL;
* PostgreSQL

Учётные данные и URL для подключения задаются в файле [application.properties](application.properties).

### Банковские сервисы

Доступ к реальным банковским сервисам не даётся, поэтому разработчики подготовили для вас симулятор банковских сервисов, который может принимать запросы в нужном формате и генерировать ответы.

Симулятор написан на Node.js, поэтому для запуска вам нужен либо Docker, либо установленный Node.js. Симулятор расположен в каталоге [gate-simulator](gate-simulator). Для запуска нужно перейти в этот каталог.

Симулятор запускается командой `npm start` на порту 9999. Он позволяет генерировать предопределённые ответы для заданного набора карт. Набор карт представлен в формате JSON в файле [`data.json`](gate-simulator/data.json).

Разработчики сделали один сервис, симулирующий и Payment Gate, и Credit Gate.

## Запуск приложения

* Клонировать репозиторий на локальную машину командой `git clone`
* Открыть проект в Intellij IDEA
* для запуска контейнеров с MySql, PostgreSQL и Node.js запустить docker-compose.yml, использовав команду `docker-compose up`, в случае ошибок используем `docker-compose up -d --force-recreat`
* Запуск под MySQL
  `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
* Запуск под PostgreSQL `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
##  Запуск тестов

* Запуск под MySQL
  `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`
* Запуск под PostgreSQL
  `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`
##  Allure отчёты

* Генерация отчета запускается командой `./gradlew allureReport`
* Открыть отчет в браузере `./gradlew allureServe`

## Завершение
* После завершения тестов останавливаем контейнер командой `docker-compose down`
* Останавливаем сервис сочетанием клавиш `CTRL+C` в терминале.