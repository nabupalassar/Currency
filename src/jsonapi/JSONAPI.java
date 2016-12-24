/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jsonapi.Const.ADDRESS_KEY;
import static jsonapi.Const.CURRENCY_KEY;
import static jsonapi.Const.PHONE_KEY;
import static jsonapi.Const.TITLE_KEY;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author student
 */
public class JSONAPI 
{


     public static void main(String[] args)
    {
        String jsonResult;
        
        jsonResult = NetworkUtils.get("http://resources.finance.ua/ua/public/currency-cash.json");
        System.out.println("resilt is - "+jsonResult);
        
        try
         {
            
            JSONObject object = new JSONObject(jsonResult);
            System.out.println("keys - "+JSONHelper.getKeys(object));;
            
            ArrayList<String> keys = JSONHelper.getKeys(object); //keys[6]="currencies"
            
            JSONObject currencyObject = object.getJSONObject(keys.get(6));
            System.out.println("currency key - "+JSONHelper.getKeys(currencyObject));  
            
            JSONArray organizations = object.getJSONArray(keys.get(5));
            System.out.println(organizations.get(8));
            
            HashMap<String,Object> resultMap = new HashMap<>();
            double minCurrency = Double.parseDouble(((JSONObject)  organizations.get(0))
                    .getJSONObject("currencies")
                    .getJSONObject("USD")
                    .getString("bid"));                     
            
            resultMap = JSONHelper.createMapFromJSON((JSONObject)organizations.get(0));
            
            for (int i=0; i<organizations.length(); i++ )
            {
                JSONObject org = (JSONObject) organizations.get(i);
                try{
                    System.out.println(org.getString("title"));

                    JSONObject curr = org.getJSONObject("currencies");
                    System.out.println("EUR - "+curr.getString("EUR"));
                    System.out.println("USD - "+curr.getString("USD"));
                    
                    String bidUSDCurrancy = curr.getJSONObject("USD").getString("bid");
                    if (Double.parseDouble(bidUSDCurrancy) < minCurrency )
                    {
                        resultMap = JSONHelper.createMapFromJSON(org);
                        minCurrency = Double.parseDouble(bidUSDCurrancy);  
                    }
                    System.out.println("================================");
                    
                }catch(JSONException e){
                    
                    System.out.println(org.getString("title")+ " error! ");
                    
                }
                
            } 
            
            System.out.println("min currency "+resultMap.toString());
            
         } catch (JSONException ex) {
             Logger.getLogger(JSONAPI.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
    
}
