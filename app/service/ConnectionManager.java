package service;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import java.net.InetSocketAddress;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class ConnectionManager {
    
    private static ConnectionManager ref;
    private static PartnerConnection connection;
    private static ConnectorConfig config;
    private static final String username = "username";
    private static final String password = "password";
    private static final String SESSION_KEY = "sessionKey";
    private static final String SESSION_VALUE = "sessionValue";
    
    private ConnectionManager() { }

    public static ConnectionManager getConnectionManager() {
        if (ref == null)
            ref = new ConnectionManager();
        return ref;
    }
    
    public PartnerConnection getConnection() {
        
        try {
            
            config = new ConnectorConfig();
            config.setUsername(username);
            config.setPassword(password);
            connection = Connector.newConnection(config);
            
        } catch ( ConnectionException ce) {
            System.out.println("ConnectionException " +ce.getMessage());
        }
        
        return connection;
    }

}