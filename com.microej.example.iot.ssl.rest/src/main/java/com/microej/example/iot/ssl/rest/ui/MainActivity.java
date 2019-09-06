/*
 * Java
 *
 * Copyright 2015-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.rest.ui;

import java.util.logging.Level;

import com.microej.example.iot.ssl.rest.ExampleRestyHttps;
import com.microej.example.iot.ssl.rest.ui.out.OutputStreamRedirection;
import com.microej.example.iot.ssl.rest.ui.style.StylesheetPopulator;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkInfo;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.mwt.MWT;
import ej.wadapps.app.Activity;
import ej.widget.StyledDesktop;
import ej.widget.StyledPanel;
import ej.widget.container.transition.SlideScreenshotTransitionContainer;
import ej.widget.container.transition.TransitionContainer;

public class MainActivity extends NetworkCallback implements Activity {

	private boolean isConnected;
	private TransitionContainer navi;

	public static void main(String[] args) {
		new MainActivity().onStart();
		ExampleRestyHttps.waitForConnectivity();
		ExampleRestyHttps.updateTime();
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRestart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart() {
		ExampleRestyHttps.LOGGER.setLevel(Level.ALL);
		ExampleRestyHttps.LOGGER.addHandler(OutputStreamRedirection.getINSTANCE().getHandler());

		try {
			ExampleRestyHttps.initRestyHttpsContext();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MicroUI.start();

		StylesheetPopulator.initialize();

		StyledDesktop desktop = new StyledDesktop();
		navi = new SlideScreenshotTransitionContainer(MWT.BOTTOM, true, false);
		StyledPanel panel = new StyledPanel();
		panel.setWidget(navi);
		panel.showFullScreen(desktop);
		desktop.show();

		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			connectivityManager.registerDefaultNetworkCallback(this);
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			boolean connected = info.isConnected();
			isConnected = !connected;
			if (connected) {
				onAvailable(null);
			} else {
				onLost(null);
			}
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStop() {
		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			connectivityManager.unregisterNetworkCallback(this);
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAvailable(Network network) {
		Display.getDefaultDisplay().callSerially(new Runnable() {

			@Override
			public void run() {
				if (!isConnected) {
					isConnected = true;
					navi.show(new RestHttpsPage(), true);
				}
			}
		});
	}

	@Override
	public void onLost(Network network) {
		Display.getDefaultDisplay().callSerially(new Runnable() {

			@Override
			public void run() {
				if (isConnected) {
					isConnected = false;
					navi.show(new ConnectingPage(), false);
				}
			}
		});
	}

}
