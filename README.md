# Scalable URL Shortener

A production-oriented URL shortening service built with **Spring Boot, PostgreSQL, JWT authentication, and Apache Kafka**, designed with scalability, reliability, and asynchronous analytics in mind.

The system separates the latency-sensitive URL redirection path from analytics processing, allowing click events to be handled asynchronously without blocking redirects.

---

## Overview

Traditional URL shorteners appear simple at first, but a production-ready implementation needs to consider:

* Fast URL redirection
* Unique short URL generation
* Authentication and authorization
* Duplicate URL handling
* Rate limiting
* Persistent storage
* Click analytics
* Horizontal scalability
* Failure isolation
* Asynchronous event processing

This project explores these concerns through a modular backend architecture.

---

## Architecture

```text
                    ┌──────────────────┐
                    │      Client      │
                    └────────┬─────────┘
                             │
                             ▼
                  ┌─────────────────────┐
                  │   Spring Boot API   │
                  │                     │
                  │  Authentication     │
                  │  URL Management     │
                  │  Rate Limiting      │
                  └───────┬───────┬─────┘
                          │       │
              ┌───────────┘       └──────────────┐
              ▼                                  ▼
      ┌────────────────┐                ┌────────────────┐
      │   PostgreSQL   │                │ Apache Kafka   │
      │                │                │                │
      │ URL mappings   │                │ Click events   │
      │ User data      │                │                │
      │ Analytics data │                └───────┬────────┘
      └────────────────┘                        │
                                               ▼
                                     ┌────────────────────┐
                                     │ Analytics Consumer │
                                     └─────────┬──────────┘
                                               │
                                               ▼
                                      ┌──────────────────┐
                                      │ Analytics Store  │
                                      └──────────────────┘

                                               │
                                               ▼
                                      ┌──────────────────┐
                                      │ React Dashboard  │
                                      └──────────────────┘
```

### Request Flow

When a user accesses a shortened URL:

```text
Short URL
   │
   ▼
Spring Boot API
   │
   ├── Validate short code
   │
   ├── Retrieve original URL
   │
   ├── Record click event
   │
   └── Redirect immediately
```

Analytics processing is handled asynchronously through Kafka rather than making the redirect request wait for analytics processing.

---

## Key Features

### 🔗 URL Shortening

Convert long URLs into compact, unique short URLs.

```text
https://example.com/very/long/path
                    ↓
              /aB72xK
```

The generated short code is associated with the original URL and persisted in PostgreSQL.

---

### 🔐 JWT Authentication

Protected operations use **JWT-based authentication**.

Authenticated users can manage their shortened URLs and access associated analytics.

The authentication layer separates public redirection from protected URL-management operations.

---

### ⚡ Fast Redirection

The redirect path is designed to perform only the operations necessary to resolve a short URL.

Analytics processing is moved away from the critical request path using an event-driven architecture.

This prevents expensive analytics operations from unnecessarily increasing redirect latency.

---

### 📊 Event-Driven Analytics

Each URL visit can produce an analytics event.

```text
User clicks short URL
        │
        ▼
Redirect request
        │
        ├──────────────► Redirect user
        │
        ▼
   Kafka Event
        │
        ▼
Analytics Consumer
        │
        ▼
Persist / Aggregate
        │
        ▼
Analytics Dashboard
```

Using Kafka allows analytics processing to happen independently from URL redirection.

This also provides a foundation for scaling analytics consumers independently as traffic increases.


### 🔎 URL Validation

The service validates URL operations and handles cases such as:

* Invalid URLs
* Duplicate URLs where applicable
* Non-existent short URLs
* Invalid authentication
* Unauthorized resource access

The API returns appropriate HTTP status codes instead of treating every failure as a generic server error.

---

### 📈 Analytics Dashboard

A React-based dashboard provides visibility into URL activity.

Depending on the available analytics data, this can include:

* Total clicks
* Clicks per URL
* Recent activity
* Time-based click trends
* URL performance

The dashboard consumes backend APIs rather than accessing the database directly.

---

# Technology Stack

| Layer            | Technology        |
| ---------------- | ----------------- |
| Backend          | Java, Spring Boot |
| Authentication   | JWT               |
| Database         | PostgreSQL        |
| Messaging        | Apache Kafka      |
| Frontend         | React             |
| API              | REST              |
| Build Tool       | Maven / Gradle    |
| Containerization | Docker            |
| Version Control  | Git               |

Only retain technologies in this table that are actually present in the repository.

---

# API Design

Example API structure:

### Authentication

```http
POST /api/auth/register
POST /api/auth/login
```

### URL Management

```http
POST   /api/urls
GET    /api/urls
GET    /api/urls/{id}
DELETE /api/urls/{id}
```

### Redirection

```http
GET /{shortCode}
```

### Analytics

```http
GET /api/urls/{id}/analytics
```

> Update these endpoints to match the actual controller mappings in the project.

---

# Database Model

A simplified representation:

```text
User
 │
 │ 1:N
 ▼
URL
 │
 │ 1:N
 ▼
Click / Analytics Event
```

The URL entity maintains the mapping between the generated short code and the original URL.

Analytics data is separated conceptually from the core URL mapping so that analytics growth does not unnecessarily complicate the redirect path.

---

# Scalability

The backend is designed with **horizontal scalability** in mind.

Multiple instances of the Spring Boot service can run behind a load balancer:

```text
                  Load Balancer
                 /      |      \
                ▼       ▼       ▼
             API-1    API-2    API-3
                \       |       /
                 \      |      /
                    PostgreSQL
```

Analytics processing can similarly be scaled using multiple Kafka consumers.

```text
                 Kafka Topic
                /     |     \
               ▼      ▼      ▼
          Consumer  Consumer  Consumer
              1        2        3
```

This allows the system to scale different workloads independently.

---

# Why Kafka?

Analytics does not need to block the user's redirect request.

Without asynchronous processing:

```text
Request
  ↓
Database
  ↓
Analytics processing
  ↓
Response
```

With event-driven processing:

```text
Request
  ↓
Resolve URL
  ↓
Redirect
  │
  └──► Kafka ──► Analytics Consumer
```

This reduces coupling between the latency-sensitive redirect path and analytics processing.

It also provides a foundation for handling analytics workloads independently from the core URL-shortening service.

---

# Performance

The architecture was designed to minimize work performed during URL redirection by moving analytics processing to an asynchronou
