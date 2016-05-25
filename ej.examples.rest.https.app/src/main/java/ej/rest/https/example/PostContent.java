/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package ej.rest.https.example;

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
