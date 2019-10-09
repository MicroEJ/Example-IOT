/*
 * Java
 *
 * Copyright 2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.mutual.server;

import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class Server {

	private static final int PORT = 12345;
	private static final String SERVER_PKS12 = "/server.p12";
	private static final String PASSWORD = "demo";

	private static final String[] CLIENT_CERTIFICATES = new String[] { "clientA-crt.pem", "ca-crt.pem" };

	public static void main(String[] args)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException, KeyManagementException {
		KeyStore keyStore = KeyStore.getInstance("pkcs12");
		keyStore.load(Server.class.getResourceAsStream(SERVER_PKS12), PASSWORD.toCharArray());

		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyManagerFactory.init(keyStore, PASSWORD.toCharArray());


		KeyStore trustStore = KeyStore.getInstance("jks");
		trustStore.load(null, null);

		for (String certificate : CLIENT_CERTIFICATES) {
			Certificate cert = CertificateFactory.getInstance("X509") //$NON-NLS-1$
					.generateCertificate(Server.class.getResourceAsStream("/" + certificate)); //$NON-NLS-1$
			trustStore.setCertificateEntry(certificate, cert);
		}
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(trustStore);
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2"); //$NON-NLS-1$
		sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

		SSLServerSocket serverSocket = (SSLServerSocket) sslContext.getServerSocketFactory()
				.createServerSocket(PORT);
		serverSocket.setNeedClientAuth(true);
		while (true) {
			System.out.println("Server waiting for connection on " + PORT);
			try (SSLSocket accept = (SSLSocket) serverSocket.accept()) {
				accept.startHandshake();
				try (OutputStream out = accept.getOutputStream()) {
					out.write("Hello World".getBytes());
					out.flush();
				}
				accept.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
