/*
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.restget;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.json.me.JSONArray;

import android.net.SntpClient;
import ej.bon.Util;
import ej.rest.web.JSONResource;
import ej.rest.web.Resty;
import ej.wadapps.app.BackgroundService;

/**
 *
 *
 */
public class GetBS implements BackgroundService {

	private static final Logger LOGGER = java.util.logging.Logger.getLogger("HTTPS GET"); //$NON-NLS-1$

	// The server certificate file name
	private static final String SERVER_CERT_FILENAME = "GlobalSignRootCA.crt"; //$NON-NLS-1$
	private static final String SERVER_CERT_PATH = "/certificates/"; //$NON-NLS-1$

	// X509 certificate type name
	private static final String CERT_TYPE = "X509"; //$NON-NLS-1$

	// TLS algorithm version 1.2
	private static final String TLS_VERSION_1_2 = "TLSv1.2"; //$NON-NLS-1$

	// The server url
	private static final String SERVER_URL = "https://communitystore.microej.com"; //$NON-NLS-1$

	public static void main(String[] args) {
		updateTime();
		new GetBS().onStart();
	}

	@Override
	public void onStart() {

		LOGGER.setLevel(Level.ALL);

		try {

			GetBS.initRestyHttpsContext();

			GetBS.doGetRequest();

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
	public static void initRestyHttpsContext() throws Exception {
		/*
		 * Create and initialize the SSLContext which will be used to connect to the secure Server. The followings steps
		 * show how to create and setup the SSLContext for Resty Https connection.
		 */
		/*
		 * Step 1 : Create an input stream with the server certificate file
		 */
		try (InputStream in = GetBS.class.getResourceAsStream(SERVER_CERT_PATH + SERVER_CERT_FILENAME)) {

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
			SSLContext sslContext = SSLContext.getInstance(TLS_VERSION_1_2);
			sslContext.init(null, trustManagers, null);

			/*
			 * Step 6 : Finally, tell Https to use by default our sslContext's SocketFactory for SSLSocket creation. All
			 * Https Resty connection will use this SSLContext to connect to a secure server and the SSL handshake will
			 * be done with the defined trust store and TLS algorithm v1.2.
			 */
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

		}
	}

	/**
	 * Get request example
	 *
	 * @throws Exception
	 *             if an error occurs
	 */
	public static void doGetRequest() throws Exception {

		LOGGER.info("=========== GET REQUEST ==========="); //$NON-NLS-1$
		String requestURL = SERVER_URL + "/api/v2/DeviceReferences"; //$NON-NLS-1$
		Resty resty = new Resty();

		// do GET request request;
		JSONResource resource = resty.json(requestURL);
		HttpURLConnection conn = resource.http();
		try {
			int responseCode = conn.getResponseCode();
			// check the connection response code
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

				JSONArray response = resource.array();
				LOGGER.info(response.toString(2));

			} else {
				throw new IOException("Wrong response code " + responseCode + " for GET " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}

		} finally {
			conn.disconnect();
		}

	}

	/**
	 *
	 */
	public static void updateTime() {
		LOGGER.info("=========== Updating time ==========="); //$NON-NLS-1$
		SntpClient ntpClient = new SntpClient();

		while (Util.currentTimeMillis() < 1000000) {
			/**
			 * Request NTP time
			 */
			if (ntpClient.requestTime("ntp.ubuntu.com", 123, 1000)) { //$NON-NLS-1$
				long now = ntpClient.getNtpTime() + Util.platformTimeMillis() - ntpClient.getNtpTimeReference();

				Calendar.getInstance().setTimeInMillis(now);
				Util.setCurrentTimeMillis(now);
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// Sanity.
				}
			}
		}
		LOGGER.info("Time updated"); //$NON-NLS-1$
	}

}
