# System Design and UML Diagrams
## Apartment Maintenance and Resident Complaint Management System
**Course Alignment:** 25ITT33 - Software Engineering (Units 2 & 3)  
**Scenario:** 18 (Apartment Maintenance and Resident Complaint Management System)

---

## 1. System Architecture Overview
The system follows a classic **3-Tier Layered Architecture** augmented with an **Exploratory Data Science & Visualizations Engine**.

```mermaid
graph TD
    subgraph "Tier 1: Presentation Layer"
        UI_CLI["Interactive Java Console CLI (Main.java)"]
        UI_NOTEBOOK["Jupyter Interactive Notebook / Python Scripts"]
    end

    subgraph "Tier 2: Business Logic & Service Layer"
        SRV["ApartmentService.java"]
        VAL["InputValidator.java"]
        FMT["TableFormatter.java"]
        EXC["Custom Exceptions (ApartmentException, etc.)"]
        EDA["Python Preprocessing & Feature Engineering"]
    end

    subgraph "Tier 3: Data Access Layer (DAO)"
        ADAO["ApartmentDAO.java"]
        RDAO["ResidentDAO.java"]
        MDAO["MaintenanceDAO.java"]
        CDAO["ComplaintDAO.java"]
        DBCONN["DatabaseConnection.java (JDBC Singleton)"]
    end

    subgraph "Tier 4: Relational Persistence Layer"
        DB[("SQLite / MySQL Database (apartment_system.db)")]
        T1["apartments"]
        T2["residents"]
        T3["maintenance_payments"]
        T4["complaints"]
    end

    UI_CLI --> SRV
    SRV --> VAL
    SRV --> FMT
    SRV --> EXC
    SRV --> ADAO
    SRV --> RDAO
    SRV --> MDAO
    SRV --> CDAO
    ADAO --> DBCONN
    RDAO --> DBCONN
    MDAO --> DBCONN
    CDAO --> DBCONN
    DBCONN --> DB

    UI_NOTEBOOK --> EDA
    EDA --> DB
    DB --- T1
    DB --- T2
    DB --- T3
    DB --- T4
```

---

## 2. Use Case Diagram

### 2.1 Actors
1. **Resident:** Submits grievances, checks apartment details, reviews maintenance dues.
2. **Apartment Administrator:** Manages apartment units, assigns residents, oversees monthly billing, reviews financial reports.
3. **Maintenance Supervisor / Technician:** Reviews assigned service complaints, updates progress, marks complaints resolved.
4. **Data Analyst / Auditor:** Executes Python analytics, examines facility health trends and payment recovery metrics.

### 2.2 Use Case Diagram (Mermaid)

```mermaid
usecaseDiagram
    actor Resident as "Resident"
    actor Admin as "Apartment Admin"
    actor Staff as "Maintenance Staff"
    actor Analyst as "Data Analyst"

    package "Apartment Management System" {
        usecase UC1 as "Register Apartment Unit"
        usecase UC2 as "Manage Resident Profiles"
        usecase UC3 as "Generate Maintenance Bills"
        usecase UC4 as "Record Maintenance Payment"
        usecase UC5 as "View Outstanding Dues"
        usecase UC6 as "Register Facility Complaint"
        usecase UC7 as "Track & Update Complaint Status"
        usecase UC8 as "View Category-wise Reports"
        usecase UC9 as "Run EDA & Generate Visualizations"
    }

    Admin --> UC1
    Admin --> UC2
    Admin --> UC3
    Admin --> UC4
    Admin --> UC5
    Admin --> UC8

    Resident --> UC4
    Resident --> UC5
    Resident --> UC6

    Staff --> UC7
    Staff --> UC8

    Analyst --> UC9
    Analyst --> UC8
```

### 2.3 Detailed Use Case Specifications

| Use Case ID | Use Case Name | Primary Actor | Pre-conditions | Main Flow | Post-conditions |
|---|---|---|---|---|---|
| **UC-01** | Register Resident | Admin | Unit must exist and be vacant | Admin enters resident name, email, phone, moves in. | Unit status set to 'Occupied'. |
| **UC-02** | Record Payment | Admin / Resident | Pending bill exists | User provides Payment ID, amount, and mode. | Status changed to 'Paid', receipt logged. |
| **UC-03** | File Complaint | Resident | Resident is registered | Resident selects category, enters description & priority. | New ticket created in 'Open' state. |
| **UC-04** | Resolve Complaint| Staff / Admin | Ticket is Open / In Progress | Technician updates status to 'Resolved' with remarks. | Resolution date captured, ticket closed. |
| **UC-05** | Generate EDA | Data Analyst | Python environment active | Analyst runs `eda_visualization.py`. | 6 high-res PNG charts generated. |

---

## 3. Class Diagram

The class diagram below illustrates the Object-Oriented design implemented in Java, displaying classes, attributes, methods, access modifiers, and associations.

```mermaid
classDiagram
    class Apartment {
        -String apartmentId
        -String blockNumber
        -int floorNumber
        -String apartmentType
        -int squareFeet
        -String occupancyStatus
        +getApartmentId() String
        +setApartmentId(String) void
        +getBlockNumber() String
        +getFloorNumber() int
        +getApartmentType() String
        +getSquareFeet() int
        +getOccupancyStatus() String
        +setOccupancyStatus(String) void
        +toString() String
    }

    class Resident {
        -String residentId
        -String name
        -String email
        -String phone
        -String apartmentId
        -String residentType
        -String moveInDate
        -String emergencyContact
        +getResidentId() String
        +getName() String
        +getEmail() String
        +getPhone() String
        +getApartmentId() String
        +getResidentType() String
        +getMoveInDate() String
        +getEmergencyContact() String
        +toString() String
    }

    class MaintenancePayment {
        -String paymentId
        -String apartmentId
        -String residentId
        -String billingMonth
        -double amountDue
        -double amountPaid
        -String dueDate
        -String paymentDate
        -String paymentMode
        -String paymentStatus
        +getPaymentId() String
        +getAmountDue() double
        +getAmountPaid() double
        +getBalance() double
        +getPaymentStatus() String
        +setPaymentStatus(String) void
        +toString() String
    }

    class Complaint {
        -String complaintId
        -String residentId
        -String apartmentId
        -String category
        -String priority
        -String description
        -String complaintDate
        -String resolutionDate
        -String assignedStaff
        -String status
        -String residentRemarks
        +getComplaintId() String
        +getCategory() String
        +getPriority() String
        +getStatus() String
        +setStatus(String) void
        +setResolutionDate(String) void
        +toString() String
    }

    class ApartmentService {
        -ApartmentDAO apartmentDAO
        -ResidentDAO residentDAO
        -MaintenanceDAO maintenanceDAO
        -ComplaintDAO complaintDAO
        +getAllApartments() List~Apartment~
        +registerApartment(Apartment) void
        +getAllResidents() List~Resident~
        +registerResident(Resident) void
        +getPendingPayments() List~MaintenancePayment~
        +processPayment(String, double, String) void
        +fileComplaint(Complaint) void
        +updateComplaintStatus(String, String, String, String) void
        +getComplaintCountByCategory() Map~String, Long~
    }

    class DatabaseConnection {
        -Connection connection$
        +getConnection()$ Connection
        +closeConnection()$ void
    }

    Apartment "1" <-- "0..*" Resident : occupies
    Apartment "1" <-- "0..*" MaintenancePayment : billed_to
    Resident "1" <-- "0..*" MaintenancePayment : pays
    Resident "1" <-- "0..*" Complaint : logs
    Apartment "1" <-- "0..*" Complaint : occurs_in
    ApartmentService ..> Apartment
    ApartmentService ..> Resident
    ApartmentService ..> MaintenancePayment
    ApartmentService ..> Complaint
    ApartmentService ..> DatabaseConnection
```

---

## 4. Sequence Diagrams

### 4.1 Complaint Registration and Resolution Flow
```mermaid
sequenceDiagram
    autonumber
    actor Resident as Resident
    participant UI as Console CLI (Main)
    participant SVC as ApartmentService
    participant DAO as ComplaintDAO
    participant DB as SQLite Database
    actor Staff as Facility Technician

    Resident->>UI: Enter complaint details (Water Supply, Leakage)
    UI->>SVC: fileComplaint(Complaint c)
    SVC->>SVC: validate inputs
    SVC->>DAO: registerComplaint(c)
    DAO->>DB: INSERT INTO complaints VALUES (...)
    DB-->>DAO: 1 Row Affected
    DAO-->>SVC: true
    SVC-->>UI: Success Notification
    UI-->>Resident: Ticket Generated (CMP-2026-016)

    Staff->>UI: Update status to 'Resolved'
    UI->>SVC: updateComplaintStatus("CMP-2026-016", "Resolved", "Ramesh", "Fixed pipe valve")
    SVC->>DAO: updateStatus(...)
    DAO->>DB: UPDATE complaints SET status='Resolved'...
    DB-->>DAO: 1 Row Affected
    DAO-->>SVC: true
    SVC-->>UI: Resolution Recorded
```
