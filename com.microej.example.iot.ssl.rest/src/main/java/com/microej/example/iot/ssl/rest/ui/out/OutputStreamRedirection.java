/*
 * Java
 *
 * Copyright 2015-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.ssl.rest.ui.out;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.microej.example.iot.ssl.rest.ExampleRestyHttps;

public class OutputStreamRedirection extends OutputStream {

	private OutputStreamWidget outputStreamWidget;
	private final List<Line> lines;
	private Line lastDisplayLine;
	private Line currentLine;
	private final Handler handler;
	private static OutputStreamRedirection INSTANCE;

	public OutputStreamRedirection() throws InstantiationException {
		super();
		handler = new LoggerHandler();

		if(INSTANCE != null){
			throw new InstantiationException("Can't instanciate two instances"); //$NON-NLS-1$
		}
		INSTANCE = this;
		lines = new ArrayList<Line>();
		newLine();
	}

	public OutputStreamRedirection(OutputStreamWidget outputStreamWidget) throws InstantiationException {
		this();
		this.outputStreamWidget = outputStreamWidget;
	}

	@Override
	public void write(int b) throws IOException {
		// character added
		currentLine.add((char) b);
		if (b == '\n') {
			currentLine.flush();
			newLine();

			return;
		}
	}

	private void newLine() {
		synchronized (lines) {
			currentLine = new Line();
			lines.add(currentLine);
			flushLines();
		}

	}

	private void flushLines() {
		synchronized (lines) {
			if(outputStreamWidget!=null){
				Iterator<Line> iterator = lines.iterator();
				while(iterator.hasNext()){
					Line next = iterator.next();
					iterator.remove();

					if(next != lastDisplayLine){
						outputStreamWidget.addLine(next);
						lastDisplayLine = next;
					}
				}
			}
		}

	}

	public OutputStreamWidget getOutputStreamWidget() {
		return outputStreamWidget;
	}

	public void setOutputStreamWidget(OutputStreamWidget outputStreamWidget) {
		this.outputStreamWidget = outputStreamWidget;
		flushLines();
	}

	public static OutputStreamRedirection getINSTANCE() {
		if(INSTANCE==null){
			try {
				INSTANCE = new OutputStreamRedirection();
			} catch (InstantiationException e) {
				ExampleRestyHttps.LOGGER.info(e.getMessage());
			}
		}
		return INSTANCE;
	}

	public Handler getHandler(){
		return handler;
	}

	private class LoggerHandler extends Handler{

		@Override
		public void publish(LogRecord record) {
			try {
				byte[] bytes = record.getMessage().getBytes();
				write(bytes);
				if (bytes[bytes.length - 1] != '\n') {
					write('\n');
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void flush() {
			flushLines();

		}

		@Override
		public void close() throws SecurityException {
			// nothing to do

		}

	}
}
