/*
 * Java
 *
 * Copyright 2015-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.rest.ui;

import com.microej.example.iot.ssl.rest.ui.style.ClassSelectors;

import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.basic.drawing.CircularProgressBar;
import ej.widget.container.List;

public class ConnectingPage extends AbstractOutputPage {

	@Override
	protected Widget createMainContent() {
		List list = new List();
		list.addClassSelector(ClassSelectors.GREY_BG);
		CircularProgressBar progressBar = new CircularProgressBar(0, 100, 0);
		progressBar.setIndeterminate(true);
		list.add(progressBar);
		list.add(new Label("Connecting...")); //$NON-NLS-1$
		return list;
	}

	@Override
	protected Widget createMenu() {
		return null;
	}
}
