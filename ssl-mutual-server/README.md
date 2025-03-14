# Overview

This example contains the Server code to show a mutual TLS authentication.

The server waits for a mutual authentication then sends "Hello World".

Refer to the [ssl-mutual](../ssl-mutual/) example to run the Client code.

# Requirements

This example has been tested on:

* Windows 10/11.
* IntelliJ IDEA with MicroEJ plugin for IntelliJ IDEA ``1.1.0``.
* MICROEJ SDK 6.
* With a JRE 11.

# Usage

This sample only runs on PC.

This sample is pre-configured with certificates.
To change the certificates, follow the ``Changing Certificates`` section.

## Run the Server

Run the following command in your IDE
(or click the ``Play`` button next to the line
below when opening this README in IntelliJ IDEA):

`./gradlew :ssl-mutual-server:run`

## Expected Behavior

The application starts, the traces are visible in the Run view:

```
Server waiting for connection on 12345
```

## Changing Certificates

### Changing the Server Certificate

To change the server certificate:

1. Change the server PKCS12 certificate file: ``server.p12``:
	* Drop the new file in [src/main/resources/](src/main/resources/).
2. In [Server.java](src/main/java/com/microej/example/iot/ssl/mutual/server/Server.java):
	* Set the variable ``PORT`` to your server port
	* Set the variable ``SERVER_PKCS12`` to the path to the PKCS12 certificate.
	* Set the variable ``PASSWORD`` to the password of the PKCS12 certificate.

### Changing the Client Certificate

To change the client certificate:

1. Get the client certificate.
2. Drop it into the folder [src/main/resources/](src/main/resources/).
2. In [Server.java](src/main/java/com/microej/example/iot/ssl/mutual/server/Server.java):
	* Set the variable ``CLIENT_CERTIFICATES`` to your certificate chain, it must be ordered (e.g. ``[client.pem, root.com.crt]``).
	* Set the variable ``DEVICE_KEY`` to your device key (e.g. ``/certificates/key.der``).
	* Set the variable ``KEY_PASSWORD`` to your key's password.

### Keys and certificates generation

The keys and certificates available on this example have been generated using the below ``openssl`` commands.

#### Root Certificate Generation

If you don't have it, generate a key to be used as root certificate: `openssl req -new -x509 -days 3650 -keyout ca-key.pem -out ca-crt.pem`.

#### Server's Key Generation

1. Generate a server key: `openssl genrsa -out server-key.pem 4096`.
2. Generate a signing request: `openssl req -new -sha256 -key server-key.pem -out server-csr.pem`.
3. Sign the key: `openssl x509 -req -days 365 -in server-csr.pem -CA ca-crt.pem -CAkey ca-key.pem -CAcreateserial -out server-crt.pem`.
4. Generate the PKCS12: `openssl pkcs12 -export  -inkey .\server-key.pem -in .\server-crt.pem -certfile .\ca-crt.pem -out server.p12`.

# Dependencies

_All dependencies are retrieved transitively by Gradle._

# Source

N/A

# Restrictions

None.

---  
_Markdown_   
_Copyright 2019-2025 MicroEJ Corp. All rights reserved._   
_Use of this source code is governed by a BSD-style license that can be found with this software._  