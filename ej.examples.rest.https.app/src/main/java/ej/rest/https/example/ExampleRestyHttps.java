/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package ej.rest.https.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.json.me.JSONObject;

import ej.rest.web.AbstractContent;
import ej.rest.web.Content;
import ej.rest.web.Deletion;
import ej.rest.web.JSONResource;
import ej.rest.web.Replacement;
import ej.rest.web.Resty;

public class ExampleRestyHttps {

	public static final Logger LOGGER = java.util.logging.Logger.getLogger("HTTPS example");

	//The server certificate file name
	public static String SERVER_CERT_FILENAME = "httpbin.org.crt";
	public static String SERVER_CERT_PATH = "/certificates/";

	//X509 certificate type name
	public static String CERT_TYPE = "X509";

	//TLS algorithm version 1.2
	public static String TLS_VERSION_1_2 = "TLSv1.2";

	//The server url
	public static String SERVER_URL = "https://httpbin.org";

	public static void main(String[] args) throws Exception{

		//On first, we need to initialize the SSLContext for Resty Https connection
		initRestyHttpsContext();

		//GET request
		doGetRequest();

		//POST request
		doPostRequest();

		//PUT request
		doPutRequest();

		//DELETE request
		doDeleteRequest();
	}


	/**
	 * Initialize the SSLContext used for Resty Https connection
	 * @throws Exception
	 */
	public static void initRestyHttpsContext() throws Exception{
		/*
		 * Create and initialize the SSLContext which will be used to connect the secure Server.
		 * The followings steps show how to create and setup the SSLContext for Resty Https connection.
		 */
		try (
				/*
				 * Step 1 : Create an input stream with the server certificate
				 * file
				 */

				InputStream in = ExampleRestyHttps.class.getResourceAsStream(SERVER_CERT_PATH + SERVER_CERT_FILENAME)) {

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
			 * Step 6 : Finally, tell Https to use by default our sslContext's SocketFactory for SSLSocket creation.
			 * All Https Resty connection will use this SSLContext to connect a secure server and the SSL handshake
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
		LOGGER.info("=========== GET REQUEST ===========");
		String requestURL = SERVER_URL+"/get";
		Resty resty = new Resty();
		//do GET request request;
		JSONResource resource = resty.json(requestURL);
		HttpURLConnection conn = resource.http();
		try{
			int responseCode = conn.getResponseCode();
			//check the connection response code
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
				JSONObject response = resource.object();
				LOGGER.info(response.toString());
				String ip = response.getString("origin");
				String url = response.getString("url");
				JSONObject headers = response.getJSONObject("headers");
				String agent = headers.getString("User-Agent");
				String host = headers.getString("Host");

				StringBuffer sb = new StringBuffer();
				sb.append("my ip: ").append(ip).append("\n");
				sb.append("url: ").append(url).append("\n");
				sb.append("user-agent: ").append(agent).append("\n");
				sb.append("host: ").append(host).append("\n");
				LOGGER.info(sb.toString());

			}else{
				throw new IOException("Wrong response code "+ responseCode+ " for GET "+requestURL);
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
		LOGGER.info("=========== POST REQUEST ===========");
		String requestURL = SERVER_URL+"/post";
		Resty resty = new Resty();
		String data = "My POST request data";

		//create the POST request content
		AbstractContent postContent = new PostContent("text/plain; charset=UTF-8", data.getBytes("UTF-8"));
		//do POST request
		JSONResource resource = resty.json(requestURL, postContent);

		HttpURLConnection conn = resource.http();
		try{
			int responseCode = conn.getResponseCode();
			//check the connection response code
			if(responseCode == HttpURLConnection.HTTP_OK){
				JSONObject response = resource.object();
				LOGGER.info(response.toString());
				LOGGER.info("Data sent for POST request: "+response.getString("data"));
			}else{
				throw new IOException("Wrong response code "+ responseCode+ " for POST "+requestURL);
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
		LOGGER.info("=========== PUT REQUEST ===========");
		String requestURL = SERVER_URL+"/put";
		Resty resty = new Resty();
		String data = "My PUT request data";

		//create the PUT request content
		Content content = new Content("text/plain; charset=UTF-8", data.getBytes("UTF-8"));
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
				LOGGER.info("Data sent for PUT request: "+response.getString("data"));
			}else{
				throw new IOException("Wrong response code "+ responseCode+ " for PUT "+requestURL);
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
		LOGGER.info("=========== DELETE REQUEST ===========");
		String requestURL = SERVER_URL+"/delete";
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
				//get the response infos
				JSONObject response = resource.object();
				LOGGER.info(response.toString());
			}else{
				throw new IOException("Wrong response code "+ responseCode+ " for DELETE "+requestURL);
			}
		}finally{
			conn.disconnect();
		}

	}


}
