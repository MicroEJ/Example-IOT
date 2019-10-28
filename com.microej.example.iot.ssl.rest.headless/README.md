# Overview

MicroEJ Java library: Simple Resty example using https connecion

# Usage

## Change the server 

To change the server:
1. Download the server's certificate (eg **server.com.crt**).
2. Drop it into the folder [certificates](src/main/resources/certificates)
3. In [com.microej.example.iot.ssl.rest.resources.list](src/main/resources/com/microej/example/iot/ssl/rest/com.microej.example.iot.ssl.rest.resources.list)
	1. change the existing path **/certificates/httpbin.org.crt** to the path of your certificate (eg **/certificates/server.com.crt**)
4. In  [ExampleRestyHttps.java](src/main/java/com/microej/example/iot/ssl/rest/ExampleRestyHttps.java)
 	1. Set the variable **SERVER_CERT_FILENAME** to your certificate name (eg **server.com.crt**)
	2. Set the variable **SERVER_URL** to your server URL

## Run on MicroEJ Simulator

1. Right Click on the project
2. Select **Run as -> MicroEJ Application**
3. Select **BackgroundServicesStandalone**
4. Select your platform 
5. Press **Ok**

## Run on device

### Local deploy

1. Right Click on the project
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

This example has been tested on:

* MicroEJ SDK 5.1
* With a platform that contains:
    * EDC-1.2 or higher
    * BON-1.2 or higher
    * NET-1.1 or higher
    * SSL-2.0 or higher
    * KF-1.4 or higher

## Dependencies

_All dependencies are retrieved transitively by Ivy resolver_.

---  
_Markdown_   
_Copyright 2019 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._   
_MicroEJ Corp. PROPRIETARY. Use is subject to license terms._  