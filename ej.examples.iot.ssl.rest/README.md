<!--
	Markdown
	
	Copyright 2016 IS2T. All rights reserved.
	Modification and distribution is permitted under certain conditions.
	IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->
# Overview
MicroEJ Java library: Simple Resty example using https connecion

# Usage
## Run on MicroEJ Simulator
1. Right Click on the project
1. Select **Run as -> MicroEJ Application**
1. Select **MainActivityStandalone**
1. Select your platform 
1. Press **Ok**

## Run on device
### Local deploy
1. Right Click on [RestHTTPSEntryPoint.java](/ej.examples.rest.https.app/src/.generated~/.java/__RestHTTPS__/generated/RestHTTPSEntryPoint.java)
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
	* NET-1.0 or higher
	* SSL-2.0 or higher

## Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.