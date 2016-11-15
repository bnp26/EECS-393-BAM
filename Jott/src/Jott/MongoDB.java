package Jott;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
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
            for (String str; cursor.hasNext();) {
                str = new String((String) cursor.next());
                notebooks.add(str);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
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
            for (String str : myset) {
                pageList.add(str);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return pageList;
    }

    // increments the line numbers of the lines after the position inserted
    public void insertLine(String notebook, String page, int linenum, String linestr) {
        MongoDatabase db;
        MongoClient mongoClient;
        MongoCollection<Document> collection;

        Document doc = new Document();
        doc.append("linenum", new Integer(linenum));
        doc.append("linestr", linestr);
        //ArrayList<String> appsApplied = new ArrayList<String>();
        try {
            // To connect to mongodb server
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase(notebook);
            collection = db.getCollection(page); // --implicitly creates collection if none exists 
            if (!collectionExists(notebook, page)) {
                System.out.println("created new collection");
                db.createCollection(page);
                System.out.println("about to insert doc to the database");
                collection.insertOne(doc);
            } else {
                Document query = new Document("linenum", new Integer(linenum));
                FindIterable<Document> queryIterator = collection.find(query);

                if (queryIterator.first() != null) {

                    long collectionLength = collection.count();
                    System.out.println("collectionLength=" + collectionLength);

                    for (int counter = (int) (collectionLength + 1); counter > linenum; counter--) {
                        Document newQuery = new Document("linenum", new Integer(counter - 1));

                        FindIterable<Document> currentIterable = collection.find(newQuery);
                        MongoCursor<Document> currentCursor = currentIterable.iterator();

                        Document currentDoc = currentCursor.next();
                        currentDoc.put("linenum", new Integer(counter));

                        collection.findOneAndReplace(newQuery, currentDoc);
                    }

                    System.out.println("about to insert doc to the database");
                    collection.insertOne(doc);
                } else {
                    collection.insertOne(doc);
                }
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    // decrements the line numbers of the lines after the position deleted
    public void deleteLine(String notebook, String page, int linenum){ 
        MongoDatabase db;
        MongoClient mongoClient;
        MongoCollection<Document> collection;
        
        try {
            // To connect to mongodb server
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase(notebook);
            collection = db.getCollection(page); // --implicitly creates collection if none exists 
            if (!collectionExists(notebook, page)) {
                System.out.println("The notebook - page combination doesn't exist");
                
            } 
            else {
                Document query = new Document("linenum", new Integer(linenum));
                FindIterable<Document> queryIterator = collection.find(query);
                long collectionLength = collection.count();
                System.out.println("collectionLength=" + collectionLength);
                System.out.println("QI " + queryIterator.first());
                
                // Deleting the last line
                if(linenum == collectionLength) {
                    collection.deleteOne(query);
                }
                
                else {
                    int difference = (int) collectionLength - linenum;
                    collection.deleteOne(query);

                    for (int counter = 1; difference >= counter; counter++) {
                            Document newQuery = new Document("linenum", new Integer(linenum + counter));

                            FindIterable<Document> currentIterable = collection.find(newQuery);
                            MongoCursor<Document> currentCursor = currentIterable.iterator();

                            Document currentDoc = currentCursor.next();
                            currentDoc.put("linenum", new Integer(linenum + counter - 1));

                            collection.findOneAndReplace(newQuery, currentDoc);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public String getLine(String notebook, String page, int linenum) {
        MongoDatabase db;
        MongoClient mongoClient;
        MongoCollection collection;

        try {
            // To connect to mongodb server
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase(notebook);
            collection = db.getCollection(page); // --implicitly creates collection if none exists 
            if (!collectionExists(notebook, page)) {
                System.out.println("Collection does not exist");
                return null;
            } else {
                MongoCursor cursor = collection.find().iterator();
                System.out.println(cursor.tryNext().toString());

                while (cursor.hasNext()) {
                    Document currentDoc = (Document) cursor.next();
                    Integer linenumValue = new Integer(linenum);

                    if (currentDoc.getInteger("linenum").equals(linenumValue)) {
                        return currentDoc.getString("linestr");
                    }
                }
                System.out.println("Could not find line");
                return null;
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    public boolean collectionExists(String notebook, final String collectionName) {
        MongoDatabase db;
        MongoClient mongoClient;
        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDatabase(notebook);
        MongoIterable collectionNames = db.listCollectionNames();
        MongoCursor cursor = collectionNames.iterator();
        while (cursor.hasNext()) {
            String currentCollection = (String) cursor.next();

            if (collectionName.equals(currentCollection)) {
                return true;
            }
        }
        return false;
    }

}
