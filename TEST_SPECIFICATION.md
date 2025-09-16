# Test Specification - Input/Output Pairs

## Test Cases Based on Expected Behavior

### SUCCESS SCENARIOS

#### 1. CREATE USER - Valid Data
**Input:**
```json
POST /api/users
{
    "fullName": "Maria Silva",
    "email": "maria@email.com",
    "phone": "+55 11 88888-8888",
    "birthDate": "1985-03-20",
    "userType": "EDITOR"
}
```
**Expected Output:**
```json
Status: 201 Created
{
    "id": 2,
    "fullName": "Maria Silva",
    "email": "maria@email.com",
    "phone": "+55 11 88888-8888",
    "birthDate": "1985-03-20",
    "userType": "EDITOR"
}
```

#### 2. GET ALL USERS - Empty List
**Input:**
```
GET /api/users
```
**Expected Output:**
```json
Status: 200 OK
[]
```

#### 3. GET ALL USERS - With Data
**Input:**
```
GET /api/users
```
**Expected Output:**
```json
Status: 200 OK
[
    {
        "id": 1,
        "fullName": "João Silva",
        "email": "joao@email.com",
        "phone": "+55 11 99999-9999",
        "birthDate": "1990-05-15",
        "userType": "ADMIN"
    }
]
```

#### 4. GET USER BY ID - Found
**Input:**
```
GET /api/users/1
```
**Expected Output:**
```json
Status: 200 OK
{
    "id": 1,
    "fullName": "João Silva",
    "email": "joao@email.com",
    "phone": "+55 11 99999-9999",
    "birthDate": "1990-05-15",
    "userType": "ADMIN"
}
```

#### 5. UPDATE USER - Valid Data
**Input:**
```json
PUT /api/users/1
{
    "fullName": "João Santos Updated",
    "email": "joao.updated@email.com",
    "phone": "+55 11 77777-7777",
    "birthDate": "1990-05-15",
    "userType": "VIEWER"
}
```
**Expected Output:**
```json
Status: 200 OK
{
    "id": 1,
    "fullName": "João Santos Updated",
    "email": "joao.updated@email.com",
    "phone": "+55 11 77777-7777",
    "birthDate": "1990-05-15",
    "userType": "VIEWER"
}
```

#### 6. DELETE USER - Success
**Input:**
```
DELETE /api/users/1
```
**Expected Output:**
```
Status: 204 No Content
(empty body)
```

### ERROR SCENARIOS

#### 7. CREATE USER - Multiple Invalid Fields
**Input:**
```json
POST /api/users
{
    "fullName": "",
    "email": "email-invalido",
    "phone": "telefone-invalido",
    "birthDate": "2030-01-01",
    "userType": null
}
```
**Expected Output:**
```json
Status: 400 Bad Request
{
    "fieldErrors": {
        "fullName": "Full name is required",
        "email": "Email must have a valid format",
        "phone": "Phone must be in international format (ex: +55 11 99999-9999)",
        "birthDate": "Birth date must be in the past",
        "userType": "User type is required"
    },
    "error": "Validation error",
    "status": 400
}
```

#### 8. CREATE USER - Duplicate Email
**Input:**
```json
POST /api/users
{
    "fullName": "Outro Usuario",
    "email": "maria@email.com",
    "phone": "+55 11 99999-9999",
    "birthDate": "1990-01-01",
    "userType": "ADMIN"
}
```
**Expected Output:**
```json
Status: 400 Bad Request
{
    "fieldErrors": {
        "email": "Email is already in use"
    },
    "error": "Validation error",
    "status": 400
}
```

#### 9. CREATE USER - Duplicate Phone
**Input:**
```json
POST /api/users
{
    "fullName": "Novo Usuario",
    "email": "novo@email.com",
    "phone": "+55 11 88888-8888",
    "birthDate": "1992-06-15",
    "userType": "VIEWER"
}
```
**Expected Output:**
```json
Status: 400 Bad Request
{
    "fieldErrors": {
        "phone": "Phone number is already in use"
    },
    "error": "Validation error",
    "status": 400
}
```

#### 10. CREATE USER - Invalid User Type
**Input:**
```json
POST /api/users
{
    "fullName": "Test User",
    "email": "test@email.com",
    "phone": "+55 11 66666-6666",
    "birthDate": "1995-12-25",
    "userType": "INVALID_TYPE"
}
```
**Expected Output:**
```json
Status: 400 Bad Request
{
    "fieldErrors": {
        "userType": "Invalid user type. Accepted values: ADMIN, EDITOR, VIEWER"
    },
    "error": "Validation error",
    "status": 400
}
```

#### 11. GET USER BY ID - Not Found
**Input:**
```
GET /api/users/999
```
**Expected Output:**
```json
Status: 404 Not Found
{
    "fieldErrors": {
        "id": "User not found with ID: 999"
    },
    "error": "Resource not found",
    "status": 404
}
```

#### 12. UPDATE USER - Not Found
**Input:**
```json
PUT /api/users/999
{
    "fullName": "Test Update",
    "email": "update@email.com",
    "phone": "+55 11 55555-5555",
    "birthDate": "1988-07-10",
    "userType": "ADMIN"
}
```
**Expected Output:**
```json
Status: 404 Not Found
{
    "fieldErrors": {
        "id": "User not found with ID: 999"
    },
    "error": "Resource not found",
    "status": 404
}
```

#### 13. UPDATE USER - Invalid Data
**Input:**
```json
PUT /api/users/1
{
    "fullName": "",
    "email": "email-invalido",
    "phone": "telefone-invalido",
    "birthDate": "2030-01-01",
    "userType": null
}
```
**Expected Output:**
```json
Status: 400 Bad Request
{
    "fieldErrors": {
        "fullName": "Full name is required",
        "email": "Email must have a valid format",
        "phone": "Phone must be in international format (ex: +55 11 99999-9999)",
        "birthDate": "Birth date must be in the past",
        "userType": "User type is required"
    },
    "error": "Validation error",
    "status": 400
}
```

#### 14. UPDATE USER - Duplicate Email
**Input:**
```json
PUT /api/users/1
{
    "fullName": "João Santos",
    "email": "maria@email.com",
    "phone": "+55 11 77777-7777",
    "birthDate": "1990-05-15",
    "userType": "VIEWER"
}
```
**Expected Output:**
```json
Status: 400 Bad Request
{
    "fieldErrors": {
        "email": "Email is already in use"
    },
    "error": "Validation error",
    "status": 400
}
```

#### 15. UPDATE USER - Duplicate Phone
**Input:**
```json
PUT /api/users/1
{
    "fullName": "João Santos",
    "email": "joao.santos@email.com",
    "phone": "+55 11 88888-8888",
    "birthDate": "1990-05-15",
    "userType": "VIEWER"
}
```
**Expected Output:**
```json
Status: 400 Bad Request
{
    "fieldErrors": {
        "phone": "Phone number is already in use"
    },
    "error": "Validation error",
    "status": 400
}
```

#### 16. DELETE USER - Not Found
**Input:**
```
DELETE /api/users/999
```
**Expected Output:**
```json
Status: 404 Not Found
{
    "fieldErrors": {
        "id": "User not found with ID: 999"
    },
    "error": "Resource not found",
    "status": 404
}
```

### EDGE CASES

#### 17. CREATE USER - Valid Phone Format Variations
**Input:**
```json
POST /api/users
{
    "fullName": "Test Phone",
    "email": "phone@test.com",
    "phone": "+1 12 12345-6789",
    "birthDate": "1990-01-01",
    "userType": "ADMIN"
}
```
**Expected Output:**
```json
Status: 201 Created
{
    "id": 3,
    "fullName": "Test Phone",
    "email": "phone@test.com",
    "phone": "+1 12 12345-6789",
    "birthDate": "1990-01-01",
    "userType": "ADMIN"
}
```

#### 18. UPDATE USER - Same Email/Phone (Should Allow)
**Input:**
```json
PUT /api/users/1
{
    "fullName": "João Silva Updated",
    "email": "joao@email.com",
    "phone": "+55 11 99999-9999",
    "birthDate": "1990-05-15",
    "userType": "EDITOR"
}
```
**Expected Output:**
```json
Status: 200 OK
{
    "id": 1,
    "fullName": "João Silva Updated",
    "email": "joao@email.com",
    "phone": "+55 11 99999-9999",
    "birthDate": "1990-05-15",
    "userType": "EDITOR"
}
```

## TEST EXECUTION PLAN

1. **Setup Phase**: Create test data
2. **Success Tests**: Execute tests 1-6
3. **Validation Error Tests**: Execute tests 7-10, 13
4. **Not Found Error Tests**: Execute tests 11-12, 16
5. **Duplicate Error Tests**: Execute tests 8-9, 14-15
6. **Edge Case Tests**: Execute tests 17-18
7. **Cleanup Phase**: Verify state

## EXPECTED RESULTS SUMMARY

- **Total Tests**: 18
- **Success Scenarios**: 6
- **Validation Errors**: 6
- **Not Found Errors**: 3
- **Duplicate Errors**: 4
- **Edge Cases**: 2

All tests should pass with the expected status codes and response formats as documented.