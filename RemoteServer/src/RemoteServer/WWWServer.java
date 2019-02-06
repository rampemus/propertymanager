package RemoteServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controlserver.serverInterface;


public class WWWServer {

    private HttpServer server;

    public WWWServer(InetSocketAddress address, serverInterface rmi) throws IOException {
        //TODO: Create http-server instance and run it
        server = HttpServer.create(address,0);
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();


        server.createContext("/home", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {



                int[] states = new int[10];


                if (exchange.getRequestMethod().contains("POST")) {

                    InputStream inputStream = exchange.getRequestBody();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    String input = reader.readLine();

                    //this rmi-call will also ask from server about
                    //which one of the light switches are offline
                    try {
                        states = rmi.updateStates(parseInput(input));
                    } catch ( Exception e ) {
                        e.printStackTrace();
                    }

//                    System.out.println("Printing states that we got from server:");
//                    for ( int i = 0; i < 10; i++ ) {
//                        System.out.println("states[" + i + "]:" + states[i]);
//                    }

                } else {

                    System.out.println("GET");

                    states = rmi.receiveInitStates();

                }

                //Sending the page of updated state
                String lightForm = "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Light switch web app!</title>\n" +
                        "    <style>\n" +
                        "        td {\n" +
                        "            padding: 10px;\n" +
                        "        }\n"+
                        "        .disabledField {\n" +
                        "            color:grey;\n" +
                        "        }" +
                        "#temperature {\n" +
                        "            width:80px;\n" +
                        "        }" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<form action=\"/home\" method=\"post\">\n" +
                        "    Temperature <input id=\"temperature\" type=\"text\" name=\"temperature\" value=\"" + states[0] + "\"><input type=\"submit\" value=\"Submit\">\n" +
                        "<table>\n" +
                        "    <tr>\n" +
                        "        <td " + (states[1] == 2 ? "class=\"disabledField\"" : "") + "><input type=\"checkbox\" name=\"light1\" " + (states[1] == 1 ? "checked" : "") +  " onChange=\"this.form.submit()\" " + (states[1] == 2 ? "disabled" : "") + ">light1</td>\n" +
                        "        <td " + (states[2] == 2 ? "class=\"disabledField\"" : "") + "><input type=\"checkbox\" name=\"light2\" " + (states[2] == 1 ? "checked" : "") +  " onChange=\"this.form.submit()\" " + (states[2] == 2 ? "disabled" : "") + ">light2</td>\n" +
                        "        <td " + (states[3] == 2 ? "class=\"disabledField\"" : "") + "><input type=\"checkbox\" name=\"light3\" " + (states[3] == 1 ? "checked" : "") +  " onChange=\"this.form.submit()\" " + (states[3] == 2 ? "disabled" : "") + ">light3</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td " + (states[4] == 2 ? "class=\"disabledField\"" : "") + "><input type=\"checkbox\" name=\"light4\" " + (states[4] == 1 ? "checked" : "") +  " onChange=\"this.form.submit()\" " + (states[4] == 2 ? "disabled" : "") + ">light4</td>\n" +
                        "        <td " + (states[5] == 2 ? "class=\"disabledField\"" : "") + "><input type=\"checkbox\" name=\"light5\" " + (states[5] == 1 ? "checked" : "") +  " onChange=\"this.form.submit()\" " + (states[5] == 2 ? "disabled" : "") + ">light5</td>\n" +
                        "        <td " + (states[6] == 2 ? "class=\"disabledField\"" : "") + "><input type=\"checkbox\" name=\"light6\" " + (states[6] == 1 ? "checked" : "") +  " onChange=\"this.form.submit()\" " + (states[6] == 2 ? "disabled" : "") + ">light6</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td " + (states[7] == 2 ? "class=\"disabledField\"" : "") + "><input type=\"checkbox\" name=\"light7\" " + (states[7] == 1 ? "checked" : "") +  " onChange=\"this.form.submit()\" " + (states[7] == 2 ? "disabled" : "") + ">light7</td>\n" +
                        "        <td " + (states[8] == 2 ? "class=\"disabledField\"" : "") + "><input type=\"checkbox\" name=\"light8\" " + (states[8] == 1 ? "checked" : "") +  " onChange=\"this.form.submit()\" " + (states[8] == 2 ? "disabled" : "") + ">light8</td>\n" +
                        "        <td " + (states[9] == 2 ? "class=\"disabledField\"" : "") + "><input type=\"checkbox\" name=\"light9\" " + (states[9] == 1 ? "checked" : "") +  " onChange=\"this.form.submit()\" " + (states[9] == 2 ? "disabled" : "") + ">light9</td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "</form>\n" +
                        "</body>\n" +
                        "</html>\n";

                final byte[] out = lightForm.getBytes("UTF-8");
                exchange.sendResponseHeaders(200, out.length);

                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(out);
                outputStream.close();
            }
        });
    }

    /**
     * Searches from POST-line which switches are turned
     * @param input
     * @return int[] that has number 1 inserted if checkbox was selected
     */
    private static int[] parseInput(String input) {

        System.out.println("parseInput starts...");
        int[] result = new int[10];
        for ( int i = 1; i < 10; i++) {
            result[i] = (input.contains("light" + i + "=on") ? 1 : 0);
        }

        System.out.println("1");

        String temperature = "temperature=";

        int start = input.indexOf(temperature) + temperature.length();

        int end = input.length();
        if ( input.contains("&")) {
            end = input.indexOf("&");
        }
        System.out.println("Start "+ start + " and end " + end);

        /*
        There should be a proper way to parse these integers but
        I'm gonna leave this here...
         */
        try {
            result[0] = Integer.parseInt(input.substring(start, end));
            System.out.println("New temperature is " + result[0]);
        } catch ( NumberFormatException e ) {
            result[0] = Integer.MIN_VALUE;
        }

        return result;
    }
}
