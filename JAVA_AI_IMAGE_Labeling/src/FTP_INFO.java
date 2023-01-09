
public class FTP_INFO {
    String Host;
    String Port;
    String name;
    String password;
    String Server;
 

    public FTP_INFO(String Host, String Port, String name, String password, String Server) {
    	this.Host = Host;
    	this.Port = Port;
    	this.name = name;
    	this.password = password;
    	this.Server = Server;
    }

    public String getHost() {
        return Host;
    }

    public String getPort() {
        return Port;
    }

    public String getname() {
        return name;
    }
    
    public String getpassword() {
        return password;
    }
    
    public String getServer() {
        return Server;
    }

    public void setName(String Host) {
        this.Host = Host;
    }

    public void setPort(String Port) {
        this.Port = Port;
    }
    
    public void setpassword(String password) {
        this.password = password;
    }

    public void setname(String name) {
        this.name = name;
    }
    
    public void setServer(String Server) {
    	this.Server = Server;
    }

 
}

