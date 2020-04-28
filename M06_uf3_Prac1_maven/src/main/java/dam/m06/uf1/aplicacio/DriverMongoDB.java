package dam.m06.uf1.aplicacio;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

public class DriverMongoDB {
    
    private static volatile DriverMongoDB instance = null;
    
    private MongoClient conn = null;

    public static DriverMongoDB getInstance() throws AplicacioException {
        if (instance == null) {
            // mecanisme per garantir coherencia en entorns multifil
            synchronized(DriverMongoDB.class) {
                if (instance == null) {
                    instance = new DriverMongoDB();
                }
            }
        }

        return instance;
    }
    
    public MongoClient getConnection() throws AplicacioException
    {
        MongoClient ret = null;
        
        ret = ConnectarBD();
        
        return ret;
    }
    
    private DriverMongoDB() throws AplicacioException {
        this.ConnectarBD();
    }
    
        
    /**
    * Connecta a una BD mysql i gestiona la connexió
    *
    * @return objecte Connection
    * @throws MongoException 
    */
    private MongoClient ConnectarBD() throws AplicacioException
    { 
        try {
            MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
            conn = new MongoClient(connectionString);
        } 
        
        catch (MongoException ex) {
            throw new AplicacioException("Error ConnectarBD: " + ex.toString());
        }
       
        return conn;
    }
    
    public void closeConnection() throws AplicacioException
    {
        try {
            this.conn.close();
        } catch (MongoException ex) {
            throw new AplicacioException("Error inicialitzant connexió: " + ex.toString());
        }
    }
}