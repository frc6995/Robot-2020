# NOMAD-Base

This is an attempt to create a basic command-based FRC robot program that is expandable and customizable for the FRC Team 6995, NOMAD.

More Details

The intent of this repository is to be forked every time team NOMAD begins coding for a new robot. It is designed around principles of modularity, code reusability, and timing-critical systems. As such, it attempts to avoid "Magic Numbers", i.e. hard-coded values. Every constant that may need to be modified for an individual challenge and robot will be found in one place for easy modification. Similarly, the code will be written to be as flexible as possible. This means that if a functionality does not have a forseeable use in multiple types of challenges and robots, it likely does not belong in this repository.

Contents

The project will contain utility classes for common functionality (average, unit conversions, etc), wrapper classes for some of the basic WPILib objects to add functionality, and commmands and subsystems related to the drivebase to allow for a driving robot as soon as one is built. This includes driver control functionality with various types of input devices implemented, autonomous helper commands (wall squaring, path following, drive straight, turn to an angle) and vision tracking using a Limelight 2.0.

Assumed Hardware:

The project is coded to assume a basic drivebase with the following electronics and design:

Two parallel independently controllable wheel sets (Tank, West Coast Drive, etc. Not Omniwheel H-drive or Swerve) driven by one "master" motor per side, each connected to a Talon SRX. Other drivebase motors need only be driven by motor controllers that can be set to follow a Talon. This design was chosen as the supported design to allow the project to be usable by rookies using the standard AndyMark drivebase.

One quadrature encoder connected to each side of the drivebase and the "master" Talon for that side. The pulse-per-revolution and sensor type can be configured. (Required for autonomous only)

One navX-MXP gyro connected to the expansion port on the RoboRIO. (Required for autonomous only)

One Limelight v2, pointing forward. (Required for vision only)

Additional electronics as required by FIRST (roboRIO, Power Distribution Panel, etc)
