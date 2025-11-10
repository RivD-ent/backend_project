# Backend Project API

Base URL (desarrollo): `http://localhost:8080`

La API usa JWT para autenticar peticiones protegidas. Obtén un token llamando a `POST /api/auth/login` y envíalo en la cabecera `Authorization: Bearer <token>`.

## Endpoints públicos

### POST /api/users/register
- **Descripción**: Registra un nuevo usuario en el sistema.
- **Request body (`UserCreateDTO`)**:
  ```json
  {
    "name": "Jane Doe",
    "email": "jane@example.com",
    "password": "secretoSeguro",
    "phoneNumber": "+51 999999999"
  }
  ```
- **Respuesta (`UserResponseDTO`)**:
  ```json
  {
    "id": 7,
    "name": "Jane Doe",
    "email": "jane@example.com",
    "phoneNumber": "+51 999999999",
    "createdAt": "2025-11-08T07:15:32.123Z",
    "updatedAt": "2025-11-08T07:15:32.123Z"
  }
  ```

### POST /api/auth/login
- **Descripción**: Autentica credenciales y devuelve un token JWT para futuras peticiones.
- **Request body (`AuthRequestDTO`)**:
  ```json
  {
    "email": "jane@example.com",
    "password": "secretoSeguro"
  }
  ```
- **Respuesta (`AuthResponseDTO`)**:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 3600000,
    "tokenType": "Bearer"
  }
  ```

### GET /api/properties
- **Descripción**: Lista propiedades visibles públicamente para mostrar en catálogos o landing.
- **Respuesta (`List<PropertyListDTO>`)**:
  ```json
  [
    {
      "id": 12,
      "title": "Departamento en Miraflores",
      "price": 180000.00,
      "city": "Lima",
      "district": "Miraflores",
      "firstImageUrl": "https://cdn.example.com/img/depto1.jpg"
    }
  ]
  ```

### GET /api/properties/{id}
- **Descripción**: Devuelve el detalle completo de una propiedad pública.
- **Respuesta (`PropertyResponseDTO`)**:
  ```json
  {
    "id": 12,
    "title": "Departamento en Miraflores",
    "description": "Vista al mar",
    "price": 180000.00,
    "address": "Av. Larco 123",
    "city": "Lima",
    "district": "Miraflores",
    "operationType": "SALE",
    "propertyType": "APARTMENT",
    "bedrooms": 3,
    "bathrooms": 2,
    "areaSqMeters": 120,
    "contactName": "Ana Pérez",
    "contactPhone": "+51 988777666",
    "googleMapsUrl": "https://maps.google.com/...",
    "status": "ACTIVE",
    "ownerId": 5,
    "createdAt": "2025-11-08T05:01:22.510Z",
    "updatedAt": "2025-11-08T05:01:22.510Z",
    "imageUrls": [
      "https://cdn.example.com/img/depto1.jpg",
      "https://cdn.example.com/img/depto1b.jpg"
    ]
  }
  ```

## Endpoints protegidos (Authorization: Bearer <token>)

### POST /api/properties
- **Descripción**: Crea una nueva propiedad asociándola al usuario autenticado.
- **Request body (`PropertyCreateDTO`)**:
  ```json
  {
    "title": "Casa en Surco",
    "description": "Amplio jardín",
    "price": 250000.00,
    "address": "Calle Las Flores 456",
    "city": "Lima",
    "district": "Surco",
    "operationType": "SALE",
    "propertyType": "HOUSE",
    "bedrooms": 4,
    "bathrooms": 3,
    "areaSqMeters": 280,
    "contactName": "Luis León",
    "contactPhone": "+51 955555555",
    "googleMapsUrl": "https://maps.google.com/...",
    "amenityIds": [1, 3, 6],
    "imageUrls": [
      "https://cdn.example.com/img/casa1.jpg"
    ]
  }
  ```
- **Respuesta (`PropertyResponseDTO`)**: igual formato que en `GET /api/properties/{id}`.

### GET /api/users/me/properties
- **Descripción**: Lista las propiedades creadas por el usuario autenticado para gestionarlas en el panel personal.
- **Respuesta (`List<PropertyResponseDTO>`)**: arreglo de propiedades completas pertenecientes al usuario.

### PUT /api/properties/{id}
- **Descripción**: Actualiza campos de una propiedad existente.
- **Request body (`PropertyUpdateDTO`)**:
  ```json
  {
    "price": 240000.00,
    "imageUrls": [
      "https://cdn.example.com/img/casa1.jpg",
      "https://cdn.example.com/img/casa1-nueva.jpg"
    ]
  }
  ```
- **Respuesta (`PropertyResponseDTO`)**: propiedad actualizada.

### DELETE /api/properties/{id}
- **Descripción**: Elimina lógicamente la propiedad (o la remueve definitivamente, según capa de servicio actual).
- **Respuesta**: `204 No Content`.

### Otros recursos protegidos
- **Descripción**: Endpoints relacionados con favoritos, mensajes u otras operaciones internas compartirán el mismo esquema de autenticación. Siempre enviar `Authorization: Bearer <token>`.

### POST /api/favorites
- **Descripción**: Marca una propiedad como favorita para el usuario autenticado.
- **Request body**:
  ```json
  {
    "propertyId": 12
  }
  ```
- **Respuesta (`PropertyResponseDTO`)**: detalle de la propiedad marcada como favorita.

### GET /api/favorites
- **Descripción**: Devuelve la lista de propiedades que el usuario autenticado tiene como favoritas.
- **Respuesta (`List<PropertyListDTO>`)**: arreglo con información resumida de cada propiedad favorita.

### DELETE /api/favorites/{propertyId}
- **Descripción**: Quita la propiedad indicada de los favoritos del usuario autenticado.
- **Respuesta**: `204 No Content`.

## Notas para el frontend
- Todas las solicitudes deben enviar `Content-Type: application/json` cuando haya body.
- Para llamadas autenticadas, almacenar el token JWT y renovarlo cuando expire (`expiresIn` se expresa en milisegundos).
- Si el backend rechaza una petición con `401`, reintentar autenticando nuevamente.
