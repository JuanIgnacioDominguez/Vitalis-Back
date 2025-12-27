# 🏥 Vitalis Backend - API de Turnos Médicos

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.4-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

*API RESTful para gestionar usuarios, profesionales, turnos, favoritos, notificaciones y calificaciones. Autenticación JWT, recordatorios por email, fotos de perfil y ranking de profesionales.*

</div>

---

## 📖 Descripción
Vitalis digitaliza la gestión de turnos médicos con seguridad y buenas prácticas:
- Registro/login con verificación por código y JWT
- Búsqueda de profesionales por especialidad y sistema de favoritos
- Reserva de turnos con validación de obra social y número de afiliado
- Recordatorios y notificaciones por email
- Foto de perfil en BD (LONGBLOB) y calificación única de la app

---

## 🧭 Índice
- [Descripción](#-descripción)
- [Arquitectura](#-arquitectura)
- [Repositorios](#-repositorios)
- [Stack Tecnológico](#-stack-tecnológico)
- [Modelo de Datos](#-modelo-de-datos)
- [Seguridad](#-seguridad)
- [Endpoints Clave](#-endpoints-clave)
- [Instalación Rápida](#-instalación-rápida)
- [Configuración](#-configuración)
- [Docker](#-docker)
- [Testing](#-testing)
- [Troubleshooting](#-troubleshooting)
- [Frontend](#-frontend)
- [Licencia](#-licencia)
- [Créditos](#-créditos)

---

## 🏗️ Arquitectura
```
[ Controllers ] → [ Services ] → [ Repositories ] → [ MySQL ]
          ↘           Security (JWT Filter + Config)
```
- Controllers: capa API REST
- Services: lógica de negocio y validaciones
- Repositories: acceso JPA/Hibernate
- Security: JWT, CORS, reglas de autorización

---

## 📂 Repositorios

| Repositorio | Descripción | Puerto |
|-------------|-------------|--------|
| **Vitalis-Back** (este repo) | Backend - Spring Boot | `localhost:4002` |
| **[VitalisFront](https://github.com/JuanIgnacioDominguez/VitalisFront)** | Frontend - React + Vite | `localhost:5173` |

---

## 🧰 Stack Tecnológico

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 17 | Lenguaje |
| Spring Boot | 3.4.4 | Framework principal |
| Spring Security | 6.x | Autenticación y autorización |
| Spring Data JPA | - | Persistencia |
| Spring Mail | - | Envío de emails |
| MySQL Connector | 8.0.33 | Driver BD |
| JWT (jjwt) | 0.9.1 | Tokens de autenticación |
| Lombok | - | Boilerplate mínimo |
| Maven | 3.x | Build y dependencias |
| Spring Actuator | - | Monitoreo |
| Spring DevTools | - | Hot reload |

---

## 🗄️ Modelo de Datos
Entidades principales (UUID):

| Entidad | Campos clave |
|---------|--------------|
| User | nombre, email (unique), password, telefono, obraSocial, nroAfiliado, dni, fechaRegistro, imagen (LONGBLOB) |
| Professional | name, specialty (enum), imagen |
| Appointment | userId, professionalId, date, time, status |
| TimeSlot | professionalId, date, time, appointmentId |
| Favorite | userId, professionalId |
| Notification | userId, message, type, isRead, date |
| AppRating | user (ManyToOne), puntuacion, aspectosPositivos[], aspectosMejorar[], comentario, fecha |
| Specialty (enum) | UROLOGO, CARDIOLOGO, PSIQUIATRA, PEDIATRA, DERMATOLOGO, ... |

---

## 🔐 Seguridad
- JWT para autenticación stateless (`Authorization: Bearer <token>`)
- BCrypt para hashing de contraseñas
- Endpoints públicos: `/auth/**`, `/professionals/**`, `/favorites/**`, `/appointments/**`, `/timeslots/**`
- Endpoints protegidos: `/users/**`, `/app-ratings/**` y operaciones sensibles
- Códigos de 6 dígitos: registro, recuperación de contraseña, eliminación de cuenta

`SecurityConfig`: CORS habilitado, CSRF deshabilitado, `JwtFilter` antes de `UsernamePasswordAuthenticationFilter`.

---

## 📡 Endpoints Clave

| Dominio | Endpoint | Método | Auth | Notas |
|---------|----------|--------|------|-------|
| Auth | /auth/register | POST | ❌ | Registro con código de verificación |
| Auth | /auth/login | POST | ❌ | Devuelve JWT |
| Users | /users/{id} | GET/PUT/DELETE | ✅ | Perfil y borrado con código |
| Users | /users/{id}/profile-picture | GET/PUT/DELETE | ✅ | Foto (máx 5MB) |
| Professionals | /professionals, /top-favorites | GET | ❌ | Listado y ranking |
| Appointments | /appointments | GET/POST/DELETE | ❌ | CRUD básico |
| Appointments | /appointments/user/{userId} | GET/PUT | ✅ | Solo dueño |
| TimeSlots | /timeslots | GET/POST/DELETE | ❌ | Gestión de horarios |
| TimeSlots | /timeslots/reserve | POST | ❌ | Requiere obra social + nro afiliado |
| Favorites | /favorites | GET/POST/DELETE | ❌ | Favoritos |
| Notifications | /notifications | GET/POST/DELETE | ❌ | Incluye `/send-reminders` |
| App Ratings | /app-ratings | POST/GET | ✅ | Calificación única |

---

## ⚡ Instalación Rápida
```bash
# 1) Clonar
git clone https://github.com/tu-usuario/vitalis-back.git
cd vitalis-back/demo

# 2) Configurar MySQL (crear DB y usuario)

# 3) Ajustar src/main/resources/application.properties

# 4) Ejecutar
mvnw.cmd spring-boot:run   # Windows
# ./mvnw spring-boot:run   # Linux/Mac

# Salud
curl http://localhost:4002/actuator/health
```

---

## ⚙️ Configuración

### Base de datos
| Propiedad | Ejemplo |
|-----------|---------|
| spring.datasource.url | jdbc:mysql://localhost:3306/vitalis |
| spring.datasource.username | root |
| spring.datasource.password | root |
| spring.jpa.hibernate.ddl-auto | update |

### Servidor
| Propiedad | Ejemplo |
|-----------|---------|
| server.port | 4002 |
| spring.application.name | vitalis |

### Email (Gmail SMTP)
| Propiedad | Ejemplo |
|-----------|---------|
| spring.mail.username | tu_email@gmail.com |
| spring.mail.password | tu_app_password |
| spring.mail.host | smtp.gmail.com |
| spring.mail.port | 587 |

### Archivos
| Propiedad | Valor |
|-----------|-------|
| spring.servlet.multipart.max-file-size | 10MB |
| spring.servlet.multipart.max-request-size | 10MB |

### application.properties de ejemplo
```properties
server.port=4002
spring.datasource.url=jdbc:mysql://localhost:3306/vitalis
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_email@gmail.com
spring.mail.password=tu_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## 🐳 Docker
`Dockerfile` listo:
```dockerfile
FROM openjdk:17-jdk-alpine
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

Build y run:
```bash
./mvnw clean package -DskipTests
docker build -t vitalis-backend:latest .
docker run -p 4002:4002 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/vitalis \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=root \
  vitalis-backend:latest
```

Compose (opcional):
```yaml
version: "3.8"
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

---

## 🧪 Testing
```bash
./mvnw test
./mvnw test -Dtest=DemoApplicationTests
./mvnw clean test jacoco:report   # con cobertura
```

---

## 🔧 Troubleshooting
- MySQL: verificar servicio y credenciales (`spring.datasource.*`).
- SMTP: usar contraseña de aplicación de Gmail y STARTTLS en 587.
- JWT 401: token expirado o header sin `Bearer`.
- Upload: respetar límite 10MB y tipo image/*.
- Puerto 4002 ocupado: liberar proceso antes de levantar.

---

## 🎨 Frontend
El frontend vive en un repositorio separado y consume la API en `http://localhost:4002`.

📦 **Repositorio:** [VitalisFront](https://github.com/JuanIgnacioDominguez/VitalisFront)

**Stack del Frontend**
- Framework: React 18 + Vite
- Puerto: `localhost:5173`
- Estado Global: Redux Toolkit
- Estilos: TailwindCSS + DaisyUI
- Animaciones: Framer Motion

---

## 📄 Licencia
Proyecto desarrollado con fines educativos y llevado a producción.

---

## 👨‍💻 Créditos

| Desarrollador | Rol |
|---------------|-----|
| **Thomas Giardina** | Fullstack Developer |
| **Juan Ignacio Domínguez** | Fullstack Developer |

<div align="center">

**Hecho con ❤️ por Thomas Giardina & Juan Ignacio Domínguez**

*Proyecto universitario mejorado y llevado a producción*

</div>

