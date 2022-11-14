/*
 * Java
 *
 * Copyright 2019-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot;

import java.io.IOException;

import com.microej.example.iot.server.IndexEndpoint;
import com.microej.example.iot.server.ValueEndPoint;

import ej.restserver.RestServer;
import ej.restserver.endpoint.AliasEndpoint;
import ej.restserver.endpoint.ResourceRestEndpoint;

/**
 * Entry point initiating a server at the port 8080.
 */
public class Main {

	/**
	 * Base resource folder.
	 */
	public static final String RESOURCES = "/com/microej/example/iot/"; //$NON-NLS-1$
	private static final String SCRIPT = "script.js"; //$NON-NLS-1$
	private static final String STYLE = "styles.css"; //$NON-NLS-1$
	private static final int PORT = 8080;
	private static RestServer restServer;

	/**
	 * Entry point.
	 *
	 * @param args
	 *            not used.
	 * @throws IOException
	 *             if the server cannot be started.
	 */
	public static void main(String[] args) throws IOException {
		restServer = new RestServer(PORT, 10, 2);
		IndexEndpoint index = new IndexEndpoint();
		restServer.addEndpoint(index);
		// Add an alias to get the index by default.
		restServer.addEndpoint(new AliasEndpoint("/", index)); //$NON-NLS-1$
		restServer.addEndpoint(new ValueEndPoint());
		restServer.addEndpoint(new ResourceRestEndpoint(SCRIPT, RESOURCES + SCRIPT));
		restServer.addEndpoint(new ResourceRestEndpoint(STYLE, RESOURCES + STYLE));
		restServer.start();
	}

	/**
	 * Stops the server.
	 */
	public static void stop() {
		restServer.stop();

	}

}
