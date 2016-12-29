/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/
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
