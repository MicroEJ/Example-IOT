/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.json;

import java.io.DataInputStream;
import java.io.IOException;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 * This example uses the org.json.me parser provided by json.org to parse and
 * browse a JSON file.
 *
 * The contents of the JSON file is taken from an example available from http://www.json.org/example.html
 *
 * The example then iterates over all the menuitem elements available in the popup menu and print their contents
 *
 */
public class MyJSONExample {

	public static void main(String[] args) {

		// get back an input stream from the resource that represents the JSON
		// content
		DataInputStream dis = new DataInputStream(
				MyJSONExample.class.getResourceAsStream("/json/menu.json"));

		byte[] bytes = null;

		try {

			// assume the available returns the whole content of the resource
			bytes = new byte[dis.available()];

			dis.readFully(bytes);

		} catch (IOException e1) {
			// something went wrong
			e1.printStackTrace();
			return;
		}
		JSONObject jsono = null;
		JSONObject popup = null;
		JSONArray menuItems = null;
		try {

			// create the data structure to exploit the content
			// the string is created assuming default encoding
			jsono = new JSONObject(new String(bytes));

			// get the JSONObject named "menu" from the root JSONObject
			JSONObject menu = jsono.getJSONObject("menu");
			System.out.println("The JSONObject named \"menu\" is:");
			System.out.println(menu + "\n");

			popup = menu.getJSONObject("popup");
			System.out.println("The JSONObject named \"popup\" in \"menu\" is:");
			System.out.println(popup + "\n");

			menuItems = popup.getJSONArray("menuitem");

			System.out.println("The menuitem content of popup is:");
			System.out.println(menuItems.toString() + "\n");

			String defaultValue = jsono.optString("Unknown key", "Default value");
			System.out.println("The value of \"Unknown key\" is:");
			System.out.println(defaultValue + "\n");
		} catch (JSONException e) {
			// a getJSONObject() or a getJSONArray() failed
			// or the parsing failed
			throw new AssertionError(e);
		}

		// Write data
		try {
			// append a new menu item in the JSONArray
			popup.append("menuitem", new JSONObject("{\"value\": \"Save\", \"onClick\": \"SaveDoc()\"}"));

			// Add a new field.
			jsono.put("window", false);
			// Override existing field.
			jsono.put("window", true);

			// Accumulate a new menu (menu is a JsonObject)
			jsono.accumulate("menu", new JSONObject("{\"id\": \"file2\"}"));
			// Accumulate a new menu (menu is a JsonArray)
			jsono.accumulate("menu", new JSONObject("{\"id\": \"file3\"}"));

			System.out.println("The new JSONArray is:");
			System.out.println(jsono + "\n");
		} catch (JSONException e) {
			// An error occured while adding data.
			throw new AssertionError(e);
		}

	}

}
