/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.example.iot.zwave.app;

import java.io.IOException;
import java.util.Iterator;

import ej.basedriver.BinaryState;
import ej.basedriver.SwitchWithReturnState;
import ej.ecom.DeviceManager;
import ej.wadapps.app.BackgroundService;

/**
 * Background service switching on and off Zwave lights to displa
 */
public class MorseSwitchBG extends Thread implements BackgroundService {

	/**
	 * Convert a lower case char to morse: 
	 * CHAR_TO_MORSE[0] = 'a' = ._ 
	 * CHAR_TO_MORSE[1] = 'b' = _... 
	 *         ...
	 * CHAR_TO_MORSE[24] = 'y' = ____.
	 * CHAR_TO_MORSE[25] = 'z' = _____
	 */
	private static final String[] CHAR_TO_MORSE = {
			"._", "_...", "_._.", "_..", ".", ".._.", "__.", "....", "..", ".___", "_._", "._..", "__", "_.", "___",
			".__.", "__._", "._.", "...", "_", ".._", "..._", ".__", "_.._", "_.__", "__..", ".____", "..___", "...__",
			"...._", ".....", "_....", "__...", "___..", "____.", "_____"
	};

	/**
	 * Symbol of a short mark in morse.
	 */
	private static final char SHORT = '.';
	// private static final char LONG = '-';

	/**
	 * Delay between loops.
	 */
	private static final int LOOP_DELAY = 5_000; // 5s

	/**
	 * Time for a . in morse.
	 */
	private static final int SHORT_DELAY = 500;

	/**
	 * Time for a _ in morse.
	 */
	private static final int LONG_DELAY = SHORT_DELAY * 2;

	/**
	 * Delay between morse marks
	 */
	private static final int OFF_DELAY = SHORT_DELAY;

	/**
	 * Delay when the symbol is unknown.
	 */
	private static final int UNKNOWN_DELAY = SHORT_DELAY;

	/**
	 * Whether the application is running.
	 */
	private volatile boolean running = false;

	/**
	 * Text used for the morse.
	 */
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
	 * Gets the morse symbol of a character.
	 *
	 * @param c
	 *            the character.
	 * @return The morse representation of the character.
	 * @throws IllegalArgumentException
	 *             when c is not in the range ['a', 'z'].
	 */
	private String getMorse(char c) throws IllegalArgumentException {
		if (c < 'a' || c > 'z') {
			throw new IllegalArgumentException();
		}
		return CHAR_TO_MORSE[c - 'a'];
	}

	/**
	 * Switch all the lights to a state.
	 *
	 * @param state
	 *            The state to set the lights.
	 * @see BinaryState
	 */
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

	/**
	 * Sleep for a delay.
	 *
	 * @param delay
	 *            the delay to sleep.
	 * @see Thread.sleep
	 */
	private void sleep(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
