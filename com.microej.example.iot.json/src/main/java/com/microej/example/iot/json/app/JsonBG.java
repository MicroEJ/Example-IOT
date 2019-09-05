/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.json.app;

import com.microej.example.iot.json.MyJSONExample;

import ej.wadapps.app.BackgroundService;
/**
 *
 */
public class JsonBG implements BackgroundService {

	@Override
	public void onStart() {
		MyJSONExample.main(null);

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

}
