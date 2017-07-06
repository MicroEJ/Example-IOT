package com.microej.example.iot.ssl.rest.ui;

import java.util.logging.Level;

import com.microej.example.iot.ssl.rest.ExampleRestyHttps;
import com.microej.example.iot.ssl.rest.ui.out.OutputStreamRedirection;
import com.microej.example.iot.ssl.rest.ui.style.StylesheetPopulator;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.MicroUI;
import ej.wadapps.app.Activity;
import ej.widget.StyledDesktop;
import ej.widget.StyledPanel;
import ej.widget.navigation.navigator.HistorizedNavigator;

public class MainActivity extends NetworkCallback implements Activity {

	private HistorizedNavigator navi;

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
		navi = new HistorizedNavigator();
		StyledPanel panel = new StyledPanel();
		panel.setWidget(navi);
		panel.showFullScreen(desktop);
		desktop.show();

		ConnectivityManager connectivityManager = ServiceLoaderFactory.getServiceLoader()
				.getService(ConnectivityManager.class);
		if (connectivityManager != null) {
			NetworkRequest request = new NetworkRequest.Builder().build();
			connectivityManager.registerNetworkCallback(request, this);
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			if (info.isConnected()) {
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
		RestHttpsPage restHttpPage= new RestHttpsPage(navi);
		navi.show(restHttpPage);
	}

	@Override
	public void onLost(Network network) {
		ConnectingPage connectingPage = new ConnectingPage(navi);
		navi.show(connectingPage);
	}

}
