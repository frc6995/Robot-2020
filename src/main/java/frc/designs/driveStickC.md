#
---
name: Command
about: Issue for the development of a command (Robot Action). This includes command
  groups.
title: COMMAND
labels: For Development
assignees: ''

---

**Description**
driveStickC is an inline command that simply forwards the Y(forward/backward) and x (left/right) axes of a joystick to the DrivebaseS arcadeDrive Method.

**Subsystem Requirements**
A list of subsystems required and what is used from the subsystem (Resource type is: Method, Constant, Static Member, etc).
- DrivebaseS : #|Subsystem Issue|
    - Method - arcadeDrive

**Command Requirements (If applicable)**
If this is a command group, specify which commands it uses.
- |Command Name| : #|Command Issue|

**Inputs**
No inputs, gets the joystick X and Y values.

**Outputs**
Drives both sides of the drivetrain, using the WPILIB differentialDrive.arcadeDrive method.

**Constraints**
A list of all constraints. Make sure to list all applicable constraints from the subsystem.

**Design (Algorithm)**
private final Command driveStickC = new RunCommand(() -> drivebaseS.arcadeDrive(stick.getY(), stick.getX()), drivebaseS);

**Test Cases**
Given a particular input, what is the expected output of the command. 
*this should then be used to test the command*
Given: Joystick full forward
Expect: Both wheelsets go full speed forward

Given: Joystick full backward
Expect: Both wheelsets go full speed backward

Given: Joystick full left
Expect: Right side forward, left side backward at full speed

Given: Joystick full right
Expect: Left side forward, right side backward at full speed

Given: Joystick in center
Expect: No movement
