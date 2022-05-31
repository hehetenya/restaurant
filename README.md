# Restaurant
This project was created for EPAM Java external course final project

## There are two user roles:
- **Client** can view a menu with the ability to sort (by name, category and price) and filter (by category) dishes. Clients also can make orsers and view a list of their orders.
- **Manager** can view all orders and update their status.

## Project decription
> The goal of the final project is to develop a WEB application that supports functionality according to task.
Information on the subject is stored in a relational database (MySQL).
JDBC API was used to access the data.
The application architecture conforms MVC pattern.
When implementing business logic were used design patterns: Singleton (for DBManager), Chain of Responsibility (HTTP Filters).
Apache Tomcat was used as a servlet container.

## Database schema
![DBSchema](https://cdn.discordapp.com/attachments/873196736997376063/981141686480674826/schema.png)

## UI for clients
![logIn](https://cdn.discordapp.com/attachments/873196736997376063/981134646362390558/login.png)
![userOrders](https://cdn.discordapp.com/attachments/873196736997376063/981134331965755434/userOrders.png)
## UI for manager
![managerOrders](https://cdn.discordapp.com/attachments/873196736997376063/981134646584676412/managerOrders.png)
