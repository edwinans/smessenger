# TODO

- [x] H2 in-memory DB
- [ ] PG DB backend
- [x] JWT authentification
  - [ ] Implement refresh token
- [x] Extend ~~`PagingAndSortingRepository`~~ (`JpaRepository`) in repository
- [x] Model: Use @Id, @GeneratedValue, @Columns
- [ ] Error handling using Exception mapping
- [ ] Swagger documentation
- [ ] Postman collection

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
4. Implement send/receive messages services
