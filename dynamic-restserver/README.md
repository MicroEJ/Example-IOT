# Overview

MicroEJ Application using a rest server.

# Usage

## Run on MicroEJ Simulator

1. Right Click on the project
2. Select Main
3. Select **Run as -> MicroEJ Application**
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

## Expected output

Once the REST Server is running:
- If you are running the application in simulation you should be able to access it through a web brower using the url http://localhost:8080.
- If you are running the application on a board you should be able to access it through a web brower using the board's local ip address: http://**board-ip**:8080.
You should get on a simple html page which shows some simulated informations about the board (uptime, java heap usage, thread).

## Going further

The source code is provided as is for educative purpose, to reduce the embedded constraint, it is recommended to:
- Minify then compress the resources with gz and use a GzipResourceEndpoint.
- Unify the resources in a single HTML page to avoid 3 connections.


# Requirements

This example has been tested on:

* MicroEJ SDK 5.1
* With a platform that contains:
    * EDC-1.2 or higher
    * BON-1.2 or higher
    * NET-1.1 or higher
    * KF-1.5 or higher

# Dependencies

_All dependencies are retrieved transitively by MicroEJ Module Manager_.

# Source

N/A

# Restrictions

None.

---  
_Markdown_   
_Copyright 2019-2022 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._