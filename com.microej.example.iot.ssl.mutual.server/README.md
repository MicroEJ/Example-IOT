# Overview

The server wait for a mutual autentification then send an Hello World.

# Usage

## Change the server 

To change the server:
1. Change the server p12 file **server.p12**.
    1. Drop the new P2 in [src](src/)
2. In  [Server.java](src/com/microej/example/iot/ssl/mutual/server/Server.java)
	1. Set the variable **PORT** to your server port
	2. Set the variable **SERVER_PKS12** to the path to the pks12.
	3. Set the variable **PASSWORD** to the password of the pks12.

## Change the client 

To change the server:
1. Get the client certificate.
2. Drop it into the folder [src](src/)
2. In  [Server.java](src/com/microej/example/iot/ssl/mutual/server/Server.java)
 	1. Set the variable **CLIENT_CERTIFICATES** to your certificate chain, it must be ordered (eg **[client.pem, root.com.crt]**)
	2. Set the variable **DEVICE_KEY** to your device key (eg **/certificates/key.der**)
	3. Set the variable **KEY_PASSWORD** to your key's password

## Run on MicroEJ Simulator

1. Right Click on the project
2. Select **Run as -> Java application** 

## Keys and certificates generation

The keys and certificates available on this example have been generated using the following openssl commands:

### Root certificate

If you don't have it, generate a key to be used as root certificate: `openssl req -new -x509 -days 3650 -keyout ca-key.pem -out ca-crt.pem`


### Key for the server

Generate a server key: `openssl genrsa -out server-key.pem 4096`
Generate a signing request: `openssl req -new -sha256 -key server-key.pem -out server-csr.pem`
Sign the key: `openssl x509 -req -days 365 -in server-csr.pem -CA ca-crt.pem -CAkey ca-key.pem -CAcreateserial -out server-crt.pem`
Generate the pks12: `openssl pkcs12 -export  -inkey .\server-key.pem -in .\server-crt.pem -certfile .\ca-crt.pem -out server.p12`

# Requirements

This example has been tested on:

* MicroEJ SDK version 5.1
* With a JRE 1.8

## Dependencies

_All dependencies are retrieved transitively by Ivy resolver_.

---  
_Markdown_   
_Copyright 2019 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._   
_MicroEJ Corp. PROPRIETARY. Use is subject to license terms._   