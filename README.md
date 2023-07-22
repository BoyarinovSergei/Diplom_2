В данном проекте написаны api автотесты на сервис яндекса - Stellar Burgers..

Используются библиотеки:

1. junit4 - для тестов
2. rest-assured - фреймворк для тестирования api
3. io.qameta.allure - отчеты
4. gson - для сериализации/десериализации
5. lombok - для добавления setters/getters и constructors
6. org.aspectj - слушалка для отчетов

Для генерации allure-report можно использовать
allure serve target/allure-results --host localhost --port 9999
