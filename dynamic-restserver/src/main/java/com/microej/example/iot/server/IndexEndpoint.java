/*
 * Java
 *
 * Copyright 2019-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.microej.example.iot.Main;

import ej.hoka.http.HTTPConstants;
import ej.hoka.http.HTTPRequest;
import ej.hoka.http.HTTPResponse;
import ej.restserver.RestEndpoint;

/**
 * The endpoint displaying the index.
 */
public class IndexEndpoint extends RestEndpoint {
	private static final String INDEX_HTML = "index.html"; //$NON-NLS-1$

	/**
	 * The endpoint displaying the index.
	 *
	 * @throws IllegalArgumentException
	 *             should not occurs.
	 */
	public IndexEndpoint() throws IllegalArgumentException {
		super(INDEX_HTML);
	}

	@Override
	public HTTPResponse get(HTTPRequest request, Map<String, String> headers, Map<String, String> parameters) {
		HTTPResponse response = null;
		StringBuilder index = null;
		try (InputStream inputStream = IndexEndpoint.class.getResourceAsStream(Main.RESOURCES + INDEX_HTML)) {
			index = new StringBuilder(inputStream.available());
			byte[] buffer = new byte[1024];
			while (true) {
				int read = inputStream.read(buffer);
				if (-1 == read) {
					break;
				}
				index.append(new String(buffer, 0, read));
			}
		} catch (IOException e) {
			e.printStackTrace();
			response = HTTPResponse.RESPONSE_INTERNAL_ERROR;
		}
		if (index != null) {
			replace(index, "ram", Model.getRam()); //$NON-NLS-1$
			replace(index, "thread", Model.getThread()); //$NON-NLS-1$
			replace(index, "up", Model.getUptime()); //$NON-NLS-1$
			response = new HTTPResponse(index.toString().getBytes());
			response.setStatus(HTTPConstants.HTTP_STATUS_OK);
		} else {
			response = HTTPResponse.RESPONSE_INTERNAL_ERROR;
		}

		return response;
	}

	/**
	 * Replaces a pattern <code>$init.id</code> to the value.
	 *
	 * @param builder
	 *            the builder to replace from.
	 *
	 * @param id
	 *            the id to look for.
	 * @param value
	 *            the value to replace with.
	 */
	private void replace(StringBuilder builder, String id, long value) {
		String entering = "$init." + id; //$NON-NLS-1$
		int enterIndex = builder.indexOf(entering);
		if (enterIndex >= 0) {
			builder.replace(enterIndex, enterIndex + entering.length(), Long.toString(value));
		}
	}
}
