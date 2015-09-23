package com.servers;
import java.io.*;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class DeviceManagement {
	
	public static void main(String[] args) {

		BufferedReader bufferRead;
		Client client = Client.create();
		WebResource webResource;
		ClientResponse response;

		try {
			do{
				System.out.println("Enter the endpoint name of device to be managed - ");	// first see if device is registered or not
				bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String name = bufferRead.readLine();
				String checkRegister = MongoService.CheckIfRegistered(name);
				
				if(checkRegister.equalsIgnoreCase("false")){
					System.out.println("The device is not registered to the server \n");
					break;
				}
				
				String deviceURI = MongoService.getDeviceURI(name);				// device ipaddress+port
				String projectname = "com.273.lwm2m.device";
				// get object instance id corresponding to the endpoint name
				String objInstance = MongoService.getRegisteredObjectInstanceID(name);		
				
				String choice;
				do{
					System.out.println("Select what you want to do.. \n");
					System.out.println("1. Device Management \n2. Information Reporting \n0. Exit");
					BufferedReader bufferobj = new BufferedReader(new InputStreamReader(System.in));
					choice = bufferobj.readLine();

					switch(choice){
					case "1":
						
						String choice1;
						do{
							System.out.println("Select what you want to do.. \n");
							System.out.println("1.1 Read \n1.2 Discover \n1.3 Write  \n1.4 Write Attributes \n1.5 Execute \n1.6 Create \n1.7 Delete  \n0. Exit");
							BufferedReader bufferobj1 = new BufferedReader(new InputStreamReader(System.in));
							choice1 = bufferobj1.readLine();
							
							switch(choice1){
							case "1.1":
								
								String choice2;
								do{
								System.out.println("Select the resource to read.. \n");	
								System.out.println("1. Short Server ID(/1/instance/0) \n2. Lifetime(/1/instance/1) \n3. Notification Storing(/1/instance/6)  \n4. Binding(/1/instance/7) \n5. Manufacturer Name(/3/instance/0) \n6. Device Type(/3/instance/17) \n7. Error Code(/3/instance/11) \n0. Exit");
								BufferedReader bufferobj2 = new BufferedReader(new InputStreamReader(System.in));
								choice2 = bufferobj2.readLine();
						
								switch(choice2){
								case "1":
									
									webResource = client
									.resource("http://"+deviceURI+"/"+projectname+"/"+"read"+"/"+"1"+"/"+objInstance+"/"+"0");

									response = webResource.accept("application/json")
									.get(ClientResponse.class);

									if (response.getStatus() != 200) {
										throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
									}

									String read1 = response.getEntity(String.class);
									System.out.println("Output from Device .... \n");
									System.out.println("The Short Server ID is: "+read1 +"\n");
									break;
								
								case "2":
									
									webResource = client
									.resource("http://"+deviceURI+"/"+projectname+"/"+"read"+"/"+"1"+"/"+objInstance+"/"+"1");

									response = webResource.accept("application/json")
									.get(ClientResponse.class);

									if (response.getStatus() != 200) {
										throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
									}

									String read2 = response.getEntity(String.class);
									System.out.println("Output from Device .... \n");
									System.out.println("The Lifetime of device is: "+read2 +"seconds \n");
									break;
									
								case "3":
									
									webResource = client
									.resource("http://"+deviceURI+"/"+projectname+"/"+"read"+"/"+"1"+"/"+objInstance+"/"+"6");

									response = webResource.accept("application/json")
									.get(ClientResponse.class);

									if (response.getStatus() != 200) {
										throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
									}

									String read3 = response.getEntity(String.class);
									System.out.println("Output from Device .... \n");
									System.out.println("The value of Notification Storing is: "+read3 +"\n");
									break;	
									
								case "4":
									
									webResource = client
									.resource("http://"+deviceURI+"/"+projectname+"/"+"read"+"/"+"1"+"/"+objInstance+"/"+"7");

									response = webResource.accept("application/json")
									.get(ClientResponse.class);

									if (response.getStatus() != 200) {
										throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
									}

									String read4 = response.getEntity(String.class);
									System.out.println("Output from Device .... \n");
									System.out.println("The device supported Binding Mode is: "+read4 +"\n");
									break;	
								
								case "5":
									
									webResource = client
									.resource("http://"+deviceURI+"/"+projectname+"/"+"read"+"/"+"3"+"/"+objInstance+"/"+"0");

									response = webResource.accept("application/json")
									.get(ClientResponse.class);

									if (response.getStatus() != 200) {
										throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
									}

									String read5 = response.getEntity(String.class);
									System.out.println("Output from Device .... \n");
									System.out.println("The Manufacturer Name of device is: "+read5 +"\n");
									break;	
								
								case "6":
									
									webResource = client
									.resource("http://"+deviceURI+"/"+projectname+"/"+"read"+"/"+"3"+"/"+objInstance+"/"+"17");

									response = webResource.accept("application/json")
									.get(ClientResponse.class);

									if (response.getStatus() != 200) {
										throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
									}

									String read6 = response.getEntity(String.class);
									System.out.println("Output from Device .... \n");
									System.out.println("The device type is: "+read6 +"\n");
									break;	
								
								case "7":
									
									webResource = client
									.resource("http://"+deviceURI+"/"+projectname+"/"+"read"+"/"+"3"+"/"+objInstance+"/"+"11");

									response = webResource.accept("application/json")
									.get(ClientResponse.class);

									if (response.getStatus() != 200) {
										throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
									}

									String read7 = response.getEntity(String.class);
									System.out.println("Output from Device .... \n");
									System.out.println("The Error Code at device is: "+read7 +"\n");
									break;	
																
								case "0":
									break;

								default:
									System.out.println("Sorry.. Enter a choice.. \n");
									break;			
								}		
							} while(!choice2.equalsIgnoreCase("0"));
								break;					// break of case 1.1
							
							case "1.2":
								
								String choice3;
								do{
								System.out.println("Select what to discover.. \n");
								System.out.println("1. By Object ID \n2. By Resource ID \n0. Exit");
								BufferedReader bufferobj2 = new BufferedReader(new InputStreamReader(System.in));
								choice3 = bufferobj2.readLine();
						
								switch(choice3){
								case "1":
									
									System.out.println("Enter the object id to discover(0,1 or 3) \n");
									BufferedReader bufferobj3 = new BufferedReader(new InputStreamReader(System.in));
									String objectid = bufferobj3.readLine();
									
									webResource = client
											.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"discover"+"/"+objectid+"/"+objInstance);

											response = webResource.accept("application/json")
											.get(ClientResponse.class);

											if (response.getStatus() != 200) {
												throw new RuntimeException("Failed : HTTP error code : "
												+ response.getStatus());
											}

											String discover1 = response.getEntity(String.class);
											System.out.println("Output from Device .... \n");
											System.out.println(discover1 +"\n");
									break;
									
								case "2":
									
									System.out.println("Enter the object id first(0,1 or 3) \n");
									BufferedReader bufferobj4 = new BufferedReader(new InputStreamReader(System.in));
									String objectid1 = bufferobj4.readLine();
																				// **0- 1,2**	**1-0,7**	**3-0,11**
									System.out.println("Now enter the resource id to discover \n");
									BufferedReader bufferobj5 = new BufferedReader(new InputStreamReader(System.in));
									String resourceid = bufferobj5.readLine();
									
									webResource = client
											.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"discover"+"/"+objectid1+"/"+objInstance+"/"+resourceid);

											response = webResource.accept("application/json")
											.get(ClientResponse.class);

											if (response.getStatus() != 200) {
												throw new RuntimeException("Failed : HTTP error code : "
												+ response.getStatus());
											}

											String discover2 = response.getEntity(String.class);
											System.out.println("Output from Device .... \n");
											System.out.println(discover2 +"\n");									
									break;	
									
								case "0":
									break;

								default:
									System.out.println("Sorry.. Enter a choice.. \n");
									break;			
								}		
							} while(!choice3.equalsIgnoreCase("0"));
								break;					// break of case 1.2
							
							case "1.3":
								
								String choice4;
								do{
								System.out.println("Select the resource to Write.. \n");
								System.out.println("1. Lifetime(/1/instance/1) \n2. Notification Storing(/1/instance/6) \n3. Binding(/1/instance/7) \n0. Exit");
								BufferedReader bufferobj2 = new BufferedReader(new InputStreamReader(System.in));
								choice4 = bufferobj2.readLine();
						
								switch(choice4){
								
								case "1":
									
									System.out.println("Enter the new Lifetime value to Write to Device: ");
									BufferedReader bufferobj3 = new BufferedReader(new InputStreamReader(System.in));
									String newvalue = bufferobj3.readLine();
									
									webResource = client
											.resource("http://"+deviceURI+"/"+projectname+"/"+"write/3"+"/"+objInstance+"/1");

											response = webResource.accept("text/plain")
											.post(ClientResponse.class,newvalue);

											if (response.getStatus() != 200) {
												throw new RuntimeException("Failed : HTTP error code : "
												+ response.getStatus());
											}

											response.getEntity(String.class);
											System.out.println("Output from Device .... \n");
											System.out.println("Write Successful.. Lifetime updated to "+newvalue +" seconds. \n");
									break;

								case "2":
									
									System.out.println("Enter the new value for Notification Storing:  ");
									BufferedReader bufferobj4 = new BufferedReader(new InputStreamReader(System.in));
									String newvalue1 = bufferobj4.readLine();
									
									webResource = client
											.resource("http://"+deviceURI+"/"+projectname+"/"+"write/3"+"/"+objInstance+"/6");

											response = webResource.accept("text/plain")
											.post(ClientResponse.class,newvalue1);

											if (response.getStatus() != 200) {
												throw new RuntimeException("Failed : HTTP error code : "
												+ response.getStatus());
											}

											response.getEntity(String.class);
											System.out.println("Output from Device .... \n");
											System.out.println("Write Successful.. Notification Storing changed to "+newvalue1 +".\n");
									break;
								
								case "3":
									
									System.out.println("Enter the new Binding Preference Mode:  ");
									BufferedReader bufferobj5 = new BufferedReader(new InputStreamReader(System.in));
									String newvalue2 = bufferobj5.readLine();
									
									webResource = client
											.resource("http://"+deviceURI+"/"+projectname+"/"+"write/3"+"/"+objInstance+"/7");

											response = webResource.accept("text/plain")
											.post(ClientResponse.class,newvalue2);

											if (response.getStatus() != 200) {
												throw new RuntimeException("Failed : HTTP error code : "
												+ response.getStatus());
											}

											response.getEntity(String.class);
											System.out.println("Output from Device .... \n");
											System.out.println("Write Successful.. New binding Mode is: "+newvalue2 +".\n");
									break;	
																		
								case "0":
									break;

								default:
									System.out.println("Sorry.. Enter a choice.. \n");
									break;			
								}		
							} while(!choice4.equalsIgnoreCase("0"));								
								break;
								
							case "1.4":
								
								String choice5;
								do{
								System.out.println("Select the Object ID to Write Attributes to.. \n");
								System.out.println("1. LWM2M Security Object(0) \n2. LWM2M Server Object(1) \n0. Exit");
								BufferedReader bufferobj2 = new BufferedReader(new InputStreamReader(System.in));
								choice5 = bufferobj2.readLine();
						
								switch(choice5){
								case "1":
									String writeattr1;
									String attributevalue;
									System.out.println("Enter the Resource ID(6,11) to write to this Object ID(0)..");	
									BufferedReader bufferobj3 = new BufferedReader(new InputStreamReader(System.in));
									String resourceid = bufferobj3.readLine();
									
									if(resourceid.equalsIgnoreCase("6")){
										attributevalue = "3"; 					// SMS Security Mode SET NEW VALUE
									}
									
									else if(resourceid.equalsIgnoreCase("11")){
										attributevalue = "1";					// Client Hold-off Time SET NEW VALUE
									}
									
									else{
										writeattr1 = "There's no resource corresponding to this object id..\n";
										System.out.println("Output from Device .... \n");
										System.out.println(writeattr1 +"\n");
										break;
									}	
									
									webResource = client				// send endpoint and objinstance
											.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"writeattribute"+"/0"+"/"+objInstance+"/"+resourceid);

											response = webResource.accept("text/plain")
											.put(ClientResponse.class, attributevalue);					// send new value

											if (response.getStatus() != 200) {
												throw new RuntimeException("Failed : HTTP error code : "
												+ response.getStatus());
											}

											writeattr1 = response.getEntity(String.class);
											System.out.println("Output from Device .... \n");
											System.out.println(writeattr1 +"\n");
									break;
									
								case "2":
									
									String writeattr2;
									String attributevalue1;
									System.out.println("Enter the Resource ID(4,5) to write to this Object ID(1)..");	
									BufferedReader bufferobj4 = new BufferedReader(new InputStreamReader(System.in));
									String resourceid1 = bufferobj4.readLine();
									
									if(resourceid1.equalsIgnoreCase("4")){
										attributevalue1 = ""; 					// Disable(Execute)
									}
									
									else if(resourceid1.equalsIgnoreCase("5")){
										attributevalue1 = "86400";					// Disable Timeout SET NEW VALUE
									}
									
									else{
										writeattr2 = "There's no resource corresponding to this object id..\n";
										System.out.println("Output from Device .... \n");
										System.out.println(writeattr2 +"\n");
										break;
									}	
									
									webResource = client				// send endpoint and objinstance
											.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"writeattribute"+"/1"+"/"+objInstance+"/"+resourceid1);

											response = webResource.accept("text/plain")
											.put(ClientResponse.class, attributevalue1);					// send new value

											if (response.getStatus() != 200) {
												throw new RuntimeException("Failed : HTTP error code : "
												+ response.getStatus());
											}

											writeattr2 = response.getEntity(String.class);
											System.out.println("Output from Device .... \n");
											System.out.println(writeattr2 +"\n");
									
									break;
									
								case "0":
									break;

								default:
									System.out.println("Sorry.. Enter a choice.. \n");
									break;			
								}		
							} while(!choice5.equalsIgnoreCase("0"));
								break;					// break of case 1.4
								
							case "1.5":
								
								String choice6;
								do{
								System.out.println("Select the Object ID of the Resource to Execute \n");
								System.out.println("1. LWM2M Server Object(1) \n2. Device Object(3) \n0. Exit");
								BufferedReader bufferobj2 = new BufferedReader(new InputStreamReader(System.in));
								choice6 = bufferobj2.readLine();
						
								switch(choice6){
								case "1":					// Registration Update Trigger
									String execute1;
									System.out.println("Enter the Resource ID(8) to execute...\n");	
									BufferedReader bufferobj3 = new BufferedReader(new InputStreamReader(System.in));
									String resourceid = bufferobj3.readLine();
									
									if(!resourceid.equalsIgnoreCase("8")){
										execute1 = "There's no resource to execute corresponding to this object id..\n";
										System.out.println("Output from Device .... \n");
										System.out.println(execute1 +"\n");
										break;		
									}
									
									
									String objInstance1 = MongoService.getobjectinstanceid();		// 1. get new object instance
								
									webResource = client				// send endpoint and objinstance
											.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"execute"+"/1"+"/"+objInstance+"/8");

									response = webResource.accept("text/plain")
											.post(ClientResponse.class, objInstance1);		// send new objectinstance

									if (response.getStatus() != 200) {
										throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
									}
									
									webResource = client
											.resource("http://"+deviceURI+"/"+projectname+"/"+"read"+"/"+"3"+"/"+objInstance+"/"+"17");

											response = webResource.accept("application/json")
											.get(ClientResponse.class);

											if (response.getStatus() != 200) {
												throw new RuntimeException("Failed : HTTP error code : "
												+ response.getStatus());
											}

											String type = response.getEntity(String.class);
									
									String reginfo = response.getEntity(String.class);
									execute1 = "Execute Operation success.. Device re-registered with a new Object Instance ID: "+objInstance1+" .\n";
									System.out.println("Output from Device .... \n");
									System.out.println(execute1 +"\n");
									
									MongoService.UpdateRegisterSuccess(name, reginfo, type);		// IF SUCCESS, re-register.
																			
									break;
									
								case "2":
									
									String execute2;
									System.out.println("Enter the Resource ID(4,5) to execute...\n");	
									BufferedReader bufferobj4 = new BufferedReader(new InputStreamReader(System.in));
									String resourceid1 = bufferobj4.readLine();
									
									if(!resourceid1.equalsIgnoreCase("4") && !resourceid1.equalsIgnoreCase("5")){
										execute2 = "There's no resource to execute corresponding to this object id..\n";
										System.out.println("Output from Device .... \n");
										System.out.println(execute2 +"\n");
										break;		
									}
									
									// String objInstance2 = MongoService.getobjectinstanceid();		// 1. get new object instance
								
									System.out.println("Loading.... \n");
									
									webResource = client				// send endpoint and objinstance
											.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"execute"+"/3"+"/"+objInstance+"/"+resourceid1);

									response = webResource.accept("text/plain")
											.post(ClientResponse.class);		// nothing to send for this resource execution

									if (response.getStatus() != 200) {
										throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
									}
									
									if(resourceid1.equalsIgnoreCase("4")){
									
									execute2 = response.getEntity(String.class);
									System.out.println("Output from Device .... \n");
									System.out.println(execute2 +"\n");
									}
									else{
									execute2 = response.getEntity(String.class);
									System.out.println("Output from Device .... \n");
									System.out.println(execute2 +"\n");
									MongoService.DeregisterDevice(name , objInstance);
									
									}
									break;
									
								case "0":
									break;

								default:
									System.out.println("Sorry.. Enter a choice.. \n");
									break;			
								}		
							} while(!choice6.equalsIgnoreCase("0"));
								break;					// break of case 1.5
								
							case "1.6":
								
								System.out.println("Enter the Object ID(1) to Create an Object Instance \n");
								BufferedReader bufferobj2 = new BufferedReader(new InputStreamReader(System.in));
								String objectid= bufferobj2.readLine();
								
								if(!objectid.equalsIgnoreCase("1")){
									System.out.println("There's no object to create with this Object ID.. \n");
									break;					
								}

								String objInstance1 = MongoService.getobjectinstanceid();		// 1. get new object instance
								
								// get new values..
								String newvalue = MongoService.createNewServerObject(name, objInstance1);
														
								webResource = client
										.resource("http://"+deviceURI+"/"+projectname+"/"+"create/1"+"/"+objInstance1);

										response = webResource.accept("text/plain")
										.post(ClientResponse.class,newvalue);

										if (response.getStatus() != 200) {
											throw new RuntimeException("Failed : HTTP error code : "
											+ response.getStatus());
										}

										String newinfo = response.getEntity(String.class);
										String create1 = "The Create Operation is successfully done.. The device is now registered with the new Object Instance i.e.  "+objInstance1+ "\nExit first, and than run the discover operation to see that the new object is added to the intended device\n";
										System.out.println("Output from Device .... \n");
										System.out.println(create1 +"\n");
								
								// IF SUCCESS, changes in RegisteredDevices collection.	
								MongoService.RegistrationUpdate(name, objInstance1);

								// REGISTRATION PERSISTS with new Object Instance								
								// CHANGE PARAMETERS VALUE RECIEVED FRMO DEVICE in ServerObject	
								MongoService.UpdateNewObjectInstanceServerObject(newinfo);
								
								break;
								
							case "1.7":
											// DELETE only for ServerObject
								System.out.println("Loading.... \n");
								
								webResource = client				// send endpoint and objinstance
								.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"delete"+"/1"+"/"+objInstance);

								response = webResource.accept("text/plain")
								.delete(ClientResponse.class);		

								if (response.getStatus() != 200) {
									throw new RuntimeException("Failed : HTTP error code : "
											+ response.getStatus());
								}
								
								response.getEntity(String.class);
								System.out.println("Output from Device .... \n");
								System.out.println("Delete Operation successful.. The object instance is deleted from the device.."+"\n");
								
								// IF SUCCESS, also remove from Serverdb...		// basically deregister device operation
								MongoService.DeregisterDevice(name , objInstance);
								
								break;
								
							case "0":
								break;

							default:
								System.out.println("Sorry.. Enter a choice.. \n");
								break;
							
							}				// end of inner switch
							
						} while(!choice1.equalsIgnoreCase("0"));							// end of device do

						break;
						
					case "2":
						
						webResource = client
							.resource("http://"+deviceURI+"/"+projectname+"/"+"read"+"/"+"3"+"/"+objInstance+"/"+"17");

						response = webResource.accept("application/json")
								.get(ClientResponse.class);

						if (response.getStatus() != 200) {
							throw new RuntimeException("Failed : HTTP error code : "
									+ response.getStatus());
						}

						String read6 = response.getEntity(String.class);
						
						if(read6.equalsIgnoreCase("Thermostat")){	
													
						String choice7;
						do{
							System.out.println("Select what you want to do.. \n");
							System.out.println("2.1 Observe \n2.2 Cancel Observation \n0. Exit");
							BufferedReader bufferobj1 = new BufferedReader(new InputStreamReader(System.in));
							choice7 = bufferobj1.readLine();
						
						switch(choice7){

						case "2.1":
							System.out.println("Observe operation started... Check Serverdb to check values.. \n");
							
							webResource = client				// send endpoint and objinstance
							.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"observe"+"/8"+"/"+objInstance+"/1");

							response = webResource.accept("text/plain")
							.get(ClientResponse.class);		

							if (response.getStatus() != 200) {
								throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
							}
							
							response.getEntity(String.class);
							
							break;

						case "2.2":
							
							webResource = client				// send endpoint and objinstance
							.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"observe"+"/8"+"/"+objInstance+"/0");

							response = webResource.accept("text/plain")
							.get(ClientResponse.class);		

							if (response.getStatus() != 200) {
								throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
							}
							
							String cancel = response.getEntity(String.class);
							System.out.println("Output from Device .... \n");
							System.out.println(cancel +"\n");
							
							break;
							
						case "0":
							break;

						default:
							System.out.println("Sorry.. Enter a choice.. \n");
							break;
						
						}				// end of inner switch
					} while(!choice7.equalsIgnoreCase("0"));
				}														// end of if
					
					else if(read6.equalsIgnoreCase("Light Switch")){
						
						String choice7;
						do{
							System.out.println("Select what you want to do.. \n");
							System.out.println("2.1 Observe Current Status \n2.2 Cancel Observation \n0. Exit");
							BufferedReader bufferobj1 = new BufferedReader(new InputStreamReader(System.in));
							choice7 = bufferobj1.readLine();
						
						switch(choice7){

						case "2.1":
							System.out.println("Observe operation started... Check Devicedb to check values.. \n");
							
							webResource = client				// send endpoint and objinstance
							.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"observe"+"/9"+"/"+objInstance+"/1");

							response = webResource.accept("text/plain")
							.get(ClientResponse.class);		

							if (response.getStatus() != 200) {
								throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
							}
							
							response.getEntity(String.class);
							
							break;
							
//							String status = MongoService.getCurrentStatusLightSwitch(objInstance);							
//							System.out.println("The current status of the Light Switch is:" +status +"\n");
							
						case "2.2":
							
							webResource = client				// send endpoint and objinstance
							.resource("http://"+deviceURI+"/"+projectname+"/"+name+"/"+"observe"+"/9"+"/"+objInstance+"/0");

							response = webResource.accept("text/plain")
							.get(ClientResponse.class);

							if (response.getStatus() != 200) {
								throw new RuntimeException("Failed : HTTP error code : "
										+ response.getStatus());
							}
							
							String cancel = response.getEntity(String.class);
							System.out.println("Output from Device .... \n");
							System.out.println(cancel +"\n");
							
							break;
							
						case "0":
							break;

						default:
							System.out.println("Sorry.. Enter a choice.. \n");
							break;
						
						}				// end of inner switch
					} while(!choice7.equalsIgnoreCase("0"));
				}													// end of else-if			
				
						break;										// break for Case 2: Information Reporting
					
					case "0":
						System.exit(0);
						break;

					default:
						System.out.println("Sorry.. Enter a choice.. \n");
						break;	
						
					}											// end of outer switch
				} while(choice.equalsIgnoreCase("0"));							// end of initial menu do
				
		} while(true);						// end of initial(1st) do
	} 								// end of try
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
}