
public class RMIServer {

public RMIServer (int RMIport) {
    //Constructor
    //You will need Security manager to make RMI work
    //Remember to add security.policy to your run time VM options
    //-Djava.security.policy=[YOUR PATH HERE]\server.policy
    if (System.getSecurityManager() == null) {
        System.setSecurityManager(new SecurityManager());
    }

}

}

