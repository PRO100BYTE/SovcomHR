# Необходимо для запуска
* Java 11
* Docker compose
# Как запустить

Из корня проекта выполнить ряд команд

``cd backend``

``gradlew clean build``

``cd ..``

``docker compose up``

# Список сервисов

| Имя сервиса    | Роль                                                   | Порты       |
|----------------|--------------------------------------------------------|-------------|
| auth-service   | Аутентификация/регистрация/авторизация пользователей   | 8000, 50005 |
| auth-postgres  | ДБ для аутентификации                                  | 5432        |

# Дефолтные пользователи
| Email         | Пароль   | Роль   |
|---------------|----------|--------|
| user@mail.ru  | user     | USER   |
| hr@mail.ru    | hr       | HR     |
| admin@mail.ru | admin    | ADMIN  |