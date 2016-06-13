package ej.examples.iot.ssl.rest.ui;

import java.util.logging.Level;

import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.examples.iot.ssl.rest.ExampleRestyHttps;
import ej.examples.iot.ssl.rest.ui.out.OutputStreamRedirection;
import ej.examples.iot.ssl.rest.ui.style.StylesheetPopulator;
import ej.microui.MicroUI;
import ej.navigation.desktop.NavigationDesktop;
import ej.navigation.page.ClassNameURLResolver;
import ej.navigation.page.PagesStackURL;
import ej.navigation.page.URLResolver;
import ej.wadapps.app.Activity;

public class MainActivity extends NetworkCallback implements Activity {

	private NavigationDesktop desktop;

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
		URLResolver solver = new ClassNameURLResolver();
		PagesStackURL urlStackURL = new PagesStackURL(solver);
		desktop = new NavigationDesktop(solver,
				urlStackURL);

		StylesheetPopulator.initialize(desktop);

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
		desktop.show(RestHttpsPage.class.getName());
	}

	@Override
	public void onLost(Network network) {
		desktop.show(ConnectingPage.class.getName());
	}
}
