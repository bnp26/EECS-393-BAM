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
import com.mongodb.MongoNamespace;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import java.util.Collections;

public class MongoDB {

    private String host;
    private int port;

    public MongoDB() {
        host = new String("localhost");
        port = 27017;
    }
    // axg769
    /*
    METHODS FOR NOTEBOOK
    */
    
    public void createNotebook(String notebook) {

        MongoDatabase db;
        MongoClient mongoClient;
        String page = "-**BLANK**-";
        
        try {
            mongoClient = new MongoClient(host, port);
            db = mongoClient.getDatabase(notebook);

            if (!pageExists(notebook, page)) {
                System.out.println("created new collection");
                db.createCollection(page);
            }
            else
                System.out.println("Such page with the given name exists");
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    } // createPage()
    
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
    } // getNotebooks()
    
    // Will remove the notebook if no other page exists
    public void deleteNotebook(String notebook) {

        MongoDatabase db = null;
        MongoClient mongoClient;

        try {
            mongoClient = new MongoClient(host, port);
            db = mongoClient.getDatabase(notebook);
            if (getNotebooks().contains(notebook)) {
                System.out.println("dropping the notebook");
                db.drop();
            }
            else
                System.out.println("Such notebook with the given name does not exist");
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    } // deleteNotebook()
    
    /*
    METHODS FOR PAGE
    */
    
    // Need to create a page together with a notebook
    public void createPage(String notebook, String page) {

        MongoDatabase db;
        MongoClient mongoClient;

        try {
            mongoClient = new MongoClient(host, port);
            db = mongoClient.getDatabase(notebook);

            if (!pageExists(notebook, page)) {
                System.out.println("created new collection");
                db.createCollection(page);
            }
            else
                System.out.println("Such page with the given name exists");
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    } // createPage()
    
    public void renamePage(String notebook, String page, String newPage) {

        MongoNamespace newPageName = new MongoNamespace(notebook, newPage);

        MongoDatabase db;
        MongoClient mongoClient;
        MongoCollection<Document> collection;
        boolean rename = false;
            
        try {
            mongoClient = new MongoClient(host, port);
            db = mongoClient.getDatabase(notebook);
            
            if (!pageExists(notebook, page)) {
                System.out.println("such a page does not exist!");
            }
            else {
                collection = db.getCollection(page); // --implicitly creates collection if none exists 
                collection.renameCollection(newPageName);
            }
            
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    } // createPage()
    
    // Will remove the notebook if no other page exists
    public void deletePage(String notebook, String page) {

        MongoDatabase db;
        MongoClient mongoClient;
        MongoCollection<Document> collection;

        try {
            mongoClient = new MongoClient(host, port);
            db = mongoClient.getDatabase(notebook);

            if (pageExists(notebook, page)) {
                collection = db.getCollection(page); // --implicitly creates collection if none exists 
                System.out.println("dropping the page");
                collection.drop();
            }
            else
                System.out.println("Such page with the given name exists");
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    } // deletePage()
    
    public ArrayList<String> getPages(String Notebook) {

        MongoDatabase db;
        MongoClient mongoClient;
        ArrayList<String> pageList = new ArrayList<String>();

        try {
            mongoClient = new MongoClient(host, port);
            db = mongoClient.getDatabase(Notebook);
            MongoIterable<String> myset = db.listCollectionNames();
            for (String str : myset) {
                pageList.add(str);
            }
            if(pageList.contains("-**BLANK**-"))
                pageList.remove("-**BLANK**-");

            
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        Collections.sort(pageList.subList(0, pageList.size()));
        return pageList;
    } // getPages()
    
    public boolean pageExists(String notebook, String page) {
        MongoDatabase db;
        MongoClient mongoClient;
        mongoClient = new MongoClient(host, port);
        db = mongoClient.getDatabase(notebook);
        MongoIterable collectionNames = db.listCollectionNames();
        MongoCursor cursor = collectionNames.iterator();
        while (cursor.hasNext()) {
            String currentCollection = (String) cursor.next();

            if (page.equals(currentCollection)) {
                return true;
            }
        }
        return false;
    } // pageExists()
    
    public long getPageLength(String notebook, String page) {
        MongoDatabase db;
        MongoClient mongoClient;
        MongoCollection<Document> collection;
        long collectionLength = 0;
        
        try {
            // To connect to mongodb server
            mongoClient = new MongoClient(host, port);
            db = mongoClient.getDatabase(notebook);
            collection = db.getCollection(page); // --implicitly creates collection if none exists 
            collectionLength = collection.count();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return collectionLength;
    } // getPageLength()


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
            mongoClient = new MongoClient(host, port);
            db = mongoClient.getDatabase(notebook);
            collection = db.getCollection(page); // --implicitly creates collection if none exists 
            if (!pageExists(notebook, page)) {
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
    } // insertLine()
    
    // decrements the line numbers of the lines after the position deleted
    public void deleteLine(String notebook, String page, int linenum){ 
        MongoDatabase db;
        MongoClient mongoClient;
        MongoCollection<Document> collection;
        
        try {
            // To connect to mongodb server
            mongoClient = new MongoClient(host, port);
            db = mongoClient.getDatabase(notebook);
            collection = db.getCollection(page); // --implicitly creates collection if none exists 
            if (!pageExists(notebook, page)) {
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
    } // deleteLine()

    public String getLine(String notebook, String page, int linenum) {
        MongoDatabase db;
        MongoClient mongoClient;
        MongoCollection collection;

        try {
            // To connect to mongodb server
            mongoClient = new MongoClient(host, port);
            db = mongoClient.getDatabase(notebook);
            collection = db.getCollection(page); // --implicitly creates collection if none exists 
            if (!pageExists(notebook, page)) {
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

        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    } // getLine()
   
    

}
