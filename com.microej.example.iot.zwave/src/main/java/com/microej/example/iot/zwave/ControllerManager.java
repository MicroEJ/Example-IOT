/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
 */
package com.microej.example.iot.zwave;

import java.util.Iterator;

import ej.basedriver.Controller;
import ej.basedriver.DryContact;
import ej.basedriver.EventControllerListener;
import ej.basedriver.SwitchWithReturnState;
import ej.basedriver.event.DryContactEvent;
import ej.basedriver.event.EventHandler;
import ej.basedriver.event.SwitchStateEvent;
import ej.ecom.DeviceManager;
import ej.ecom.RegistrationEvent;
import ej.ecom.RegistrationListener;

/**
 * Manager for the controllers, add the handlers for DryContact and SwitchManager
 */
public class ControllerManager implements RegistrationListener<Controller> {

	private final EventHandler<SwitchWithReturnState, SwitchStateEvent> switchStateManager;
	private final EventHandler<DryContact, DryContactEvent> dryContactManager;

	/**
	 * Instantiate a Controller Manager.
	 *
	 * @param switchStateManager
	 *            Switch
	 * @param dryContactManager
	 */
	public ControllerManager(EventHandler<SwitchWithReturnState, SwitchStateEvent> switchStateManager,
			EventHandler<DryContact, DryContactEvent> dryContactManager) {
		this.switchStateManager = switchStateManager;
		this.dryContactManager = dryContactManager;
		Iterator<Controller> it = DeviceManager.list(Controller.class);
		while (it.hasNext()) {
			Controller controller = it.next();
			addHandlers(controller);
		}
	}

	@Override
	public void deviceRegistered(RegistrationEvent<Controller> event) {
		addHandlers(event.getDevice());

	}

	@Override
	public void deviceUnregistered(RegistrationEvent<Controller> event) {
		removeHandlers(event.getDevice());

	}

	/**
	 * Add handlers to a controller.
	 *
	 * @param controller
	 */
	private void addHandlers(Controller controller) {
		if (controller.getListener() instanceof EventControllerListener) {
			EventControllerListener eventControllerListener = (EventControllerListener) controller.getListener();
			eventControllerListener.addEventHandler("ej.basedriver.SwitchWithReturnState", switchStateManager);
			eventControllerListener.addEventHandler("ej.basedriver.DryContact", dryContactManager);
		}
	}

	/**
	 * Remove handlers to a controller.
	 *
	 * @param device
	 */
	private void removeHandlers(Controller controller) {
		if (controller.getListener() instanceof EventControllerListener) {
			EventControllerListener eventControllerListener = (EventControllerListener) controller.getListener();
			eventControllerListener.removeEventHandler(switchStateManager);
			eventControllerListener.removeEventHandler(dryContactManager);
		}

	}

	/**
	 * Remove all handlers to all controllers.
	 */
	public void stop() {
		Iterator<Controller> it = DeviceManager.list(Controller.class);
		while (it.hasNext()) {
			Controller controller = it.next();
			removeHandlers(controller);
		}
	}

}
