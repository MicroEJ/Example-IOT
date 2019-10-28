# Overview

A MicroEJ sandboxed application listening for ZWave notifications and switching on/off ZWave switchs.

# Usage

## Run on MicroEJ Simulator

1. Right Click on **com.microej.example.iot.zwave.simulator** project
2. Select **Run as -> Run Configurations...** 
3. Select **MicroEJ Application** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab
	1. In **Target** frame, in **Platform** field, select a relevant platform
	2. In **Execution** frame
		1. Select **Execute on Device**
		2. In **Settings** field, select **Build & Deploy**
6. In **Configuration** tab
	1. In **Board** frame
		1. Set **Host** field to your board IP address
	1. In **Configuration** frame
		1. Go to **Libraries -> ECOM -> Comm Connection**
			* Check **Enable comm connections**
			* Check **Enable dynamic comm ports registration**
		1. Go to **Simulator -> Com Port**
			* Select simulation type **UART <-> UART**
			* Set the **Port mapping** field to ZWave dongle port
7. Press **Apply**
8. Press **Run**

## Run on a device

### Local deploy

1. Right Click on **com.microej.example.iot.zwave** project
2. Select **Run as -> Run Configurations...** 
3. Select **MicroEJ Application** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab
	1. In **Target** frame, in **Platform** field, select a relevant platform
	2. In **Execution** frame
		1. Select **Execute on Device**
		2. In **Settings** field, select **Build & Deploy**
6. In **Configuration** tab
	1. In **Board** frame
		1. Set **Host** field to your board IP address
7. Press **Apply**
8. Press **Run**
9. Install ZWave driver
10. Launch ZWave driver

# Requirements

This example has been tested on:

* MicroEJ SDK 5.1
* With a platform that contains:
    * EDC-1.2 or higher
    * BON-1.2 or higher
    * ECOM-1.1 or higher
    * ECOM-COMM-1.1 or higher
    * KF-1.4 or higher

## Dependencies

_All dependencies are retrieved transitively by Ivy resolver_.

---  
_Markdown_   
_Copyright 2019 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._   
_MicroEJ Corp. PROPRIETARY. Use is subject to license terms._  