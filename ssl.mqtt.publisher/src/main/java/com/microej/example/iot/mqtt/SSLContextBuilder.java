/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.mqtt;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * This class provides functions to help creating trust stores to initialise the
 * SSL context.
 */
@SuppressWarnings("nls")
public class SSLContextBuilder {

	private static final String TLS_V_1_2 = "TLSv1.2";

	/**
	 * Builds a SSL context using the mosquitto certificate.
	 *
	 * @return a configured SSL context.
	 */
	public static SSLContext getSslContext() {

		try {

			// Trust managers
			KeyStore tStore = KeyStore.getInstance(KeyStore.getDefaultType());
			tStore.load(null, null);

			tStore.setCertificateEntry("mosquitto.org", loadCertificate("/certificates/mosquitto.org.crt"));

			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
			trustManagerFactory.init(tStore);

			// SSL context
			SSLContext context = SSLContext.getInstance(TLS_V_1_2);
			context.init(null, trustManagerFactory.getTrustManagers(), null);
			return context;
		} catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | KeyManagementException
				| IOException e) {
			throw new IllegalStateException(e);
		}

	}

	private static Certificate loadCertificate(String certPath) throws IOException, CertificateException {
		Certificate cert;
		try (InputStream in = SSLContextBuilder.class.getResourceAsStream(certPath)) {
			if (in == null) {
				throw new IllegalStateException("resource not found: " + certPath);
			}

			// Generate the server certificates
			cert = CertificateFactory.getInstance("X.509").generateCertificate(in);
		}
		return cert;
	}

}

