/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.examples.iot.json.app;

import ej.examples.iot.json.MyJSONExample;
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
