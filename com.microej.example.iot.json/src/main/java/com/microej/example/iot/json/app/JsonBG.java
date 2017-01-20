/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
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
