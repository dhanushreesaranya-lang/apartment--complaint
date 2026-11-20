# Software Requirements Specification (SRS)
## Apartment Maintenance and Resident Complaint Management System
**Document Identifier:** VCET-IT-2026-SCENARIO-18-SRS  
**Academic Year:** 2026 - 2027 (ODD Semester)  
**Class:** 25IT3A & 25IT3B  
**Institution:** Velalar College of Engineering and Technology (Autonomous), Erode – 638012  
**Department:** Department of Information Technology  
**Course Alignment:** 
- 25ITT33: Software Engineering
- 25ITT31: Database Management Systems
- 25ITT32: Java Programming
- 25ITC31: Foundations of Data Science

---

## 1. Introduction

### 1.1 Purpose
This Software Requirements Specification (SRS) document describes the complete functional, non-functional, external interface, and architectural requirements for the **Apartment Maintenance and Resident Complaint Management System** (Scenario 18). It serves as the formal baseline agreement between stakeholders, developers, software testers, and academic evaluators.

### 1.2 Scope of the System
The system is an enterprise management and analytics software designed to digitize, streamline, and optimize daily operations within residential apartment complexes. It eliminates manual ledger-based recordkeeping, reduces complaint resolution delays, provides transparent billing and automated dues tracking, and leverages exploratory data science to identify recurring facility breakdowns.

Key operational modules:
- Resident Profile and Tenancy Management
- Apartment Unit & Occupancy Tracking
- Monthly Maintenance Fee Demand, Collection, and Defaulter Tracking
- Resident Grievance / Complaint Lifecycle Management
- Python-powered Exploratory Data Analysis (EDA) and Facility Trend Visualizations

### 1.3 Definitions, Acronyms, and Abbreviations
- **SRS:** Software Requirements Specification
- **DBMS:** Database Management System
- **DAO:** Data Access Object
- **JDBC:** Java Database Connectivity
- **EDA:** Exploratory Data Analysis
- **1NF / 2NF / 3NF / BCNF:** First, Second, Third, and Boyce-Codd Normal Forms
- **UPI:** Unified Payments Interface
- **CLI:** Command-Line Interface

### 1.4 References
- IEEE Std 830-1998: IEEE Recommended Practice for Software Requirements Specifications.
- VCET Information Technology Curriculum & Syllabi (2026-2027 Regulations).
- Roger S. Pressman, *Software Engineering: A Practitioner's Approach*, 9th Edition.
- Abraham Silberschatz, Henry F. Korth, S. Sudarshan, *Database System Concepts*, 7th Edition.

---

## 2. Overall Description

### 2.1 Product Perspective
The Apartment Maintenance and Resident Complaint Management System operates as a modular, three-tier desktop and analytical platform. The system interacts with an embedded relational database (SQLite / MySQL) via JDBC in Java, while Python scripts and Jupyter notebooks interface with the same schema for descriptive and predictive data analytics.

```
+-----------------------------------------------------------------------+
|                         PRESENTATION LAYER                            |
|     Interactive Console CLI / Menu Interface (Java Application)       |
+-----------------------------------------------------------------------+
                                   |
                                   v
+-----------------------------------------------------------------------+
|                     BUSINESS LOGIC / SERVICE LAYER                    |
|      ApartmentService, InputValidator, TableFormatter, Exceptions      |
+-----------------------------------------------------------------------+
                                   |
                                   v
+-----------------------------------------------------------------------+
|                        DATA ACCESS OBJECT (DAO)                       |
|        ApartmentDAO, ResidentDAO, MaintenanceDAO, ComplaintDAO        |
+-----------------------------------------------------------------------+
                                   |
                                   v
+-----------------------------------------------------------------------+
|                        PERSISTENCE & DB LAYER                         |
|            SQLite / MySQL Relational Engine (schema.sql)              |
+-----------------------------------------------------------------------+
                                   ^
                                   |
+-----------------------------------------------------------------------+
|                    DATA SCIENCE & ANALYTICS LAYER                     |
|           Python 3.x (Pandas, NumPy, Matplotlib, Seaborn)             |
+-----------------------------------------------------------------------+
```

### 2.2 Product Functions
1. **Resident Management:** Creation, search, modification, and profiling of owners and tenants across multiple blocks.
2. **Unit Directory Management:** Monitoring physical inventory (block, floor, square footage, type, occupancy state).
3. **Maintenance Accounting:** Monthly charge generation, payment recording across various channels (UPI, Cards, Cash), and overdue balance calculation.
4. **Complaint Lifecycle Tracking:** Ticket generation, category classification, priority assignment, staff dispatch, and resolution tracking.
5. **Analytical Reporting & EDA:** Automated chart generation for collection efficiency, complaint frequencies, and facility issue density.

### 2.3 User Classes and Characteristics
- **Apartment Administrator / Association Secretary:** Full privileges to add units, register residents, generate bills, and review analytical reports.
- **Facility Maintenance Supervisor:** Views open tickets, assigns repair technicians (plumbers, electricians, carpenters), and marks complaints resolved.
- **Resident / Tenant:** Files complaints, reviews personal dues, and checks maintenance notices.
- **Data Analyst / Auditor:** Executes Python analytics scripts to review facility health and overdue trends.

### 2.4 Operating Environment
- **Operating System:** Platform independent (Windows 10/11, Ubuntu Linux 22.04+, macOS).
- **Runtime Environment:** Java Runtime Environment (JRE/JDK) 17 LTS or higher; Python 3.10+ runtime.
- **Database:** SQLite 3.x embedded engine / MySQL 8.x.

### 2.5 Design and Implementation Constraints
- The core desktop application must be implemented in Java using strict Object-Oriented Programming (OOP) paradigms.
- Relational schema must adhere to Boyce-Codd Normal Form (BCNF) to prevent insertion, update, and deletion anomalies.
- Python data retrieval must utilize standard database connectivity without altering production state.

---

## 3. Specific Functional Requirements

### 3.1 Module 1: Resident Details Management
- **FR-1.1:** The system shall store unique Resident ID, full legal name, valid email address, phone number, assigned apartment ID, occupancy type (Owner/Tenant), move-in date, and emergency contact details.
- **FR-1.2:** The system shall validate email syntax and telephone numbers using regular expressions before committing records.
- **FR-1.3:** The system shall automatically update the occupancy status of an apartment unit to "Occupied" upon new resident assignment.

### 3.2 Module 2: Apartment Unit Directory
- **FR-2.1:** The system shall maintain apartment records including unit ID (e.g., A-101), block identifier, floor number, layout type (1BHK, 2BHK, 3BHK, Penthouse), square footage, and occupancy status (Occupied, Vacant, Under Maintenance).
- **FR-2.2:** The system shall prevent registering duplicate unit identifiers.

### 3.3 Module 3: Maintenance Fee & Payment Tracking
- **FR-3.1:** The system shall compute monthly maintenance assessments based on apartment unit type and square footage.
- **FR-3.2:** The system shall record payments against specific billing invoice IDs, updating payment date, payment mode (Online/UPI, Credit Card, Cash, Cheque), and status to 'Paid'.
- **FR-3.3:** The system shall provide an automated query and report of overdue/pending accounts with total outstanding liability.

### 3.4 Module 4: Resident Grievance & Complaint Lifecycle
- **FR-4.1:** Residents shall be permitted to log tickets with category (Water Supply, Electrical, Cleanliness, Elevator, Security, Plumbing, Parking, Other), priority (Low, Medium, High, Critical), description, and timestamp.
- **FR-4.2:** Supervisors shall be able to update ticket state from 'Open' to 'In Progress' and 'Resolved', recording resolution timestamp and technician remarks.

### 3.5 Module 5: Data Science & Visualizations (Python EDA)
- **FR-5.1:** Python scripts shall query the SQLite database via Pandas `read_sql_query`.
- **FR-5.2:** Data cleaning pipelines shall handle null resolution dates and engineer new features (`resolution_days`, `turnaround_time`, `collection_pct`).
- **FR-5.3:** The script shall export high-resolution visualization charts covering payment distribution, complaint category frequency, stacked resolution statuses, monthly revenue trends, turnaround box plots, and facility hotspot heatmaps.

---

## 4. External Interface Requirements

### 4.1 User Interfaces
- Console-based command interface equipped with structured ANSI ASCII border formatting, responsive sub-menus, and clear success/error prompts.
- Jupyter Notebook interactive web-based data exploration interface.

### 4.2 Software Interfaces
- **Java JDBC 4.2 API** connecting through `org.sqlite.JDBC`.
- **Python `sqlite3` and `pandas` connectors** interfacing with the SQLite database file.
- **Matplotlib 3.x & Seaborn 0.13+** rendering visual graphical analytics.

---

## 5. Non-Functional Requirements

### 5.1 Performance Requirements
- Query response time for resident and apartment directory lookups shall be under 100 milliseconds for datasets up to 100,000 rows.
- Data visualization script execution and batch image rendering shall complete within 5 seconds.

### 5.2 Reliability & Availability
- Relational integrity enforced via ACID-compliant transactions with foreign key cascade controls.
- Zero data corruption on ungraceful shutdown.

### 5.3 Security & Data Integrity
- Field-level validation constraints checking email patterns, non-negative monetary amounts, and standardized enum status fields.
- PreparedStatements utilized in all DAO layers to completely eliminate SQL Injection vulnerabilities.

### 5.4 Maintainability & Extensibility
- Modular separation into Model, DAO, Service, and Utility packages.
- Loose coupling allows replacing SQLite with MySQL/PostgreSQL without altering service or presentation layers.
