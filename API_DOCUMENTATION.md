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
- **Query params opcionales**:
  - `title`: texto parcial a buscar en el título.
  - `city`: ciudad exacta (no sensible a mayúsculas/minúsculas).
  - `district`: distrito exacto (no sensible a mayúsculas/minúsculas).
  - `propertyType`: tipo de propiedad (`APARTMENT`, `HOUSE`, `OFFICE`, `LAND`).
  - `operationType`: tipo de operación (`SALE`, `RENTAL`).
  - Parámetros de paginación estándar (`page`, `size`, `sort`) compatibles con Spring Data, por ejemplo `?page=0&size=9&sort=price,asc`.
- **Ejemplo**: `/api/properties?city=Lima&operationType=SALE&page=0&size=6`
- **Respuesta (`PropertyPageDTO`)**:
  ```json
  {
    "content": [
      {
        "id": 12,
        "title": "Departamento en Miraflores",
        "price": 180000.00,
        "city": "Lima",
        "district": "Miraflores",
        "firstImageUrl": "https://cdn.example.com/img/depto1.jpg",
        "amenities": [
          "Piscina",
          "Gimnasio"
        ]
      }
    ],
    "currentPage": 0,
    "totalPages": 3,
    "totalElements": 18,
    "pageSize": 6
  }
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
    ],
    "amenities": [
      "Piscina",
      "Gimnasio",
      "Ascensor"
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
- **Descripción**: Endpoints relacionados con favoritos, mensajería u otras operaciones internas comparten el mismo esquema de autenticación. Siempre enviar `Authorization: Bearer <token>`.

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

### POST /api/messages
- **Descripción**: Envía un nuevo mensaje al propietario de un anuncio.
- **Request body (`MessageCreateDTO`)**:
  ```json
  {
    "propertyId": 12,
    "receiverId": 5,
    "messageContent": "Hola, ¿sigue disponible?"
  }
  ```
- **Respuesta (`MessageResponseDTO`)**:
  ```json
  {
    "id": 48,
    "propertyId": 12,
    "senderId": 9,
    "receiverId": 5,
    "messageContent": "Hola, ¿sigue disponible?",
    "isRead": false,
    "sentAt": "2025-11-16T23:45:12.398Z"
  }
  ```

### GET /api/messages/conversations
- **Descripción**: Devuelve la bandeja de entrada del usuario autenticado agrupando conversaciones por anuncio y contraparte.
- **Respuesta (`List<ConversationSummaryDTO>`)**:
  ```json
  [
    {
      "propertyId": 12,
      "propertyTitle": "Departamento en Miraflores",
      "otherUserId": 5,
      "otherUserName": "Ana Pérez",
      "lastMessageContent": "Gracias, te aviso pronto"
    },
    {
      "propertyId": 0,
      "propertyTitle": null,
      "otherUserId": 14,
      "otherUserName": "Carlos López",
      "lastMessageContent": "¿Podemos coordinar una llamada?"
    }
  ]
  ```
  > `propertyId` vale `0` cuando la conversación no está asociada a un anuncio específico.

### GET /api/messages/conversations/{propertyId}/{otherUserId}
- **Descripción**: Obtiene todo el historial de mensajes entre el usuario autenticado y el otro participante para el anuncio indicado.
- **Respuesta (`List<MessageResponseDTO>`)**:
  ```json
  [
    {
      "id": 40,
      "propertyId": 12,
      "senderId": 9,
      "receiverId": 5,
      "messageContent": "Hola, ¿sigue disponible?",
      "isRead": false,
      "sentAt": "2025-11-16T20:41:15.123Z"
    },
    {
      "id": 41,
      "propertyId": 12,
      "senderId": 5,
      "receiverId": 9,
      "messageContent": "Sí, podemos agendar una visita",
      "isRead": false,
      "sentAt": "2025-11-16T20:42:03.501Z"
    }
  ]
  ```
  > Usa `0` como `propertyId` en la URL para recuperar conversaciones sin anuncio.

## Notas para el frontend
- Todas las solicitudes deben enviar `Content-Type: application/json` cuando haya body.
- Para llamadas autenticadas, almacenar el token JWT y renovarlo cuando expire (`expiresIn` se expresa en milisegundos).
- Si el backend rechaza una petición con `401`, reintentar autenticando nuevamente.
