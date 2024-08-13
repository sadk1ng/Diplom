# **Дипломная работа по профессии "Тестировщик ПО"**

**Документация:**
* Отчет об автоматизации - https://github.com/sadk1ng/Diplom/blob/mai/Docs/Report.md
* План автоматизации тестирования - https://github.com/sadk1ng/Diplom/blob/mai/Docs/Plan.md
* Отчёт по итогам автоматизации  - https://github.com/sadk1ng/Diplom/blob/mai/Docs/Summary.md

### **Инструкция по запуску автотестов**

**_Подготовка окружения:_**
*   **Установленное ПО:** Chrome, java,Windows 10, Git, IntelliJ IDEA, JDK 11, Docker Desktop

**_Требования для запуска Веб-страницы_**
1. Склонировать проект git clone https://github.com/sadk1ng/Diplom

2. Открыть проект в IntelliJ IDEA

3. Запустить контейнеры командой docker-compose up --build 

4. Для запуска сервиса с указанием пути к базе данных использовать следующие команды:
для mysql java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar 
для postgresql java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

5. Веб страница открывается по адресу http://localhost:8080/

6. Запуск тестов стоит выполнить с параметрами, указав путь к базе данных в командной строке:
для mysql ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
для postgresql ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

7. Для формирования отчета (Allure), после выполнения тестов выполнть команду ./gradlew allureReport

8. После заврешения тестирования завершить работу приложения (CTRL+C) и остановить контейнеры командой docker-compose down