Society Management System – Backend
(Authentication & Authorization)

JWT-based authentication

Stateless security

Role-based access control

Roles-
Role	Purpose
SUPER_ADMIN	System owner, creates societies & admins
ADMIN	Manages users inside a society
OWNER	Flat owner
TENANT	Flat tenant
GUARD	Visitor & entry management
Modules Implemented
1.Authentication

Login with email + password

JWT token generation

Role embedded in token

JWT filter validates every request

2️.User Module

User entity with validation

Admin/Super Admin can create users

Passwords encrypted using BCrypt

3️.Society Module

Only SUPER_ADMIN can create societies

Society is linked to creator (Super Admin)

Prevents duplicate societies

DTO-based API (no entity exposure)

🔑 Access Control Summary
API	Role Required
/api/auth/login	Public
/api/users/register	ADMIN, SUPER_ADMIN
/api/societies	SUPER_ADMIN
/admin/**	ADMIN / SUPER_ADMIN
/any-other	Authenticated
🧠 Design Decisions (WHY)

DTOs → prevent entity leaks

Service layer → business rules live here

JWT filter → single source of authentication

Role-based endpoints → predictable security

ER integrity preserved → future modules won’t break
