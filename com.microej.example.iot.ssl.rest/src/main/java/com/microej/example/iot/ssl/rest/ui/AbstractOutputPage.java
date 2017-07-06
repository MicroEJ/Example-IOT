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
import ej.widget.navigation.navigator.HistorizedNavigator;
import ej.widget.navigation.page.Page;

public abstract class AbstractOutputPage extends Page{

	private Dock content;
	private OutputStreamWidget outStreamWidget;
	private HistorizedNavigator navi;
	

	public AbstractOutputPage(HistorizedNavigator navi) {
		super();
		this.navi = navi;
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
		if (canGoBack()) {
			// Add a back button.
			backButton.addOnClickListener(new OnClickListener() {

				@Override
				public void onClick() {
					navi.back();
				}
			});
			label = new Label(Character.toString(Pictos.BACK));
		} else {
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
		}
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

	private boolean canGoBack() {
		return navi.canGoBackward();

	}

	protected Widget getTitle() {
		return new Label(getClass().getSimpleName());
	}
}
