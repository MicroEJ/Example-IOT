/*
 * Java
 *
 * Copyright 2019-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.server;

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
