package com.microej.example.iot.ssl.rest.ui;

import ej.container.List;
import com.microej.example.iot.ssl.rest.ExampleRestyHttps;
import com.microej.example.iot.ssl.rest.ui.style.ClassSelectors;
import com.microej.example.iot.ssl.rest.ui.style.Pictos;
import ej.mwt.Widget;
import ej.util.concurrent.SingleThreadExecutor;
import ej.widget.basic.Label;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;

public class RestHttpsPage extends AbstractOutputPage {

	SingleThreadExecutor executor = new SingleThreadExecutor();

	@Override
	protected Widget createMenu() {
		write("HTTPS request tester connected to " + ExampleRestyHttps.SERVER_URL);
		List menu = new List(false);
		Button doGetButton = new Button("Get");
		doGetButton.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				executor.execute(new Runnable() {

					@Override
					public void run() {
						try {
							ExampleRestyHttps.doGetRequest();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		menu.add(doGetButton);

		Button doPutButton = new Button("Put");
		doPutButton.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				executor.execute(new Runnable() {

					@Override
					public void run() {
						try {
							ExampleRestyHttps.doPutRequest();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		menu.add(doPutButton);

		Button doPostButton = new Button("Post");
		doPostButton.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				executor.execute(new Runnable() {

					@Override
					public void run() {
						try {
							ExampleRestyHttps.doPostRequest();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		menu.add(doPostButton);

		Button doDeleteButton = new Button("Delete");
		doDeleteButton.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				executor.execute(new Runnable() {

					@Override
					public void run() {
						try {
							ExampleRestyHttps.doDeleteRequest();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		menu.add(doDeleteButton);
		menu.addClassSelector(ClassSelectors.MENU);
		return menu;
	}

	private void write(String string) {
		ExampleRestyHttps.LOGGER.info(string);
	}

	@Override
	protected Widget getTitle() {
		List title = new List(true);
		Label lock = new Label(Character.toString(Pictos.LOCK));
		lock.addClassSelector(ClassSelectors.PICTO);
		title.add(lock);
		title.add(new Label(ExampleRestyHttps.SERVER_URL));

		return new WrapComposite(title);
	}


}
