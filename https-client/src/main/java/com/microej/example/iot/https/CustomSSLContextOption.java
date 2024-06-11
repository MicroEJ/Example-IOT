/*
 * Copyright 2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.iot.https;

import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import ej.rest.web.Resty.Option;

/**
 * Custom Resty Option used to set the SSL Context to the URLConnection before the actual connection is made.
 * This option is required when using a custom SSL Context that is not set globally.
 */
public class CustomSSLContextOption extends Option {
	private final SSLContext sslConext;

	/**
	 * Provide the SSL Context that should be used by Resty.
	 *
	 * @param sslConext the SSL Context to use.
	 */
	public CustomSSLContextOption(SSLContext sslConext) {
		this.sslConext = sslConext;
	}

	/**
	 * Set the SSL Context only if required.
	 */
	@Override
	public void apply(URLConnection urlConnection) {
		if (urlConnection instanceof HttpsURLConnection) {
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlConnection;
			httpsURLConnection.setSSLSocketFactory(this.sslConext.getSocketFactory());
		}
	}
}
