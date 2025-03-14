/*
 * Java
 *
 * Copyright 2019-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import com.microej.example.iot.server.IndexEndpoint;
import com.microej.example.iot.server.ValueEndPoint;

import ej.restserver.RestServer;
import ej.restserver.endpoint.AliasEndpoint;
import ej.restserver.endpoint.ResourceRestEndpoint;
import ej.service.ServiceFactory;

/**
 * Entry point initiating a server at the port 8080.
 */
public class Main {

	/**
	 * Base resource folder.
	 */
	public static final Logger LOGGER = java.util.logging.Logger.getLogger("Dynamic Rest Server example"); //$NON-NLS-1$
	public static final String RESOURCES = "/com/microej/example/iot/"; //$NON-NLS-1$
	private static final String SCRIPT = "script.js"; //$NON-NLS-1$
	private static final String STYLE = "styles.css"; //$NON-NLS-1$
	private static final int PORT = 8080;

	/**
	 * Entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {

		try {
			waitForConnectivity();

			RestServer restServer = new RestServer(PORT, 10, 2);
			IndexEndpoint index = new IndexEndpoint();
			restServer.addEndpoint(index);
			// Add an alias to get the index by default.
			restServer.addEndpoint(new AliasEndpoint("/", index)); //$NON-NLS-1$
			restServer.addEndpoint(new ValueEndPoint());
			restServer.addEndpoint(new ResourceRestEndpoint(SCRIPT, RESOURCES + SCRIPT));
			restServer.addEndpoint(new ResourceRestEndpoint(STYLE, RESOURCES + STYLE));
			restServer.start();

			LOGGER.info("Server listening on port: " + PORT);

		} catch (IOException | InterruptedException e) {
			LOGGER.severe("Failed to create RestServer");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Log the list of registered {@link NetworkInterface}.
	 */
	private static void listNetworkInterfaces() {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Available Network interfaces:");

			boolean netifFound = false;

			try {
				Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
				while (interfaces.hasMoreElements()) {
					NetworkInterface netif = interfaces.nextElement();
					if (isValidNetworkInterface(netif)) {
						netifFound = true;
						logInterfaceInformation(netif);
					}
				}

				if (!netifFound) {
					LOGGER.info("(none)");
				}
			} catch (SocketException e) {
				if (LOGGER.isLoggable(Level.FINEST)) {
					LOGGER.log(Level.FINEST, e.getMessage(), e); // NOSONAR log if log level is finest or above
				}
			}
		}
	}

	/**
	 *
	 * @param netif
	 *            to validate
	 * @return true if interface is UP and not loop back
	 */
	private static boolean isValidNetworkInterface(final NetworkInterface netif) {
		try {
			return netif.isUp() && !netif.isLoopback();
		} catch (SocketException e) {
			if (LOGGER.isLoggable(Level.FINEST)) {
				LOGGER.log(Level.FINEST, e.getMessage(), e); // NOSONAR log if log level is finest or above
			}
		}
		return false;
	}

	private static void logInterfaceInformation(NetworkInterface netif) throws SocketException {
		LOGGER.info("Network Name: " + netif.getName());
		LOGGER.info("--- MTU: " + netif.getMTU());
		Enumeration<InetAddress> addresses = netif.getInetAddresses();
		while (addresses.hasMoreElements()) {
			InetAddress address = addresses.nextElement();
			LOGGER.info("--- IP Address: " + address.getHostAddress());
		}
	}

	public static void waitForConnectivity() throws InterruptedException {
		LOGGER.info("=========== Waiting for connectivity ==========="); //$NON-NLS-1$
		final Object mutex = new Object();
		final ConnectivityManager service = ServiceFactory.getServiceLoader().getService(ConnectivityManager.class);
		if (service != null) {
			ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
				@Override
				public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
					if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
						synchronized (mutex) {
							listNetworkInterfaces();
							mutex.notifyAll();
						}
					}
				}
			};
			service.registerDefaultNetworkCallback(callback);
			NetworkCapabilities capabilities = service.getNetworkCapabilities(service.getActiveNetwork());
			if (capabilities == null || !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
				synchronized (mutex) {
					try {
						mutex.wait();
					} catch (InterruptedException e) {
						throw new InterruptedException(e.getMessage());
					}
				}
			}
			service.unregisterNetworkCallback(callback);
			LOGGER.info("Connected"); //$NON-NLS-1$
		} else {
			LOGGER.severe("No connectivity manager found."); //$NON-NLS-1$
		}
	}
}
