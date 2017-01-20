/*
 * Java
 *
 * Copyright 2015-2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
 *
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

	protected void addContent(URLConnection con) throws IOException{
		((HttpURLConnection) con).setRequestMethod("POST");
		super.addContent(con);
	}
}
