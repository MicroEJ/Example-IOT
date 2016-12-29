package ej.examples.iot.ssl.rest.ui;

import ej.container.List;
import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.basic.drawing.CircularProgressBar;

public class ConnectingPage extends AbstractOutputPage {

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
