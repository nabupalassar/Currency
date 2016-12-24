package jsonapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


class NetworkUtils {

    public static String get(String address)
    {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            //преобразовываем адрес в урл
            URL url = new URL(address);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                
                connection.setRequestProperty("User-Agent", "Mozila/5.0");
                
                int requestCode = connection.getResponseCode();
                
                System.out.println("request - "+requestCode);
                
                if(requestCode != 200)
                {
                    return "Bad request";
                }
                
                InputStreamReader is = new InputStreamReader(connection.getInputStream());
                BufferedReader in = new BufferedReader(is);
                
                String line;
                while ((line = in.readLine())!=null)
                {
                    stringBuffer.append(line);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(NetworkUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(NetworkUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stringBuffer.toString();
    }
    
}
