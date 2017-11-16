package org.usfirst.frc.team1836.robot;

import java.util.LinkedList;
import java.util.List;

public class Systems {

	private static List<Subsystem> systems = new LinkedList<Subsystem>();

	public static void addSystem(Subsystem e) {
		systems.add(e);
	}

	public static void updateAuto() {
		for (Subsystem e : systems) {
			e.updateAuto();
		}
	}

	public static void updateTeleop() {
		for (Subsystem e : systems) {
			e.updateTeleop();
		}
	}

	public static void smartDashboard() {
		for (Subsystem e : systems) {
			e.sendToSmartDash();
		}
	}

	public static void initAuto() {
		for (Subsystem e : systems) {
			e.initAuto();
		}
	}

	public static void initTeleop() {
		for (Subsystem e : systems) {
			e.initTeleop();
		}
	}

}
