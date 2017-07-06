package com.microej.example.iot.ssl.restget;

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

import org.json.me.JSONArray;

import ej.rest.web.JSONResource;
import ej.rest.web.Resty;
import ej.wadapps.app.Activity;

/**
 *
 *
 */
public class GetActivity implements Activity {

	public static final Logger LOGGER = java.util.logging.Logger.getLogger("HTTPS GET");

	// The server certificate file name
	public static String SERVER_CERT_FILENAME = "communitystore.microej.com.crt";
	public static String SERVER_CERT_PATH = "/certificates/";

	// X509 certificate type name
	public static String CERT_TYPE = "X509";

	// TLS algorithm version 1.2
	public static String TLS_VERSION_1_2 = "TLSv1.2";

	// The server url
	public static String SERVER_URL = "https://preprodstore.microej.com";

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRestart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart() {

		LOGGER.setLevel(Level.ALL);

		try {

			GetActivity.initRestyHttpsContext();

			GetActivity.doGetRequest();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

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
		try (
				/*
				 * Step 1 : Create an input stream with the server certificate file
				 */

				InputStream in = GetActivity.class.getResourceAsStream(SERVER_CERT_PATH + SERVER_CERT_FILENAME)) {

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
			store.setCertificateEntry("myServer", myServerCert);

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

		LOGGER.info("=========== GET REQUEST ===========");
		String requestURL = SERVER_URL + "/api/v2/DeviceReferences";
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
				throw new IOException("Wrong response code " + responseCode + " for GET " + requestURL);
			}

		} finally {
			conn.disconnect();
		}

	}

}
