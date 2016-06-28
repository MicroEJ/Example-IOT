/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.examples.iot.ssl.rest.ui;

import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.style.Style;
import ej.style.container.AlignmentHelper;
import ej.style.container.Rectangle;
import ej.widget.StyledComposite;

/**
 * A composite that takes all the available space, and gives the minimum
 * space to its only child.
 */
public class WrapComposite extends StyledComposite {

	/**
	 * Constructor of a WrapComposite
	 *
	 * @param widget
	 *            the only child.
	 */
	public WrapComposite(Widget widget) {
		removeAllWidgets();
		add(widget);
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle bounds) {
		int widthHint = bounds.getWidth();
		int heightHint = bounds.getHeight();

		if (getWidgetsCount() == 1) {
			Widget widget = getWidget(0);
			widget.validate(MWT.NONE, MWT.NONE);

			boolean revalidate = false;
			int preferredWidth = widget.getPreferredWidth();
			if (widthHint != MWT.NONE && widthHint < preferredWidth) {
				revalidate = true;
			} else {
				widthHint = preferredWidth;
			}

			int preferredHeight = widget.getPreferredHeight();
			if (heightHint != MWT.NONE && heightHint < preferredHeight) {
				revalidate = true;
			} else {
				heightHint = preferredHeight;
			}

			if (revalidate) {
				widget.validate(widthHint, heightHint);
			}

		}

		return new Rectangle(0, 0, widthHint, heightHint);
	}

	@Override
	protected void setBoundsContent(Rectangle bounds) {
		if (getWidgetsCount() == 1) {
			int preferredWidth = getPreferredWidth();
			int preferredHeight = getPreferredHeight();
			int alignment = getStyle().getAlignment();
			int x = AlignmentHelper.computeXLeftCorner(preferredWidth, bounds.getX(), bounds.getWidth(), alignment);
			int y = AlignmentHelper.computeYTopCorner(preferredHeight, bounds.getY(), bounds.getHeight(), alignment);
			getWidget(0).setBounds(x, y,
					preferredWidth, preferredHeight);

		}
	}
}
