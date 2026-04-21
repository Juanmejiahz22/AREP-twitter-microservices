# Twitter Microservices

> **Autores:** Juan Jose Mejia & David Castro

## Descripción

Refactor de la arquitectura monolítica del proyecto Twitter Secure hacia una arquitectura de microservicios serverless. Utiliza AWS Lambda, API Gateway, y DynamoDB para escalabilidad y eficiencia.

Este proyecto es la **segunda fase** del bootcamp académico de Arquitectura de Software, donde se descompone el monolito en servicios independientes.

## Estado del Proyecto

🔨 **En construcción** - Versión inicial en desarrollo

## Estructura Planeada

```
twitter-microservices/
├── services/
│   ├── auth-service/           # Gestión de autenticación
│   ├── post-service/           # CRUD de posts
│   ├── profile-service/        # Perfiles de usuarios
│   └── feed-service/           # Cálculo y caché del feed
├── shared/
│   ├── utils/                  # Funciones compartidas
│   ├── middlewares/            # Middlewares comunes
│   └── types/                  # Tipos TypeScript globales
├── infrastructure/
│   ├── terraform/              # IaC para AWS
│   └── config/                 # Configuraciones
├── frontend-react/             # Frontend mejorado
└── README.md
```

## Tecnologías Base

- **Compute:** AWS Lambda (Node.js 20)
- **API:** API Gateway
- **Database:** DynamoDB
- **Cache:** ElastiCache (Redis)
- **IaC:** Terraform
- **Monitoring:** CloudWatch

## Diferencias con el Monolito

| Aspecto | Monolito | Microservicios |
|--------|----------|-------------------|
| Lenguaje Backend | Java 21 + Spring Boot | Node.js + Lambda |
| Base de Datos | In-Memory | DynamoDB |
| Escalabilidad | Vertical | Horizontal (serverless) |
| Despliegue | JAR único | Funciones independientes |
| Latencia | HTTP local | HTTPS + API Gateway |

## Próximos Pasos

- [ ] Configurar AWS Lambda
- [ ] Crear API Gateway
- [ ] Implementar DynamoDB schema
- [ ] Migrar servicios del monolito
- [ ] Tests para microservicios
- [ ] CI/CD con GitHub Actions
- [ ] Documentación de deployment

## Cómo Ejecutar (Coming Soon)

```bash
# Se completará cuando el proyecto esté listo
npm install
npm run deploy
```

## Licencia

MIT

---

Para más información sobre la fase anterior (monolito), ver: [AREP-Monolito](https://github.com/Juanmejiahz22/AREP-Monolito)
