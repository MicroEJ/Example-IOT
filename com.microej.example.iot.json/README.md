# Overview
A MicroEJ sandboxed application that uses the org.json.me parser provided by json.org to parse and browse a [JSON file](src/main/resources/json/menu.json). 

The contents of the JSON file is taken from an example available from http://www.json.org/example.html

The example then iterates over all the **menuitem** elements available in the **popup** menu and print their contents.

# Usage
## Run on MicroEJ Simulator
1. Right Click on the project
2. Select **Run as -> MicroEJ Application**
3. Select **BackgroundServicesStandalone**
4. Select your platform 
5. Press **Ok**

## Run on device
### Local deploy
1. Right Click on [JsonExampleEntryPoint.java](src/.generated~/.java/__JsonExample__/generated/JsonExampleEntryPoint.java)
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