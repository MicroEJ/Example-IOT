<!--
	Markdown
	
	Copyright 2016 IS2T. All rights reserved.
	Modification and distribution is permitted under certain conditions.
	IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->
# Overview
MicroEJ Java library: Simple Resty example using https connecion

# Usage
## Change the server 
To change the server:
1. Download the broker server's certificate (eg **server.com.crt**).
1. Drop it into the folder [certificates](ej.examples.iot.ssl.rest/src/main/resources/certificates)
1. In [ej.examples.iot.ssl.rest.resources.list](ej.examples.iot.ssl.rest/src/main/resources/ej/examples/iot/ssl/rest/ej.examples.iot.ssl.rest.resources.list)
	1. change the existing path **/certificates/httpbin.org.crt** to the path of your certificate (eg **/certificates/server.com.crt**)
1. In  [ExampleRestyHttps.java](ej.examples.iot.ssl.rest/src/main/java/ej/examples/iot/ssl/rest/ExampleRestyHttps.java)
 	1. Set the variable **SERVER_CERT_FILENAME** to your certificate name (eg **server.com.crt**)
	1. Set the variable **SERVER_URL** to your server URL


## Run on MicroEJ Simulator
1. Right Click on the project
1. Select **Run as -> MicroEJ Application**
1. Select **MainActivityStandalone**
1. Select your platform 
1. Press **Ok**

## Run on device
### Local deploy
1. Right Click on [RestHTTPSEntryPoint.java](ej.examples.rest.https.app/src/.generated~/.java/__RestHTTPS__/generated/RestHTTPSEntryPoint.java)
1. Select **Run as -> Run Configurations...** 
1. Select **MicroEJ Application** configuration kind
1. Click on **New launch configuration** icon
1. In **Execution** tab
	1. In **Target** frame, in **Platform** field, select a relevant platform
	1. In **Execution** frame
		1. Select **Execute on Device**
		2. In **Settings** field, select **Build & Deploy**
1. In **Configuration** tab
	1. In **Board** frame
		1. Set **Host** field to your board IP address
1. Press **Apply**
1. Press **Run**

# Requirements
* MicroEJ Studio or SDK 4.0 or later
* A platform with at least:
	* EDC-1.2 or higher
	* MICROUI-2.0 or higher
	* MWT-2.0 or higher
	* NET-1.0 or higher
	* SSL-2.0 or higher
	* KF-1.4 or higher

## Dependencies
_All dependencies are retrieved transitively by Ivy resolver_.