package ej.rest.https.example.ui;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.container.Dock;
import ej.exit.ExitHandler;
import ej.mwt.Widget;
import ej.navigation.desktop.NavigationDesktop;
import ej.navigation.page.Page;
import ej.rest.https.example.ui.out.OutputStreamRedirection;
import ej.rest.https.example.ui.out.OutputStreamWidget;
import ej.rest.https.example.ui.style.ClassSelectors;
import ej.rest.https.example.ui.style.Images;
import ej.rest.https.example.ui.style.Pictos;
import ej.widget.StyledDesktop;
import ej.widget.basic.Image;
import ej.widget.basic.Label;
import ej.widget.basic.image.ImageHelper;
import ej.widget.composed.ButtonComposite;
import ej.widget.listener.OnClickListener;

public abstract class AbstractOutputPage extends Page{

	private Dock content;
	private OutputStreamWidget outStreamWidget;

	public AbstractOutputPage() {
		super();
		setWidget(createContent());
	}

	private Widget createContent() {
		this.content = new Dock();
		this.content.setHorizontal(false);
		this.content.setFirst(createTopBar());
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
		dock.setFirst(createMenu());
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

		ButtonComposite backButton = new ButtonComposite();
		Label label;
		if (canGoBack()) {
			// Add a back button.
			backButton.addOnClickListener(new OnClickListener() {

				@Override
				public void onClick() {
					StyledDesktop desktop = AbstractOutputPage.this.getDesktop();
					if(desktop instanceof NavigationDesktop){
						((NavigationDesktop)desktop).back();
					}
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

		topBar.setFirst(backButton);

		ButtonComposite logo = new ButtonComposite();
		logo.setWidget(new Image(ImageHelper.loadImage(Images.MICROEJ_LOGO)));
		logo.setWidget(new Image(Images.MICROEJ_LOGO));
		logo.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				outStreamWidget.clear();
			}
		});

		// Image logo = new Image(Images.MICROEJ_LOGO);
		topBar.setLast(logo);
		topBar.addClassSelector(ClassSelectors.TITLE);

		return topBar;
	}

	private boolean canGoBack() {
		StyledDesktop desktop = this.getDesktop();
		if(desktop instanceof NavigationDesktop){
			return ((NavigationDesktop)desktop).canGoBack();
		}
		return false;
	}

	protected Widget getTitle() {
		return new Label(getClass().getSimpleName());
	}
}
