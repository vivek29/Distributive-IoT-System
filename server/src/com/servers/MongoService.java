package com.servers;
import java.net.UnknownHostException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mongodb.*;
											// HERE objid is objectinstanceid
public class MongoService {
	static JSONObject jsonObj;
	
	static String p;							// send during bootstrap
	public static String getSecurityObject(String endpoint) throws UnknownHostException{
	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		
		DBCollection collection = db.getCollection("LWM2MSecurityObject");
		BasicDBObject document = new BasicDBObject();
		document.put("_id",endpoint);
		document.append("Object Id", "0");
		document.append("LWM2M Server URI", "http://vivek:8080/com.273.lwm2m.servers/lwm2m/server2/register/");	
		// ** vivek bcoz, get ipaddress for machine(ethernet adapter virtualbox) *****
		document.append("Bootstrap Server", "False");
		document.append("Security Mode","3");			 
		collection.insert(document);
		DBCursor cursor = collection.find(document);
		while(cursor.hasNext()) {
		    p = cursor.next().toString();
		}		
		return p;
	}
	
	static String u;							// send during bootstrap
	public static String getUpdatedSecurityObject(String endpoint) throws UnknownHostException{
	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection = db.getCollection("LWM2MSecurityObject");

		BasicDBObject whereQuery = new BasicDBObject().append("_id", endpoint);
		
		BasicDBObject newDocument = new BasicDBObject().append("_id", endpoint).append("Object Id", "0").append("LWM2M Server URI", "http://vivek:8080/com.273.lwm2m.servers/lwm2m/server2/register/").append("Bootstrap Server", "False").append("Security Mode","3");
		DBObject update = new BasicDBObject("$set", newDocument);
		collection.updateMulti(whereQuery, update);

		DBCursor cursor = collection.find(newDocument);
		while(cursor.hasNext()) {
		    u = cursor.next().toString();
		}		
		return u;
	}
	
									// check if already bootstrapped
	public static int checkBootstrapping(String endpoint) throws UnknownHostException{
			
		int isBootStrapped = 0;
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
			
		DBCollection collection = db.getCollection("LWM2MSecurityObject");
			
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", endpoint);	 
		DBCursor cursor = collection.find(searchQuery);
		if(cursor.hasNext()) {
			isBootStrapped=1;
		}		
		return isBootStrapped;
	}

	static String temp;
	public static String getobjectinstanceid() throws UnknownHostException, JSONException{
	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("ObjectInstanceID");
		DBCursor cursor = collection2.find();
		while(cursor.hasNext()) {
		    temp = cursor.next().toString();
		}
		jsonObj = new JSONObject(temp);							// get's the last document(last used id) in the collection
		String objectinstanceid = jsonObj.getString("_id");

		int x = Integer.parseInt(objectinstanceid);
		x++;													// increment by 1
		String y = String.valueOf(x);							// convert to string
		BasicDBObject document1 = new BasicDBObject();
		document1.put("_id",y);								// insert the incremented value to db, so next time +1 from that..
		collection2.insert(document1);
		
		return objectinstanceid;
	}
	static String q;								//  send during bootstrap
	public static String getServerObject(String endpoint, String objid) throws UnknownHostException{
	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		BasicDBObject document = new BasicDBObject();
		document.append("_id", objid);
		document.append("Device Endpoint Name", endpoint);
		document.append("Object Id", "1");
		document.append("Short Server Id", "12345");
		document.append("Lifetime", "");
		document.append("Notification Storing", "");
		document.append("Binding", "");
		document.append("Registration Update Trigger", "");
		collection1.insert(document);
		DBCursor cursor = collection1.find(document);
		while (cursor.hasNext()) {
			q = cursor.next().toString();			
		}
		return q;
	}
											//  update server object
	public static void updateServerObject(String reginfo) throws UnknownHostException, JSONException{
	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		jsonObj = new JSONObject(reginfo);
		
		String a1 = jsonObj.getString("_id");					// a1 - get _id
		String a2 = jsonObj.getString("Lifetime");					
		String a3 = jsonObj.getString("Notification Storing");
		String a4 = jsonObj.getString("Binding");
		String a5 = jsonObj.getString("Registration Update Trigger");		
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", a1);							// check the specific id
				
		BasicDBObject newDocument = new BasicDBObject().append("_id", a1).append("Lifetime", a2).append("Notification Storing", a3).append("Binding", a4).append("Registration Update Trigger", a5);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);			
	}
	
	public static void updateParameters(String updateinfo) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		jsonObj = new JSONObject(updateinfo);
		String a1 = jsonObj.getString("_id");					// a1 - get _id
		String a2 = jsonObj.getString("Lifetime");					
		String a3 = jsonObj.getString("Binding");

		BasicDBObject whereQuery = new BasicDBObject().append("_id", a1);							// check the specific id		
		BasicDBObject newDocument = new BasicDBObject().append("Lifetime", a2).append("Binding", a3);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);
	}
	
	public static void RegisterSuccess(String endpoint , String reginfo, String type) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("RegisteredDevices");
		
		BasicDBObject document = new BasicDBObject().append("_id", endpoint);

		jsonObj = new JSONObject(reginfo);
		String a1 = jsonObj.getString("_id");				// get server object id
		document.append("Object Instance ID", a1);
		document.append("Status", "Registered");
		document.append("Device Type", type);
		collection2.insert(document);
	}

	public static void UpdateRegisterSuccess(String endpoint , String reginfo, String type) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("RegisteredDevices");
		
		jsonObj = new JSONObject(reginfo);
		String a1 = jsonObj.getString("_id");				// get server object id
		
		BasicDBObject document = new BasicDBObject().append("_id", endpoint);

		BasicDBObject newDocument = new BasicDBObject().append("_id", endpoint).append("Object Instance ID", a1).append("Status", "Registered").append("Device Type", type);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection2.updateMulti(document, update);	
	}
	
	public static void saveIpaddress(String endpoint , String contact) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("DeviceContact");
		BasicDBObject document = new BasicDBObject().append("_id", endpoint);
		document.append("Contact", contact);
		collection2.insert(document);
	}	
									// for re-registration request
	public static void saveNewIpaddress(String endpoint , String contact) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("DeviceContact");
		BasicDBObject document = new BasicDBObject().append("_id", endpoint);

		BasicDBObject newDocument = new BasicDBObject().append("_id", endpoint).append("Contact", contact);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection2.update(document, update);
	}
	
	static String result4;
	static String m;
	public static String CheckIfRegistered(String endpoint) throws UnknownHostException, JSONException{
	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("RegisteredDevices");
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection2.find(whereQuery);		
		while (cursor.hasNext()) {
			m = cursor.next().toString();			
		}
		if(m != null){
		jsonObj = new JSONObject(m);
		result4 = jsonObj.getString("Status");		
		}
		else{
		result4 = "false";
		}
		return result4;
	}
	
	public static void DeregisterDevice(String endpoint , String objid) throws UnknownHostException {
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		
		DBCollection collection = db.getCollection("LWM2MSecurityObject");			// 1st collection		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", endpoint);
		collection.remove(whereQuery);
		
		DBCollection collection1 = db.getCollection("LWM2MServerObject");			// 2nd collection
		BasicDBObject whereQuery1 = new BasicDBObject().append("_id", objid);
		collection1.remove(whereQuery1);
		
		DBCollection collection2 = db.getCollection("RegisteredDevices");			// 3rd collection
		BasicDBObject whereQuery2 = new BasicDBObject().append("_id", endpoint);
		collection2.remove(whereQuery2);
		
		DBCollection collection3 = db.getCollection("DeviceContact");				// 4th collection
		BasicDBObject whereQuery3 = new BasicDBObject().append("_id", endpoint);
		collection3.remove(whereQuery3);
	}
	
	static String result5;
	static String o;
	public static String getRegisteredObjectInstanceID(String endpoint) throws UnknownHostException, JSONException{
	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("RegisteredDevices");
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection2.find(whereQuery);		
		while (cursor.hasNext()) {
			o = cursor.next().toString();			
		}
		jsonObj = new JSONObject(o);
		result5 = jsonObj.getString("Object Instance ID");
		return result5;
	}
	
	static String result2;
	static String z;
	public static String getDeviceURI(String endpoint) throws UnknownHostException, JSONException{
	MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	DB db = mongoClient.getDB( "Serverdb" );
	DBCollection collection1 = db.getCollection("DeviceContact");
	BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("_id", endpoint);							// check the specific id
	DBCursor cursor = collection1.find(whereQuery);		
	while (cursor.hasNext()) {
		z = cursor.next().toString();			
	}
	jsonObj = new JSONObject(z);
	result2 = jsonObj.getString("Contact");
	return result2;
	}
	
	static String c1;								//  send during bootstrap
	public static String createNewServerObject(String endpoint, String objid) throws UnknownHostException{
	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		BasicDBObject document = new BasicDBObject();
		document.append("_id", objid);
		document.append("Device Endpoint Name", endpoint);
		document.append("Object Id", "1");
		document.append("Short Server Id", "12345");
		document.append("Lifetime", "");
		document.append("Disable", "");
		document.append("Disable Timeout", "");
		document.append("Notification Storing", "");
		document.append("Binding", "");
		document.append("Registration Update Trigger", "");
		
		collection1.insert(document);
		DBCursor cursor = collection1.find(document);
		while (cursor.hasNext()) {
			q = cursor.next().toString();			
		}
		return q;
	}
							// called at time of Create, to save changes.
	public static void RegistrationUpdate(String endpoint , String objid) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("RegisteredDevices");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", endpoint);
		
		BasicDBObject document = new BasicDBObject();
		document.append("Object Instance ID", objid);					// update corresponding object instance id
		DBObject update = new BasicDBObject("$set", document);
		collection2.updateMulti(whereQuery, update);		
	}
				
	public static void UpdateNewObjectInstanceServerObject(String newinfo) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		jsonObj = new JSONObject(newinfo);

		String a1 = jsonObj.getString("_id");
		String a2 = jsonObj.getString("Lifetime");
		String a3 = jsonObj.getString("Disable");
		String a4 = jsonObj.getString("Disable Timeout");
		String a5 = jsonObj.getString("Notification Storing");
		String a6 = jsonObj.getString("Binding");
		String a7 = jsonObj.getString("Registration Update Trigger");		
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", a1);							// check the specific id
				
		BasicDBObject newDocument = new BasicDBObject().append("_id", a1).append("Lifetime", a2).append("Disable", a3).append("Disable Timeout", a4).append("Notification Storing", a5).append("Binding", a6).append("Registration Update Trigger", a7);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);			
	}
	
	static String temp1;
	public static String getmessageid() throws UnknownHostException, JSONException{
	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("MessageID");
		DBCursor cursor = collection2.find();
		while(cursor.hasNext()) {
		    temp1 = cursor.next().toString();
		}
		jsonObj = new JSONObject(temp1);							// get's the last document(last used id) in the collection
		String messageid = jsonObj.getString("_id");

		int x = Integer.parseInt(messageid);
		x++;													// increment by 1
		String y = String.valueOf(x);							// convert to string
		BasicDBObject document1 = new BasicDBObject();
		document1.put("_id",y);								// insert the incremented value to db, so next time +1 from that..
		collection2.insert(document1);
		
		return messageid;
	}
	
	public static void storeObservationValues(String messageid , String endpoint, String objinstance, String value, String time) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("TemperatureObservationValues");
		
		BasicDBObject document = new BasicDBObject();

		document.append("_id", messageid);
		document.append("Device Endpoint Name", endpoint);
		document.append("Object Instance ID",objinstance);
		document.append("Temperature Values", value);
		document.append("Time Inserted", time);
		
		collection2.insert(document);
	}
	
	public static void storeCurrentStatusLightSwitch(String messageid , String endpoint, String objinstance, String status, String time) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("LightSwitchStatus");
		
		BasicDBObject document = new BasicDBObject();

		document.append("_id", messageid);
		document.append("Device Endpoint Name", endpoint);
		document.append("Object Instance ID",objinstance);
		document.append("Power On/Off", status);
		document.append("Time Checked", time);
		
		collection2.insert(document);
	}
	
	static String validate;
	static String v;
	public static String getRegisterURI(String endpoint) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			v = cursor.next().toString();			
		}
		if(v != null){
			jsonObj = new JSONObject(v);
			validate = jsonObj.getString("LWM2M Server URI");
		}
		else{
			validate = "false";
		}
		return validate;
	}
	
	static String validate1;
	static String v1;
	public static String checkServerObjectInstanceID(String objinstance) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			v1 = cursor.next().toString();			
		}
		if(v1 != null){
			jsonObj = new JSONObject(v1);
			validate1 = jsonObj.getString("Device Endpoint Name");
		}
		else{
			validate1 = "false";
		}
		return validate1;
	}
	
	public static String getRegisteredThermostatDevices() throws UnknownHostException, JSONException{

		String data1="";
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("RegisteredDevices");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("Device Type", "Thermostat");
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			data1 = data1+"&"+cursor.next().toString();			
		}
		data1 = data1.substring(1);
		data1 = data1.replaceAll("Object Instance ID", "objinstanceid").replaceAll("Device Type", "devicetype");
		return data1;
	}
	
	
	public static String getRegisteredLightSwitchDevices() throws UnknownHostException, JSONException{

		String data2="";	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("RegisteredDevices");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("Device Type", "Light Switch");
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			data2 = data2+"&"+cursor.next().toString();			
		}
		data2 = data2.substring(1);
		data2 = data2.replaceAll("Object Instance ID", "objinstanceid").replaceAll("Device Type", "devicetype");
		return data2;
	}
	
}