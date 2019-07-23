/*
 * Java
 *
 * Copyright 2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot;

import ej.bon.Util;

/**
 * Model providing values.
 */
public class Model {

	/**
	 * Gets the ram usage.
	 *
	 * @return the ram usage.
	 */
	public static long getRam() {
		System.gc();
		Runtime runtime = Runtime.getRuntime();
		return runtime.totalMemory() - runtime.freeMemory();
	}

	/**
	 * Gets the number of active threads.
	 *
	 * @return the number of active threads.
	 */
	public static int getThread() {
		return Thread.activeCount();
	}

	/**
	 * Gets the system uptime.
	 *
	 * @return the system uptime.
	 */
	public static long getUptime() {
		return Util.platformTimeMillis();
	}
}
