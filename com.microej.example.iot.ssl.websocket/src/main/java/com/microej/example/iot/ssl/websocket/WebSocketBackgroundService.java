/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import ej.bon.Util;
import ej.net.util.NtpUtil;
import ej.wadapps.app.BackgroundService;
import ej.websocket.Endpoint;
import ej.websocket.ReasonForClosure;
import ej.websocket.WebSocket;
import ej.websocket.WebSocketSecure;
import ej.websocket.WebSocketURI;

/**
 *
 * Background service using a WebSocket server to echo a hello world.
 */
public class WebSocketBackgroundService implements BackgroundService, Endpoint {

	private static final int SLEEP_DURATION = 1000;
	private static final String HELLO_WORLD = "Hello World!"; //$NON-NLS-1$
	private static final String GOODBYE_WORLD = "Goodbye World!"; //$NON-NLS-1$

	private static final Logger LOGGER = java.util.logging.Logger.getLogger("WEBSOCKET"); //$NON-NLS-1$

	// The server certificate file name
	private static final String SERVER_CERT_FILENAME = "LetsEncryptAuthorityX3.crt"; //$NON-NLS-1$
	private static final String SERVER_CERT_PATH = "/certificates/"; //$NON-NLS-1$

	// X509 certificate type name
	private static final String CERT_TYPE = "X509"; //$NON-NLS-1$

	// TLS algorithm version 1.2
	private static final String TLS_VERSION_1_2 = "TLSv1.2"; //$NON-NLS-1$

	// The server url
	private static final String SERVER_URL = "echo.websocket.org"; //$NON-NLS-1$

	private SSLContext sslContext;

	public static void main(String[] args) {
		updateTime();
		new WebSocketBackgroundService().onStart();
	}

	@Override
	public void onStart() {

		LOGGER.setLevel(Level.ALL);

		try {

			initRestyHttpsContext();

			useWebsocket();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onStop() {
		// Nothing to do.
	}

	/**
	 * Initialize the SSLContext used for Resty Https connection
	 *
	 * @throws Exception
	 */
	public void initRestyHttpsContext() throws Exception {
		/*
		 * Create and initialize the SSLContext which will be used to connect to the secure Server. The followings steps
		 * show how to create and setup the SSLContext for Resty Https connection.
		 */
		/*
		 * Step 1 : Create an input stream with the server certificate file
		 */
		try (InputStream in = WebSocketBackgroundService.class
				.getResourceAsStream(SERVER_CERT_PATH + SERVER_CERT_FILENAME)) {

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
	}

	/**
	 * Get request example
	 *
	 * @throws Exception
	 *             if an error occurs
	 */
	public void useWebsocket() throws Exception {
		LOGGER.info("=========== Websocket USAGE ==========="); //$NON-NLS-1$

		try (WebSocketSecure webSocket = new WebSocketSecure(
				new WebSocketURI(SERVER_URL, WebSocketURI.DEFAULT_SECURE_PORT, "/", true), this, this.sslContext)) { //$NON-NLS-1$
			webSocket.connect();
			Thread.sleep(SLEEP_DURATION);
			System.out.println("Sending messqge " + HELLO_WORLD);
			webSocket.sendText(HELLO_WORLD);
			Thread.sleep(SLEEP_DURATION);
			System.out.println("Sending binary message length " + HELLO_WORLD.length());
			webSocket.sendBinary(HELLO_WORLD.getBytes());
			Thread.sleep(SLEEP_DURATION);
		}
	}

	@Override
	public byte[] onBinaryMessage(WebSocket ws, byte[] message) {
		System.out.println("Binary message receive length = " + message.length); //$NON-NLS-1$
		if (Arrays.equals(message, HELLO_WORLD.getBytes())) {
			// When hello is echoed, return goodbye
			System.out.println("Answering with binary message length " + GOODBYE_WORLD.length());
			return GOODBYE_WORLD.getBytes();
		} else {
			// Does not return anything to the server to avoid loop.
			return null;
		}
	}

	@Override
	public void onClose(WebSocket ws, ReasonForClosure closeReason) {
		System.out.println("Close: " + closeReason); //$NON-NLS-1$

	}

	@Override
	public void onError(WebSocket ws, Throwable thr) {
		thr.printStackTrace();

	}

	@Override
	public void onOpen(WebSocket ws) {
		System.out.println("WebsocketBS.onOpen()"); //$NON-NLS-1$

	}

	@Override
	public void onPong(byte[] data) {
		System.out.println("WebsocketBS.onPong()"); //$NON-NLS-1$

	}

	@Override
	public String onTextMessage(WebSocket ws, String message) {
		System.out.println("Message received: " + message); //$NON-NLS-1$
		if (message.equals(HELLO_WORLD)) {
			System.out.println("Answering with " + GOODBYE_WORLD);
			// When hello is echoed, return goodbye
			return GOODBYE_WORLD;
		} else {
			// Does not return anything to the server to avoid loop.
			return null;
		}
	}

	/**
	 * Update the platform time.
	 */
	public static void updateTime() {
		LOGGER.info("=========== Updating time ==========="); //$NON-NLS-1$
		while (Util.currentTimeMillis() < 1000000) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// Sanity.
			}
			try {
				NtpUtil.updateLocalTime();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("Time updated"); //$NON-NLS-1$
	}

}
