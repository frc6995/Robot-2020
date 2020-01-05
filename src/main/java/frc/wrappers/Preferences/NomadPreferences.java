/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.wrappers.Preferences;


    /**
 * SuperNURDs encapsulation of RobotPreferences
 */
public abstract class NomadPreferences {
	private static boolean useDefaults = false;
	protected String m_name;

	/**
	 * Set to use coded preference values
	 */
	public static void useDefaults() {
		useDefaults = true;
	}

	/**
	 * Set not to use coded preference values
	 */
	public static void usePreferences() {
		useDefaults = false;
	}

	/**
	 * @return Check if we are using default preference vaules
	 */
	public static boolean isUsingDefaults() {
		return useDefaults;
	}
}

