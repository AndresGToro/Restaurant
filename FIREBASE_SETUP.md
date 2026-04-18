# Firebase Setup (Produccion)

## Colecciones base (Firestore)
- `users/`
- `categories/`
- `products/`
- `orders/`
- `messages/`
- `settings/`

## Seguridad
- Reglas Firestore: `firestore.rules`
- Reglas Storage: `storage.rules`
- Reglas Realtime DB: `database.rules.json`

Despliegue:

```bash
firebase deploy --only firestore:rules,storage:rules,database
```

## Auth
Flujos implementados en app:
- Login
- Registro
- Recuperacion de contraseña
- Persistencia de sesion
- Logout

## Offline
- Firestore cache persistente habilitada en `FirebaseModule`.
- Cache local Room para productos (tabla `products_cache`).

## Integraciones productivas
- Crashlytics
- FCM
- Firebase Storage para imagenes comprimidas
- Firebase Functions (cliente callable)
