/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.examples.iot.json;

import java.io.DataInputStream;
import java.io.IOException;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 * This example uses the org.json.me parser provided by json.org to parse and
 * browse a JSON content.
 *
 * The JSON content is simple abstraction of a file menu as provided here:
 * http://www.json.org/example.html
 *
 * The example then tries to list all the 'menuitem's available in the popup
 * menu. It is assumed the user knows the menu JSON file structure.
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

		try {

			// create the data structure to exploit the content
			// the string is created assuming default encoding
			JSONObject jsono = new JSONObject(new String(bytes));

			// get the JSONObject named "menu" from the root JSONObject
			JSONObject menu = jsono.getJSONObject("menu");
			System.out.println("The JSONObject named \"menu\" is:");
			System.out.println(menu + "\n");

			JSONObject popup = menu.getJSONObject("popup");
			System.out.println("The JSONObject named \"popup\" in \"menu\" is:");
			System.out.println(popup + "\n");

			JSONArray a = popup.getJSONArray("menuitem");

			System.out.println("The menuitem content of popup is:");
			System.out.println(a.toString() + "\n");

		} catch (JSONException e) {
			// a getJSONObject() or a getJSONArray() failed
			// or the parsing failed
			e.printStackTrace();
		}

	}

}
