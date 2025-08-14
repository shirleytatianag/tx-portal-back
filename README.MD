# 🌐 Backend - TX-Portal (Spring Boot)

Este proyecto es el backend del sistema **TX-Portal** (Portal Transaccional), desarrollado con **Spring Boot**.  
Provee una **API REST segura** para la gestión de datos, integración con APIs externas y almacenamiento en **PostgreSQL**.  
Su diseño sigue una **arquitectura modular** para mantener un código limpio, mantenible y escalable.

----------

## 🚀 Características

-   📡 **Consumo de APIs externas** para obtención y sincronización de información.
    
-   🔐 **Autenticación y autorización** mediante **JWT**.
    
-   🗄️ **Persistencia de datos** con **Spring Data JPA** y PostgreSQL.
    
-   📑 **Documentación interactiva** con **Swagger/OpenAPI**.
    
-   🛠️ Arquitectura en capas (**Controller**, **Service**, **Repository**).
    
-   ⚙️ Configuración centralizada mediante `application.yml`.
    

----------

## 🛠️ Tecnologías Utilizadas

-   ☕ Java 21
    
-   Spring Boot
    
-   Spring Web (REST)
    
-   Spring Security + JWT
    
-   Spring Data JPA
    
-   PostgreSQL
    
-   Spring WebClient / RestTemplate
    
-   Swagger / OpenAPI
    
-   Gradle
    

----------

## 📦 Instalación y Configuración

### 🔹 Requisitos Previos

-   **Java 21** → [Descargar aquí](https://www.oracle.com/java/technologies/downloads/#java21) 
    
-   **Gradle** instalado
    
-   **PostgreSQL** configurado y en ejecución

----------

### 🔹 Creación de la Base de Datos y Esquema
Antes de ejecutar el backend, debe existir en PostgreSQL la base de datos llamada `transaccional_portal` y el esquema `main`.

----------

### ⚙️ Configuración (`application.yml`)

El proyecto utiliza **`application.yml`** para las variables de configuración, solo debes modificar las que ves a continuación.  
**Ejemplo:**

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/transaccional_portal
    username: <tu usuario>
    password: <tu contraseña>
cors:
  allowed-origins: http://localhost:3000
```

⚠️ **Importante:** Actualmente las credenciales están en `application.yml` solo para fines de producción.  
En un futuro, se deben mover a variables de entorno para mayor seguridad.

----------

### 🔹 Pasos de Instalación

1.  **Clonar el repositorio**

    ```sh
    git clone https://github.com/shirleytatianag/tx-portal-back.git
    ```    

2. Ingresa al directorio del proyecto:
   ```sh
   cd tx-portal-back
   ```
3. Compila el proyecto con Maven:
   ```sh
   gradle build
   ```
4. Ejecuta el backend:
   ```sh
   gradle bootRun
    ```

----------

## 📄 Documentación de la API

Este backend cuenta con **Swagger** para visualizar y probar los endpoints.  

Una vez en ejecución em ambiente local, accede a:

👉 [http://localhost:8009/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

#### La API desplegada en Azure está disponible en el siguiente enlace:

👉 [https://api-tx-portal.azurewebsites.net/app/swagger-ui/index.html#/](https://api-tx-portal.azurewebsites.net/app/swagger-ui/index.html#/)

----------

## 📬 Contacto

📧 **shirleytg339@gmail.com**  
💻 Proyecto desarrollado por **Shirley Garcia**

----------

