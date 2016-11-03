package Jott;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bson.Document;

import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MongoDB {
    private String host;
    private int port;
    public MongoDB() {
        host = new String("localhost");
        port = 27017;
    }
    
    public ArrayList<String> getNotebooks() {

        MongoClient mongoClient;
        MongoIterable notebooksIterable;
        
        ArrayList<String> notebooks = new ArrayList<String>();
        try {
            mongoClient = new MongoClient(host, port);
            notebooksIterable = mongoClient.listDatabaseNames();
            MongoCursor cursor = notebooksIterable.iterator();
            for(String str; cursor.hasNext();) {
                str = new String((String)cursor.next());
                notebooks.add(str);
            }
        }
        catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
        return notebooks;
    }
    
    public ArrayList<String> getPages(String Notebook) {
                
        MongoDatabase db;
        MongoClient mongoClient;
        ArrayList<String> pageList = new ArrayList<String>();
        
        try {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase(Notebook);
            MongoIterable<String> myset = db.listCollectionNames();
            for(String str : myset) {
                pageList.add(str);
            }
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return pageList;
    }
    
    
    
    public void insertLine(String Notebook, String Page, int linenum, String linestr) {
    	MongoDatabase db;
		MongoClient mongoClient;
		MongoCollection collection;
		JottBSONObject lineBSONObj = new JottBSONObject();
		//ArrayList<String> appsApplied = new ArrayList<String>();
		try{ 
			// To connect to mongodb server
	        mongoClient = new MongoClient("localhost", 27017);
	        db = mongoClient.getDatabase(Notebook);
	        collection = db.getCollection(Page); // --implicitly creates collection if none exists 
	        if(!collectionExists(Notebook, Page)) {
	        	db.createCollection(Page);
	        	System.out.println("created new collection");
	            lineBSONObj.createNewLine(linenum, linestr);
	        }
	        else
	        {
	        	if(lineBSONObj.containsKey(new Integer(linenum).toString())){
	        		HashMap<Integer, String> lineMap = (HashMap<Integer, String>) lineBSONObj.toMap();
	        		final int lineMapSize = lineMap.size();
	        		
	        		for(int counter = lineMapSize + 1; counter > linenum; counter--)
	        		{
	        			Integer currentLineNumber  = new Integer(counter);
	        			Integer previousLineNumber = new Integer(counter - 1);
	        			String currentLine = lineMap.get(previousLineNumber.toString());
	        			
	        			lineBSONObj.put(currentLineNumber.toString(), currentLine);
	        		}
	        		
	        		lineBSONObj.put(new Integer(linenum).toString(), linestr);
	        	}
	        }
	        collection.insertOne(lineBSONObj);
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
    }
    
    public String getLine(String notebook, String page, int linenum) {
    	
    	
    	return null;
    }
    
    public boolean collectionExists(String notebook, final String collectionName) {
    	MongoDatabase db;
        MongoClient mongoClient;
        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDatabase(notebook);
    	MongoIterable collectionNames = db.listCollectionNames();
    	MongoCursor cursor = collectionNames.iterator();
    	while(cursor.hasNext())
    	{
    		String currentCollection = (String) cursor.next();
    		
    		if(collectionName.equals(currentCollection)) {
    			return true;
    		}
    	}
    	return false;
    }
    
}
    
    
   
      
  