<!--
	Markdown
	
	Copyright 2016 IS2T. All rights reserved.
	Modification and distribution is permitted under certain conditions.
	IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->
# Overview
A MicroEJ sandboxed application that uses the org.json.me parser provided by json.org to parse and  browse a JSON content. 

The JSON content is simple abstraction of a file menu as provided here: http://www.json.org/example.html

The example then tries to list all the 'menuitem's available in the popup menu. It is assumed the user knows the menu JSON file structure.

# Usage
## Run on MicroEJ Simulator
1. Right Click on the project
1. Select **Run as -> MicroEJ Application**
1. Select **BackgroundServicesStandalone**
1. Select your platform 
1. Press **Ok**

## Run on device
### Local deploy
1. Right Click on [JsonExampleEntryPoint.java](/ej.examples.iot.json/src/.generated~/.java/__JsonExample__/generated/JsonExampleEntryPoint.java)
1. Select **Run as -> Run Configuration** 
1. Click on **New launch configuration** icon
1. Select your platform 
1. Select **Execute on Device**
1. Select **Local Deployment**
1. Go to **Configuration** tab
	* Set **Host** field to your board IP address
1. Press **Apply**
1. Press **Run**

# Requirements
* MicroEJ Studio or SDK 4.0 or later
* A platform with at least:
	* EDC-1.2 or higher
	* BON-1.2 or higher
	* KF-1.4 or higher

## Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.