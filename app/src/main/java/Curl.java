import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Curl 
{
    private HttpURLConnection connection;
    private String content;

    public Curl(String serverIp, String method, String content)
    {
        try { 
            URL url = new URL(serverIp);
            this.content = content;
            this.connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("Content-Length",
                    Integer.toString(content.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");  

            connection.setUseCaches(false);
            connection.setDoOutput(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String send()
    {
        try {
            System.out.println("Sending:  " + this.content);
            //Send request
            DataOutputStream wr = new DataOutputStream (
                connection.getOutputStream());
            wr.writeBytes(this.content);
            wr.close();

            //Get Response  
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
            String line;

            while((line = rd.readLine()) != null) 
            {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args)
    {
        /*
         * e.g.
         * arg[0] = "/users"
         * arg[1] = "POST"
         * arg[2] = '{"firstname":"Jimmy","lastname":"hello","facebookid":"12fdggdkds","password":"Bennet","email":"squiggle","name":"blagger"}'
         */
        Curl curler = new Curl("http://52.16.78.184/api" + args[0], args[1], args[2]);
        System.out.println(curler.send());
    }
}
