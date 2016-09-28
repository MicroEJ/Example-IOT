/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/
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
