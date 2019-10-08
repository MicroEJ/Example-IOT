/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.json;

import java.io.DataInputStream;
import java.io.IOException;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import ej.wadapps.app.BackgroundService;

/**
 * Background service starting the JSON example.
 */
public class JsonBackgroundService implements BackgroundService {

	@Override
	public void onStart() {

		// get back an input stream from the resource that represents the JSON
		// content
		DataInputStream dis = new DataInputStream(
				Main.class.getResourceAsStream("/json/menu.json")); //$NON-NLS-1$

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
			JSONObject menu = jsono.getJSONObject("menu"); //$NON-NLS-1$
			System.out.println("The JSONObject named \"menu\" is:"); //$NON-NLS-1$
			System.out.println(menu + "\n"); //$NON-NLS-1$

			popup = menu.getJSONObject("popup"); //$NON-NLS-1$
			System.out.println("The JSONObject named \"popup\" in \"menu\" is:"); //$NON-NLS-1$
			System.out.println(popup + "\n"); //$NON-NLS-1$

			menuItems = popup.getJSONArray("menuitem"); //$NON-NLS-1$

			System.out.println("The menuitem content of popup is:"); //$NON-NLS-1$
			System.out.println(menuItems.toString() + "\n"); //$NON-NLS-1$

			String defaultValue = jsono.optString("Unknown key", "Default value"); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println("The value of \"Unknown key\" is:"); //$NON-NLS-1$
			System.out.println(defaultValue + "\n"); //$NON-NLS-1$
		} catch (JSONException e) {
			// a getJSONObject() or a getJSONArray() failed
			// or the parsing failed
			throw new AssertionError(e);
		}

		// Write data
		try {
			// append a new menu item in the JSONArray
			popup.append("menuitem", new JSONObject("{\"value\": \"Save\", \"onClick\": \"SaveDoc()\"}")); //$NON-NLS-1$ //$NON-NLS-2$

			// Add a new field.
			jsono.put("window", false); //$NON-NLS-1$
			// Override existing field.
			jsono.put("window", true); //$NON-NLS-1$

			// Accumulate a new menu (menu is a JsonObject)
			jsono.accumulate("menu", new JSONObject("{\"id\": \"file2\"}")); //$NON-NLS-1$ //$NON-NLS-2$
			// Accumulate a new menu (menu is a JsonArray)
			jsono.accumulate("menu", new JSONObject("{\"id\": \"file3\"}")); //$NON-NLS-1$ //$NON-NLS-2$

			System.out.println("The new JSONArray is:"); //$NON-NLS-1$
			System.out.println(jsono + "\n"); //$NON-NLS-1$
		} catch (JSONException e) {
			// An error occured while adding data.
			throw new AssertionError(e);
		}
	}

	@Override
	public void onStop() {
		// Nothing to do.

	}

}
