/*
 * Copyright 2023-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.https;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import ej.net.PollerConnectivityManager;
import ej.net.util.NtpUtil;
import ej.rest.web.*;
import ej.service.ServiceFactory;

import org.json.me.JSONObject;

/**
 * The entry point of the https client demo.
 */
public class Main {
	public static final Logger LOGGER = java.util.logging.Logger.getLogger("HTTPS example"); //$NON-NLS-1$

	// The server certificate file name
	public static final String SERVER_CERT_FILENAME = "AmazonRootCA1.crt"; //$NON-NLS-1$
	public static final String SERVER_CERT_PATH = "/certificates/"; //$NON-NLS-1$

	// X509 certificate type name
	public static final String CERT_TYPE = "X509"; //$NON-NLS-1$

	// TLS algorithm version 1.2
	public static final String TLS_VERSION_1_2 = "TLSv1.2"; //$NON-NLS-1$

	// The server url
	public static final String SERVER_URL = "https://postman-echo.com"; //$NON-NLS-1$



	/**
	 * Shows the main page.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {

		waitForConnectivity();

		updateTime();

		try {
			SSLContext sslContext = createCustomSSLContext();

			requestGETWithQueryParameters(sslContext);
			requestGETWithPathParameters(sslContext);
			requestPOSTWithUrlEncodedParameters(sslContext);
			requestPOSTWithFormData(sslContext);
			requestPOSTWithRawJson(sslContext);
			requestPOSTWithBinaryData(sslContext);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		stopConnectivityManager();
	}

	/*
	 * Create and initialize the SSLContext which will be used to connect to the secure Server. The followings steps
	 * show how to create and setup the SSLContext for Resty Https connection.
	 * @return SSLContext returns the custom SSL context.
	 */
	public static SSLContext createCustomSSLContext() throws Exception {

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
			SSLContext sslContext = SSLContext.getInstance(TLS_VERSION_1_2);
			sslContext.init(null, trustManagers, null);
			
			return sslContext;
		}
	}

	/**
	 * GET request with query parameters.
	 * @param sslContext custom SSL Context to use.
	 * @throws Exception io exception or json exception.
	 */
	private static void requestGETWithQueryParameters(SSLContext sslContext) throws Exception {
		LOGGER.info("=========== GET REQUEST WITH QUERY PARAMETERS ===========");

		Resty.Option sslContextOption = new CustomSSLContextOption(sslContext);
		Resty restClient = new Resty(sslContextOption);
		String requestURL = SERVER_URL + "/get?param1=val1&param2=val2"; //$NON-NLS-1$
		// do GET request request;
		TextResource resource = restClient.text(requestURL);
		HttpURLConnection conn = resource.http();
		try {
			int responseCode = conn.getResponseCode();
			// check the connection response code
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				LOGGER.info(resource.toString());
			} else {
				throw new IOException("Wrong response code " + responseCode + " for GET " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} finally {
			conn.disconnect();
		}
	}

	/**
	 * GET request with path parameters.
	 * @param sslContext custom SSL Context to use.
	 * @throws Exception io exception or json exception.
	 */
	private static void requestGETWithPathParameters(SSLContext sslContext) throws Exception {
		LOGGER.info("=========== GET REQUEST WITH PATH PARAMETERS ===========");

		Resty.Option sslContextOption = new CustomSSLContextOption(sslContext);
		Resty restClient = new Resty(sslContextOption);
		String requestURL = SERVER_URL + "/delay/2"; //$NON-NLS-1$
		// do GET request request;
		TextResource resource = restClient.text(requestURL);
		HttpURLConnection conn = resource.http();
		try {
			int responseCode = conn.getResponseCode();
			// check the connection response code
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				LOGGER.info(resource.toString());
			} else {
				throw new IOException("Wrong response code " + responseCode + " for GET " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} finally {
			conn.disconnect();
		}
	}

	/**
	 * POST request with url-encoded parameters.
	 * @param sslContext custom SSL Context to use.
	 * @throws Exception io exception or json exception.
	 */
	private static void requestPOSTWithUrlEncodedParameters(SSLContext sslContext) throws Exception {
		LOGGER.info("=========== POST REQUEST WITH URL ENCODED PARAMETERS ===========");

		Resty.Option sslContextOption = new CustomSSLContextOption(sslContext);
		Resty restClient = new Resty(sslContextOption);
		String requestURL = SERVER_URL + "/post"; //$NON-NLS-1$
		// do POST request
		TextResource resource = restClient.text(requestURL, Resty.content(URLEncoder.encode("formParam1=value1&formparam2=value2", "UTF-8")));

		HttpURLConnection conn = resource.http();
		try {
			int responseCode = conn.getResponseCode();
			// check the connection response code
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				LOGGER.info(resource.toString());
			} else {
				throw new IOException("Wrong response code " + responseCode + " for POST " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} finally {
			conn.disconnect();
		}
	}

	/**
	 * POST request with form data.
	 * @param sslContext custom SSL Context to use.
	 * @throws Exception io exception or json exception.
	 */
	private static void requestPOSTWithFormData(SSLContext sslContext) throws Exception {
		LOGGER.info("=========== POST REQUEST WITH FORM DATA  ===========");

		Resty.Option sslContextOption = new CustomSSLContextOption(sslContext);
		Resty restClient = new Resty(sslContextOption);
		String requestURL = SERVER_URL + "/post"; //$NON-NLS-1$
		Content content = new Content("text/plain;charset=UTF-8", "I am the content that is being sent.".getBytes());
		// do POST request
		TextResource resource = restClient.text(requestURL, Resty.form(new FormData("form-data-content", content)));

		HttpURLConnection conn = resource.http();
		try {
			int responseCode = conn.getResponseCode();
			// check the connection response code
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				LOGGER.info(resource.toString());
			} else {
				throw new IOException("Wrong response code " + responseCode + " for POST " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} finally {
			conn.disconnect();
		}
	}

	/**
	 * POST request with a json body.
	 * @param sslContext custom SSL Context to use.
	 * @throws Exception io exception or json exception.
	 */
	private static void requestPOSTWithRawJson(SSLContext sslContext) throws Exception {
		LOGGER.info("=========== POST REQUEST WITH RAW JSON  ===========");

		Resty.Option sslContextOption = new CustomSSLContextOption(sslContext);
		Resty restClient = new Resty(sslContextOption);
		String requestURL = SERVER_URL + "/post"; //$NON-NLS-1$
		JSONObject ob = new JSONObject();
		ob.append("greetings", "Hi There!");
		// do POST request
		// The Content-Type and Content-Length are filled here automatically.
		TextResource resource = restClient.text(requestURL, Resty.content(ob));
		HttpURLConnection conn = resource.http();
		try {
			int responseCode = conn.getResponseCode();
			// check the connection response code
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				LOGGER.info(resource.toString());
			} else {
				throw new IOException("Wrong response code " + responseCode + " for POST " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} finally {
			conn.disconnect();
		}
	}

	/**
	 * POST request with binary data.
	 * @param sslContext custom SSL Context to use.
	 * @throws Exception io exception or json exception.
	 */
	private static void requestPOSTWithBinaryData(SSLContext sslContext) throws Exception {
		LOGGER.info("=========== POST REQUEST WITH BINARY DATA  ===========");

		Resty.Option sslContextOption = new CustomSSLContextOption(sslContext);
		Resty restClient = new Resty(sslContextOption);
		String requestURL = SERVER_URL + "/post"; //$NON-NLS-1$
		InputStream stream = new ByteArrayInputStream("Hi there!".getBytes("UTF-8"));
		// do POST request
		TextResource resource = restClient.text(requestURL, Resty.chunked("application/octet-stream", stream));
		HttpURLConnection conn = resource.http();
		try {
			int responseCode = conn.getResponseCode();
			// check the connection response code
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				LOGGER.info(resource.toString());
			} else {
				throw new IOException("Wrong response code " + responseCode + " for POST " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} finally {
			conn.disconnect();
		}
	}


	public static void waitForConnectivity() {
		LOGGER.info("=========== Waiting for connectivity ==========="); //$NON-NLS-1$
		final Object mutex = new Object();
		final ConnectivityManager service = ServiceFactory.getServiceLoader().getService(ConnectivityManager.class);
		if(service!=null) {
			ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
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

	/**
	 *
	 */
	public static void updateTime() {
		LOGGER.info("=========== Updating time ==========="); //$NON-NLS-1$
		try {
			NtpUtil.updateLocalTime();
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Could not update time.", e); //$NON-NLS-1$
		}
		LOGGER.info("Time updated"); //$NON-NLS-1$
	}


}
