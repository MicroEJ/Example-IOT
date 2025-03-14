.. Copyright 2019-2025 MicroEJ Corp. All rights reserved.
.. Use of this source code is governed by a BSD-style license that can be found with this software.

.. image:: https://shields.microej.com/endpoint?url=https://repository.microej.com/packages/badges/sdk_6.0.json
   :alt: sdk_6 badge
.. image:: https://shields.microej.com/endpoint?url=https://repository.microej.com/packages/badges/arch_8.3.json
   :alt: arch_8.3 badge
   :align: left


.. class:: center

⚠️ Those samples are compatible with MICROEJ SDK 6. MICROEJ SDK 5 compatible samples are available `here <https://github.com/MicroEJ/Example-IOT/tree/SDK-5.x>`_. ⚠️


Overview
========

This project gathers simple applications using IoT libraries.

By default, the samples are using either the
`NXP i.MX RT1170 VEE Port <https://github.com/MicroEJ/nxp-vee-imxrt1170-evk>`_ or
the `STM32F7508-DK VEE Port <https://github.com/MicroEJ/VEEPort-STMicroelectronics-STM32F7508-DK>`_.
Those VEE Ports are retrieved as modules.

See https://github.com/search?q=org%3AMicroEJ+VEEPort&type=repositories for the list of supported boards using MICROEJ SDK.

Each sample provides a ``README.md`` that contains instructions on how to run it.

Usage
=====

Each subfolder contains a distinct IoT application and its own README file.

| `androidconnectivity <androidconnectivity/>`__: shows how to use the connectivity manager.
| `cbor <cbor/>`__: shows how to use the ej.cbor serializer/deserializer.
| `cbor-json <cbor-json/>`__: shows how to use the ej.cbor with json serializer/deserializer.
| `dynamic-restserver <dynamic-restserver/>`__: shows how to use a rest server.
| `https-client <https-client/>`__: shows how to establish an HTTPS connection.
| `json <json/>`__: shows how to use the org.json.me parser.
| `mqtt-publisher <mqtt-publisher/>`__: shows how to publish MQTT data to a regular MQTT broker.
| `mqtt-subscriber <mqtt-subscriber/>`__: shows how to subscribe to a regular MQTT broker.
| `ssl-mqtt-publisher <ssl-mqtt-publisher/>`__: shows how to publish MQTT data to a MQTT broker via SSL.
| `ssl-mqtt-subscriber <ssl-mqtt-subscriber/>`__: shows how to subscribe to a MQTT broker via SSL.
| `ssl-mutual <ssl-mutual/>`__: shows a mutual authentication.
| `ssl-mutual-server <ssl-mutual-server/>`__: shows how to use a mutual server with SSL.
| `ssl-rest-headless <ssl-rest-headless/>`__: shows a simple Resty using TLS.
| `ssl-websocket <ssl-websocket/>`__: shows how to use WebSockets connecting to echo.microej.com over TLS.

Changes
=======

See the change log file `CHANGELOG.rst <CHANGELOG.rst>`__ located at the root of this repository.

License
=======

See the license file `LICENSE.txt <LICENSE.txt>`__ located at the root of this repository.

Troubleshooting 
===============

If you are encountering issues with the examples, make sure you are not running any VPN as it may cause errors.

