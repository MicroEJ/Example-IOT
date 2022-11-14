/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.cbor.json;

import static ej.cbor.CborConstants.BREAK;
import static ej.cbor.CborConstants.DOUBLE_PRECISION_FLOAT;
import static ej.cbor.CborConstants.FALSE;
import static ej.cbor.CborConstants.HALF_PRECISION_FLOAT;
import static ej.cbor.CborConstants.NULL;
import static ej.cbor.CborConstants.ONE_BYTE;
import static ej.cbor.CborConstants.SINGLE_PRECISION_FLOAT;
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

import ej.cbor.CborDecoder;
import ej.cbor.CborEncoder;
import ej.cbor.CborType;

/**
 * Helper class to serialize and deserialize message using CBOR.
 */
public class CborJsonHelper {

	/**
	 * Deserialize a Cbor Item to an object.
	 *
	 * @param stream
	 *            The serialized object.
	 * @return The desiaralied object.
	 * @throws IOException
	 *             when an {@link IOException} is thrown when handling the stream.
	 */
	public static Object deserialize(CborDecoder stream) throws IOException {
		// Peek at the next type...
		CborType type = stream.peekType();

		int mt = type.getMajorType();

		if (mt == TYPE_UNSIGNED_INTEGER || mt == TYPE_NEGATIVE_INTEGER) {
			return Long.valueOf(stream.readInt());
		} else if (mt == TYPE_BYTE_STRING) {
			return stream.readByteString();
		} else if (mt == TYPE_TEXT_STRING) {
			return stream.readTextString();
		} else if (mt == TYPE_ARRAY) {
			return readArray(stream);
		} else if (mt == TYPE_MAP) {
			return readMap(stream);
		} else if (mt == TYPE_TAG) {
			return Long.valueOf(stream.readTag());
		} else if (mt == TYPE_FLOAT_SIMPLE) {
			return readFloat(stream, type);
		}

		return null; // to keep compiler happy...
	}



	/**
	 * Serialize an object into a stream.
	 *
	 * @param stream
	 *            the stream to serialized the object to.
	 * @param item
	 *            the item to serialized.
	 * @throws IOException
	 *             when an {@link IOException} is thrown when handling the stream.
	 */
	public static void serialize(CborEncoder stream, Object item) throws IOException {
		if (item instanceof Number) {
			writeNumber(stream, item);
		} else if (item instanceof String) {
			stream.writeTextString((String) item);
		} else if (item instanceof Boolean) {
			stream.writeBoolean(((Boolean) item).booleanValue());
		} else if (item instanceof Map) {
			writeMap(stream, item);
		} else if (item != null) {
			Class<?> type = item.getClass();
			if (type.isArray()) {
				writeArray(stream, item);
			} else {
				throw new IOException("Unknown/unhandled component type: " + type); //$NON-NLS-1$
			}
		} else {
			stream.writeNull();
		}
	}

	/**
	 * Serialize a JSONObject into a stream.
	 *
	 * @param stream
	 *            the stream to serialized the object to.
	 * @param item
	 *            the JSONObject to serialized.
	 * @throws IOException
	 *             when an {@link IOException} is thrown when handling the stream.
	 */
	public static void serialize(CborEncoder stream, JSONObject json) throws IOException {
		Map<String, Object> item = toCborCompatible(json);
		serialize(stream, item);
	}

	/**
	 * Serialize a JSONArray into a stream.
	 *
	 * @param stream
	 *            the stream to serialized the object to.
	 * @param item
	 *            the JSONArray to serialized.
	 * @throws IOException
	 *             when an {@link IOException} is thrown when handling the stream.
	 */
	public static void serialize(CborEncoder stream, JSONArray json) throws IOException {
		Object[] item = toCborCompatible(json);
		serialize(stream, item);
	}

	private static Object[] objectToArray(Object o) {
		Object[] res = null;
		if (!o.getClass().isArray()) {
			return res;
		}
		switch (o.getClass().getSimpleName()) {
		case "int[]": //$NON-NLS-1$
			res = objectToIntArray(o);
			break;
		case "float[]": //$NON-NLS-1$
			res = objectToFloatArray(o);
			break;
		case "double[]": //$NON-NLS-1$
			res = objectToDoubleArray(o);
			break;
		case "long[]": //$NON-NLS-1$
			objectToLongArray(o);
			break;
		case "char[]": //$NON-NLS-1$
			res = objectToCharArray(o);
			break;
		case "byte[]": //$NON-NLS-1$
			res = objectToByteArray(o);
			break;
		default:
			res = (Object[]) o;
			break;
		}
		return res;
	}

	private static Object readFloat(CborDecoder stream, CborType type) throws IOException {
		int subtype = type.getAdditionalInfo();
		if (subtype < ONE_BYTE) {
			if (subtype == FALSE || subtype == TRUE) {
				return Boolean.valueOf(stream.readBoolean());
			} else if (subtype == NULL) {
				return stream.readNull();
			} else if (subtype == UNDEFINED) {
				return stream.readUndefined();
			}
		} else if (subtype == ONE_BYTE) {
			return Byte.valueOf(stream.readSimpleValue());
		} else if (subtype == HALF_PRECISION_FLOAT) {
			return Double.valueOf(stream.readHalfPrecisionFloat());
		} else if (subtype == SINGLE_PRECISION_FLOAT) {
			return Float.valueOf(stream.readFloat());
		} else if (subtype == DOUBLE_PRECISION_FLOAT) {
			return Double.valueOf(stream.readDouble());
		} else if (subtype == BREAK) {
			return stream.readBreak();
		}
		return null;
	}

	private static Object readMap(CborDecoder stream) throws IOException {
		long len = stream.readMapLength();

		Map<Object, Object> result = new HashMap<>();
		for (long i = 0; len < 0 || i < len; i++) {
			Object key = deserialize(stream);
			if (len < 0 && (key == null)) {
				// break read...
				break;
			}
			Object value = deserialize(stream);
			result.put(key, value);
		}
		return result;
	}

	private static Object readArray(CborDecoder stream) throws IOException {
		long len = stream.readArrayLength();

		List<Object> result = new ArrayList<>();
		for (int i = 0; len < 0 || i < len; i++) {
			Object item = deserialize(stream);
			if (len < 0 && (item == null)) {
				// break read...
				break;
			}
			result.add(item);
		}
		return result;
	}

	private static void writeArray(CborEncoder stream, Object item) throws IOException {
		Object[] arrayItem = objectToArray(item);
		int len = (arrayItem == null) ? 0 : arrayItem.length;
		stream.writeArrayStart(len);
		for (int i = 0; i < len; i++) {
			serialize(stream, arrayItem[i]);
		}
	}

	private static void writeMap(CborEncoder stream, Object item) throws IOException {
		Map<?, ?> map = (Map<?, ?>) item;
		stream.writeMapStart(map.size());
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			serialize(stream, entry.getKey());
			serialize(stream, entry.getValue());
		}
	}

	private static void writeNumber(CborEncoder stream, Object item) throws IOException {
		Number num = (Number) item;
		if (item instanceof Double) {
			stream.writeDouble(num.doubleValue());
		} else if (item instanceof Float) {
			stream.writeFloat(num.floatValue());
		} else {
			stream.writeInt(num.longValue());
		}
	}

	private static Object[] objectToIntArray(Object o) {
		Object[] res;
		int[] tmp = (int[]) o;
		res = new Integer[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			res[i] = new Integer(tmp[i]);
		}
		return res;
	}

	private static Object[] objectToFloatArray(Object o) {
		Object[] res;
		float[] tmp = (float[]) o;
		res = new Float[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			res[i] = new Float(tmp[i]);
		}
		return res;
	}

	private static Object[] objectToDoubleArray(Object o) {
		Object[] res;
		double[] tmp = (double[]) o;
		res = new Double[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			res[i] = new Float(tmp[i]);
		}
		return res;
	}

	private static void objectToLongArray(Object o) {
		Object[] res;
		long[] tmp = (long[]) o;
		res = new Long[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			res[i] = new Long(tmp[i]);
		}
	}

	private static Object[] objectToCharArray(Object o) {
		Object[] res;
		char[] tmp = (char[]) o;
		res = new Character[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			res[i] = new Character(tmp[i]);
		}
		return res;
	}

	private static Object[] objectToByteArray(Object o) {
		Object[] res;
		byte[] tmp = (byte[]) o;
		res = new Byte[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			res[i] = new Byte(tmp[i]);
		}
		return res;
	}

	/**
	 * Convert a JSOArray into an array of compatible object with CBOR.
	 *
	 * @param json
	 *            the json to convert.
	 * @return an array of Map compatible with CBOR.
	 */
	private static Object[] toCborCompatible(JSONArray json) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < json.length(); i++) {
			try {
				Object element = json.get(i);
				if (element instanceof JSONArray) {
					list.add(toCborCompatible((JSONArray) element));
				} else if (element instanceof JSONObject) {
					list.add(toCborCompatible((JSONObject) element));
				} else {
					list.add(element);
				}
			} catch (JSONException e) {
				throw new AssertionError(e);
			}
		}
		return list.toArray();
	}

	/**
	 * Convert a JSONObject into a Map compatible with CBOR.
	 *
	 * @param json
	 *            the json to convert.
	 * @return a Map compatible with CBOR.
	 */
	private static Map<String, Object> toCborCompatible(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		@SuppressWarnings("unchecked")
		Enumeration<String> keys = json.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			try {
				Object element = json.get(key);
				if (element instanceof JSONArray) {
					map.put(key, toCborCompatible((JSONArray) element));
				} else if (element instanceof JSONObject) {
					map.put(key, toCborCompatible((JSONObject) element));
				} else {
					map.put(key, element);
				}
			} catch (JSONException e) {
				throw new AssertionError(e);
			}
		}
		return map;
	}
}
