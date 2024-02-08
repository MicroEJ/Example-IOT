/*
 * Java
 *
 * Copyright 2019-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.server;

import java.util.Map;

import ej.hoka.http.HTTPConstants;
import ej.hoka.http.HTTPRequest;
import ej.hoka.http.HTTPResponse;
import ej.restserver.RestEndpoint;

/**
 * End point providing the value with a JSON format on a get request. e.g.
 * <code>{"ram":11789,"thread":3,"up":1563868508310}</code>
 */
public class ValueEndPoint extends RestEndpoint {

	/**
	 * Instantiates a value endpoint.
	 *
	 * @throws IllegalArgumentException
	 *             should not occurs.
	 */
	public ValueEndPoint() throws IllegalArgumentException {
		super("values"); //$NON-NLS-1$
	}

	@Override
	public HTTPResponse get(HTTPRequest request, Map<String, String> headers, Map<String, String> parameters) {
		StringBuilder builder = new StringBuilder("{\"ram\":"); //$NON-NLS-1$
		builder.append(Model.getRam());
		builder.append(",\"thread\":"); //$NON-NLS-1$
		builder.append(Model.getThread());
		builder.append(",\"up\":"); //$NON-NLS-1$
		builder.append(Model.getUptime());
		builder.append("}"); //$NON-NLS-1$
		HTTPResponse response = new HTTPResponse(builder.toString());
		response.setStatus(HTTPConstants.HTTP_STATUS_OK);
		response.setMimeType("application/json"); //$NON-NLS-1$
		return response;
	}
}
