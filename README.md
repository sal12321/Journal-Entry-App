

# 📖 JournalApp – Spring Boot & MongoDB

A backend application for managing personal journal entries with full **CRUD functionality** (Create, Read, Update, Delete). Built using **Spring Boot** and **MongoDB**, this project is designed for learning backend development with clean and beginner-friendly code.

---

## 👋 About Me

<h3 align="center">Hi, I'm Aaqib 👋</h3>

<p align="center">
  🎓 Computer Engineering @ Amity University <br>
  💻 Passionate about Java backend development <br>
  🔬 Learning full-stack development by building real-world projects <br>
  📬 Contact: <a href="mailto:aaqibalam291@gmail.com">aaqibalam291@gmail.com</a> <br>
  🌐 <a href="https://www.linkedin.com/in/aaqib-alam-50929a204/">LinkedIn</a>
</p>

---

## 📂 Project Structure

The application code is located inside the [`journalApp/`](./journalApp/) directory.
Before running the project:

```bash
cd journalApp
```

---

## ✨ Features

* 📝 Create journal entries (with user association)
* 📑 View all journal entries
* 🔍 Get a journal entry by ID
* ✏️ Update existing entries
* ❌ Delete journal entries
* ⚡ REST API endpoints tested with Postman
* 🌱 MongoDB integration

---

## 🛠️ Tech Stack

<p align="center">
  <img title="Java" height="30" src="https://raw.githubusercontent.com/sal12321/images/main/aaqibAlam/images/java-original.svg">&nbsp;&nbsp;
  <img title="Spring Boot" height="35" src="https://raw.githubusercontent.com/sal12321/images/main/aaqibAlam/images/spring-boot.svg">&nbsp;&nbsp;
  <img title="MongoDB" height="34" src="https://raw.githubusercontent.com/sal12321/images/main/aaqibAlam/images/mongodb.svg">
</p>

---

## 🚀 How to Run

1. Clone the repo:

   ```bash
   git clone https://github.com/sal12321/journalApp.git
   cd journalApp
   ```

2. Start MongoDB (default: `localhost:27017`).

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

4. Access API at:

   ```
   http://localhost:8080/journal
   ```

---

## 📌 Example API Endpoints

### ➕ Create an entry

`POST /journal/{username}`
Body (JSON):

```json
{
  "title": "My First Journal",
  "content": "Today I started using JournalApp!"
}
```

---

### 📖 Get all entries

`GET /journal/{username}`

---

### 🔍 Get entry by ID

`GET /journal/id/{username}/{id}`

---

### ✏️ Update entry

`PUT /journal/id/{username}/{id}`
Body (JSON):

```json
{
  "title": "Updated Title",
  "content": "Updated content"
}
```

---

### ❌ Delete entry

`DELETE /journal/id/{username}/{id}`

---

## 🧪 Tests

* A basic Spring Boot context test is included.
* API endpoints can be tested with **Postman**.

---

## 🧠 Future Improvements

* 🔐 Add authentication & JWT security
* 🌍 Deploy with Docker
* 🖥️ Create a frontend (React/Vue)
* 🧪 Add unit & integration tests

---

<p align="center">
  Made with ❤️ by <a href="https://github.com/sal12321">Aaqib Alam</a>
</p>

---

Would you like me to also add a **Postman collection JSON export** for your API (so others can import and test your endpoints easily)?
