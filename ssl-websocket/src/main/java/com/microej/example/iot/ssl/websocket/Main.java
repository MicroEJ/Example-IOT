/*
 * Java
 *
 * Copyright 2019-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.ssl.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;

import ej.net.PollerConnectivityManager;
import ej.net.util.NtpUtil;
import ej.service.ServiceFactory;
import ej.websocket.Endpoint;
import ej.websocket.ReasonForClosure;
import ej.websocket.WebSocket;
import ej.websocket.WebSocketException;
import ej.websocket.WebSocketSecure;
import ej.websocket.WebSocketURI;

/**
 *
 * Entry point using a WebSocket server to echo a hello world.
 */
public class Main implements Endpoint {

	private static final int SLEEP_DURATION = 1000;
	private static final String HELLO_WORLD = "Hello World!"; //$NON-NLS-1$
	private static final String GOODBYE_WORLD = "Goodbye World!"; //$NON-NLS-1$

	private static final Logger LOGGER = java.util.logging.Logger.getLogger("WEBSOCKET"); //$NON-NLS-1$

	// The server certificate file name
	private static final String SERVER_CERT_FILENAME = "GTSRootR1.crt"; //$NON-NLS-1$
	private static final String SERVER_CERT_PATH = "/certificates/"; //$NON-NLS-1$

	// X509 certificate type name
	private static final String CERT_TYPE = "X509"; //$NON-NLS-1$

	// TLS algorithm version 1.2
	private static final String TLS_VERSION_1_2 = "TLSv1.2"; //$NON-NLS-1$

	// The server url

	private static final String SERVER_URL = "ws.ifelse.io"; //$NON-NLS-1$

	private static final String RESOURCE_NAME = "/.ws"; //$NON-NLS-1$

	private static final int SERVER_PORT = 443;

	private SSLContext sslContext;

	/**
	 * Starts the {@link Main} after initialisation.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {

		new Main().start();
		stopConnectivityManager();
	}

	public void start() {
		waitForConnectivity();
		updateTime();

		LOGGER.setLevel(Level.ALL);

		try {
			initSSLContext();
			useSecureWebsocket();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the SSLContext.
	 *
	 * @throws IOException
	 *             if the certificate cannot be loaded.
	 *
	 * @throws CertificateException
	 *             if the certificate is not valid.
	 * @throws NoSuchAlgorithmException
	 *             if the platform does not support x509.
	 * @throws KeyStoreException
	 *             if an error occurs during keystore init.
	 * @throws KeyManagementException
	 *             if an error occurs during keystore init.
	 */
	public void initSSLContext() throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException,
			KeyManagementException {
		LOGGER.info("=========== Initialize SSLContext ==========="); //$NON-NLS-1$
		/*
		 * Create and initialize the SSLContext which will be used to connect to the secure Server. The followings steps
		 * show how to create and setup the SSLContext for Resty Https connection.
		 */
		/*
		 * Step 1 : Create an input stream with the server certificate file
		 */
		try (InputStream in = Main.class.getResourceAsStream(SERVER_CERT_PATH + SERVER_CERT_FILENAME)) {

			/*
			 * Step 2 : Generate the server certificate
			 */
			CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
			Certificate myServerCert = certificateFactory.generateCertificate(in);

			/*
			 * Step 3 : Create and setup the KeyStore with the server certificate
			 */

			// create a default KeyStore
			KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
			// our default KeyStore can not be loaded from an InputStream; so just load as empty KeyStore with null
			// parameters
			store.load(null, null);
			// add the server certificate to our created KeyStore
			store.setCertificateEntry("myServer", myServerCert); //$NON-NLS-1$

			/*
			 * Step 4: Create and initialize the trust manager with our KeyStore
			 */
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(CERT_TYPE);
			trustManagerFactory.init(store);
			TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

			/*
			 * Step 5 : Create and and initialize the SSLContext with our trust managers
			 */
			this.sslContext = SSLContext.getInstance(TLS_VERSION_1_2);
			this.sslContext.init(null, trustManagers, null);
		}
		LOGGER.info("Initialization done"); //$NON-NLS-1$
	}

	/**
	 * Uses a secure websocket.
	 *
	 * @throws IOException
	 *             if an {@link IOException} occurs during the communication.
	 * @throws NoSuchAlgorithmException
	 *             if context is null and does not
	 * @throws WebSocketException
	 *             if an {@link WebSocketException} occurs during the communication.
	 * @throws InterruptedException
	 *             if the thread is interrupted.
	 */
	public void useSecureWebsocket()
			throws NoSuchAlgorithmException, IOException, WebSocketException, InterruptedException {
		LOGGER.info("=========== Secure Websocket USAGE ==========="); //$NON-NLS-1$

		try (WebSocketSecure webSocket = new WebSocketSecure(
				new WebSocketURI(SERVER_URL, SERVER_PORT, RESOURCE_NAME, true), this, this.sslContext)) {
			webSocket.connect();
			Thread.sleep(SLEEP_DURATION);
			LOGGER.info("Sending message " + HELLO_WORLD); //$NON-NLS-1$
			webSocket.sendText(HELLO_WORLD);
			Thread.sleep(SLEEP_DURATION);
			LOGGER.info("Sending binary message length " + HELLO_WORLD.length()); //$NON-NLS-1$
			webSocket.sendBinary(HELLO_WORLD.getBytes());
			Thread.sleep(SLEEP_DURATION);
		}
	}

	@Override
	public byte[] onBinaryMessage(WebSocket ws, byte[] message) {
		LOGGER.info("Binary message receive length = " + message.length); //$NON-NLS-1$
		if (Arrays.equals(message, HELLO_WORLD.getBytes())) {
			// When hello is echoed, return goodbye
			LOGGER.info("Answering with binary message length " + GOODBYE_WORLD.length()); //$NON-NLS-1$
			return GOODBYE_WORLD.getBytes();
		} else {
			// Does not return anything to the server to avoid loop.
			return null;
		}
	}

	@Override
	public void onClose(WebSocket ws, ReasonForClosure closeReason) {
		LOGGER.info("Close: " + closeReason); //$NON-NLS-1$

	}

	@Override
	public void onError(WebSocket ws, Throwable thr) {
		LOGGER.log(Level.INFO, "onError", thr); //$NON-NLS-1$
	}

	@Override
	public void onOpen(WebSocket ws) {
		LOGGER.info("onOpen"); //$NON-NLS-1$

	}

	@Override
	public void onPong(byte[] data) {
		LOGGER.info("onPong"); //$NON-NLS-1$

	}

	@Override
	public String onTextMessage(WebSocket ws, String message) {
		LOGGER.info("Message received: " + message); //$NON-NLS-1$
		if (message.equals(HELLO_WORLD)) {
			LOGGER.info("Answering with " + GOODBYE_WORLD); //$NON-NLS-1$
			// When hello is echoed, return goodbye
			return GOODBYE_WORLD;
		} else {
			// Does not return anything to the server to avoid loop.
			return null;
		}
	}

	private static void waitForConnectivity() {
		LOGGER.info("=========== Waiting for connectivity ==========="); //$NON-NLS-1$
		final Object mutex = new Object();
		final ConnectivityManager service = ServiceFactory.getServiceLoader().getService(ConnectivityManager.class);
		if (service != null) {
			NetworkCallback callback = new NetworkCallback() {
				@Override
				public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
					if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
						synchronized (mutex) {
							mutex.notify();
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
						e.printStackTrace();
					}
				}
			}
			service.unregisterNetworkCallback(callback);
			LOGGER.info("Connected"); //$NON-NLS-1$
		} else {
			LOGGER.info("No connectivity manager found."); //$NON-NLS-1$
		}
	}

	private static void stopConnectivityManager() {
		LOGGER.info("=========== Stopping connectivity Manager ==========="); //$NON-NLS-1$
		final ConnectivityManager service = ServiceFactory.getServiceLoader().getService(ConnectivityManager.class);
		if (service instanceof PollerConnectivityManager) {
			((PollerConnectivityManager) service).cancel();
		} else {
			LOGGER.info("No connectivity manager found."); //$NON-NLS-1$
		}
	}

	/**
	 * Update the platform time.
	 */
	public static void updateTime() {
		LOGGER.info("=========== Updating time ==========="); //$NON-NLS-1$
		try {
			NtpUtil.updateLocalTime();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Time updated"); //$NON-NLS-1$
	}

	@Override
	public void onPing(byte[] data) {
		LOGGER.info("onPing"); //$NON-NLS-1$

	}

}
