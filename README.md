# restaurant
EPAM Java External course final project


Існують ролі: Клієнт, Менеджер.
Клієнт (авторизований користувач) здійснює замовлення із меню — каталогу страв, а також має можливість переглядати каталог з врахуванням сортування:
- за назвою страви;
- за вартістю;
- категорією

та робити фільтрацію списку страв за категоріями. Клієнт, в рамках одного замовлення, може замовити декілька однакових страв.
Менеджер керує замовленнями: після отримання нового замовлення, відправляє його на готування. Після приготування Менеджер передає замовлення на доставку. 
Після  доставки і отримання оплати Менеджер переводить статус замовлення у «виконано».

# Project decription

The goal of the final project is to develop a web application that supports functionality according to task.
Information on the subject is stored in a relational database (I used MySQL).
To access the data, I used JDBC API using Connection Pool.
The application architecture conforms MVC pattern.
When implementing business logic I used design patterns: Singleton (for DBManager), Chain of Responsibility (HTTP Filters).
Apache Tomcat was used as a servlet container.
