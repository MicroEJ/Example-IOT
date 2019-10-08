/*
 * Java
 *
 * Copyright 2015-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.rest.ui.out;

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
