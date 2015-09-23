- This project was taken as a graduation course(CMPE 273, Enterprise Distributed Systems) project at San Jose State University. The project is about an IoT Management System for a smart Home, based on OMA Lightweight Machine to Machine (LWM2M) interfaces. The project contains simple client/device and server with specified functionality (by OMA LWM2M) of bootstrapping, registration, updating and de-registration of the client, device management and information reporting interfaces. The server also has a web application which is build using AngularJS, HTML5, CSS3 at front-end, Java (Jersey) at back-end and MongoDB for database doings.

The project has the following structure:

-- Server - The server hosts the specified functionalities of the OMA LWM2M interfaces. The database activities are done using MongoDb. Also, the server hosts a web app using Apache Tomcat Server for the user to login and manage the functionalities provided for the smart devices. The supported devices in this project was limited to Thermostats and Light Switches. The web app is build using AngularJS at front end and the underlying structure of the code follows the general practice. The WebContent part of this folder contains all the code for the web app.

-- Device - The device/client contains the specified functionalities like Read, Write, Create, Discover, Execute, Observe, Delete services of the Device Management interface provided by the OMA LWM2M specification. The related database doings are again performed using the NoSql MongoDB.
 
