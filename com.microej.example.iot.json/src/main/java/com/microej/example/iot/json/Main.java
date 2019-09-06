/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.json;

/**
 * This example uses the org.json.me parser provided by json.org to parse and
 * browse a JSON file.
 *
 * The contents of the JSON file is taken from an example available from http://www.json.org/example.html
 *
 * The example then iterates over all the menuitem elements available in the popup menu and print their contents
 *
 */
public class Main {

	/**
	 * Starts the background service.
	 * 
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		new JsonBackgroundService().onStart();
	}

}
