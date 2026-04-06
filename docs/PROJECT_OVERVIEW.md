# Project Overview: IS-Backend

## Purpose and Goals
The `IS-Backend` is the core server-side component of the **ISTEAMX** University Management System. Its primary objective is to manage academic resources, including professors, students, course schedules, and physical rooms, through a robust and secure RESTful API.

The main goals of the project are to:
- **Centralized Data Management**: Provide a single source of truth for university schedules and resources.
- **Secure Access**: Ensure that only authorized users can modify sensitive academic data.
- **High Performance**: Handle concurrent requests for schedule lookups and updates with minimal latency.
- **Scalability**: Support horizontal scaling to accommodate a growing number of university departments.

## Scope
The backend handles the following domains:
- **User Authentication**: Registration and Login using JWT.
- **Resource Management**: CRUD operations for Rooms, Professors, Groups, and Subjects.
- **Scheduling**: Coordination between time slots, rooms, professors, and student groups.
- **API Security**: Role-based access control (RBAC) and request filtering.

## Target Audience
- **University Administrators**: Who manage the overall system and resource allocation.
- **Professors**: Who need to check their schedules and room assignments.
- **Students**: Who use the platform to stay updated on their course timetables.
- **Developers**: Who maintain or extend the ISTEAMX ecosystem.
