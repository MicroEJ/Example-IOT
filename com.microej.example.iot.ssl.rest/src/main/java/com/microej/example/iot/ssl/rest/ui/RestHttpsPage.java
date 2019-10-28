/*
 * Java
 *
 * Copyright 2015-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.rest.ui;

import com.microej.example.iot.ssl.rest.ExampleRestyHttps;
import com.microej.example.iot.ssl.rest.ui.style.ClassSelectors;
import com.microej.example.iot.ssl.rest.ui.style.Pictos;

import ej.mwt.Widget;
import ej.util.concurrent.SingleThreadExecutor;
import ej.widget.basic.Button;
import ej.widget.basic.Label;
import ej.widget.container.List;
import ej.widget.listener.OnClickListener;

public class RestHttpsPage extends AbstractOutputPage {

	private final SingleThreadExecutor executor = new SingleThreadExecutor();

	@Override
	protected Widget createMenu() {
		ExampleRestyHttps.LOGGER.info("HTTPS request tester connected to " + ExampleRestyHttps.SERVER_URL); //$NON-NLS-1$
		List menu = new List(false);
		Button doGetButton = new Button("Get"); //$NON-NLS-1$
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

		Button doPutButton = new Button("Put"); //$NON-NLS-1$
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

		Button doPostButton = new Button("Post"); //$NON-NLS-1$
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

		Button doDeleteButton = new Button("Delete"); //$NON-NLS-1$
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
