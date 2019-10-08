/*
 * Java
 *
 * Copyright 2015-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.rest.ui.out;

import ej.mwt.Panel;
import ej.mwt.Widget;
import ej.widget.container.List;
import ej.widget.container.Scroll;

public class OutputStreamWidget extends Scroll {

	private static final int UNLIMITED = -1;
	private int maxLines = 50;

	private final List content;

	// private boolean isPressed;

	/**
	 *
	 */
	public OutputStreamWidget() {
		super();
		content = new List();
		super.setWidget(content);
	}

	/**
	 * @param horizontal
	 * @param showScrollbar
	 */
	public OutputStreamWidget(boolean horizontal, boolean showScrollbar) {
		super(horizontal, showScrollbar);
		content = new List(horizontal);
		super.setWidget(content);
	}

	public void addLine(final Widget widget) throws NullPointerException, IllegalArgumentException {
		Panel panel = getPanel();
		if (panel != null && isShown()) {
			panel.getDesktop().getDisplay().callSerially(new Runnable() {
				@Override
				public void run() {
					removeOldLines();
					synchronized (content) {
						content.add(widget);
					}
					content.revalidate();
					scrollDown();
				}
			});
		} else {
			content.add(widget);
		}
	}

	private void scrollDown() {
		int height = content.getHeight();
		if (height > OutputStreamWidget.this.getHeight()) {// &&
			// !isPressed){
			scrollTo(height);
		}
	}

	/**
	 * Gets the maxLines.
	 *
	 * @return the maxLines.
	 */
	public int getMaxLines() {
		return maxLines;
	}

	/**
	 * Sets the maxLines.
	 *
	 * @param maxLines
	 *            the maxLines to set.
	 */
	public void setMaxLines(int maxLines) {
		this.maxLines = maxLines;
		Panel panel = getPanel();
		if (panel != null) {
			panel.getDesktop().getDisplay().callSerially(new Runnable() {
				@Override
				public void run() {
					removeOldLines();
				}
			});
		}
	}

	/**
	 *
	 */
	private void removeOldLines() {
		synchronized (content) {
			while (maxLines != UNLIMITED && content.getWidgetsCount() >= maxLines) {
				content.remove(content.getWidget(0));
			}
		}

	}

	public void clear() {
		Panel panel = getPanel();
		if (panel != null) {
			panel.getDesktop().getDisplay().callSerially(new Runnable() {
				@Override
				public void run() {
					synchronized (content) {
						content.removeAllWidgets();
						content.revalidate();
					}
				}
			});
		}

	}

}
