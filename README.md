GST Compliance Automation System
Overview
The GST Compliance Automation System is an enterprise-grade desktop application designed to streamline and automate tax invoice generation. By transitioning from manual spreadsheet calculations to this database-driven Java application, businesses can achieve high precision in tax compliance and significantly reduce human error.

Key Features
Precision Tax Engine: Implements complex tax logic for CGST, SGST, and IGST, reducing manual calculation errors by 40%.

Modern GUI: Built with Java Swing and enhanced with the FlatLaf library for a sleek, modern, and professional user interface.

Instant Digital Documentation: Automatically generates and saves professional GST invoices as PDF slips directly to the user's desktop.

Reliable Data Persistence: Uses JDBC to commit all transaction records to a MySQL database, ensuring data integrity and auditability.

Automated Testing: Includes a dedicated JUnit 5 test suite to mathematically verify the accuracy of the tax engine under various edge cases.

Architecture & Tech Stack
The project follows a robust Multi-Tier Architecture to separate concerns and ensure maintainability:

Model Layer: Manages data objects (Invoice, Product, Client).

DAO Layer: Handles database interactions using JDBC.

Service Layer: Contains core business logic and tax calculation rules.

UI Layer: Provides an intuitive desktop experience using Java Swing.

Tech Stack:

Language: Java (JDK 17)

Build Tool: Apache Maven

Database: MySQL

UI/UX: Swing, FlatLaf

Testing: JUnit 5

Reporting: OpenPDF

Installation & Setup
Database Initialization: Ensure you have MySQL running. Execute the provided schema.sql script to initialize the database tables.

Download:
Download the latest gst-calculator.jar from the Releases section.

Run:
Launch the application using your terminal:

Bash
java -jar gst-calculator.jar
Documentation
Calculation Logic: The GstService class handles all tax splits (CGST/SGST vs. IGST), ensuring compliance with standard GST regulations.

Testing: Run the GstServiceTest suite within your IDE or via Maven to verify the precision of the calculation engine.
