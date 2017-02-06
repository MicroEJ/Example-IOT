# Overview
A MicroEJ sandboxed application listening for ZWave notifications and switching on/off ZWave switchs.

# Usage
## Run on MicroEJ Simulator
1. Right Click on [Simulation.java](/com.microej.example.iot.zwave.simulator/src/main/java/ej/examples/iot/zwave/Simulation.java) in [com.microej.example.iot.zwave.simulator](/com.microej.example.iot.zwave.simulator/)
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
1. Right Click on [BackgroundServicesStandalone.java](/com.microej.example.iot.zwave/src/.generated~/.java/__ZWave__/generated/BackgroundServicesStandalone.java)
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
* MicroEJ Studio or SDK 4.0 or later
* A platform with at least:
	* EDC-1.2 or higher
	* BON-1.2 or higher
	* KF-1.4 or higher
	* NET-1.0 or higher
	* ECOM-1.1 or higher
	* ECOM-COMM-1.1 or higher

## Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.