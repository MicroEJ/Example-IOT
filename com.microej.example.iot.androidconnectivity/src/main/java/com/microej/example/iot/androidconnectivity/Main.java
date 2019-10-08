/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.androidconnectivity;

/**
 * Entry point.
 */
public class Main {


	/**
	 * Entry point.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new ExampleBackgroundService().onStart();
	}

}
