# Rest Advice Controller - Guía de Implementación

## Archivos Agregados

### 1. **ApiResponse.java** (dto/)
Clase genérica para respuestas estándar de API
- `code`: Código HTTP
- `message`: Mensaje descriptivo  
- `data`: Datos de respuesta
- `error`: Detalles de error

### 2. **ErrorDetails.java** (dto/)
Detalles estructurados de errores
- `detail`: Descripción del error
- `path`: Ruta donde ocurrió
- `timestamp`: Hora del error

### 3. **RestAdviceController.java** (controller/)
Controlador global para manejar excepciones
- Centraliza manejo de errores
- Mapea excepciones a códigos HTTP
- Retorna respuestas consistentes

### 4. Excepciones Personalizadas (controller/)
- **BadRequestException**: 400
- **ResourceNotFoundException**: 404  
- **InternalServerErrorException**: 505

## Uso en Servicios/Controladores

```java
// En servicio, lanzar excepciones específicas
if (producto == null) {
    throw new ResourceNotFoundException("Producto no encontrado");
}

if (!isValid(producto)) {
    throw new BadRequestException("Datos inválidos");
}
```

## Respuesta Éxitosa
```json
{
  "code": 200,
  "message": "Success",
  "data": { /* objeto */ }
}
```

## Respuesta de Error
```json
{
  "code": 404,
  "message": "Not Found",
  "error": {
    "detail": "Producto no encontrado",
    "path": "/product/999",
    "timestamp": "2025-05-25T19:30:00"
  }
}
```

## Códigos Implementados
- **200**: OK
- **201**: Created
- **400**: Bad Request
- **404**: Not Found
- **505**: Internal Server Error
