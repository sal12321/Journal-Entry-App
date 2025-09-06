[//]: # ()
[//]: # ()
[//]: # (# 📖 JournalApp – Spring Boot & MongoDB)

[//]: # ()
[//]: # (A backend application for managing personal journal entries with full **CRUD functionality** &#40;Create, Read, Update, Delete&#41;. Built using **Spring Boot** and **MongoDB**, this project is designed for learning backend development with clean and beginner-friendly code.)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 👋 About Me)

[//]: # ()
[//]: # (<h3 align="center">Hi, I'm Aaqib 👋</h3>)

[//]: # ()
[//]: # (<p align="center">)

[//]: # (  🎓 Computer Engineering @ Amity University <br>)

[//]: # (  💻 Passionate about Java backend development <br>)

[//]: # (  🔬 Learning full-stack development by building real-world projects <br>)

[//]: # (  📬 Contact: <a href="mailto:aaqibalam291@gmail.com">aaqibalam291@gmail.com</a> <br>)

[//]: # (  🌐 <a href="https://www.linkedin.com/in/aaqib-alam-50929a204/">LinkedIn</a>)

[//]: # (</p>)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 📂 Project Structure)

[//]: # ()
[//]: # (The application code is located inside the [`journalApp/`]&#40;./journalApp/&#41; directory.)

[//]: # (Before running the project:)

[//]: # ()
[//]: # (```bash)

[//]: # (cd journalApp)

[//]: # (```)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## ✨ Features)

[//]: # ()
[//]: # (* 📝 Create journal entries &#40;with user association&#41;)

[//]: # (* 📑 View all journal entries)

[//]: # (* 🔍 Get a journal entry by ID)

[//]: # (* ✏️ Update existing entries)

[//]: # (* ❌ Delete journal entries)

[//]: # (* ⚡ REST API endpoints tested with Postman)

[//]: # (* 🌱 MongoDB integration)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 🛠️ Tech Stack)

[//]: # ()
[//]: # (<p align="center">)

[//]: # (  <img title="Java" height="30" src="https://raw.githubusercontent.com/sal12321/images/main/aaqibAlam/images/java-original.svg">&nbsp;&nbsp;)

[//]: # (  <img title="Spring Boot" height="35" src="https://raw.githubusercontent.com/sal12321/images/main/aaqibAlam/images/spring-boot.svg">&nbsp;&nbsp;)

[//]: # (  <img title="MongoDB" height="34" src="https://raw.githubusercontent.com/sal12321/images/main/aaqibAlam/images/mongodb.svg">)

[//]: # (</p>)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 🚀 How to Run)

[//]: # ()
[//]: # (1. Clone the repo:)

[//]: # ()
[//]: # (   ```bash)

[//]: # (   git clone https://github.com/sal12321/journalApp.git)

[//]: # (   cd journalApp)

[//]: # (   ```)

[//]: # ()
[//]: # (2. Start MongoDB &#40;default: `localhost:27017`&#41;.)

[//]: # ()
[//]: # (3. Run the application:)

[//]: # ()
[//]: # (   ```bash)

[//]: # (   mvn spring-boot:run)

[//]: # (   ```)

[//]: # ()
[//]: # (4. Access API at:)

[//]: # ()
[//]: # (   ```)

[//]: # (   http://localhost:8080/journal)

[//]: # (   ```)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 📌 Example API Endpoints)

[//]: # ()
[//]: # (### ➕ Create an entry)

[//]: # ()
[//]: # (`POST /journal/{username}`)

[//]: # (Body &#40;JSON&#41;:)

[//]: # ()
[//]: # (```json)

[//]: # ({)

[//]: # (  "title": "My First Journal",)

[//]: # (  "content": "Today I started using JournalApp!")

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (### 📖 Get all entries)

[//]: # ()
[//]: # (`GET /journal/{username}`)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (### 🔍 Get entry by ID)

[//]: # ()
[//]: # (`GET /journal/id/{username}/{id}`)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (### ✏️ Update entry)

[//]: # ()
[//]: # (`PUT /journal/id/{username}/{id}`)

[//]: # (Body &#40;JSON&#41;:)

[//]: # ()
[//]: # (```json)

[//]: # ({)

[//]: # (  "title": "Updated Title",)

[//]: # (  "content": "Updated content")

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (### ❌ Delete entry)

[//]: # ()
[//]: # (`DELETE /journal/id/{username}/{id}`)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 🧪 Tests)

[//]: # ()
[//]: # (* A basic Spring Boot context test is included.)

[//]: # (* API endpoints can be tested with **Postman**.)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 🧠 Future Improvements)

[//]: # ()
[//]: # (* 🔐 Add authentication & JWT security)

[//]: # (* 🌍 Deploy with Docker)

[//]: # (* 🖥️ Create a frontend &#40;React/Vue&#41;)

[//]: # (* 🧪 Add unit & integration tests)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (<p align="center">)

[//]: # (  Made with ❤️ by <a href="https://github.com/sal12321">Aaqib Alam</a>)

[//]: # (</p>)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (Would you like me to also add a **Postman collection JSON export** for your API &#40;so others can import and test your endpoints easily&#41;?)

---

# 📖 JournalApp – Secure Spring Boot & MongoDB Application

A **secure backend journal management system** built with **Spring Boot 3**, **Spring Security 6**, and **MongoDB**.
The project demonstrates modern **authentication/authorization**, **hashed password storage**, and complete **CRUD APIs** for managing personal journal entries.

---

## 👨‍💻 About Me

<h3 align="center">Hi, I'm Aaqib 👋</h3>

<p align="center">
  🎓 Computer Engineering @ Amity University <br>
  💻 Passionate about Java backend development <br>  
  🔬 Exploring Spring Boot, MongoDB, and Security <br>  
  📬 Contact: <a href="mailto:aaqibalam291@gmail.com">aaqibalam291@gmail.com</a> <br>  
  🌐 <a href="https://www.linkedin.com/in/aaqib-alam-50929a204/">LinkedIn</a>  
</p>  

---

## ✨ Features

* 📝 **User Management**

   * Create new users via `/public/create-user`
   * Passwords stored in **BCrypt hashed format**
* 🔐 **Authentication & Security**

   * Integrated with **Spring Security 6**
   * `/public/**` → accessible without login
   * `/journal/**` & `/user/**` → require authentication (Basic Auth)
* 📓 **Journal Management**

   * Create, view, update, delete journal entries per user
   * Each journal entry stored with **timestamp** and linked to a user
* 🗄 **MongoDB Integration**

   * User and journal data stored in MongoDB collections
* ⚡ REST API tested with Postman

---

## 🛠️ Tech Stack

<p align="center">  
  <img title="Java" height="30" src="https://raw.githubusercontent.com/sal12321/images/main/aaqibAlam/images/java-original.svg">&nbsp;&nbsp;  
  <img title="Spring Boot" height="35" src="https://raw.githubusercontent.com/sal12321/images/main/aaqibAlam/images/spring-boot.svg">&nbsp;&nbsp;  
  <img title="Spring Security" height="32" src="https://raw.githubusercontent.com/sal12321/images/main/aaqibAlam/images/spring-security.svg">&nbsp;&nbsp;  
  <img title="MongoDB" height="34" src="https://raw.githubusercontent.com/sal12321/images/main/aaqibAlam/images/mongodb.svg">  
</p>  

---

## 📂 Project Structure

```
journalApp/
│── src/main/java/com/salAce/journalApp/
│   ├── config/              # Spring Security configuration
│   ├── controller/          # REST controllers
│   ├── entity/              # MongoDB entities (User, JournalEntry)
│   ├── repo/                # Spring Data MongoDB repositories
│   ├── service/             # Business logic services
│── src/main/resources/
│   ├── application.properties  # DB & server configs
│── pom.xml                  # Maven dependencies
```

---

## 🚀 How to Run

1. Clone the repo:

   ```bash
   git clone https://github.com/sal12321/journalApp.git
   cd journalApp
   ```

2. Start MongoDB (default: `localhost:27017`)

3. Run the Spring Boot app:

   ```bash
   mvn spring-boot:run
   ```

4. API available at:

   ```
   http://localhost:8080
   ```

---

## 📌 API Endpoints

### 🔑 User APIs

#### ➕ Create User (No Auth Required)

`POST /public/create-user`

```json
{
  "userName": "aaqib",
  "password": "mypassword"
}
```

✅ Password automatically hashed before saving.

---

### 📓 Journal APIs (Require Basic Auth)

#### ➕ Create Journal Entry

`POST /journal/{username}`

```json
{
  "title": "My First Journal",
  "content": "Started using JournalApp today!"
}
```

#### 📖 Get All Entries of User

`GET /journal/{username}`

#### 🔍 Get Entry by ID

`GET /journal/id/{id}`

#### ✏️ Update Entry

`PUT /journal/id/{username}/{id}`

```json
{
  "title": "Updated Title",
  "content": "Updated content"
}
```

#### ❌ Delete Entry

`DELETE /journal/id/{username}/{id}`

---

## 🔐 Security Flow

* **Spring Security 6** with `SecurityFilterChain` (no deprecated `WebSecurityConfigurerAdapter`).
* `/public/**` → accessible without authentication.
* All other endpoints require **Basic Auth** with valid username & password.
* Passwords stored in **BCrypt format** (never plain text).
* Authentication handled by **UserDetailsService** implementation.

---

## 🧪 Testing

* Use **Postman** for API testing.
* Add credentials in **Basic Auth** tab when hitting `/journal/**`.
* First request after user creation: login with plain password.
* Verified entries in MongoDB stored with **hashed password**.

---

## 🧠 Future Improvements

* 🔑 Replace Basic Auth with **JWT Authentication**.
* 🌍 Dockerize Spring Boot + MongoDB setup.
* 🖥 Build frontend with **React**.
* 🧪 Add unit & integration tests.
* 📊 Add role-based access (Admin/User).

---

## 📝 Commit Update

```
git commit -m "SpringSecurity 6 added, /public/** has no encryption and /journal/** , /user/** and rest are encrypted. Passwords are stored in hashed format."
```

---

<p align="center">  
  Made with ❤️ by <a href="https://github.com/sal12321">Aaqib Alam</a>  
</p>  

---


