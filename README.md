# 🐄 Animal Type Classification API

The **Animal Type Classification API** is designed to manage users, animal records, and classification data using AI-powered image classification. It enables efficient tracking of livestock information, AI-driven breed/species detection, and analytics for dashboards.

---

## 🌍 Base URL
`{{BASE_URL}}`

---

## 📌 Features

- 👤 **User Management** – Create, update, retrieve, and delete users.
- 🐑 **Animal Records** – Manage animal details linked to users.
- 🤖 **AI Classification** – Classify animals and fetch structured breed details using AI.
- 📊 **Dashboard Analytics** – Get statistical insights for management.
- 🛠 **Health Monitoring** – Built-in API health check endpoint.

---

## 🔑 API Endpoints

### 1. 👤 User Service

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/user` | `POST` | Create a new user. |
| `/user/{id}` | `GET` | Get a user by ID. |
| `/user/{id}` | `PUT` | Update user information. |
| `/user/{id}` | `DELETE` | Delete a user. |
| `/user` | `GET` | List all users. |

---

### 2. 🐮 Animal Service

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/animal/{user_id}` | `POST` | Add an animal to a specific user. |
| `/animal/user/{user_id}` | `GET` | Get all animals belonging to a user. |
| `/animal/{earId}` | `GET` | Get an animal by its `earId`. |
| `/animal/{earId}` | `PUT` | Update an animal by its `earId`. |
| `/animal/{earId}` | `DELETE` | Delete an animal by its `earId`. |

---

### 3. 📷 Classification Record Service

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/animals/{animal_id}/records/classify`| `POST`| Upload an image and classify the animal. |
| `/animals/{animal_id}/records` | `GET` | Get all classification records for an animal. |
| `/animals/{animal_id}/records/{record_id}`| `GET`| Get a specific classification record for an animal. |
| `/animals/{animal_id}/records/{record_id}`| `DELETE`| Delete a specific classification record. |

---

### 4. 📑 Classification Details Service

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/records/{recordId}/details` | `GET` | Get detailed information for a classification record. |

---

### 5. 🤖 AI Service

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/ai/classify` | `GET` | Classify an animal from an uploaded image. |

---

### 6. 📊 Dashboard Service

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/dashboard/stats` | `GET` | Fetch API statistics for the dashboard. |

---

### 7. 🩺 Health Check

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/` | `GET` | Check the API's health status. |

---

## 🛠 Tech Stack

- **Backend**: Spring Boot (Java)
- **Database**: PostgreSQL (Supabase)
- **AI Integration**: Roboflow + Groq (LLMs)
- **Image Processing**: AI classification models

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL (Supabase or local DB)

### Run Locally

```bash
# Clone repository
git clone [https://github.com/your-username/animal-type-classification.git](https://github.com/your-username/animal-type-classification.git)
cd animal-type-classification

# Build the project
mvn clean install

# Run the server
mvn spring-boot:run
```
