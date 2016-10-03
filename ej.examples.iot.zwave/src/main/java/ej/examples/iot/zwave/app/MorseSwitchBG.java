/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.examples.iot.zwave.app;

import java.io.IOException;
import java.util.Iterator;

import ej.basedriver.BinaryState;
import ej.basedriver.SwitchWithReturnState;
import ej.ecom.DeviceManager;
import ej.wadapps.app.BackgroundService;

/**
 *
 */
public class MorseSwitchBG extends Thread implements BackgroundService {

	private static final String[] CHAR_TO_MORSE = {
			"._", "_...", "_._.", "_..", ".", ".._.", "__.", "....", "..", ".___", "_._", "._..", "__", "_.", "___",
			".__.", "__._", "._.", "...", "_", ".._", "..._", ".__", "_.._", "_.__", "__..", ".____", "..___", "...__",
			"...._", ".....", "_....", "__...", "___..", "____.", "_____"
	};
	private static final char SHORT = '.';
	// private static final char LONG = '-';

	private static final int LOOP_DELAY = 5_000; // 5s
	private static final int SHORT_DELAY = 500;
	private static final int LONG_DELAY = SHORT_DELAY * 2;
	private static final int OFF_DELAY = SHORT_DELAY;
	private static final int UNKNOWN_DELAY = SHORT_DELAY;

	private boolean running = false;

	private static final String TEXT = "MicroEJ";

	@Override
	public void onStart() {
		running = true;
		this.start();

	}

	@Override
	public void onStop() {
		running = false;
		this.interrupt();

	}

	@Override
	public void run() {
		while (running) {
			// Initial state
			switchLights(BinaryState.OFF);
			// For each character
			for(char c: TEXT.toLowerCase().toCharArray()){
				String morse = getMorse(c);
				if (morse != null) {
					for (char m : morse.toCharArray()) {
						switchLights(BinaryState.ON);
						sleep((m == SHORT) ? SHORT_DELAY : LONG_DELAY);
						switchLights(BinaryState.OFF);
						sleep(OFF_DELAY);
					}
				} else { // The character is not known
					sleep(UNKNOWN_DELAY);
				}
			}
			sleep(LOOP_DELAY);
		}
	}

	/**
	 * @param c
	 * @return
	 */
	private String getMorse(char c) {
		if (c < 'a' || c > 'z') {
			return null;
		}
		return CHAR_TO_MORSE[c - 'a'];
	}

	private void switchLights(int state) {
		// List all Switch With Return States.
		Iterator<SwitchWithReturnState> list = DeviceManager.list(SwitchWithReturnState.class);
		while (list.hasNext()) {
			SwitchWithReturnState next = list.next();
			try {
				if (state == BinaryState.ON) {
					next.on();
				} else {
					next.off();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sleep(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
