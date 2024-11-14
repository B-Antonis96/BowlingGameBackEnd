# Bowling Game Back End

Dit project vormt de backend-infrastructuur voor een Augmented Reality Bowling Game.
## Projectstructuur

### Microservices

De API Gateway beheert de toegang en communicatie tussen de volgende microservices:

- **user-service**: Behandelt gebruikersbeheer (URI: `/api/user/**`).
- **game-service**: Beheert game en scores (URI: `/api/game/**`).
- **notification-service**: Zorgt voor het versturen van meldingen (URI: `/api/notification/**`).

De API Gateway zorgt ervoor dat de verschillende services bereikbaar zijn via uniforme routes en maakt gebruik van een beveiligingsconfiguratie.

### Databases
- **user-db**: Database met MySql.
- **game-db**: Msql database.
- **notification-db**: Database met MongoDb.

### Uitbreidingen
- **Unit tests**: Unit testing op alle service classes.
- **Keycloak**: Keycloak niet geheel kunnen implementeren, een probleem met "HTTP Header size". Niet meer de tijd gehad dit op te lossen.
- **Hosting**: Gehost via DDNS bij Bryan. Vandaar geen URL.

## API Diagram

![API Diagram](./assets/API%20Diagram.png)

## Endpoints

### User
#### POST - /api/user/login
```text
http://localhost:8086/api/user/login
```
![/api/user/login](./assets/Endpoint%20screenshots/CreateUser.png)

#### GET - /api/user/{id}
```text
http://localhost:8086/api/user/1
```
![/api/user/{id}](./assets/Endpoint%20screenshots/GetUserById.png)

#### GET - /api/user/all
```text
http://localhost:8086/api/user/all
```
![/api/user/all](./assets/Endpoint%20screenshots/GetAllUsers.png)

#### PUT - /api/user/update/{id}
```text
http://localhost:8086/api/user/update/2
```
![/api/user/update/{id}](./assets/Endpoint%20screenshots/UpdateUser.png)

#### DELETE - /api/user/delete/{id}
```text
http://localhost:8086/api/user/delete/1
```
![/api/user/delete/{id}](./assets/Endpoint%20screenshots/DeleteUser.png)

### Game
#### POST - /api/game/create/{userId}
```text
http://localhost:8086/api/game/create/2
```
![/api/game/create/{userId}](./assets/Endpoint%20screenshots/CreateGame.png)

#### GET - /api/game/{id}
```text
http://localhost:8086/api/game/1
```
![/api/game/{id}](./assets/Endpoint%20screenshots/GetGameById.png)

#### GET - /api/game/all
```text
http://localhost:8086/api/game/all
```
![/api/game/all](./assets/Endpoint%20screenshots/GetAllGames.png)

#### PUT - /api/game/update/{id}
```text
http://localhost:8086/api/game/update/1
```
![/api/game/update/{id}](./assets/Endpoint%20screenshots/UpdateGame.png)

#### DELETE - /api/game/delete/{id}
```text
http://localhost:8086/api/game/delete/1
```
![/api/game/delete/{id}](./assets/Endpoint%20screenshots/DeleteGame.png)

### Notification
#### GET - /api/notification/ping
```text
http://localhost:8086/api/notification/ping
```
![/api/notification/ping](./assets/Endpoint%20screenshots/PingNotification.png)

#### POST - /api/notification/create
```text
http://localhost:8086/api/notification/create
```
![/api/notification/create](./assets/Endpoint%20screenshots/CreateNotification.png)

#### GET - /api/notification/{id}
```text
http://localhost:8086/api/notification/{{notificationId}}
```
![/api/notification/{id}](./assets/Endpoint%20screenshots/GetNotificationById.png)

#### GET - /api/notification/all
```text
http://localhost:8086/api/notification/all
```
![/api/notification/all](./assets/Endpoint%20screenshots/GetAllNotifications.png)

#### PUT - /api/notification/update/{id}
```text
http://localhost:8086/api/notification/update/{{notificationId}}
```
![/api/notification/update/{id}](./assets/Endpoint%20screenshots/UpdateNotification.png)

#### DELETE - /api/notification/delete/{id}
```text
http://localhost:8086/api/notification/delete/{{notificationId}}
```
![/api/notification/delete/{id}](./assets/Endpoint%20screenshots/DeleteNotification.png)