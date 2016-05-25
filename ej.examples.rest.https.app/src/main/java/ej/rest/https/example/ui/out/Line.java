/**
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package ej.rest.https.example.ui.out;

import ej.widget.basic.Label;

/**
 * Represents a line on the display
 */
public class Line extends Label{

	private final StringBuilder content;			// line content

	public Line() {
		super();
		content = new StringBuilder();
	}

	public void add(char c) {
		content.append(c);
	}

	public void flush(){
		setText(content.toString());
	}
}
