#
---
name: Subsystem
about: Issue for the development of a core robot subsystem.
title: SUBSYSTEM
labels: For Development
assignees: ''

---

**Description**
This subsystem constructs two master NomadTalonSRXs and two corresponding ArrayLists of NomadVictorSPXs, slaved to the masters, as well as a DifferentialDrive object. It then implements the differentialDrive ArcadeDrive method to drive the controller groups.

**Inputs**
Currently none.

**Outputs**
All drivebase Talons and Victors

**Actions**
Moves drivebase motors at various speeds depending on parameters passed in.

**Constraints**
Master motors must be driven by Talon SRX
Slave motors must be on Victor SPX


**Design (Algorithm)**
Create master Talons on ports as specified in DriveConstants

Create empty ArrayLists for slave Victors

Create DifferentialDrive object with master Talons

Constructor:

For each port specified in the arrays in DriveConstants, create a NomadVictorSPX on that port and add it to its corresponding group.

arcadeDrive:

pass the parameters straight to differentialDrive.arcadeDrive.
