package com.devices;
import java.io.*;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class ThermostatDevice {
	
	public static void main(String[] args) {

		BufferedReader bufferRead;
		Client client = Client.create();
		WebResource webResource;
		ClientResponse response;

		try {
			String choice;
			do{
				System.out.println("Select what you want to do.. \n");
				System.out.println("1. Bootstrap \n2. Device Registration \n3. Update Registration Information  \n4. De-register \n0. Exit");
				BufferedReader bufferobj = new BufferedReader(new InputStreamReader(System.in));
				choice = bufferobj.readLine();
				
				switch(choice){
				case "1":								// bootstrap device
					System.out.println("Your endpoint name - ");
					bufferRead = new BufferedReader(new InputStreamReader(System.in));
					String name = bufferRead.readLine();

					String bootstrapURI = DeviceMongoService.getBootstrapURI();
					
					webResource = client
							.resource(bootstrapURI+name);

					response = webResource.accept("application/json")
							.get(ClientResponse.class);

					if (response.getStatus() != 200) {
						throw new RuntimeException("Failed : HTTP error code : "
								+ response.getStatus());
					}

					String output = response.getEntity(String.class);
					System.out.println("Output from Server .... \n");
	
					String bootstrapcheck = DeviceMongoService.BootstrapCheckDevice(name);
					if(bootstrapcheck.equalsIgnoreCase("false")){			// new request
						DeviceMongoService.saveBootstrapInfo(output);				// save server output
						System.out.println("The device is successfully bootstrapped..! \n");
						System.out.println(output);
					}
					else{
						DeviceMongoService.UpdateBootstrapInfo(output, name);
						System.out.println("The device is successfully bootstrapped with a new Server Object Instance ID..! \n");
						System.out.println(output);				
					}					
					break;

				case "2":					// device registration
					
					System.out.println("Your endpoint name - ");
					bufferRead = new BufferedReader(new InputStreamReader(System.in));
					String name1 = bufferRead.readLine();
					String registerURI = DeviceMongoService.getRegisterURI(name1);
					
					if(registerURI.equalsIgnoreCase("false")){
						System.out.println("There's no device bootstrapped with this endpoint name..The device needs to be bootstrapped first..! \n");
						break;
					}
					
					System.out.println("Your server object instance id - ");
					bufferRead = new BufferedReader(new InputStreamReader(System.in));
					String objid1 = bufferRead.readLine();					
					String reginfo = DeviceMongoService.getRegisterInfo(objid1);			// to change/update registerinfo
										
						webResource = client
								.resource(registerURI+name1);			// send endpoint name and reg info
						response = webResource.accept("text/plain")
								.post(ClientResponse.class,reginfo);

						if (response.getStatus() != 200) {
							throw new RuntimeException("Failed : HTTP error code : "
									+ response.getStatus());
						}

						response.getEntity(String.class);
						System.out.println("Output from Server .... \n");
						System.out.println("You're successfully registered with your endpoint name.. \n");
						
						// save device information corresponding to registered endpoint name
						String check = DeviceMongoService.CheckDevice(name1, objid1); 								// get endpoint name
						if(check.equalsIgnoreCase(name1)){		
							DeviceMongoService.saveThermostatUpdatedDeviceInformation(name1, objid1);
						}
						else{
							DeviceMongoService.saveThermostatDeviceInformation(name1, objid1);
						}
					break;

				case "3":									// update registration information

					System.out.println("Your endpoint name - ");
					bufferRead = new BufferedReader(new InputStreamReader(System.in));
					String name2 = bufferRead.readLine();
					String updateURI = DeviceMongoService.getRegisterURI(name2);
					if(updateURI.equalsIgnoreCase("false")){
						System.out.println("There's no device bootstrapped with this endpoint name..The device needs to be bootstrapped first..! \n");
						break;
					}
					
					System.out.println("Your server object instance id - ");
					bufferRead = new BufferedReader(new InputStreamReader(System.in));
					String objid2 = bufferRead.readLine();
					String updateinfo = DeviceMongoService.getUpdateParameters(objid2);

					Client.create();
					webResource = client
							.resource(updateURI+name2);

					response = webResource.accept("text/plain")
							.put(ClientResponse.class,updateinfo);

					if (response.getStatus() != 200) {
						throw new RuntimeException("Failed : HTTP error code : "
								+ response.getStatus());
					}

					String output2 = response.getEntity(String.class);
					System.out.println("Output from Server .... \n");
					if(output2.equalsIgnoreCase("Registered")){
						System.out.println("The parameters were successfully updated..!\n");	
					}
					else{
						System.out.println("The updation was not successful, the server object id was not of this device..! \n");
					}
					
					break;

				case "4":							// de-register device, device requests

					System.out.println("Your endpoint name - ");
					bufferRead = new BufferedReader(new InputStreamReader(System.in));
					String name3 = bufferRead.readLine();
					String deleteURI = DeviceMongoService.getRegisterURI(name3);
					
					if(deleteURI.equalsIgnoreCase("false")){
						System.out.println("There's no device registered with this endpoint name..! \n");
						break;
					}
					
					System.out.println("Your server object id - ");
					bufferRead = new BufferedReader(new InputStreamReader(System.in));
					String objid3 = bufferRead.readLine();
					
					webResource = client
							.resource(deleteURI+name3+"/"+objid3);

					response = webResource.accept("text/plain")
							.delete(ClientResponse.class);

					if (response.getStatus() != 200) {
						throw new RuntimeException("Failed : HTTP error code : "
								+ response.getStatus());
					}

					String output3 = response.getEntity(String.class);

					System.out.println("\n \t \t **Output from Server** \n");

					if(output3.equalsIgnoreCase("true")){
						System.out.println("The device is successfully de-registered.. \n");
						DeviceMongoService.executeFactoryResetDevice(name3, objid3);			// // delete and factory reset does same
					}
					else{
						System.out.println("The de-registration was not successful ..! \n");
					}
					break;

				case "0":
					System.exit(0);
					break;

				default:
					System.out.println("Sorry.. Enter a choice.. \n");
					break;
				}											// end of switch
			} while(choice != "0");						
		} 							// end of try

		catch (Exception e) {
			e.printStackTrace();
		}
	}
}