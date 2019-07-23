# Overview

MicroEJ Application using a restserver.

# Usage

## Run on MicroEJ Simulator

1. Right Click on the project
2. Select **Run as -> MicroEJ Application**
3. Select **BackgroundServicesStandalone**
4. Select your platform 
5. Press **Ok**

## Run on device

### Local deploy

1. Right Click on [Main.java](src\main\java\com\microej\example\iot\Main.java)
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

## Going further

The source code is provided as is for educative purpose, to reduce the embedded constraint, it is recommended to:
- Minify then compress the resources with gz and use a GzipResourceEndpoint.
- Unify the resources in a single HTML page to avoid 3 connections.


# Requirements

  - EDC-1.2 or higher
  - BON-1.2 or higher
  - NET-1.1 or higher

# Dependencies

_All dependencies are retrieved transitively by Ivy resolver_.

# Source

N/A

# Restrictions

None.

---  
_Markdown_   
_Copyright 2019 MicroEJ Corp. All rights reserved._   
_For demonstration purpose only._   
_MicroEJ Corp. PROPRIETARY. Use is subject to license terms._  