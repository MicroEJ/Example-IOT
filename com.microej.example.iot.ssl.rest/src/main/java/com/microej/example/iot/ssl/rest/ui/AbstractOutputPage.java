/*
 * Java
 *
 * Copyright 2015-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.rest.ui;

import com.microej.example.iot.ssl.rest.ui.out.OutputStreamRedirection;
import com.microej.example.iot.ssl.rest.ui.out.OutputStreamWidget;
import com.microej.example.iot.ssl.rest.ui.style.ClassSelectors;
import com.microej.example.iot.ssl.rest.ui.style.Images;
import com.microej.example.iot.ssl.rest.ui.style.Pictos;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.exit.ExitHandler;
import ej.mwt.Widget;
import ej.widget.basic.Image;
import ej.widget.basic.Label;
import ej.widget.composed.ButtonWrapper;
import ej.widget.container.Dock;
import ej.widget.listener.OnClickListener;
import ej.widget.navigation.page.Page;

public abstract class AbstractOutputPage extends Page{

	private Dock content;
	private OutputStreamWidget outStreamWidget;


	public AbstractOutputPage() {
		super();
		setWidget(createContent());
	}

	private Widget createContent() {
		this.content = new Dock();
		this.content.addTop(createTopBar());
		this.content.setCenter(createMainContent());
		return this.content;
	}

	protected Widget createOutput() {
		OutputStreamRedirection redirection = OutputStreamRedirection.getINSTANCE();
		outStreamWidget = new OutputStreamWidget(false, true);
		redirection.setOutputStreamWidget(outStreamWidget);
		return outStreamWidget;
	}

	abstract protected Widget createMenu();

	protected Widget createMainContent() {
		Dock dock = new Dock();
		dock.addLeft(createMenu());
		dock.setCenter(createOutput());
		dock.addClassSelector(ClassSelectors.GREY_BG);
		return dock;
	}

	/**
	 * Creates the widget representing the top bar of the page.
	 *
	 * @return the top bar widget.
	 */
	protected Widget createTopBar() {
		// The title of the page.
		Widget title = getTitle();
		Dock topBar = new Dock();
		topBar.setCenter(title);

		ButtonWrapper backButton = new ButtonWrapper();
		Label label;
		// Add an exit button.
		backButton.addOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				ExitHandler exitHandler = ServiceLoaderFactory.getServiceLoader().getService(ExitHandler.class);
				if (exitHandler != null) {
					exitHandler.exit();
				}
			}
		});
		label = new Label(Character.toString(Pictos.STORE));
		label.addClassSelector(ClassSelectors.PICTO);
		backButton.setWidget(label);

		topBar.addLeft(backButton);

		ButtonWrapper logo = new ButtonWrapper();
		logo.setWidget(new Image(Images.MICROEJ_LOGO));
		logo.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				outStreamWidget.clear();
			}
		});

		// Image logo = new Image(Images.MICROEJ_LOGO);
		topBar.addRight(logo);
		topBar.addClassSelector(ClassSelectors.TITLE);

		return topBar;
	}

	protected Widget getTitle() {
		return new Label(getClass().getSimpleName());
	}
}
