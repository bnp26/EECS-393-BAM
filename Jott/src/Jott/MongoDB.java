package Jott;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import java.util.ArrayList;

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
            mongoClient = new MongoClient("localhost", 27017);
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
      //ArrayList<String> appsApplied = new ArrayList<String>();
    
      try{ 
        // To connect to mongodb server
        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDatabase(Notebook);
	collection = db.getCollection(Page); // --implicitly creates collection if none exists 
	 
        BasicDBObject doc = new BasicDBObject(); // create a document object
     
        FindIterable cursor = collection.find();

        BasicDBObject query = new BasicDBObject("linenum", linenum);
        MongoCursor mongoCursor = collection.find(query).iterator();
        boolean found = false;
        try {
           while(mongoCursor.hasNext()) {
               System.out.println(mongoCursor.next());
               found = true;
        }
        } finally {
           mongoCursor.close();
        }
        
        if(!found) {   
            doc.put("linenum", linenum);
            doc.put("linestr", linestr);
            collection.insertOne(doc);
        }
        
      }catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
   }

}
    
    
   
      
  