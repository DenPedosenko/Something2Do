# Something 2 Do website
## Start page

```http request
http://localhost:8080/login
```
No authentication required, so you can use any name and password to login.
Or use one of the predefined users:
- user1
- user2

with _any password_;

## Main page

```http request
http://localhost:8080/main
```

Contains the task for a day for current user and a list of all tasks for this user. You can add more tasks to the list by clicking on the button "Retrieve task".
Also contains navigation bar with links to the main page, user wishlist page, completed page and drop down with user settings and logout.

## User wishlist page

```http request
http://localhost:8080/wishlist
```

Contains the list of tasks that user marked as wished. You can complete task or remove .

## User completed tasks page

```http request
http://localhost:8080/completed
```

Contains the list of tasks that user marked as completed.




# Something 2 Do API Documentation

## Users

### Create User

Create a new user.

- **Endpoint:**
  ```bash
  POST http://localhost:8080/api/v1/users/create?userName=user3
    ```
- **Request Type:** `application/x-www-form-urlencoded`

---

### Get All Users

Get a list of all users.

- **Endpoint:**

```bash
 GET http://localhost:8080/api/v1/users
```

---

## User Settings

### Toggle Rating Mode

Toggle the rating mode for a user.

- **Endpoint:**

```bash
 POST http://localhost:8080/api/v1/{{userId}}/ratingMode?isRatingEnabled=true
```

- **Request Type:** `application/x-www-form-urlencoded`
- **Path Variables:**
- `userId`: The unique identifier of the user.

---

# Tasks

## Wishlist

### Get User's Wishlist

Get the wishlist of a specific user.

- **Endpoint:**

```bash
 GET http://localhost:8080/api/v1/{{userId}}/wishlist
```

- **Path Variables:**
    - `userName`: The name of the user.

- **Example Response File:**

```json
[
  {
    "activity": "Play Chess",
    "accessibility": 1.8,
    "type": "relaxation",
    "participants": 2,
    "price": 0.0,
    "link": "",
    "key": "779"
  }
]
```

---

## Completed Tasks

### Get User's Completed Tasks

Get the completed tasks of a specific user.

- **Endpoint:**

```bash
 GET http://localhost:8080/api/v1/{{userId}}/completedTasks
```

- **Path Variables:**
- `userName`: The name of the user.

- **Example Response File:**

```json
[
  {
    "activity": "Play Chess",
    "accessibility": 1.8,
    "type": "relaxation",
    "participants": 2,
    "price": 0.0,
    "link": "",
    "key": "779"
  }
]
```

---

## Task Operations

### Fetch Task

Fetch a task for a user based on the specified type.

- **Endpoint:**

```bash
POST http://localhost:8080/api/v1/tasks/fetch
```

- **Request Type:** `application/json`
- **Request Body:**

```json
{
  "userName": "user1",
  "taskType": "education"
}
```

## Complete Task

### Mark a task as completed for a user.

- **Endpoint**:

```bash
POST http://localhost:8080/api/v1/tasks/complete
```

- **Request Type**: `application/json`
- **Request Body**:

```json
{
  "userName": "user1",
  "taskId": "3136036"
}
```

---

## Mark Task as Wished

### Mark a task as wished for a user.

- **Endpoint**:

```bash
POST http://localhost:8080/api/v1/tasks/wish
```

- **Request Type**: `application/json`
- **Request Body**:

```json
{
  "userName": "user1",
  "taskId": "3136036"
}
```

---

## Get users task of the day

### Get a task of the day for a user.

- **Endpoint**:

```bash
GET http://localhost:8080/api/v1/tasks/{{userName}}/taskOfTheDay
```

- **Path Variables:**
    - `userName`: The name of the user.

---
