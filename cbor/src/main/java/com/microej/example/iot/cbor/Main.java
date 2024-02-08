/*
 * Java
 *
 * Copyright 2021-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.cbor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ej.cbor.CborDecoder;
import ej.cbor.CborEncoder;

/**
 * This example program uses the ej.cbor library first to encode some data in a stream, then to read it back and to
 * print it in a byte string format and in a JSON-like format.
 *
 * The encoded data mirrors the contents of the JSON file example available from http://www.json.org/example.html
 */
public class Main {

	/**
	 * Entry point for the CBOR encoding and decoding example.
	 *
	 * @param args
	 *            Not used.
	 */
	public static void main(String[] args) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		CborEncoder encoder = new CborEncoder(baos);

		try {
			// START writing data to CBOR stream
			encoder.writeMapStart(1);
			{
				encoder.writeTextString("menu");
				encoder.writeMapStart(3);
				{
					encoder.writeTextString("id");
					encoder.writeTextString("file");
					encoder.writeTextString("value");
					encoder.writeTextString("File"); //
					encoder.writeTextString("popup");
					encoder.writeMapStart(1);
					{
						encoder.writeTextString("menuitem");
						encoder.writeArrayStart(3);
						{
							encoder.writeMapStart(2);
							{
								encoder.writeTextString("value");
								encoder.writeTextString("New");
								encoder.writeTextString("onclick");
								encoder.writeTextString("CreateNewDoc()");
							}
							encoder.writeMapStart(2);
							{
								encoder.writeTextString("value");
								encoder.writeTextString("Open");
								encoder.writeTextString("onclick");
								encoder.writeTextString("OpenDoc()");
							}
							encoder.writeMapStart(2);
							{
								encoder.writeTextString("value");
								encoder.writeTextString("Close");
								encoder.writeTextString("onclick");
								encoder.writeTextString("CloseDoc()");
							}
						}
					}
				}
			}
			// END data writing

			byte[] resultCborData = baos.toByteArray();
			CborDecoder decoder = new CborDecoder(new ByteArrayInputStream(resultCborData));

			// Prints in the hexadecimal value of the binary data content that was encoded just above.
			// You can convert this hexadecimal string into JSON data for proof-checking at http://cbor.me
			System.out.print("CBOR data string : ");
			for (byte b : resultCborData) {
				int ubyte = b & 0xff;
				if (ubyte < 0x10) {
					System.out.print("0");
				}
				System.out.print(Integer.toHexString(ubyte));
			}
			System.out.println();

			// Prints the CBOR data in a JSON-like format :
			System.out.println("Data content : ");
			CborPrintUtil.printData(decoder, System.out, 0);
			System.out.println();

		} catch (IOException e) {
			throw new AssertionError(e);
		}

	}

}
