/*
 * Java
 *
 * Copyright 2015-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.ssl.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.json.me.JSONObject;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;
import ej.net.PollerConnectivityManager;
import ej.net.util.NtpUtil;
import ej.rest.web.AbstractContent;
import ej.rest.web.Content;
import ej.rest.web.Deletion;
import ej.rest.web.JSONResource;
import ej.rest.web.Replacement;
import ej.rest.web.Resty;
import ej.service.ServiceFactory;

public class Main {

	public static final Logger LOGGER = java.util.logging.Logger.getLogger("HTTPS example"); //$NON-NLS-1$

	//The server certificate file name
	public static final String SERVER_CERT_FILENAME = "AmazonRootCA1.crt"; //$NON-NLS-1$
	public static final String SERVER_CERT_PATH = "/certificates/"; //$NON-NLS-1$

	//X509 certificate type name
	public static final String CERT_TYPE = "X509"; //$NON-NLS-1$

	//TLS algorithm version 1.2
	public static final String TLS_VERSION_1_2 = "TLSv1.2"; //$NON-NLS-1$

	//The server url
	public static final String SERVER_URL = "https://postman-echo.com"; //$NON-NLS-1$

	public static void main(String[] args) throws Exception{

		waitForConnectivity();

		updateTime();

		try {
			// On first, we need to initialize the SSLContext for Resty Https connection
			initRestyHttpsContext();

			// GET request
			doGetRequest();

			// POST request
			doPostRequest();

			// PUT request
			doPutRequest();

			// DELETE request
			doDeleteRequest();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		stopConnectivityManager();
	}


	public static void waitForConnectivity() {
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


	/**
	 * Initialize the SSLContext used for Resty Https connection
	 * @throws Exception
	 */
	public static void initRestyHttpsContext() throws Exception{
		/*
		 * Create and initialize the SSLContext which will be used to connect to the secure Server.
		 * The followings steps show how to create and setup the SSLContext for Resty Https connection.
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

			//create a default KeyStore
			KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
			//our default KeyStore can not be loaded from an InputStream; so just load as empty KeyStore with null parameters
			store.load(null, null);
			//add the server certificate to our created KeyStore
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
			 * Step 6 : Finally, tell Https to use by default our sslContext's SocketFactory for SSLSocket creation.
			 * All Https Resty connection will use this SSLContext to connect to a secure server and the SSL handshake
			 * will be done with the defined trust store and TLS algorithm v1.2.
			 */
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		}
	}

	/**
	 * Get request example
	 * @throws Exception if an error occurs
	 */
	public static void doGetRequest() throws Exception{
		LOGGER.info("=========== GET REQUEST ==========="); //$NON-NLS-1$
		String requestURL = SERVER_URL + "/get"; //$NON-NLS-1$
		Resty resty = new Resty();

		// do GET request request;
		JSONResource resource = resty.json(requestURL);
		HttpURLConnection conn = resource.http();
		try{
			int responseCode = conn.getResponseCode();
			//check the connection response code
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
				JSONObject response = resource.object();
				LOGGER.info(response.toString());

				JSONObject headers = response.getJSONObject("headers"); //$NON-NLS-1$
				String agent = headers.getString("user-agent"); //$NON-NLS-1$
				String host = headers.getString("host"); //$NON-NLS-1$
				StringBuffer sb = new StringBuffer();

				sb.append("user-agent: ").append(agent).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
				sb.append("host: ").append(host).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
				LOGGER.info(sb.toString());

			}else{
				throw new IOException("Wrong response code " + responseCode + " for GET " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}

		}finally{
			conn.disconnect();
		}

	}

	/**
	 * POST request example
	 * @throws Exception if an error occurs
	 */
	public static void doPostRequest() throws Exception{
		LOGGER.info("=========== POST REQUEST ==========="); //$NON-NLS-1$
		String requestURL = SERVER_URL + "/post"; //$NON-NLS-1$
		Resty resty = new Resty();
		String data = "My POST request data"; //$NON-NLS-1$

		//create the POST request content
		AbstractContent postContent = new PostContent("text/plain; charset=UTF-8", data.getBytes("UTF-8")); //$NON-NLS-1$ //$NON-NLS-2$
		//do POST request
		JSONResource resource = resty.json(requestURL, postContent);

		HttpURLConnection conn = resource.http();
		try{
			int responseCode = conn.getResponseCode();
			//check the connection response code
			if(responseCode == HttpURLConnection.HTTP_OK){
				JSONObject response = resource.object();
				LOGGER.info(response.toString());
				LOGGER.info("Data sent for POST request: " + response.getString("data")); //$NON-NLS-1$ //$NON-NLS-2$
			}else{
				throw new IOException("Wrong response code " + responseCode + " for POST " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}finally{
			conn.disconnect();
		}

	}

	/**
	 * PUT request example
	 * @throws Exception if an error occurs
	 */
	public static void doPutRequest() throws Exception{
		LOGGER.info("=========== PUT REQUEST ==========="); //$NON-NLS-1$
		String requestURL = SERVER_URL + "/put"; //$NON-NLS-1$
		Resty resty = new Resty();
		String data = "My PUT request data"; //$NON-NLS-1$

		//create the PUT request content
		Content content = new Content("text/plain; charset=UTF-8", data.getBytes("UTF-8")); //$NON-NLS-1$ //$NON-NLS-2$
		Replacement putContent = new Replacement(content);

		//do PUT request
		JSONResource resource = resty.json(requestURL, putContent);

		HttpURLConnection conn = resource.http();
		try{
			int responseCode = conn.getResponseCode();
			//check the connection response code
			if(responseCode == HttpURLConnection.HTTP_OK){
				JSONObject response = resource.object();
				LOGGER.info(response.toString());
				LOGGER.info("Data sent for PUT request: " + response.getString("data")); //$NON-NLS-1$ //$NON-NLS-2$
			}else{
				throw new IOException("Wrong response code " + responseCode + " for PUT " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}finally{
			conn.disconnect();
		}

	}

	/**
	 * DELETE request example
	 * @throws Exception if an error occurs
	 */
	public static void doDeleteRequest() throws Exception{
		LOGGER.info("=========== DELETE REQUEST ==========="); //$NON-NLS-1$
		String requestURL = SERVER_URL + "/delete"; //$NON-NLS-1$
		Resty resty = new Resty();

		//create the DELETION request content
		AbstractContent deletionContent = new Deletion();

		//do DELETE request
		JSONResource resource = resty.json(requestURL, deletionContent);

		HttpURLConnection conn = resource.http();
		try{
			int responseCode = conn.getResponseCode();
			//check the connection response code
			if(responseCode == HttpURLConnection.HTTP_OK){
				//get the response info
				JSONObject response = resource.object();
				LOGGER.info(response.toString());
			}else{
				throw new IOException("Wrong response code " + responseCode + " for DELETE " + requestURL); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}finally{
			conn.disconnect();
		}
	}
}
