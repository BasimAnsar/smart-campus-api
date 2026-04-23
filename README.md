# smart-campus-api
RESTful Smart Campus API built using Java (JAX-RS, Jersey, Grizzly) to manage rooms, sensors, and sensor readings with validation and relationships.


# Smart Campus API

## 📌 Overview
This project implements a RESTful API for a Smart Campus Monitoring System.  
It manages rooms, sensors, and sensor readings with proper validation, relationships, and error handling.

---

## 🎯 Features
- CRUD operations for Rooms and Sensors
- Sensor readings management (history tracking)
- Relationship: Room → Sensors → Readings
- Input validation and error handling
- In-memory data storage (no database required)

---

## 🛠 Technologies Used
- Java
- JAX-RS (Jersey)
- Grizzly HTTP Server
- Maven
- Postman (for testing)

---

## 🧠 System Architecture

Room → Sensor → Sensor Readings

- A Room can have multiple Sensors  
- A Sensor belongs to one Room  
- A Sensor can have multiple Readings  

---

## 📡 API Endpoints

### 🔹 Rooms
| Method | Endpoint | Description |
|--------|---------|------------|
| POST | /api/v1/rooms | Create a room |
| GET | /api/v1/rooms | Get all rooms |
| GET | /api/v1/rooms/{id} | Get room by ID |
| DELETE | /api/v1/rooms/{id} | Delete room |

---

### 🔹 Sensors
| Method | Endpoint | Description |
|--------|---------|------------|
| POST | /api/v1/sensors | Create sensor |
| GET | /api/v1/sensors | Get all sensors |
| GET | /api/v1/sensors/{id} | Get sensor by ID |
| DELETE | /api/v1/sensors/{id} | Delete sensor |

---

### 🔹 Sensor Readings
| Method | Endpoint | Description |
|--------|---------|------------|
| POST | /api/v1/readings/{sensorId} | Add reading to sensor |
| GET | /api/v1/readings/{sensorId} | Get readings of a sensor |

---

## ⚠️ Validation Rules
- Room ID must be unique
- Sensor must belong to an existing room
- Sensor ID must be unique
- Room cannot be deleted if it contains sensors
- Sensor must exist before adding readings

---

## ▶️ How to Run

1. Open the project in IntelliJ
2. Run `Main.java`
3. Server starts at:http://localhost:8080/api/v1

---

## 🧪 Testing

Use Postman to test endpoints:

Example:

### Create Room
POST `/api/v1/rooms`
```json
{
  "id": "R1",
  "name": "Library",
  "capacity": 50
}
