/*
 * Java
 *
 * Copyright 2019-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.ssl.mutual;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;
import ej.kf.FeatureEntryPoint;
import ej.net.PollerConnectivityManager;
import ej.net.util.NtpUtil;
import ej.service.ServiceFactory;

public class MutualAuthEntryPoint implements FeatureEntryPoint {


	public static final Logger LOGGER = java.util.logging.Logger.getLogger("HTTPS example"); //$NON-NLS-1$

	/**
	 * Server certificates.
	 */
	private static final String[] SERVER_CERT_FILENAME = { "ca-crt.pem", "server-crt.pem" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Ordered client certificate chain.
	 */
	private static final String[] CLIENT_CERT_CHAIN = { "clientA-crt.pem", "ca-crt.pem" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Private key of the client.
	 */
	private static final String DEVICE_KEY = "clientA-key.der"; //$NON-NLS-1$

	private static final String KEY_PASSWORD = "demo"; //$NON-NLS-1$

	private static final String RESOURCE_DIR = "/certificates/"; //$NON-NLS-1$

	//X509 certificate type name
	private static final String CERT_TYPE = "X509"; //$NON-NLS-1$

	//TLS algorithm version 1.2
	private static final String TLS_VERSION_1_2 = "TLSv1.2"; //$NON-NLS-1$

	//The server url
	private static final String HOST = "localhost"; //$NON-NLS-1$

	private static final int PORT = 12345;


	public static void main(String[] args) throws Exception{
		waitForConnectivity();

		updateTime();

		new MutualAuthEntryPoint().start();

		stopConnectivityManager();
	}

	@Override
	public void start() {
		waitForConnectivity();

		// updateTime();
		try {

			// On first, we need to initialize the SSLContext.
			SSLContext sslContext = initRestyHttpsContext();

			doRequest(sslContext);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void stop() {
		// Nothing to do.

	}

	private static void waitForConnectivity() {
		LOGGER.info("=========== Waiting for connectivity ==========="); //$NON-NLS-1$
		final Object mutex = new Object();
		final ConnectivityManager service = ServiceFactory.getServiceLoader().getService(ConnectivityManager.class);
		if(service!=null) {
			NetworkCallback callback = new NetworkCallback() {
				@Override
				public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
					if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
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
		final ConnectivityManager service = ServiceFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (service instanceof PollerConnectivityManager) {
			((PollerConnectivityManager) service).cancel();
		} else {
			LOGGER.info("No connectivity manager found."); //$NON-NLS-1$
		}
	}

	private static void updateTime() {
		LOGGER.info("=========== Updating time ==========="); //$NON-NLS-1$
		try {
			NtpUtil.updateLocalTime();
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Could not update time.", e); //$NON-NLS-1$
		}
		LOGGER.info("Time updated"); //$NON-NLS-1$
	}


	/**
	 * Initialize the SSLContext used for Resty Https connection
	 * @throws Exception
	 */
	private static SSLContext initRestyHttpsContext() throws Exception {
		/**
		 * Map that will contains the already generated certificate, to avoid multiple generation.
		 */
		Map<String, Certificate> certificates = new HashMap<>();

		/*
		 * Create and initialize the SSLContext which will be used to connect to the secure Server.
		 */
		SSLContext sslContext = SSLContext.getInstance(TLS_VERSION_1_2);
		TrustManager[] trustManagers = generateTrustManagers(certificates);
		KeyManager[] keyManagers = generateKeyManagers(certificates);
		sslContext.init(keyManagers, trustManagers, null);

		return sslContext;
	}


	private static TrustManager[] generateTrustManagers(Map<String, Certificate> certificates)
			throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
		// create a default KeyStore
		KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
		// our default KeyStore can not be loaded from an InputStream; so just load as empty KeyStore with null
		// parameters
		store.load(null, null);

		/*
		 * Step 1 : Generate the server certificate
		 */
		for (String certificate : SERVER_CERT_FILENAME) {
			/*
			 * Add each certificate.
			 */
			store.setCertificateEntry(certificate, generateCertificate(certificate, certificates));
		}



		/*
		 * Create and initialize the trust manager with our KeyStore
		 */
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(CERT_TYPE);
		trustManagerFactory.init(store);
		TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
		return trustManagers;
	}

	private static KeyManager[] generateKeyManagers(Map<String, Certificate> certificates)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException {

		// create a default KeyStore
		KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
		// our default KeyStore can not be loaded from an InputStream; so just load as empty KeyStore with null
		// parameters
		store.load(null, null);

		Certificate[] certificatesChain = new Certificate[CLIENT_CERT_CHAIN.length];
		int i = 0;
		/*
		 * Step 2 : Generate the server certificate
		 */
		for (String certificate : CLIENT_CERT_CHAIN) {
			/*
			 * Step 1 : Create an input stream with the server certificate file
			 */
			certificatesChain[i] = generateCertificate(certificate, certificates);
			i++;
		}
		// Generate the certificate and its key
		store.setKeyEntry("client", loadResource(DEVICE_KEY), certificatesChain); //$NON-NLS-1$

		// Initialize the key managers and return them
		KeyManagerFactory km = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		km.init(store, KEY_PASSWORD.toCharArray());
		return km.getKeyManagers();
	}

	/**
	 * Get request example
	 *
	 * @param sslContext
	 * @throws Exception
	 *             if an error occurs
	 */
	private static void doRequest(SSLContext sslContext) throws Exception {
		LOGGER.info("=========== REQUEST ==========="); //$NON-NLS-1$
		try (SSLSocket socket = (SSLSocket) sslContext.getSocketFactory().createSocket(HOST, PORT)) {
			LOGGER.info("start handshake"); //$NON-NLS-1$
			socket.startHandshake();
			LOGGER.info("handshake done"); //$NON-NLS-1$
			try (InputStream in = socket.getInputStream()) {
				StringBuffer stringBuffer = new StringBuffer();
				byte[] buffer = new byte[64];
				while (true) {
					int read = in.read(buffer);
					if (read < 0) {
						break;
					}
					stringBuffer.append(new String(buffer, 0, read));
				}
				LOGGER.info("Received: " + stringBuffer); //$NON-NLS-1$
			}
		}
	}

	private static Certificate generateCertificate(String certificate, Map<String, Certificate> certificates)
			throws CertificateException, IOException {
		Certificate myServerCert = certificates.get(certificate);
		if (myServerCert == null) {
			try (InputStream in = MutualAuthEntryPoint.class.getResourceAsStream(RESOURCE_DIR + certificate)) {
				myServerCert = CertificateFactory.getInstance(CERT_TYPE).generateCertificate(in);
				certificates.put(certificate, myServerCert);
				// add the server certificate to our created KeyStore
			}
		}
		return myServerCert;
	}

	private static byte[] loadResource(String resourceName) throws IOException {
		try (InputStream stream = MutualAuthEntryPoint.class.getResourceAsStream(RESOURCE_DIR + resourceName)) {
			DataInputStream dataInputStream = new DataInputStream(stream);
			byte[] data = new byte[stream.available()];

			dataInputStream.readFully(data);

			return data;
		}
	}

}
