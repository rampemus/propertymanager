
public class RMIClient {

    remoteInterface comp;

    public RMIClient() {
        //Security manager is needed. Remember policy file and VM parameter again.
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        //TODO: RMI client connection

    }

   //TODO: Create needed requests to control server


}


