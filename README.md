# 🏥 Vitalis Backend - Sistema de Gestión de Turnos Médicos

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Tabla de Contenidos

- [Descripción General](#-descripción-general)
- [Características Principales](#-características-principales)
- [Arquitectura del Proyecto](#-arquitectura-del-proyecto)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Modelo de Datos](#-modelo-de-datos)
- [Configuración e Instalación](#-configuración-e-instalación)
- [Variables de Entorno](#-variables-de-entorno)
- [Endpoints de la API](#-endpoints-de-la-api)
- [Seguridad y Autenticación](#-seguridad-y-autenticación)
- [Funcionalidades Especiales](#-funcionalidades-especiales)
- [Deployment](#-deployment)
- [Testing](#-testing)
- [Troubleshooting](#-troubleshooting)
- [Contribución](#-contribución)

---

## 🎯 Descripción General

**Vitalis** es un sistema integral de gestión de turnos médicos desarrollado con Spring Boot. La aplicación permite a los usuarios registrarse, buscar profesionales de la salud, reservar turnos médicos, gestionar sus citas y evaluar la experiencia con la aplicación.

### ¿Qué resuelve Vitalis?

- ✅ **Gestión de turnos médicos** de manera digital y eficiente
- ✅ **Búsqueda de profesionales** por especialidad
- ✅ **Sistema de favoritos** para médicos preferidos
- ✅ **Notificaciones** automáticas de recordatorios de turnos
- ✅ **Autenticación segura** con JWT
- ✅ **Recuperación de contraseña** mediante códigos de verificación por email
- ✅ **Gestión de perfil** con foto de perfil personalizable
- ✅ **Sistema de calificaciones** de la aplicación

---

## ✨ Características Principales

### 🔐 Autenticación y Seguridad
- Sistema de registro con verificación de email mediante códigos de 6 dígitos
- Login con JWT (JSON Web Tokens)
- Recuperación de contraseña con códigos temporales enviados por email
- Cambio de contraseña desde el perfil
- Eliminación de cuenta con código de confirmación
- Encriptación de contraseñas con BCrypt

### 👤 Gestión de Usuarios
- Registro de usuarios con datos personales
- Actualización de perfil (nombre, email, teléfono, DNI, obra social, nº afiliado)
- Carga y actualización de foto de perfil (almacenada en base64 en BD)
- Imagen de perfil por defecto para nuevos usuarios
- Validación de obra social y nº afiliado para reservar turnos

### 👨‍⚕️ Gestión de Profesionales
- Listado completo de profesionales
- Búsqueda por especialidad (20+ especialidades médicas)
- Top profesionales más favoriteados
- Imágenes de profesionales almacenadas en base de datos

### 📅 Sistema de Turnos
- Reserva de turnos médicos
- Listado de turnos por usuario
- Estados de turnos: pendiente, completado, cancelado
- Validación de disponibilidad horaria
- Actualización automática de turnos vencidos
- Slots de tiempo (TimeSlots) para control de disponibilidad

### ⭐ Favoritos
- Sistema de favoritos para guardar profesionales preferidos
- Ranking de profesionales más favoriteados
- Gestión completa (agregar/eliminar favoritos)

### 🔔 Notificaciones
- Sistema de notificaciones personalizadas
- Recordatorios automáticos de turnos
- Notificaciones por email
- Servicio programado de recordatorios (AppointmentReminderService)

### 📊 Calificaciones de la App
- Sistema de rating de 1 a 5 estrellas
- Aspectos positivos y a mejorar (selección múltiple)
- Comentarios adicionales
- Un usuario solo puede calificar una vez
- Seguimiento de feedback de usuarios

### 📧 Sistema de Contacto
- Formulario de contacto que envía emails al equipo de soporte
- Integración con JavaMailSender

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una **arquitectura en capas** (Layered Architecture) basada en el patrón MVC adaptado a Spring Boot:

```
┌─────────────────────────────────────────┐
│         CONTROLLERS LAYER               │  ← Endpoints REST (API)
│  (Recepción de requests HTTP)           │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│           SERVICE LAYER                 │  ← Lógica de negocio
│  (Procesamiento y validaciones)         │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│        REPOSITORY LAYER                 │  ← Acceso a datos
│  (JPA/Hibernate - Queries a BD)         │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          DATABASE (MySQL)               │  ← Persistencia
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│          SECURITY LAYER                 │  ← Autenticación/Autorización
│  (JWT Filter, Security Config)          │  ← Cross-cutting concern
└─────────────────────────────────────────┘
```

### Componentes Principales:

1. **Controllers**: Exponen los endpoints REST y manejan las peticiones HTTP
2. **Services**: Contienen la lógica de negocio y orquestación
3. **Repositories**: Interfaces que extienden JpaRepository para acceso a datos
4. **Entities**: Clases de dominio que mapean las tablas de la base de datos
5. **DTOs**: Objetos de transferencia de datos para requests y responses
6. **Security**: Configuración de seguridad, filtros JWT y utilidades

---

## 🛠️ Tecnologías Utilizadas

### Backend Framework
- **Spring Boot 3.4.4**: Framework principal
- **Spring Data JPA**: Persistencia y acceso a datos
- **Spring Security**: Autenticación y autorización
- **Spring Web**: Desarrollo de API REST
- **Spring Boot Actuator**: Métricas y monitoreo
- **Spring Boot Mail**: Envío de correos electrónicos

### Base de Datos
- **MySQL 8.0**: Base de datos principal
- **Hibernate**: ORM (Object-Relational Mapping)
- **MySQL Connector/J 8.0.33**: Driver JDBC

### Seguridad
- **JWT (JSON Web Tokens)**: Autenticación stateless
- **jjwt 0.9.1**: Librería para manejo de JWT
- **BCrypt**: Encriptación de contraseñas

### Utilidades
- **Lombok**: Reducción de código boilerplate
- **JAXB API 2.3.1**: Procesamiento XML (dependencia de JWT)

### Build Tools
- **Maven**: Gestión de dependencias y construcción
- **Maven Wrapper**: Garantiza versión consistente de Maven

### DevOps
- **Docker**: Containerización (Dockerfile incluido)
- **Spring Boot DevTools**: Desarrollo con hot-reload

### Java Version
- **Java 17**: Versión LTS (Long Term Support)

---

## 📁 Estructura del Proyecto

```
demo/
├── src/
│   ├── main/
│   │   ├── java/com/uade/dam/demo/
│   │   │   ├── DemoApplication.java           # Clase principal
│   │   │   ├── controllers/                   # Endpoints REST
│   │   │   │   ├── AppointmentController.java
│   │   │   │   ├── AppRatingController.java
│   │   │   │   ├── AuthController.java        # Autenticación
│   │   │   │   ├── FavoriteController.java
│   │   │   │   ├── NotificationController.java
│   │   │   │   ├── ProfessionalController.java
│   │   │   │   ├── TimeSlotController.java
│   │   │   │   └── UserController.java
│   │   │   ├── dto/                           # Data Transfer Objects
│   │   │   │   ├── AppointmentCreateRequestDTO.java
│   │   │   │   ├── AppRatingRequest.java
│   │   │   │   ├── AuthRequestDTO.java
│   │   │   │   ├── AuthResponseDTO.java
│   │   │   │   ├── ContactRequestDTO.java
│   │   │   │   ├── ErrorResponseDTO.java
│   │   │   │   ├── FavoriteCreateRequest.java
│   │   │   │   ├── GenericSuccessDTO.java
│   │   │   │   ├── NewPasswordRequestDTO.java
│   │   │   │   ├── PasswordChangeRequest.java
│   │   │   │   ├── PasswordResetRequest.java
│   │   │   │   ├── PasswordVerifyRequest.java
│   │   │   │   ├── TimeSlotReserveRequest.java
│   │   │   │   ├── TopFavoriteDTO.java
│   │   │   │   └── UserUpdateRequestDTO.java
│   │   │   ├── entity/                        # Entidades JPA
│   │   │   │   ├── Appointment.java           # Turnos médicos
│   │   │   │   ├── AppRating.java             # Calificaciones app
│   │   │   │   ├── Favorite.java              # Favoritos
│   │   │   │   ├── Notification.java          # Notificaciones
│   │   │   │   ├── Professional.java          # Profesionales
│   │   │   │   ├── Specialty.java             # Enum especialidades
│   │   │   │   ├── TimeSlot.java              # Slots de tiempo
│   │   │   │   └── User.java                  # Usuarios
│   │   │   ├── repository/                    # Acceso a datos
│   │   │   │   ├── AppointmentRepository.java
│   │   │   │   ├── AppRatingRepository.java
│   │   │   │   ├── FavoriteRepository.java
│   │   │   │   ├── NotificationRepository.java
│   │   │   │   ├── ProfessionalRepository.java
│   │   │   │   ├── TimeSlotRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── security/                      # Configuración seguridad
│   │   │   │   ├── JwtFilter.java             # Filtro JWT
│   │   │   │   ├── JwtUtil.java               # Utilidades JWT
│   │   │   │   └── SecurityConfig.java        # Config Spring Security
│   │   │   └── service/                       # Lógica de negocio
│   │   │       ├── AppointmentReminderService.java
│   │   │       ├── AppointmentService.java
│   │   │       ├── AppRatingService.java
│   │   │       ├── FavoriteService.java
│   │   │       ├── NotificationService.java
│   │   │       ├── ProfessionalService.java
│   │   │       └── UserService.java
│   │   └── resources/
│   │       ├── application.properties         # Configuración aplicación
│   │       └── uploads/
│   │           └── defaultUser.jpg            # Imagen por defecto
│   └── test/
│       └── java/com/uade/dam/demo/
│           └── DemoApplicationTests.java
├── target/                                    # Compilados (generado)
├── .mvn/                                      # Maven wrapper
├── .gitattributes
├── .gitignore
├── Dockerfile                                 # Docker containerization
├── mvnw                                       # Maven wrapper (Linux/Mac)
├── mvnw.cmd                                   # Maven wrapper (Windows)
└── pom.xml                                    # Configuración Maven
```

---

## 🗄️ Modelo de Datos

### Diagrama de Entidades

```
┌─────────────────────┐
│       User          │
├─────────────────────┤
│ PK id (UUID)        │
│    nombre           │
│    email (UNIQUE)   │
│    password         │
│    telefono         │
│    obraSocial       │
│    nroAfiliado      │
│    dni              │
│    fechaRegistro    │
│    imagen (BLOB)    │
└──────────┬──────────┘
           │
           │ 1:N
           │
┌──────────▼──────────┐          ┌─────────────────────┐
│    Appointment      │          │    Professional     │
├─────────────────────┤          ├─────────────────────┤
│ PK id (UUID)        │  N:1     │ PK id (UUID)        │
│ FK userId           │◄─────────│    name             │
│ FK professionalId   │          │    specialty (ENUM) │
│    date             │          │    imagen (BLOB)    │
│    time             │          └──────────┬──────────┘
│    status           │                     │
└─────────────────────┘                     │ 1:N
                                            │
┌─────────────────────┐          ┌──────────▼──────────┐
│     Favorite        │          │     TimeSlot        │
├─────────────────────┤          ├─────────────────────┤
│ PK id (UUID)        │          │ PK id (UUID)        │
│ FK userId           │          │ FK professionalId   │
│ FK professionalId   │          │    date             │
└─────────────────────┘          │    time             │
                                 │ FK appointmentId    │
┌─────────────────────┐          └─────────────────────┘
│   Notification      │
├─────────────────────┤
│ PK id (UUID)        │
│ FK userId           │
│    message          │
│    type             │
│    isRead           │
│    date             │
└─────────────────────┘

┌─────────────────────┐
│    AppRating        │
├─────────────────────┤
│ PK id (UUID)        │
│ FK user_id          │
│    puntuacion       │
│    aspectosPositivos│
│    aspectosMejorar  │
│    comentario       │
│    fecha            │
└─────────────────────┘

┌─────────────────────┐
│    Specialty        │
│     (ENUM)          │
├─────────────────────┤
│ UROLOGO             │
│ CARDIOLOGO          │
│ PSIQUIATRA          │
│ PEDIATRA            │
│ DERMATOLOGO         │
│ GINECOLOGO          │
│ NEUROLOGO           │
│ TRAUMATOLOGO        │
│ OTORRINOLARINGOLOGO │
│ OFTALMOLOGO         │
│ NEFROLOGO           │
│ ENDOCRINOLOGO       │
│ ONCOLOGO            │
│ INTERNISTA          │
│ ANESTESIOLOGO       │
│ GASTROENTEROLOGO    │
│ NEUMOLOGO           │
│ ... (y más)         │
└─────────────────────┘
```

### Descripción de Entidades

#### **User** (Usuario)
Representa los usuarios de la aplicación que pueden reservar turnos.

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador único |
| nombre | String | Nombre completo |
| email | String | Email (único) |
| password | String | Contraseña encriptada |
| telefono | String | Número de teléfono |
| obraSocial | String | Obra social (opcional) |
| nroAfiliado | String | Número de afiliado (opcional) |
| dni | String | DNI (opcional) |
| fechaRegistro | LocalDateTime | Fecha de registro |
| imagen | byte[] | Foto de perfil (LONGBLOB) |

#### **Professional** (Profesional)
Representa los profesionales de la salud disponibles.

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador único |
| name | String | Nombre del profesional |
| specialty | Specialty (ENUM) | Especialidad médica |
| imagen | byte[] | Foto del profesional (LONGBLOB) |

#### **Appointment** (Turno)
Representa los turnos médicos reservados.

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador único |
| userId | String | ID del usuario |
| professionalId | String | ID del profesional |
| date | String | Fecha del turno |
| time | String | Hora del turno |
| status | String | Estado: pending, completed, canceled |

#### **TimeSlot** (Horario)
Representa los horarios reservados de los profesionales.

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador único |
| professionalId | String | ID del profesional |
| date | String | Fecha |
| time | String | Hora |
| appointmentId | String | ID del usuario que reservó |

#### **Favorite** (Favorito)
Representa los profesionales favoritos de un usuario.

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador único |
| userId | String | ID del usuario |
| professionalId | String | ID del profesional |

#### **Notification** (Notificación)
Representa las notificaciones del sistema.

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador único |
| userId | String | ID del usuario destinatario |
| message | String | Mensaje de la notificación |
| type | String | Tipo de notificación |
| isRead | boolean | Si fue leída |
| date | LocalDateTime | Fecha de creación |

#### **AppRating** (Calificación)
Representa las calificaciones de la aplicación.

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador único |
| user | User (ManyToOne) | Usuario que califica |
| puntuacion | int | Puntuación de 1 a 5 |
| aspectosPositivos | List<String> | Aspectos positivos |
| aspectosMejorar | List<String> | Aspectos a mejorar |
| comentario | String | Comentario adicional |
| fecha | LocalDateTime | Fecha de calificación |

---

## ⚙️ Configuración e Instalación

### Prerrequisitos

- **Java 17** o superior
- **Maven 3.6+** (o usar el wrapper incluido)
- **MySQL 8.0+**
- **Git** (para clonar el repositorio)

### Instalación Local

#### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/vitalis-back.git
cd vitalis-back/demo
```

#### 2. Configurar MySQL

Crear la base de datos:

```sql
CREATE DATABASE vitalis;
CREATE USER 'vitalis_user'@'localhost' IDENTIFIED BY 'tu_password_seguro';
GRANT ALL PRIVILEGES ON vitalis.* TO 'vitalis_user'@'localhost';
FLUSH PRIVILEGES;
```

#### 3. Configurar variables de entorno

Editar `src/main/resources/application.properties`:

```properties
# Nombre de la aplicación
spring.application.name=vitalis

# Puerto del servidor
server.port=4002

# Configuración de base de datos MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/vitalis
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root

# Configuración JPA/Hibernate
spring.jpa.database=mysql
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Configuración de archivos multipart
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Configuración de correo electrónico (Gmail)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_email@gmail.com
spring.mail.password=tu_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

> ⚠️ **Nota**: Para Gmail, necesitas generar una "Contraseña de aplicación" en tu cuenta de Google.

#### 4. Instalar dependencias y compilar

**Usando Maven Wrapper (recomendado):**

Windows:
```bash
mvnw.cmd clean install
```

Linux/Mac:
```bash
./mvnw clean install
```

**O usando Maven directamente:**
```bash
mvn clean install
```

#### 5. Ejecutar la aplicación

**Opción 1 - Maven Wrapper:**
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

**Opción 2 - Maven directo:**
```bash
mvn spring-boot:run
```

**Opción 3 - JAR ejecutable:**
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en: `http://localhost:4002`

#### 6. Verificar instalación

Endpoint de salud:
```bash
curl http://localhost:4002/actuator/health
```

---

## 🔧 Variables de Entorno

### Configuración de Base de Datos

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `spring.datasource.url` | URL de conexión a MySQL | `jdbc:mysql://localhost:3306/vitalis` |
| `spring.datasource.username` | Usuario de MySQL | `root` |
| `spring.datasource.password` | Contraseña de MySQL | `root` |
| `spring.jpa.hibernate.ddl-auto` | Estrategia de creación de esquema | `update` |
| `spring.jpa.show-sql` | Mostrar consultas SQL en logs | `true` |

### Configuración de Servidor

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `server.port` | Puerto del servidor | `4002` |
| `spring.application.name` | Nombre de la aplicación | `vitalis` |

### Configuración de Email

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `spring.mail.host` | Servidor SMTP | `smtp.gmail.com` |
| `spring.mail.port` | Puerto SMTP | `587` |
| `spring.mail.username` | Email remitente | - |
| `spring.mail.password` | Contraseña de aplicación | - |

### Configuración de Archivos

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `spring.servlet.multipart.max-file-size` | Tamaño máximo de archivo | `10MB` |
| `spring.servlet.multipart.max-request-size` | Tamaño máximo de request | `10MB` |

---

## 🌐 Endpoints de la API

### Base URL: `http://localhost:4002`

---

### 🔐 Autenticación (`/auth`)

#### Registro de Usuario
```http
POST /auth/register
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "password": "MiPassword123",
  "telefono": "1155667788",
  "codigoVerificacion": "123456"
}
```

**Response 201:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "usuario": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "nombre": "Juan Pérez",
    "email": "juan@example.com",
    ...
  }
}
```

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "juan@example.com",
  "password": "MiPassword123"
}
```

**Response 200:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "usuario": {...}
}
```

#### Solicitar Código de Verificación (Registro)
```http
POST /auth/request-verification-code
Content-Type: application/json

{
  "email": "juan@example.com"
}
```

**Response 200:**
```json
{
  "message": "Código enviado"
}
```

#### Solicitar Recuperación de Contraseña
```http
POST /auth/request-password-reset
Content-Type: application/json

{
  "email": "juan@example.com"
}
```

**Response 200:**
```json
{
  "message": "Reset code sent"
}
```

#### Verificar Código de Recuperación
```http
POST /auth/verify-reset-code
Content-Type: application/json

{
  "email": "juan@example.com",
  "code": "123456"
}
```

#### Restablecer Contraseña
```http
PUT /auth/reset-password
Content-Type: application/json

{
  "email": "juan@example.com",
  "code": "123456",
  "nueva": "NuevaPassword123"
}
```

---

### 👤 Usuarios (`/users`)

> 🔒 **Requiere autenticación**: Todos los endpoints excepto los públicos

#### Obtener Usuario
```http
GET /users/{id}
Authorization: Bearer {token}
```

#### Actualizar Usuario
```http
PUT /users/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "telefono": "1155667788",
  "dni": "12345678",
  "obraSocial": "OSDE",
  "nroAfiliado": "123456789"
}
```

#### Cambiar Contraseña
```http
PUT /users/{id}/password
Authorization: Bearer {token}
Content-Type: application/json

{
  "actual": "PasswordActual123",
  "nueva": "PasswordNueva123"
}
```

#### Actualizar Foto de Perfil
```http
PUT /users/{id}/profile-picture
Authorization: Bearer {token}
Content-Type: multipart/form-data

file: [archivo de imagen]
```

**Validaciones:**
- Formato: Solo imágenes (image/*)
- Tamaño máximo: 5MB

#### Obtener Foto de Perfil
```http
GET /users/{id}/profile-picture
Authorization: Bearer {token}
```

**Response:** Imagen en formato byte[]

#### Eliminar Foto de Perfil
```http
DELETE /users/{id}/profile-picture
Authorization: Bearer {token}
```

#### Solicitar Código de Eliminación de Cuenta
```http
POST /users/{id}/request-delete-code
Authorization: Bearer {token}
```

#### Eliminar Cuenta
```http
DELETE /users/{id}?code=123456
Authorization: Bearer {token}
```

#### Contacto (Formulario de Contacto)
```http
POST /users/contact
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "mensaje": "Tengo una consulta sobre..."
}
```

---

### 👨‍⚕️ Profesionales (`/professionals`)

> ✅ **Públicos**: No requieren autenticación

#### Listar Todos los Profesionales
```http
GET /professionals
```

**Response 200:**
```json
[
  {
    "id": "prof-uuid-1",
    "name": "Dr. Carlos Gómez",
    "specialty": "CARDIOLOGO",
    "imagen": null
  },
  ...
]
```

#### Obtener Profesional por ID
```http
GET /professionals/{id}
```

#### Crear Profesional
```http
POST /professionals
Content-Type: application/json

{
  "name": "Dr. Carlos Gómez",
  "specialty": "CARDIOLOGO"
}
```

#### Eliminar Profesional
```http
DELETE /professionals/{id}
```

#### Top Profesionales Más Favoriteados
```http
GET /professionals/top-favorites?limit=4
```

**Response 200:**
```json
[
  {
    "id": "prof-uuid-1",
    "name": "Dr. Carlos Gómez",
    "specialty": "CARDIOLOGO",
    "count": 15
  },
  ...
]
```

---

### 📅 Turnos (`/appointments`)

> 🔒 **Requiere autenticación**: Algunos endpoints

#### Listar Todos los Turnos
```http
GET /appointments
```

#### Obtener Turno por ID
```http
GET /appointments/{id}
```

#### Crear Turno
```http
POST /appointments
Content-Type: application/json

{
  "userId": "user-uuid",
  "professionalId": "prof-uuid",
  "date": "2024-01-15",
  "time": "10:00",
  "status": "pending"
}
```

#### Eliminar Turno
```http
DELETE /appointments/{id}
```

#### Obtener Turnos de un Usuario
```http
GET /appointments/user/{userId}
Authorization: Bearer {token}
```

**Validación:** Solo el usuario autenticado puede ver sus propios turnos.

#### Actualizar Turnos Vencidos
```http
PUT /appointments/user/{userId}/update-expired
Authorization: Bearer {token}
Content-Type: application/json

{
  "currentDate": "2024-01-15",
  "currentTime": "10:00"
}
```

---

### ⏰ Horarios (`/timeslots`)

#### Obtener Horarios por Profesional y Fecha
```http
GET /timeslots?professionalId={profId}&date=2024-01-15
```

#### Reservar Horario
```http
POST /timeslots/reserve
Content-Type: application/json

{
  "professionalId": "prof-uuid",
  "userId": "user-uuid",
  "date": "2024-01-15",
  "time": "10:00"
}
```

**Validaciones:**
- Usuario debe tener obra social y nº afiliado cargados
- El horario no debe estar ya reservado

#### Crear Horario
```http
POST /timeslots
Content-Type: application/json

{
  "professionalId": "prof-uuid",
  "date": "2024-01-15",
  "time": "10:00",
  "appointmentId": "user-uuid"
}
```

#### Eliminar Horario
```http
DELETE /timeslots/{id}
```

---

### ⭐ Favoritos (`/favorites`)

#### Listar Todos los Favoritos
```http
GET /favorites
```

#### Obtener Favorito por ID
```http
GET /favorites/{id}
```

#### Agregar Favorito
```http
POST /favorites
Content-Type: application/json

{
  "userId": "user-uuid",
  "professionalId": "prof-uuid"
}
```

#### Eliminar Favorito
```http
DELETE /favorites/{id}
```

---

### 🔔 Notificaciones (`/notifications`)

#### Listar Notificaciones
```http
GET /notifications
```

#### Obtener Notificación por ID
```http
GET /notifications/{id}
```

#### Crear Notificación
```http
POST /notifications
Content-Type: application/json

{
  "userId": "user-uuid",
  "message": "Tu turno es mañana",
  "type": "reminder",
  "isRead": false
}
```

#### Eliminar Notificación
```http
DELETE /notifications/{id}
```

#### Enviar Recordatorios Manualmente
```http
POST /notifications/send-reminders
```

---

### 📊 Calificaciones de la App (`/app-ratings`)

> 🔒 **Requiere autenticación**

#### Crear Calificación
```http
POST /app-ratings
Authorization: Bearer {token}
Content-Type: application/json

{
  "puntuacion": 5,
  "aspectosPositivos": ["Interfaz intuitiva", "Fácil de usar"],
  "aspectosMejorar": ["Más especialidades"],
  "comentario": "Excelente aplicación"
}
```

**Validaciones:**
- Puntuación entre 1 y 5
- Un usuario solo puede calificar una vez

#### Obtener Mi Calificación
```http
GET /app-ratings/my-rating
Authorization: Bearer {token}
```

#### Verificar si Ya Calificó
```http
GET /app-ratings/has-rated
Authorization: Bearer {token}
```

**Response 200:**
```json
{
  "hasRated": true
}
```

---

### 📋 Resumen de Endpoints

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | `/auth/register` | Registrar usuario | ❌ |
| POST | `/auth/login` | Iniciar sesión | ❌ |
| POST | `/auth/request-verification-code` | Solicitar código de verificación | ❌ |
| POST | `/auth/request-password-reset` | Solicitar recuperación de contraseña | ❌ |
| POST | `/auth/verify-reset-code` | Verificar código | ❌ |
| PUT | `/auth/reset-password` | Restablecer contraseña | ❌ |
| GET | `/users/{id}` | Obtener usuario | ✅ |
| PUT | `/users/{id}` | Actualizar usuario | ✅ |
| DELETE | `/users/{id}` | Eliminar usuario | ✅ |
| PUT | `/users/{id}/password` | Cambiar contraseña | ✅ |
| PUT | `/users/{id}/profile-picture` | Actualizar foto | ✅ |
| GET | `/users/{id}/profile-picture` | Obtener foto | ✅ |
| DELETE | `/users/{id}/profile-picture` | Eliminar foto | ✅ |
| POST | `/users/contact` | Formulario contacto | ❌ |
| GET | `/professionals` | Listar profesionales | ❌ |
| GET | `/professionals/{id}` | Obtener profesional | ❌ |
| GET | `/professionals/top-favorites` | Top favoritos | ❌ |
| GET | `/appointments` | Listar turnos | ❌ |
| POST | `/appointments` | Crear turno | ❌ |
| GET | `/appointments/user/{userId}` | Turnos de usuario | ✅ |
| PUT | `/appointments/user/{userId}/update-expired` | Actualizar vencidos | ✅ |
| GET | `/timeslots` | Horarios por profesional | ❌ |
| POST | `/timeslots/reserve` | Reservar horario | ❌ |
| GET | `/favorites` | Listar favoritos | ❌ |
| POST | `/favorites` | Agregar favorito | ❌ |
| DELETE | `/favorites/{id}` | Eliminar favorito | ❌ |
| GET | `/notifications` | Listar notificaciones | ❌ |
| POST | `/notifications/send-reminders` | Enviar recordatorios | ❌ |
| POST | `/app-ratings` | Crear calificación | ✅ |
| GET | `/app-ratings/my-rating` | Mi calificación | ✅ |
| GET | `/app-ratings/has-rated` | ¿Ya calificó? | ✅ |

---

## 🔐 Seguridad y Autenticación

### JWT (JSON Web Tokens)

La aplicación utiliza JWT para autenticación stateless:

1. **Usuario se registra o inicia sesión** → Recibe un token JWT
2. **Token contiene**: User ID, fecha de emisión, fecha de expiración
3. **Cliente incluye token** en cada request: `Authorization: Bearer {token}`
4. **Servidor valida token** usando `JwtFilter` antes de procesar el request

### Componentes de Seguridad

#### `SecurityConfig.java`
Configuración principal de Spring Security:

```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/professionals/**").permitAll()
                .requestMatchers("/favorites/**").permitAll()
                .requestMatchers("/appointments/**").permitAll()
                .requestMatchers("/timeslots/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
```

#### `JwtUtil.java`
Utilidades para generar y validar tokens:

- `generateToken(String userId)`: Genera un token JWT
- `validateToken(String token)`: Valida si un token es válido
- `extractUserId(String token)`: Extrae el user ID del token

#### `JwtFilter.java`
Filtro que intercepta requests y valida el token:

1. Extrae el token del header `Authorization`
2. Valida el token usando `JwtUtil`
3. Si es válido, establece la autenticación en el contexto de Spring Security
4. Si no es válido, rechaza el request

### Endpoints Públicos vs Protegidos

**Públicos (sin autenticación):**
- `/auth/**` - Registro, login, recuperación de contraseña
- `/professionals/**` - Listado de profesionales
- `/favorites/**` - Gestión de favoritos
- `/appointments/**` - Gestión de turnos (algunos)
- `/timeslots/**` - Gestión de horarios

**Protegidos (requieren token):**
- `/users/**` - Gestión de perfil de usuario
- `/app-ratings/**` - Calificaciones de la app
- Algunos endpoints específicos de appointments por usuario

### Encriptación de Contraseñas

- **BCrypt**: Algoritmo de hashing adaptativo
- **Salt automático**: BCrypt genera un salt único para cada contraseña
- **Verificación**: `passwordEncoder.matches(rawPassword, encodedPassword)`

### Códigos de Verificación

La aplicación usa códigos temporales de 6 dígitos para:
- **Verificación de email** al registrarse
- **Recuperación de contraseña**
- **Eliminación de cuenta**

Almacenamiento: `ConcurrentHashMap` (en memoria)
- Ventaja: Simple y rápido
- Desventaja: Se pierden al reiniciar el servidor
- Mejora futura: Almacenar en BD con expiración

---

## 🚀 Funcionalidades Especiales

### 1. Sistema de Recordatorios de Turnos

**Clase:** `AppointmentReminderService.java`

Envía recordatorios automáticos por email a usuarios con turnos próximos:

```java
@Scheduled(cron = "0 0 9 * * *") // Todos los días a las 9 AM
public void sendAppointmentReminders() {
    // Lógica de envío de recordatorios
}
```

**Características:**
- Envío automático diario
- Personalizado con datos del turno
- Notificación por email

### 2. Top Profesionales Favoriteados

**Endpoint:** `GET /professionals/top-favorites`

Algoritmo:
1. Consulta los profesionales con más favoritos
2. Si hay menos de N profesionales, completa con aleatorios
3. Retorna DTO con contador de favoritos

```sql
SELECT professional_id, COUNT(*) as count 
FROM Favorite 
GROUP BY professional_id 
ORDER BY count DESC 
LIMIT ?
```

### 3. Validación de Obra Social

**Funcionalidad:** No se puede reservar turno sin obra social y nº afiliado

```java
if (user.getObraSocial() == null || user.getObraSocial().isBlank() ||
    user.getNroAfiliado() == null || user.getNroAfiliado().isBlank()) {
    return ResponseEntity.status(400).body(
        "Debes cargar tu obra social y número de afiliado"
    );
}
```

### 4. Imágenes en Base de Datos

**Estrategia:** Almacenamiento BLOB en MySQL

- **Ventajas**: No requiere filesystem, backup unificado
- **Desventajas**: Mayor tamaño de BD, menos performance
- **Tipo de columna**: `LONGBLOB` (hasta 4GB por imagen)

**Imagen por defecto:**
- Ubicación: `src/main/resources/uploads/defaultUser.jpg`
- Carga: Al registrar un usuario sin foto

### 5. Actualización Automática de Turnos Vencidos

**Endpoint:** `PUT /appointments/user/{userId}/update-expired`

Compara fecha/hora actual con turnos del usuario y actualiza estados:

```java
public List<Appointment> updateExpiredAppointmentsForUser(
    String userId, String currentDate, String currentTime
) {
    // Lógica de actualización
}
```

### 6. Sistema de Calificación Única

**Restricción:** Un usuario solo puede calificar la app una vez

```java
@Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
       "FROM AppRating r WHERE r.user = :user")
boolean existsByUser(@Param("user") User user);
```

### 7. Eliminación Segura de Cuenta

Proceso de 2 pasos:
1. Solicitar código → Se envía por email
2. Confirmar con código → Se elimina la cuenta

**Seguridad adicional:**
- Verifica que el usuario autenticado sea el mismo que se va a eliminar
- Código temporal de 6 dígitos

---

## 🐳 Deployment

### Docker

El proyecto incluye un `Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-alpine
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

#### Construir imagen Docker

```bash
# 1. Compilar el proyecto
./mvnw clean package -DskipTests

# 2. Construir imagen
docker build -t vitalis-backend:latest .

# 3. Ejecutar contenedor
docker run -p 4002:4002 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/vitalis \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=root \
  vitalis-backend:latest
```

### Docker Compose

Crear `docker-compose.yml`:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: vitalis
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql

  backend:
    build: .
    ports:
      - "4002:4002"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/vitalis
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
      SPRING_MAIL_USERNAME: ${MAIL_USERNAME}
      SPRING_MAIL_PASSWORD: ${MAIL_PASSWORD}
    depends_on:
      - mysql

volumes:
  mysql-data:
```

Ejecutar:
```bash
docker-compose up -d
```

### Despliegue en Cloud

#### Heroku

```bash
# 1. Login
heroku login

# 2. Crear app
heroku create vitalis-backend

# 3. Agregar MySQL
heroku addons:create cleardb:ignite

# 4. Deploy
git push heroku main
```

#### Railway.app

1. Conectar repositorio de GitHub
2. Configurar variables de entorno
3. Deploy automático

#### AWS Elastic Beanstalk

```bash
# 1. Instalar EB CLI
pip install awsebcli

# 2. Inicializar
eb init -p java-17 vitalis-backend

# 3. Crear entorno
eb create vitalis-prod

# 4. Deploy
eb deploy
```

---

## 🧪 Testing

### Ejecutar Tests

```bash
# Todos los tests
./mvnw test

# Tests específicos
./mvnw test -Dtest=DemoApplicationTests

# Con reporte de cobertura
./mvnw clean test jacoco:report
```

### Estructura de Tests

```
src/test/java/com/uade/dam/demo/
├── DemoApplicationTests.java
├── controller/
│   ├── AuthControllerTest.java
│   ├── UserControllerTest.java
│   └── ...
├── service/
│   ├── UserServiceTest.java
│   └── ...
└── integration/
    ├── AuthIntegrationTest.java
    └── ...
```

### Ejemplo de Test

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testGetUser() throws Exception {
        mockMvc.perform(get("/users/123")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }
}
```

---

## 🔧 Troubleshooting

### Problemas Comunes

#### 1. Error de conexión a MySQL

**Error:** `Communications link failure`

**Solución:**
- Verificar que MySQL esté ejecutándose: `systemctl status mysql`
- Verificar credenciales en `application.properties`
- Verificar que el puerto 3306 esté disponible

#### 2. Error de dependencias Maven

**Error:** `Could not resolve dependencies`

**Solución:**
```bash
./mvnw clean
./mvnw dependency:purge-local-repository
./mvnw clean install
```

#### 3. Error al enviar emails

**Error:** `AuthenticationFailedException`

**Solución:**
- Generar contraseña de aplicación en Google
- Verificar configuración en `application.properties`
- Verificar que SMTP no esté bloqueado por firewall

#### 4. Token JWT inválido

**Error:** `401 Unauthorized`

**Solución:**
- Verificar que el token no haya expirado
- Verificar formato: `Authorization: Bearer {token}`
- Verificar que la secret key sea la misma

#### 5. Tamaño de archivo excedido

**Error:** `Maximum upload size exceeded`

**Solución:**
- Verificar configuración:
```properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

#### 6. Puerto ya en uso

**Error:** `Port 4002 is already in use`

**Solución:**
```bash
# Windows
netstat -ano | findstr :4002
taskkill /PID {PID} /F

# Linux/Mac
lsof -i :4002
kill -9 {PID}
```

### Logs

Habilitar logs detallados:

```properties
# application.properties
logging.level.root=INFO
logging.level.com.uade.dam.demo=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

---

## 🤝 Contribución

### Guidelines

1. **Fork** el repositorio
2. **Crear branch** para tu feature: `git checkout -b feature/nueva-funcionalidad`
3. **Commit** tus cambios: `git commit -m 'Add: nueva funcionalidad'`
4. **Push** al branch: `git push origin feature/nueva-funcionalidad`
5. **Abrir Pull Request**

### Convenciones de Código

- **Naming:**
  - Clases: `PascalCase`
  - Métodos/Variables: `camelCase`
  - Constantes: `UPPER_SNAKE_CASE`
  
- **Packages:**
  - Controllers: `com.uade.dam.demo.controllers`
  - Services: `com.uade.dam.demo.service`
  - Repositories: `com.uade.dam.demo.repository`

- **Commits:**
  - `Add:` Nueva funcionalidad
  - `Fix:` Corrección de bug
  - `Update:` Actualización de código existente
  - `Refactor:` Refactorización
  - `Docs:` Documentación

### Code Style

- **Indentación:** 4 espacios
- **Línea máxima:** 120 caracteres
- **Comentarios:** JavaDoc para clases y métodos públicos

---

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver archivo `LICENSE` para más detalles.

---

## 👥 Equipo

Desarrollado por el equipo de **UADE - Desarrollo de Aplicaciones Móviles**

---

## 📞 Soporte

Para reportar bugs o solicitar features:
- **Email:** soportevitalis86@gmail.com
- **Issues:** GitHub Issues

---

## 🎉 Agradecimientos

- Spring Framework Team
- Comunidad de Spring Boot
- UADE - Universidad Argentina de la Empresa

---

**Última actualización:** Diciembre 2025

**Versión:** 0.0.1-SNAPSHOT
