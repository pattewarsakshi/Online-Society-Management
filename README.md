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

POST /api/auth/register-super-admin  → Register Super Admin
POST /api/auth/login                 → Login (JWT)
POST /api/super-admin/societies      → Create Society

Key Highlights

🔐 JWT-based stateless authentication

🛡️ Method-level authorization only (@PreAuthorize)

🧱 ER diagram as source of truth

🏢 Society-centric architecture

🚫 No flat/property data inside User

🚫 No role logic inside SecurityConfig

🧩 DTO ≠ Entity (strict separation)

🔁 Transactional business operations

🧠 Architectural Philosophy

Users represent identity.
Properties represent ownership and occupancy.
Society is the root aggregate.

This system avoids common anti-patterns (fat User entity, role checks in filters, endpoint-based authorization) and is designed to scale for billing, parking, visitors, and audits.

🗂️ Domain Model
User (Identity & Authentication Only)
User
- user_id (PK)
- full_name
- email (unique)
- phone (unique)
- password (hashed)
- role (ENUM)
- society_id (FK, nullable)

Society (Root Aggregate)
Society
- society_id (PK)
- society_name
- address
- city
- state
- pincode
- created_by (SUPER_ADMIN)

Property / Flat
Property
- property_id (PK)
- society_id (FK)
- flat_number
- block
- floor
- area_sqft
- owner_user_id (FK → User)
- tenant_user_id (FK → User, nullable)
- status (VACANT / OCCUPIED)

🔐 Roles
SUPER_ADMIN
ADMIN
OWNER
TENANT
GUARD

Rules

Roles are stored in the database

Authorities follow ROLE_<ROLE>

Roles are never hardcoded in filters

Authorization is annotation-driven only

🔒 Security Design
Authentication

JWT-based

Stateless sessions

/api/auth/** is public

JWT payload contains:

email

role

societyId

Authorization

Implemented only via @PreAuthorize

No role checks in SecurityConfig

No endpoint-based authorization

Example:

@PreAuthorize("hasRole('ADMIN')")

🔁 Authentication Flow

User logs in via /api/auth/login

JWT is issued

JWT filter validates token

SecurityContext is populated

@PreAuthorize enforces access

🚀 Implemented Features
1️⃣ Super Admin Bootstrap
POST /api/auth/register-super-admin


Creates the first system-level SUPER_ADMIN.

2️⃣ Society Management
POST /api/societies
Authorization: SUPER_ADMIN


Creates societies (root aggregate).

3️⃣ User Registration (Generic)
POST /api/users/register
Authorization: ADMIN | SUPER_ADMIN


Used for:

TENANT

GUARD

ADMIN

Creates identity only, no property data.

4️⃣ Owner Onboarding (Transactional)
POST /api/societies/{societyId}/owners
Authorization: ADMIN


Atomic operation:

Create OWNER user

Create PROPERTY

Link property → owner

Mark property as OCCUPIED

Ownership is always tied to a society and a flat.

5️⃣ Tenant Assignment
PUT /api/properties/{propertyId}/assign-tenant
Authorization: ADMIN


Business rules:

Property must exist

Tenant must exist

Role must be TENANT

Property status updated accordingly

6️⃣ Maintenance Module

Create maintenance:

POST /api/properties/{propertyId}/maintenance
Authorization: ADMIN


View personal maintenance:

GET /api/my/maintenance
Authorization: OWNER | TENANT

🔄 Why URLs Differ (Design Rationale)
Operation	Endpoint	Reason
Create OWNER	/api/societies/{id}/owners	Ownership is society-scoped
Create TENANT	/api/users/register	Identity-first lifecycle
Assign TENANT	/api/properties/{id}/assign-tenant	Occupancy is property behavior

URLs reflect domain ownership and lifecycle, not tables.

⚠️ Error Handling

Centralized @RestControllerAdvice

Validation errors → 400 Bad Request

Duplicates → 409 Conflict

Authentication → 401

Authorization → 403

No silent failures

🧪 Tech Stack

Java 17+

Spring Boot

Spring Security

JWT

Hibernate / JPA

Lombok

Maven

MySQL / PostgreSQL

🔜 Planned Enhancements

Tenant vacate flow

Maintenance payment tracking

Parking allocation

Visitor & guard workflow

Society-level reports

