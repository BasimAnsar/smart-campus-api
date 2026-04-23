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
```
---
# 📡 API ENDPOINTS

## 🔹 Rooms
| Method | Endpoint | Description |
|--------|---------|------------|
| POST | /api/v1/rooms | Create a room |
| GET | /api/v1/rooms | Get all rooms |
| GET | /api/v1/rooms/{id} | Get room details |
| DELETE | /api/v1/rooms/{id} | Delete room |

---

## 🔹 Sensors
| Method | Endpoint | Description |
|--------|---------|------------|
| POST | /api/v1/sensors | Create sensor |
| GET | /api/v1/sensors | Get all sensors |
| GET | /api/v1/sensors/{id} | Get sensor |

---

## 🔹 Sensor Readings
| Method | Endpoint | Description |
|--------|---------|------------|
| POST | /api/v1/readings/{sensorId} | Add reading |
| GET | /api/v1/readings/{sensorId} | Get readings |

---

# 📘 COURSEWORK QUESTIONS & ANSWERS

---

## 🔹 Part 1: Service Architecture & Setup

### Question:
Explain the default lifecycle of a JAX-RS Resource class. Is a new instance created per request or treated as a singleton? How does this affect in-memory data handling?

### Answer:
JAX-RS resource classes are instantiated per request by default. This means each HTTP request gets a new instance, ensuring thread safety at the resource level.

However, this project uses a shared static in-memory datastore (`DataStore`) to store rooms, sensors, and readings. Since this data is shared across requests, multiple requests can access it concurrently, leading to potential race conditions.

In this implementation, synchronization is minimal due to the academic scope. In production, thread-safe structures such as `ConcurrentHashMap` or synchronization mechanisms would be required.

---

### Question:
Why is Hypermedia (HATEOAS) considered important in RESTful APIs?

### Answer:
HATEOAS allows APIs to include links in responses, enabling clients to navigate the API dynamically without hardcoding endpoints.

Benefits:
- Improves flexibility  
- Reduces dependency on static documentation  
- Makes APIs self-descriptive  

In this project, `/api/v1` acts as a base entry point, and the structure supports future hypermedia enhancements.

---

## 🔹 Part 2: Room Management

### Question:
What are the implications of returning only IDs versus full objects?

### Answer:
Returning only IDs:
- Reduces bandwidth usage  
- Improves performance  

Returning full objects:
- Provides complete information  
- Reduces additional API calls  

This implementation returns full objects for better usability and simplicity.

---

### Question:
Is the DELETE operation idempotent in your implementation?

### Answer:
Yes, DELETE is idempotent.

- First request deletes the room  
- Repeated requests do not change system state further  

In this system:
- If the room exists → it is deleted  
- If not → returns NOT FOUND  
- If sensors exist → deletion is blocked  

Thus, repeated DELETE requests behave consistently.

---

## 🔹 Part 3: Sensor Operations & Linking

### Question:
What happens if a client sends data in a format other than JSON when @Consumes is used?

### Answer:
The API uses `@Consumes(MediaType.APPLICATION_JSON)` to accept only JSON input.

If a client sends data in another format (e.g., XML or plain text), JAX-RS automatically returns:

HTTP 415 Unsupported Media Type

This ensures only valid data formats are processed.

---

### Question:
Why is using query parameters better for filtering than path-based filtering?

### Answer:
Using query parameters (e.g., `/api/v1/sensors?type=CO2`) is more flexible and scalable.

Advantages:
- Supports multiple filters  
- Cleaner API design  
- Easier to extend  

Path-based filtering is more rigid and less adaptable.

---

## 🔹 Part 4: Sub-Resources & Readings

### Question:
What are the benefits of using the Sub-Resource Locator pattern?

### Answer:
The Sub-Resource Locator pattern allows nested resources such as:

/sensors/{id}/readings

Benefits:
- Better separation of concerns  
- Cleaner and modular code  
- Improved maintainability  

In this project, a dedicated resource handles sensor readings.

---

### Question:
How is data consistency maintained when adding sensor readings?

### Answer:
When a new reading is added:
- It is stored in the sensor’s reading history  
- The sensor’s `currentValue` is updated  

This ensures that both historical data and the current state remain consistent.

---

## 🔹 Part 5: Error Handling & Logging

### Question:
What are the risks of exposing internal stack traces?

### Answer:
Exposing stack traces can reveal:
- Internal class names  
- File paths  
- System structure  

This information can be exploited by attackers.

To prevent this, the system uses a global exception handler to return a generic HTTP 500 response.

---

### Question:
Why is HTTP 422 more appropriate than 404 for dependency validation?

### Answer:
HTTP 422 is used when:
- The request structure is valid  
- But the data is semantically incorrect  

Example: sensor references a non-existent room

HTTP 404 indicates a missing endpoint, while 422 indicates invalid data within a valid request.

---

### Question:
Why are JAX-RS filters preferred for logging?

### Answer:
Filters allow centralized logging of all requests and responses.

Advantages:
- Cleaner code  
- No duplication  
- Automatic logging  

This is better than manually adding logging statements in each method.

---

## 🔗 GitHub Repository
https://github.com/BasimAnsar/smart-campus-api

---

## 👤 Author
Basim Ansar
