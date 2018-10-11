/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.iot.androidconnectivity;

/**
 * Entry point.
 */
public class Main {

	/**
	 * Entry point.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		MyConnectivityExample connectivityExample = new MyConnectivityExample();
		connectivityExample.registerConnectivityManager();
	}

}
