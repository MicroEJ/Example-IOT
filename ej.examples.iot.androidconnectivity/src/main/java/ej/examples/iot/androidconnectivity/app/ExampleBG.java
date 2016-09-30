/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/
 */
package ej.examples.iot.androidconnectivity.app;

import ej.examples.iot.androidconnectivity.MyConnectivityExample;
import ej.wadapps.app.BackgroundService;
/**
 *
 */
public class ExampleBG implements BackgroundService {

	private MyConnectivityExample connectivityExample;

	@Override
	public void onStart() {
		connectivityExample = new MyConnectivityExample();

	}

	@Override
	public void onStop() {
		connectivityExample.unregisiterConnectivityManager();

	}

}
