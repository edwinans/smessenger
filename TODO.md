# TODO

- [x] H2 in-memory DB
- [x] PG DB backend
- [x] JWT authentification
  - [x] Move hardcoded secrets in a file
- [x] Extend ~~`PagingAndSortingRepository`~~ (`JpaRepository`) in repository
- [x] Model: Use @Id, @GeneratedValue, @Columns
- [x] Error handling using Exception mapping
- [x] Request schema validation
- [x] Document how to run
- [x] Swagger documentation
- [x] Produce smessenger.jar
- [x] Seperate prod and dev envs
- [x] Add Makefile
- [x] Postman collection
- [ ] Dockerize the app
- [ ] Pass DB credentials using env vars
- [ ] Improve javadoc and swagger documentation

## Evolutions

- [ ] Unit testing
- [ ] Return complete chat between two users
- [ ] Return total count for messages
- [ ] Implement JWT refresh token
- [ ] Use Flyway for better DB management

## Implementation Path

1. Working simple use case MVC
   1. User creation request (without authentification)
   2. User controller
   3. User service
   4. User repository
   5. User DAO
   6. User creation in memory/DB
2. Enrich User service with password
   1. Add password field to User
   2. Use hashing library
3. Implement user authentification
   1. Setup jwt token infra
   2. Establish jwt token when login with password
   3. Implement filter middleware to grant access to services using jwt token
4. Implement global exception handling and default errors mapping
5. Implement send messages model and services
6. Implement get messages model and services
7. Integrate a dockerized DB
