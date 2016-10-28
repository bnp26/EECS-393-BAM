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

import java.util.List;
import java.util.Set;

public class MongoDB {
    
    public static ArrayList<String> getNotebooks() {

        MongoClient mongoClient;
        ArrayList<String> notebookList = new ArrayList<String>();

        try {
        mongoClient = new MongoClient("localhost", 27017);
        notebookList = (ArrayList)mongoClient.getDatabaseNames();
        }
        catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
        return notebookList;
    }
    
    public static ArrayList<String> getPages(String Notebook) {
                
        DB db;
        MongoClient mongoClient;
        ArrayList<String> pageList = new ArrayList<String>();
        
        try {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDB(Notebook);
            Set<String> myset = db.getCollectionNames();
            for(String str : myset) {
                pageList.add(str);
            }
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return pageList;
    }
    
    public static void insertLine(String Notebook, String Page, int linenum, String linestr) {
      DB db;
      MongoClient mongoClient;
      DBCollection collection;
      //ArrayList<String> appsApplied = new ArrayList<String>();
    
      try{ 
        // To connect to mongodb server
        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDB(Notebook);
	collection = db.getCollection(Page); // --implicitly creates collection if none exists 
	 
        BasicDBObject doc = new BasicDBObject(); // create a document object
     
        DBCursor cursor = collection.find();

        BasicDBObject query = new BasicDBObject("linenum", linenum);
        cursor = collection.find(query);
        boolean found = false;
        try {
           while(cursor.hasNext()) {
               System.out.println(cursor.next());
               found = true;
        }
        } finally {
           cursor.close();
        }
        
        if(!found) {   
            doc.put("linenum", linenum);
            doc.put("linestr", linestr);
            collection.insert(doc, WriteConcern.ACKNOWLEDGED);
        }
        
      }catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
   }

}
    
    
   
      
  