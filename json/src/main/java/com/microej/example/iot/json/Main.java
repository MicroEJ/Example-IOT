/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
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
	 * Starts the entry point.
	 *
	 * @param args not used.
	 */
	public static void main(String[] args) {
		new JsonEntryPoint().start();
	}

}
