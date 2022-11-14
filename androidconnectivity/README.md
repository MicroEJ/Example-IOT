# Overview

A MicroEJ sandboxed application using the connectivity manager.

# Usage

## Run on MicroEJ Simulator

1. Right Click on the project
2. Select Main
3. Select **Run as -> MicroEJ Application**
4. Select your platform
5. Press **Ok**

## Run on a device

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
    * NET-1.1 or higher
    * KF-1.5 or higher

## Dependencies

_All dependencies are retrieved transitively by MicroEJ Module Manager_.


---  
_Markdown_   
_Copyright 2019-2022 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._