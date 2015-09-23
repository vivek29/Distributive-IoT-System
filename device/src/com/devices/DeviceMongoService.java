package com.devices;

import java.net.UnknownHostException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mongodb.*;
import com.mongodb.util.JSON;

public class DeviceMongoService {
	static JSONObject jsonObj;

	static String result;
	public static String getBootstrapURI() throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		// initial client-side lwm2msecurityobject
		DBCollection collection = db.getCollection("LWM2MSecurityObject");
		String json = "{'Object Id' : '0','LWM2M Server URI' : 'http://vivek:8080/com.273.lwm2m.servers/lwm2m/server1/bootstrap/','Bootstrap Server' : 'True', 'Security Mode' : '3'}";
		DBObject dbObject = (DBObject)JSON.parse(json);		 
		collection.insert(dbObject);

		result = "http://vivek:8080/com.273.lwm2m.servers/lwm2m/server1/bootstrap/";
		return result;
	}

	static String bootstrapcheck1;
	public static String BootstrapCheckDevice(String endpoint) throws UnknownHostException, JSONException{
	
		String bootstrapcheck1;
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", endpoint);	 
		DBCursor cursor = collection1.find(searchQuery);
		if(cursor.hasNext()) {
			bootstrapcheck1="true";
		}
		else{
			bootstrapcheck1="false";
		}
		return bootstrapcheck1;
	}	
	
	public static void saveBootstrapInfo(String info) throws UnknownHostException{

		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );

		// extract two diff objects from info string
		String[] parts = info.split("&");
		String obj1 = parts[0];					// obj1
		String obj2 = parts[1];					// obj2
		// put obj1 in LWM2MSecurityObject
		DBCollection collection = db.getCollection("LWM2MSecurityObject");
		DBObject dbObject = (DBObject)JSON.parse(obj1);		 
		collection.insert(dbObject);
		// put obj1 in LWM2MServerObject
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		DBObject dbObject1 = (DBObject)JSON.parse(obj2);		 
		collection1.insert(dbObject1);	
	}

	public static void UpdateBootstrapInfo(String info, String endpoint) throws UnknownHostException{

		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );

		// extract two diff objects from info string
		String[] parts = info.split("&");
		String obj1 = parts[0];					// obj1
		String obj2 = parts[1];					// obj2
		// put obj1 in LWM2MSecurityObject
		DBCollection collection = db.getCollection("LWM2MSecurityObject");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", endpoint);
		
		BasicDBObject newDocument = new BasicDBObject().append("_id", endpoint);		// only update endpoint name
		DBObject update = new BasicDBObject("$set", newDocument);						// other fields are not changed
		collection.updateMulti(whereQuery, update);
		
		// put obj1 in LWM2MServerObject
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		DBObject dbObject1 = (DBObject)JSON.parse(obj2);		 
		collection1.insert(dbObject1);	
	}

	
	static String result1;
	public static String getRegisterInfo(String objid) throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");

		BasicDBObject whereQuery = new BasicDBObject().append("_id", objid);

		BasicDBObject newDocument = new BasicDBObject().append("_id", objid).append("Lifetime", "86400").append("Notification Storing", "True").append("Binding", "U").append("Registration Update Trigger", "");
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);

		DBCursor cursor = collection1.find(newDocument);
		while (cursor.hasNext()) {
			result1 = cursor.next().toString();			
		}
		return result1;
	}

	static String result2;
	static String z;
	public static String getRegisterURI(String endpoint) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			z = cursor.next().toString();			
		}
		if(z != null){
			jsonObj = new JSONObject(z);
			result2 = jsonObj.getString("LWM2M Server URI");
		}
		else{
			result2 = "false";
		}
		return result2;
	}
	
	static String check1;
	static String ch;
	public static String CheckDevice(String endpoint, String objid) throws UnknownHostException, JSONException{
	
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objid);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			ch = cursor.next().toString();			
		}
		if(ch != null){
		jsonObj = new JSONObject(ch);
		check1 = jsonObj.getString("Device Endpoint Name");		
		}
		else{
		check1 = "false";
		}
		return check1;
	}

	public static void saveThermostatDeviceInformation(String endpoint, String objid) throws UnknownHostException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		
		BasicDBObject document = new BasicDBObject().append("_id", objid).append("Device Endpoint Name", endpoint).append("Object Id", "3").append("Manufacturer", "Belkin International Inc.").append("Device Type", "Thermostat").append("Reboot", "").append("Factory Reset", "").append("Error Code", "0");
		collection1.insert(document);
		
	}
	
	public static void saveThermostatUpdatedDeviceInformation(String endpoint, String objid) throws UnknownHostException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objid);
		
		BasicDBObject document = new BasicDBObject().append("_id", objid).append("Device Endpoint Name", endpoint).append("Object Id", "3").append("Manufacturer", "Belkin International Inc.").append("Device Type", "Thermostat").append("Reboot", "").append("Factory Reset", "").append("Error Code", "0");
		DBObject update = new BasicDBObject("$set", document);
		collection1.update(whereQuery, update);
	}
	
	public static void saveLightSwitchDeviceInformation(String endpoint, String objid) throws UnknownHostException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		
		BasicDBObject document = new BasicDBObject().append("_id", objid).append("Device Endpoint Name", endpoint).append("Object Id", "3").append("Manufacturer", "Belkin International Inc.").append("Device Type", "Light Switch").append("Reboot", "").append("Factory Reset", "").append("Error Code", "0");
		collection1.insert(document);
		
	}
	
	public static void saveLightSwitchUpdatedDeviceInformation(String endpoint, String objid) throws UnknownHostException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objid);
		
		BasicDBObject document = new BasicDBObject().append("_id", objid).append("Device Endpoint Name", endpoint).append("Object Id", "3").append("Manufacturer", "Belkin International Inc.").append("Device Type", "Light Switch").append("Reboot", "").append("Factory Reset", "").append("Error Code", "0");
		DBObject update = new BasicDBObject("$set", document);
		collection1.update(whereQuery, update);
	}
	
	static String result3;
	public static String getUpdateParameters(String objid) throws UnknownHostException{

		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objid);

		BasicDBObject newDocument = new BasicDBObject().append("Lifetime", "10000").append("Binding", "SMS");
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);

		DBCursor cursor = collection1.find(newDocument);
		while (cursor.hasNext()) {
			result3 = cursor.next().toString();			
		}
		return result3;

	}
	static String read1;
	static String r1;
	public static String getShortServerid(String objinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			r1 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(r1);
		read1 = jsonObj.getString("Short Server Id");
		return read1;
	}
	
	static String read2;
	static String r2;
	public static String getLifetime(String objinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			r2 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(r2);
		read2 = jsonObj.getString("Lifetime");
		return read2;
	}
	
	static String read3;
	static String r3;
	public static String getNotificationStoring(String objinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			r3 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(r3);
		read3 = jsonObj.getString("Notification Storing");
		return read3;
	}
	
	static String read4;
	static String r4;
	public static String getBinding(String objinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			r4 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(r4);
		read4 = jsonObj.getString("Binding");
		return read4;
	}
	
	static String read5;
	static String r5;
	public static String getManufacturer(String objinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			r5 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(r5);
		read5 = jsonObj.getString("Manufacturer");
		return read5;
	}
	
	static String read6;
	static String r6;
	public static String getDeviceType(String objinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			r6 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(r6);
		read6 = jsonObj.getString("Device Type");
		return read6;
	}
	
	static String read7;
	static String r7;
	public static String getErrorCode(String objinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			r7 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(r7);
		read7 = jsonObj.getString("Error Code");
		return read7;
	}
	
	static String discover1;
	public static String discoverSecurityObject(String endpoint) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			discover1 = cursor.next().toString();			
		}
		return discover1;
	}
	
	static String discover2;
	public static String discoverServerObject(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			discover2 = cursor.next().toString();			
		}
		return discover2;
	}
	
	static String discover3;
	public static String discoverDeviceObject(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			discover3 = cursor.next().toString();			
		}
		return discover3;
	}
	
	static String discover4;
	static String d1;
	public static String discoverLWM2MServerURI(String endpoint) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d1 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d1);
		discover4 = jsonObj.getString("LWM2M Server URI");
		discover4 = "{'LWM2M Server URI' : '"+discover4+"'}";
		return discover4;
	}
	
	static String discover5;
	static String d2;
	public static String discoverSecurityMode(String endpoint) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d2 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d2);
		discover5 = jsonObj.getString("Security Mode");
		discover5 = "{'Security Mode' : '"+discover5+"'}";
		return discover5;
	}
	
	static String discover6;
	static String d3;
	public static String discoverShortServerId(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d3 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d3);
		discover6 = jsonObj.getString("Short Server Id");
		discover6 = "{'Short Server Id' : '"+discover6+"'}";
		return discover6;
	}
	
	static String discover7;
	static String d4;
	public static String discoverBindingMode(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d4 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d4);
		discover7 = jsonObj.getString("Binding");
		discover7 = "{'Binding' : '"+discover7+"'}";
		return discover7;
	}
	
	static String discover8;
	static String d5;
	public static String discoverManufacturer(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d5 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d5);
		d5 = jsonObj.getString("Manufacturer");
		discover8 = "{'Manufacturer' : '"+d5+"'}";
		return discover8;
	}

	static String discover9;
	static String d6;
	public static String discoverErrorCode(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("DeviceInfo");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d6 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d6);
		d6 = jsonObj.getString("Error Code");
		discover9 = "{'Error Code' : '"+d6+"'}";
		return discover9;
	}

	
	public static void writeLifetime(String objectinstance, String newvalue) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objectinstance);							// check the specific id
				
		BasicDBObject newDocument = new BasicDBObject().append("Lifetime", newvalue);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);			
	}
	
	public static void writeNotificationStoring(String objectinstance, String newvalue) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objectinstance);							// check the specific id
				
		BasicDBObject newDocument = new BasicDBObject().append("Notification Storing", newvalue);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);			
	}
	
	public static void writeBinding(String objectinstance, String newvalue) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objectinstance);							// check the specific id
				
		BasicDBObject newDocument = new BasicDBObject().append("Binding", newvalue);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);			
	}
	
	public static void writeAttributeSMSSecurityMode(String endpoint, String newattribute) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", endpoint);							// check the specific id
		
		BasicDBObject newDocument = new BasicDBObject().append("SMS Security Mode", newattribute);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);
	}
	
	public static void writeAttributeClientHoldOffTime(String endpoint, String newattribute) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", endpoint);							// check the specific id
		
		BasicDBObject newDocument = new BasicDBObject().append("Client Hold-Off Time", newattribute);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);
	}
	
	public static void writeAttributeDisable(String objectinstance, String newattribute) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objectinstance);							// check the specific id
		
		BasicDBObject newDocument = new BasicDBObject().append("Disable", newattribute);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);
	}
	
	public static void writeAttributeDisableTimeout(String objectinstance, String newattribute) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objectinstance);							// check the specific id
		
		BasicDBObject newDocument = new BasicDBObject().append("Disable Timeout", newattribute);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);
	}
	
	public static void createNewServerObject(String newvalue) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
			
		DBObject dbObject1 = (DBObject)JSON.parse(newvalue);			// newvalue has _id field 
		collection1.insert(dbObject1);
	}
	
	static String create1;											// set new values to new object instance
	public static String setNewValuesNewObjectInstance(String objid) throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");

		BasicDBObject whereQuery = new BasicDBObject().append("_id", objid);

		BasicDBObject newDocument = new BasicDBObject().append("_id", objid).append("Lifetime", "86400").append("Disable", "").append("Disable Timeout","86400").append("Notification Storing", "True").append("Binding", "U").append("Registration Update Trigger", "");
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);

		DBCursor cursor = collection1.find(newDocument);
		while (cursor.hasNext()) {
			create1 = cursor.next().toString();			
		}
		return create1;
	}

	static String reregisterinfo;
	public static String executeNewRegisterInfo(String prevobjid, String newobjid) throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");

		BasicDBObject newDocument = new BasicDBObject().append("_id", newobjid).append("Lifetime", "86400").append("Notification Storing", "True").append("Binding", "U").append("Registration Update Trigger", "");
		collection1.insert(newDocument);
		DBCursor cursor = collection1.find(newDocument);
		while (cursor.hasNext()) {
			reregisterinfo = cursor.next().toString();			
		}
		return reregisterinfo;
	}
	
	public static void executeFactoryResetDevice(String endpoint , String objinstance) throws UnknownHostException {
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );					// Bring device to the initial configuration
		
		DBCollection collection = db.getCollection("LWM2MServerObject");				// collection1
		BasicDBObject whereQuery = new BasicDBObject().append("_id", endpoint);
		collection.remove(whereQuery);
		
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");				// collection2
		BasicDBObject whereQuery1 = new BasicDBObject().append("_id", objinstance);
		collection1.remove(whereQuery1);

		DBCollection collection2 = db.getCollection("DeviceInfo");						// collection3
		BasicDBObject whereQuery2 = new BasicDBObject().append("_id", objinstance);
		collection2.remove(whereQuery2);
	}
	
	public static int checkForDefaultLightSwitchValue(String objinstance) throws UnknownHostException{
		
		int hasDefault = 0;
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
			
		DBCollection collection = db.getCollection("LightSwitchValues");
			
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", objinstance);	 
		DBCursor cursor = collection.find(searchQuery);
		if(cursor.hasNext()) {
			hasDefault=1;
		}		
		return hasDefault;
	}
	
	public static void storeInitialLightSwitchTriggerValue(String endpoint, String objinstance) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection2 = db.getCollection("LightSwitchValues");
		
		BasicDBObject document = new BasicDBObject();
		document.append("_id", objinstance);
		document.append("Device Endpoint Name", endpoint);
		document.append("Power On/Off","Off");								// set default value off
		
		collection2.insert(document);
	}
	
	static String status;
	static String t1;
	public static String getCurrentStatusLightSwitch(String objinstance) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection2 = db.getCollection("LightSwitchValues");
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objinstance);							// check the specific id
		DBCursor cursor = collection2.find(whereQuery);		
		while (cursor.hasNext()) {
			t1 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(t1);
		status = jsonObj.getString("Power On/Off");
		return status;

	}
	
	public static void triggerLightSwitch(String objinstance, String status) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "Clientdb" );
		DBCollection collection2 = db.getCollection("LightSwitchValues");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objinstance);
		
		BasicDBObject newDocument = new BasicDBObject().append("Power On/Off", status);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection2.updateMulti(whereQuery, update);
	}
	
}