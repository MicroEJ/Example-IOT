# Overview
A MicroEJ sandboxed application using the connectivity manager.

# Usage
## Run on MicroEJ Simulator
1. Right Click on [Main.java](src\main\java\com\microej\example\iot\androidconnectivity\Main.java)
2. Select **Run as -> MicroEJ Application**
3. Select your platform
4. Press **Ok**

## Run on a device
### Local deploy
1. Right Click on [ExampleBG.java](src/main/java/com/microej/example/iot/androidconnectivity/app/ExampleBG.java)
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

# Requirements
* MicroEJ Studio or SDK 4.0 or later
* A platform with at least:
	* EDC-1.2 or higher
	* BON-1.2 or higher
	* KF-1.4 or higher

## Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.


<!--
  Copyright 2016-2018 IS2T. All rights reserved.
  For demonstration purpose only.
  IS2T PROPRIETARY. Use is subject to license terms.
-->