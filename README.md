Simple-shop-api  - rest-api по ТЗ

Замечания/допущения
1) Заказ. Дата заказа нигде не выодится и не используется. Дупускаю хранить ее в unix_timestamp.
2) Заказ. Статус заказа нигде не изменяется. Заказ создается со статусом NEW (новый).
3) Для того,  чтобы собрать проект 
	mvn clean install

4) Main class: ru.checkout.shop.api.server.ShopApiServer
   Program arguments: --app=simple-shop-api

5) Конфигурация по-умолчанию для БД:
db.driver=com.mysql.jdbc.Driver
db.jdbcurl=jdbc:mysql://127.0.0.1:3306/checkout_shop?useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull
db.username=checkout
db.password=checkout

Ее иожно переопределить создав в рабочей директории simple-shop-api.properties и в нем новую конфигурацию.
Для создания базовых настроек в mysql приложен скрпит init.sql

6) Также можно переопределить порт и хост, по-умолчанию это
shop.server.bind.host=localhost
shop.server.bind.port=5555

   
7) Стек технологий:
 - Java 8
 - Сборка проекта Apache Maven 3.3.9
 - Контейнер сервлетов jetty 9.3.11.v20160721
 - Spring Framework 4.2.3.RELEASE
 - Hibernate 4.3.5.Final
 - Маршалер для серелизации json - Jackson 2.4.1
 - Использовал Apache Commons Guava
 - Логирование log4j
 - БД mysql 5.7.13

8) Тестовое заполнение. При старте приложения каждый раз пересоздаются все таблицы с тестовыми данными (пользователи с различным начальным капиталом и продукты с разной ценовой категорией). 
resources/import.sql

9) Описание апи.

 - Выводить подробную информацию о зказе по его номеру 
Формат: shop/api/order/{uid}   GET
	uid - номер заказа; 
Пример:
Запрос: curl -H "Content-Type: application/json" http://localhost:5555/shop/api/order/cd05bef6-364b-467d-b5ee-877b52c5fd40 
Результат: 
{
	"id":1,
	"uid":"cd05bef6-364b-467d-b5ee-877b52c5fd40",
	"timestamp":1472843824,
	"status":"NEW",
	"productsList":[
		{
			"id":1,
			"vendorCode":"house",
			"name":"Дом",
			"price":1000000.0
		},{
			"id":1,
			"vendorCode":"house",
			"name":"Дом",
			"price":1000000.0
		},{
			"id":1,
			"vendorCode":"house",
			"name":"Дом",
			"price":1000000.0
		},{
			"id":1,
			"vendorCode":"house",
			"name":"Дом",
			"price":1000000.0
		},{
			"id":1,
			"vendorCode":"house",
			"name":"Дом",
			"price":1000000.0
		}
	]
}


 - Создание заказа. 
Формат: shop/api/create/1/{userId}  POST  (json: [<список идентфикаторов продуктов>])
Запрос: curl -H "Content-Type: application/json" -X POST -d '[1,2,3,4,5]' http://localhost:5555/shop/api/create/1/
Результат:
{
	"id":2,
	"uid":"7897b0f6-4119-4278-be02-49feed692f8b",
	"timestamp":1472844576,
	"status":"NEW",
	"productsList":[
		{
			"id":1,
			"vendorCode":"house",
			"name":"Дом",
			"price":1000000.0
		},{
			"id":2,
			"vendorCode":"car",
			"name":"Автомобиль",
			"price":500000.0
		},{
			"id":3,
			"vendorCode":"bike",
			"name":"Мотоцикл",
			"price":300000.0
		},{
			"id":4,
			"vendorCode":"pit-bike",
			"name":"Скутер",
			"price":100000.0
		},{
			"id":5,
			"vendorCode":"mini-bike",
			"name":"Велосипед",
			"price":50000.0
		}
	]
}

10) Анализ. Первый метод не требует особого появнения, работает только на чтение - проблем в работе в многопоточной среде не предвидется.
Второй метод поинтереснее. Здесь необходимо учесть следующее:
	- Создание заказа состоит из двух операций 1) изменить сосояние счета пользователя 2) записать заказ в БД. 
	  Здесь важно организовать транзакцию, если вдруг что-то пошло не так - все операции должны откатиться. 
        - В многопоточной среде не должно возникнуть такой ситуации, когда с одним и тем же пользователем работают больше одного потока (это грозит неправильным списыванием со счета денег).
	  Таким образом нужно организовать блокировку для работы с пользователем - это реализованом с помощью LockModeType.PESSIMISTIC_READ которая блокирует пользователя до коммита.
	- Есть еще проблема которая возникнет в многопоточной среде при одновременной попытке испоьзования ресурсов, и это MySQLTransactionRollbackException - дедлок, который обычно лечится
	  повторением неудочных транзакций (все реализовано в коде createOrderSafety()

11) Exceptions. В приложении реализованы внештатные ситуации: Нет пользователя, нет продукта, нет заказа и у пользователя нет денег. Во всех этих случаях выбрасывается исключение и отверт возвращается с ошибкой.

Вроде все...

Могу полностью покрыть функционал тестами.. Пака то, что успел... Если нужно что-то дроработать, прошу вернуть задачу обратно))))
