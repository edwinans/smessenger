# Simple Messenger REST API

## Architecture
```mermaid
flowchart TD
    A[Client] <--> F[DTO]
    F <--> B[Controller]
    B --> C[Service]
    C --> D[Repository]
    D --> E[Model]
```
