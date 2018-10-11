/*
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import ej.rest.web.Content;

public class PostContent extends Content{

	public PostContent(String aMimeType, byte[] someBytes) {
		super(aMimeType, someBytes);
	}

	@Override
	protected void addContent(URLConnection con) throws IOException{
		((HttpURLConnection) con).setRequestMethod("POST"); //$NON-NLS-1$
		super.addContent(con);
	}
}
