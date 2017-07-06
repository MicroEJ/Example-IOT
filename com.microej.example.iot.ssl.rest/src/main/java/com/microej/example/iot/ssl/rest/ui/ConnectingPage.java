package com.microej.example.iot.ssl.rest.ui;

import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.basic.drawing.CircularProgressBar;
import ej.widget.container.List;
import ej.widget.navigation.navigator.HistorizedNavigator;

public class ConnectingPage extends AbstractOutputPage {

	public ConnectingPage(HistorizedNavigator navi) {
		super(navi);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Widget createMainContent() {
		List list = new List();

		CircularProgressBar progressBar = new CircularProgressBar(0, 100, 0);
		progressBar.setIndeterminate(true);
		list.add(progressBar);
		list.add(new Label("Connecting..."));
		return list;
	}

	@Override
	protected Widget createMenu() {
		return null;
	}
}
