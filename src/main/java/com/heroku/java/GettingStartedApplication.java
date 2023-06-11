package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import com.heroku.java.AuthParams; 
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;


import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import com.sforce.soap.metadata.*;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileReader;
import java.io.IOException;
@SpringBootApplication
@Controller
public class GettingStartedApplication {
    private final DataSource dataSource;

    @Autowired
    public GettingStartedApplication(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/triggerxmlgeneration",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createHotel(@RequestBody AuthParams metadataparams) {
	    System.out.print("Hello!");  
	    System.out.println("Hello, logs!");
	    System.out.println("metadataparams "+metadataparams+metadataparams.orgURL);
        fetchMetadata(metadataparams.accessToken,metadataparams.orgURL,metadataparams.userID,metadataparams.fromDate,metadataparams.toDate);
        System.out.print("Hello!");  
      //  response.getWriter().write(hotel.accessToken);  
     
    }
     
    public void fetchMetadata(String sessionId, String endpoint, String userID, String fromDate, String toDate){
	  
 System.out.println(fromDate+"  "); 
  //  ConnectorConfig partnerConfig = new ConnectorConfig();
    ConnectorConfig metadataConfig = new ConnectorConfig();
    
    
  //  partnerConfig.setUsername(USERNAME);
   // partnerConfig.setPassword(PASSWORD);

   // @SuppressWarnings("unused")
  //  PartnerConnection partnerConnection = com.sforce.soap.partner.Connector.newConnection(partnerConfig);
    
   // String metaurl = partnerConfig.getServiceEndpoint();

    //The metadata service endpoint is baked in the wsdl file, but same metadata wsdl file can be used for differnent orgs,
    //only metadata service endpoint has to be set according to the org
    //metaurl = metaurl.replace("Soap/u", "Soap/m");
   // System.out.println(metaurl);
    metadataConfig.setServiceEndpoint(endpoint);
    
    
    // shove the partner's session id into the metadata configuration then connect
    metadataConfig.setSessionId(sessionId);
   System.out.println("sessionid"+sessionId);
    
    try {
	     MetadataConnection metadataConnection = com.sforce.soap.metadata.Connector.newConnection(metadataConfig);
    	 try {
    		List<String> metadataComponents = new ArrayList<String>();
	    	metadataComponents.add("CustomObject");	    	
	    	
    		List<ListMetadataQuery> lmqList = new ArrayList<ListMetadataQuery>();    		 
    		for (String string : metadataComponents) {
    			 ListMetadataQuery query = new ListMetadataQuery();
        		 query.setType(string);
        		 lmqList.add(query);        		 
			}    		     		    		    
    		 
    		 double asOfVersion = 27.0;
    		 // Assuming that the SOAP binding has already been established.    		    
    		 FileProperties[] lmr = metadataConnection.listMetadata(
    		    Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
		String[] arrOfFromDate  = fromDate.split("-");
		 String[] arrOfToDate  = toDate.split("-");
    		showMetaDataComponents(lmr,userID,arrOfFromDate[0],arrOfFromDate[1],arrOfFromDate[2],arrOfToDate[0],arrOfToDate[1],arrOfToDate[2]);
    		} catch (ConnectionException ce) {
    		 	ce.printStackTrace();
    	 	}
    		  
    }
    catch (Exception ex) {
        System.out.println("\n Error: \n" +ex.getMessage());
    }  
    }
     public static void showMetaDataComponents(FileProperties[] lmr,String userID, String fromYear,String fromDate,String fromMonth,String toDateYear,String toDate,String toDateMonth ){
	  if (lmr != null) {
	      for (FileProperties n : lmr) {
		     
		     // if(n.getLastModifiedDate() >=fromDateParsed && n.getLastModifiedDate() <=toDateParsed && n.getLastModifiedById()== userID)
		      Date dj = n.getLastModifiedDate().getTime();
		     
		       if(dj.getYear()+1900 == 2023){
		        System.out.println("dj.getYear()"+dj.getYear()+1900);
		      System.out.println("dj.getMonth()"+dj.getMonth());
			        System.out.println("dj.getMonth()"+dj.getDay());
			     //  System.out.println(n.getType() +" : " + n.getFullName()+" : "+n.getLastModifiedDate()+dj +n.getLastModifiedDate().getTime());   
		       }
		      int yearValue = dj.getYear()+1900;
		       System.out.println("yearValue "+yearValue);
		      if( Integer.parseInt(fromYear) >= yearValue && Integer.parseInt(fromDate) >= dj.getDay() && Integer.parseInt(fromMonth) >= dj.getMonth()
			&& Integer.parseInt(toDateYear) <= yearValue && Integer.parseInt(toDate) <= dj.getDay() && Integer.parseInt(toDateMonth) <= dj.getMonth()){
		       System.out.println("satisfied records "+n.getFullName());
		      }
	    		 		          		    		
	    	}
	  }	  
  }
    @GetMapping("/database")
    String database(Map<String, Object> model) {
        try (Connection connection = dataSource.getConnection()) {
            final var statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
            statement.executeUpdate("INSERT INTO ticks VALUES (now())");

            final var resultSet = statement.executeQuery("SELECT tick FROM ticks");
            final var output = new ArrayList<>();
            while (resultSet.next()) {
                output.add("Read from DB: " + resultSet.getTimestamp("tick"));
            }

            model.put("records", output);
            return "database";

        } catch (Throwable t) {
            model.put("message", t.getMessage());
            return "error";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(GettingStartedApplication.class, args);
    }
}
