/*
 * Java
 *
 * Copyright 2016-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.cbor.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import ej.cbor.CborDecoder;
import ej.cbor.CborEncoder;

/**
 * This example uses the ej.cbor serializer/deserializer.
 *
 * The example serialize some data then deserialize them.
 */
public class Main {

	/**
	 * Entry point, serialized a JSONobject then deserialized it.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		// Load the /json/menu.json file into a json object.
		DataInputStream dis = new DataInputStream(Main.class.getResourceAsStream("/json/menu.json")); //$NON-NLS-1$
		int size;
		try {
			size = dis.available();
		} catch (IOException e) {
			throw new AssertionError(e);
		}
		JSONObject menuJson = getJsonMenu(dis);
		System.out.println("Initial data (" + size + " bytes) = " + menuJson); //$NON-NLS-1$ //$NON-NLS-2$

		// serialize the data
		ByteArrayOutputStream serializedOutputStream = new ByteArrayOutputStream();
		CborEncoder cborEncoder = new CborEncoder(serializedOutputStream);
		try {
			CborJsonHelper.serialize(cborEncoder, menuJson);
		} catch (IllegalArgumentException | IOException e) {
			System.out.println("Error during serialization"); //$NON-NLS-1$
			throw new AssertionError(e);
		}
		byte[] serialized = serializedOutputStream.toByteArray();
		System.out.println("Data serialized (" + serialized.length + " bytes)"); //$NON-NLS-1$ //$NON-NLS-2$

		// deserialize the date
		ByteArrayInputStream serializedInputStream = new ByteArrayInputStream(serialized);
		CborDecoder cborDecoder = new CborDecoder(serializedInputStream);
		try {
			Object deserialized = CborJsonHelper.deserialize(cborDecoder);
			System.out.println("Data deserialized = " + deserialized); //$NON-NLS-1$
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}



	/**
	 * Get the menu from the file.
	 *
	 * @return The menu as a JSON array.
	 */
	private static JSONObject getJsonMenu(DataInputStream dis) {
		byte[] bytes = null;

		try {
			// assume the available returns the whole content of the resource
			bytes = new byte[dis.available()];

			dis.readFully(bytes);

		} catch (IOException e) {
			// something went wrong
			throw new AssertionError(e);
		}

		try {

			// create the data structure to exploit the content
			// the string is created assuming default encoding
			return new JSONObject(new String(bytes));
		} catch (JSONException e) {
			// the parsing failed.
			throw new AssertionError(e);
		}
	}
}
