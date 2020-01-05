---
name: Command
about: Issue for the development of a command (Robot Action). This includes command
  groups.
title: COMMAND - |Name|
labels: For Development
assignees: ''

---

**Description**
The description of the command (action) being performed.

**Subsystem Requirements**
A list of subsystems required and what is used from the subsystem (Resource type is: Method, Constant, Static Member, etc).
- |Subsystem Name| : #|Subsystem Issue|
    - |Resource Type| - |Resource Name|

**Command Requirements (If applicable)**
If this is a command group, specify which commands it uses.
- |Command Name| : #|Command Issue|

**Inputs**
The inputs of the action, what information does the command take in.

**Outputs**
The outputs of the action, what does the command do.

**Constraints**
A list of all constraints. Make sure to list all applicable constraints from the subsystem.

**Design (Algorithm)**
The design or algorithm of the command in pseudocode.

**Test Cases**
Given a particular input, what is the expected output of the command. 
*this should then be used to test the command*
