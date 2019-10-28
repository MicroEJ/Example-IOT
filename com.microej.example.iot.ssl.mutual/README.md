# Overview

MicroEJ Java library: Example of a mutual authentification.

# Usage

## Change the server 

To change the server:
1. Download the server's certificate (eg **server-crt.pem**).
2. Drop it into the folder [certificates](src/main/resources/certificates)
3. In [com.microej.example.iot.ssl.mutual.resources.list](src/main/resources/com/microej/example/iot/ssl/mutual/com.microej.example.iot.ssl.mutual.resources.list)
	1. Change the existing path **/certificates/server-crt.pem** to the path of your certificate (eg **/certificates/server.com.crt**)
	2. Remove the root certificate (if the client certificate is changed as well) **/certificates/ca-crt.pem**
	3. Add any other certificates
4. In  [MutualAuthBackgroundService.java](src/main/java/com/microej/example/iot/ssl/mutual/MutualAuthBackgroundService.java)
 	1. Set the variable **SERVER_CERT_FILENAME** to your certificate names (eg **[server.com.crt, root.com.crt]**)
	2. Set the variable **HOST** to your server host
	3. Set the variable **PORT** to your server port

## Change the client 

To change the server:
1. Get the client certificate and key.
2. Drop it into the folder [certificates](src/main/resources/certificates)
3. In [com.microej.example.iot.ssl.mutual.resources.list](src/main/resources/com/microej/example/iot/ssl/mutual/com.microej.example.iot.ssl.mutual.resources.list)
	1. Change the existing path **/certificates/clientA-crt.pem** to the path of your certificate (eg **/certificates/client.pem**)
	2. Change the existing path **/certificates/clientA-key.der** to the path of your key in DER format (eg **/certificates/key.der**)
	3. Remove the root certificate (if the client certificate is changed as well) **/certificates/ca-crt.pem**
4. In  [MutualAuthBackgroundService.java](src/main/java/com/microej/example/iot/ssl/mutual/MutualAuthBackgroundService.java)
 	1. Set the variable **CLIENT_CERT_CHAIN** to your certificate chain, it must be ordered (eg **[client.pem, root.com.crt"]**)
	2. Set the variable **DEVICE_KEY** to your device key (eg **/certificates/key.der**)
	3. Set the variable **KEY_PASSWORD** to your key's password

## Run on MicroEJ Simulator

1. Right Click on the project
2. Select **Run as -> Run Configurations...** 
3. Select **MicroEJ Application** configuration kind
4. Click on **New launch configuration** icon
5. In **Execution** tab
	1. In **Target** frame, in **Platform** field, select a relevant platform
6. In **Configuration** tab
	1. Go to **Runtime -> Memory**
		* Set **Immortal heap size (in bytes)** to **15000**
	2. Go to **Libraries -> SSL**
		* Set the **max certificate and key size** to *4096**
7. Press **Apply**
8. Press **Run**

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

## Keys and certificates generation

The keys and certificates available on this example have been generated using the following openssl commands:

### Root certificate

If you don't have it, generate a key to be used as root certificate: `openssl req -new -x509 -days 3650 -keyout ca-key.pem -out ca-crt.pem`

### Key for the client

1. Generate a client key: `openssl genrsa -out clientA-key.pem 4096`
2. Generate a signing request: `opensslreq -new -sha256 -key clientA-key.pem -out clientA-csr.pem`
3. Sign the key: `openssl x509 -req -days 365 -in clientA-csr.pem -CA ca-crt.pem -CAkey ca-key.pem -CAcreateserial -out clientA-crt.pem`
4. Generate the DER format: `openssl pkcs8 -inform PEM -in .\clientA-key.pem -topk8 -outform DER -out clientA-key.der  -v1 PBE-SHA1-3DES -passout pass:demo`

# Requirements

This example has been tested on:

* MicroEJ SDK version 5.1
* With a platform that contains:
    * EDC-1.2 or higher
    * BON-1.2 or higher
    * NET-1.1 or higher
    * SSL-2.0 or higher
    * KF-1.4 or higher
    
The server used is the one available in [com.microej.example.iot.ssl.mutual.server](../com.microej.example.iot.ssl.mutual.server).

## Dependencies

_All dependencies are retrieved transitively by Ivy resolver_.

---  
_Markdown_   
_Copyright 2019 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._   
_MicroEJ Corp. PROPRIETARY. Use is subject to license terms._   