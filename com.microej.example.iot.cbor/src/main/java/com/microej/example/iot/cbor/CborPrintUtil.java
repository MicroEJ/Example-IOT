/*
 * Java
 *
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.cbor;

import static ej.cbor.CborConstants.BREAK;
import static ej.cbor.CborConstants.DOUBLE_PRECISION_FLOAT;
import static ej.cbor.CborConstants.FALSE;
import static ej.cbor.CborConstants.HALF_PRECISION_FLOAT;
import static ej.cbor.CborConstants.NULL;
import static ej.cbor.CborConstants.SINGLE_PRECISION_FLOAT;
import static ej.cbor.CborConstants.TAG_BASE64_ENCODED;
import static ej.cbor.CborConstants.TAG_BASE64_URL_ENCODED;
import static ej.cbor.CborConstants.TAG_BIGDECIMAL;
import static ej.cbor.CborConstants.TAG_CBOR_ENCODED;
import static ej.cbor.CborConstants.TAG_CBOR_MARKER;
import static ej.cbor.CborConstants.TAG_DECIMAL_FRACTION;
import static ej.cbor.CborConstants.TAG_EPOCH_DATE_TIME;
import static ej.cbor.CborConstants.TAG_EXPECTED_BASE16_ENCODED;
import static ej.cbor.CborConstants.TAG_EXPECTED_BASE64_ENCODED;
import static ej.cbor.CborConstants.TAG_EXPECTED_BASE64_URL_ENCODED;
import static ej.cbor.CborConstants.TAG_MIME_MESSAGE;
import static ej.cbor.CborConstants.TAG_NEGATIVE_BIGINT;
import static ej.cbor.CborConstants.TAG_POSITIVE_BIGINT;
import static ej.cbor.CborConstants.TAG_REGEXP;
import static ej.cbor.CborConstants.TAG_STANDARD_DATE_TIME;
import static ej.cbor.CborConstants.TAG_URI;
import static ej.cbor.CborConstants.TRUE;
import static ej.cbor.CborConstants.TYPE_ARRAY;
import static ej.cbor.CborConstants.TYPE_BYTE_STRING;
import static ej.cbor.CborConstants.TYPE_FLOAT_SIMPLE;
import static ej.cbor.CborConstants.TYPE_MAP;
import static ej.cbor.CborConstants.TYPE_NEGATIVE_INTEGER;
import static ej.cbor.CborConstants.TYPE_TAG;
import static ej.cbor.CborConstants.TYPE_TEXT_STRING;
import static ej.cbor.CborConstants.TYPE_UNSIGNED_INTEGER;
import static ej.cbor.CborConstants.UNDEFINED;

import java.io.IOException;
import java.io.PrintStream;

import ej.bon.Immutables;
import ej.cbor.CborDecoder;
import ej.cbor.CborType;

/**
 * Utility class to print different types of CBOR data types.
 */
public class CborPrintUtil {

	static final int USE_STRING = 1; // Element after tag is a String
	static final int USE_NUMERIC = 2; // Element after tag is an integer or float
	static final int USE_BYTE_STRING = 3; // Element after tag is a byte string
	static final int USE_ARRAY = 4; // Element after tag is an array
	static final int USE_ANY = 5; // Element after tag can be anything

	/**
	 * Decodes and prints the data from the given CBOR decoder.
	 *
	 * @param decoder
	 *            The CborDecoder instance loaded with the data stream to print.
	 * @param printer
	 *            The PrintStream to write the text output to.
	 * @param tabsOffset
	 *            The number of tab characters to insert before the text output, for readability.
	 * @throws IOException
	 *             in case of I/O problems reading the CBOR-type from the underlying input stream.
	 */
	public static void printData(CborDecoder decoder, PrintStream printer, int tabsOffset) throws IOException {
		CborType comingType = decoder.peekType();

		if (comingType != null) {
			// different processes depending on incoming data type
			switch (comingType.getMajorType()) {
			case TYPE_TEXT_STRING:
				printer.print("\"" + decoder.readTextString() + "\"");
				break;
			case TYPE_MAP:
				printMap(decoder, printer, tabsOffset);
				break;
			case TYPE_ARRAY:
				printArray(decoder, printer, tabsOffset);
				break;
			case TYPE_UNSIGNED_INTEGER:
			case TYPE_NEGATIVE_INTEGER:
				printer.print(decoder.readInt());
				break;
			case TYPE_FLOAT_SIMPLE:
				printFloat(decoder, printer);
				break;
			case TYPE_BYTE_STRING:
				printByteString(decoder, printer);
				break;
			case TYPE_TAG:
				printTag(decoder, printer, tabsOffset);
				break;
			default:
				assert false;
			}
		}
	}

	private static void printFloat(CborDecoder decoder, PrintStream printer) throws IOException {
		switch (decoder.peekType().getAdditionalInfo()) {
		case FALSE:
		case TRUE:
			printer.print(decoder.readBoolean());
			break;
		case NULL:
			decoder.readNull();
			printer.print("null");
			break;
		case UNDEFINED:
			decoder.readUndefined();
			printer.print("undefined");
			break;
		case BREAK:
			decoder.readBreak();
			break;
		case HALF_PRECISION_FLOAT:
			printer.print(decoder.readHalfPrecisionFloat());
			break;
		case SINGLE_PRECISION_FLOAT:
			printer.print(decoder.readFloat());
			break;
		case DOUBLE_PRECISION_FLOAT:
			printer.print(decoder.readDouble());
			break;
		default:
			throw new IOException("Invalid CBOR float precision value");
		}
	}

	private static void printMap(CborDecoder decoder, PrintStream printer, int tabsOffset) throws IOException {
		printer.println("{");
		long mapLength = decoder.readMapLength();

		for (int i = 0; i < mapLength || mapLength < 0; i++) {
			// check for breaks for indefinite length maps
			if (mapLength >= 0 || (decoder.peekType().getAdditionalInfo() != BREAK)) {
				if (i != 0) {
					printer.println(",");
				}
				for (int j = 0; j < (tabsOffset + 1); j++) {
					printer.print("\t");
				}
				printer.print("\"" + decoder.readTextString() + "\" : ");
				printData(decoder, printer, tabsOffset + 1);
			} else {
				decoder.readBreak();
				break;
			}
		}
		printer.println();
		for (int j = 0; j < tabsOffset; j++) {
			printer.print("\t");
		}
		printer.print("}");
	}

	private static void printArray(CborDecoder decoder, PrintStream printer, int tabsOffset) throws IOException {
		printer.print("[ ");
		long arrayLength = decoder.readArrayLength();

		for (int i = 0; i < arrayLength || arrayLength < 0; i++) {
			// check for breaks for indefinite length arrays
			if (arrayLength >= 0 || (decoder.peekType().getAdditionalInfo() != BREAK)) {
				if (i != 0) {
					printer.print(", ");
				}
				printData(decoder, printer, tabsOffset + 1);
			} else {
				decoder.readBreak();
				break;
			}
		}
		printer.print(" ]");
	}

	private static void printByteString(CborDecoder decoder, PrintStream printer) throws IOException {
		byte[] byteString = decoder.readByteString();
		for (byte b : byteString) {
			int ubyte = b & 0xff;
			if (ubyte < 0x10) {
				printer.print("0");
			}
			printer.print(Integer.toHexString(ubyte));
		}

	}

	private static void printTag(CborDecoder decoder, PrintStream printer, int tabsOffset) throws IOException {

		final int[] cborTags = { TAG_STANDARD_DATE_TIME, TAG_EPOCH_DATE_TIME, TAG_POSITIVE_BIGINT, TAG_NEGATIVE_BIGINT,
				TAG_DECIMAL_FRACTION, TAG_BIGDECIMAL, TAG_EXPECTED_BASE64_URL_ENCODED, TAG_EXPECTED_BASE64_ENCODED,
				TAG_EXPECTED_BASE16_ENCODED, TAG_CBOR_ENCODED, TAG_URI, TAG_BASE64_URL_ENCODED, TAG_BASE64_ENCODED,
				TAG_REGEXP, TAG_MIME_MESSAGE, TAG_CBOR_MARKER };
		final String[] messages = (String[]) Immutables.get("tagsMessages");
		final int[] types = { USE_STRING, USE_NUMERIC, USE_BYTE_STRING, USE_BYTE_STRING, USE_ARRAY, USE_ARRAY, USE_ANY,
				USE_ANY, USE_ANY, USE_BYTE_STRING, USE_STRING, USE_STRING, USE_STRING, USE_STRING, USE_STRING,
				USE_ANY };

		int tag = (int) decoder.readTag(); // cast into int because supported values here don't exceed int values anyway
		boolean notFound = true;
		int i = 0;
		while (i < cborTags.length && notFound) {
			if (tag == cborTags[i]) {
				printer.print(messages[i] + " : ");
				switch (types[i]) {
				case USE_STRING:
					printer.print(decoder.readTextString());
					break;
				case USE_NUMERIC:
					CborType comingType = decoder.peekType();
					switch (comingType.getMajorType()) {
					case TYPE_UNSIGNED_INTEGER:
					case TYPE_NEGATIVE_INTEGER:
						printer.print(decoder.readInt());
						break;
					case TYPE_FLOAT_SIMPLE:
						// check that the value is a float and not a special one
						if (comingType.getAdditionalInfo() >= HALF_PRECISION_FLOAT
								&& comingType.getAdditionalInfo() <= DOUBLE_PRECISION_FLOAT) {
							printFloat(decoder, printer);
							break;
						}
					default:
						throw new IOException("Data type following numeric tag not numeric.");
					}
					break;
				case USE_BYTE_STRING:
					printByteString(decoder, printer);
					break;
				case USE_ARRAY:
					printArray(decoder, printer, tabsOffset);
					break;
				case USE_ANY:
					printData(decoder, printer, tabsOffset + 1);
					break;
				}
				notFound = false;
			} else {
				i++;
			}
		}
		if (notFound) {
			throw new IOException("Encountered unsupported CBOR tag.");
		}
	}

}
