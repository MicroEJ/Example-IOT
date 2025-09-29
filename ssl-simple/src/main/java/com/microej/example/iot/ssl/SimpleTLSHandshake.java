/*
 * Java
 *
 * Copyright 2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;
import ej.net.PollerConnectivityManager;
import ej.net.util.NtpUtil;
import ej.service.ServiceFactory;

public class SimpleTLSHandshake {


	public static final Logger LOGGER = java.util.logging.Logger.getLogger("TLS example"); //$NON-NLS-1$

	// Server certificates.
	private static final String[] SERVER_CERT_FILENAME = { "DigiCert_Global_Root_G3.pem", //$NON-NLS-1$
	"DigiCert_Global_Root_G3_TLS_ECC_SHA384_2020_CA1.pem" }; //$NON-NLS-1$


	private static final String RESOURCE_DIR = "/certificates/"; //$NON-NLS-1$

	// X509 certificate type name
	private static final String CERT_TYPE = "X509"; //$NON-NLS-1$

	// TLS algorithm version 1.2
	private static final String TLS_VERSION = "TLSv1.2"; //$NON-NLS-1$

	// The server URL
	private static final String HOST = "example.org"; //$NON-NLS-1$

	private static final int PORT = 443;


	public static void main(String[] args) throws Exception{

		waitForConnectivity();

		updateTime();

		try {

			// On first, we need to initialize the SSLContext.
			SSLContext sslContext = initSSLContext();

			doRequest(sslContext);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		stopConnectivityManager();
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
							mutex.notifyAll();
						}
					}
				}
			};
			service.registerDefaultNetworkCallback(callback);
			NetworkCapabilities capabilities = service.getNetworkCapabilities(service.getActiveNetwork());
			while (capabilities == null || !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
				synchronized (mutex) {
					try {
						mutex.wait();
					} catch (InterruptedException e) {
						LOGGER.log(Level.WARNING, "Interrupted!", e);
						/* Clean up whatever needs to be handled before interrupting  */
						Thread.currentThread().interrupt();
					}
					/* Re-check capabilities after being notified */
					capabilities = service.getNetworkCapabilities(service.getActiveNetwork());
				}
			}
			service.unregisterNetworkCallback(callback);
			LOGGER.info("Connected."); //$NON-NLS-1$
		}
			LOGGER.info("No connectivity manager found."); //$NON-NLS-1$

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
		LOGGER.info("Time updated."); //$NON-NLS-1$
	}


	/**
	 * Initialize the SSLContext used for socket connections
	 *
	 * @throws IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException
	 */
	private static SSLContext initSSLContext() throws IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException, KeyStoreException {
		/**
		 * Map that will contain the already generated certificate, to avoid multiple generation.
		 */
		Map<String, Certificate> certificates = new HashMap<>();

		/*
		 * Create and initialize the SSLContext which will be used to connect to the secure Server.
		 */
		SSLContext sslContext = SSLContext.getInstance(TLS_VERSION);
		TrustManager[] trustManagers = generateTrustManagers(certificates);

		sslContext.init(null, trustManagers, null);

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
		return trustManagerFactory.getTrustManagers();

	}

	/**
	 * Get request example
	 *
	 * @param sslContext
	 * @throws IOException
	 *             if an error occurs
	 */
	private static void doRequest(SSLContext sslContext) throws IOException {
		LOGGER.info("=========== REQUEST ==========="); //$NON-NLS-1$
		try (SSLSocket socket = (SSLSocket) sslContext.getSocketFactory().createSocket(HOST, PORT)) {
			LOGGER.info("Start TLS handshake."); //$NON-NLS-1$
			socket.startHandshake();
			LOGGER.info("Handshake successfully done."); //$NON-NLS-1$
			try (InputStream in = socket.getInputStream()) {
				StringBuilder stringBuilder = new StringBuilder();
				byte[] buffer = new byte[64];
				while (true) {
					int read = in.read(buffer);
					if (read < 0) {
						break;
					}
					stringBuilder.append(new String(buffer, 0, read));
				}
				if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info("Connection timeout, received: " + stringBuilder); //$NON-NLS-1$
				}
			}
		}
	}

	private static Certificate generateCertificate(String certificate, Map<String, Certificate> certificates)
			throws CertificateException, IOException {
		Certificate myServerCert = certificates.get(certificate);
		if (myServerCert == null) {
			try (InputStream in = SimpleTLSHandshake.class.getResourceAsStream(RESOURCE_DIR + certificate)) {
				myServerCert = CertificateFactory.getInstance(CERT_TYPE).generateCertificate(in);
				certificates.put(certificate, myServerCert);
				// add the server certificate to our created KeyStore
			}
		}
		return myServerCert;
	}

}
