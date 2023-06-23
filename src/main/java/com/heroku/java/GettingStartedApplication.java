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
import java.text.SimpleDateFormat;
import java.text.ParseException;

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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import org.json.JSONException;
import org.json.JSONObject;

@SpringBootApplication
@Controller
public class GettingStartedApplication {
    private final DataSource dataSource;
    public  String  packageXMLString="";
	public  String  csvRows="";
	public  String customTab="";
        public String sharingRules = "";
        public String apexEmailNotifications = "";
        public String assignmentRules = "";
        public String audience="";
        public String flows="";
        public String flowDefinitions="";
        public String queues="";
        public String communities="";
        public String connectedApps="";
        public String sites="";
        public String  duplicateRules="";
        public String  emailservices="";
        public String    escalationRules="";
        public String    experiences="";
        public String    installedPackages="";
        public String  externalServiceRegistrations="";
        public String  objectListViews="";
        public String  matchingRules="";
        public String  managedContentTypeBundles="";
        public String  managedTopics="";
        public String  navigationMenus="";
        public String  networks="";
        public String  networkBranding="";
        public String  profilePasswordPolicies="";
        public String  profileSessionSettings="";
        public String  notificationTypeConfig="";
        public String  remoteSiteSettings="";
        public String  quickActions=""; 
        public String   reportTypes="";
        public String   ModerationRules="";
        public String   topicsForObjects=""; 
        public String   siteDotComSites=""; 
        public String entitlementProcesses = "";
        public String flexipages = "";
        public String moderation = "";
        public String SharingCriteriaRules = "";
        public String milestoneTypes = "";
        public String CanvasMetadatas = "";
        public String AutoResponseRules = "";
        public String AnalyticSnapshots = "";
        public String approvalProcesses = "";
        public String userCriterias = "";
        public String permissionsets = "";
        public String corsWhitelistOrigins = "";
        public String customPermissions = "";
        public String globalValueSets = "";
        public String groups = "";
        public String sharingSets = "";
        public String layouts = "";
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
	    String[] arrOfFromDate  = fromDate.split("-");
		 String[] arrOfToDate  = toDate.split("-");
	    Date fromDateValue =new Date();  
	    Date toDateValue=new Date();
		  try{
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	fromDateValue= formatter.parse(arrOfFromDate[1]+"/"+arrOfFromDate[2]+"/"+arrOfFromDate[0]);
toDateValue = formatter.parse(arrOfToDate[1]+"/"+arrOfToDate[2]+"/"+arrOfToDate[0]);
	 }
		 catch (ParseException e) {e.printStackTrace();}
    
    try {
	     MetadataConnection metadataConnection = com.sforce.soap.metadata.Connector.newConnection(metadataConfig);
    	 try {
		  double asOfVersion = 58.0;
		  ListMetadataQuery query ;
    		List<String> metadataComponents = new ArrayList<String>();
		 List<ListMetadataQuery> lmqList = new ArrayList<ListMetadataQuery>();  
		
	    	/*metadataComponents.add("CustomTab");
		metadataComponents.add("SharingRules");
		 metadataComponents.add("ApexEmailNotifications");
	    	
    		
    		for (String string : metadataComponents) {
    			 query = new ListMetadataQuery();
        		 query.setType(string);
        		 lmqList.add(query);        		 
			}    		     		    		    
    		 
    		 FileProperties[] lmr = metadataConnection.listMetadata(
    		    Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
			  showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);
		metadataComponents = new ArrayList<String>();
		lmqList = new ArrayList<ListMetadataQuery>();  
		
	    	metadataComponents.add("AssignmentRule");
		metadataComponents.add("Audience");
		 metadataComponents.add("Flow");
	    	
    		
    		for (String string : metadataComponents) {
    			 query = new ListMetadataQuery();
        		 query.setType(string);
        		 lmqList.add(query);        		 
			}    

		 lmr = metadataConnection.listMetadata(
    		    Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
			  showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);
*/
		 metadataComponents = new ArrayList<String>();
		lmqList = new ArrayList<ListMetadataQuery>();  
		 metadataComponents.add("Flow");
	    	metadataComponents.add("FlowDefinition");
		//metadataComponents.add("Queue");
		 //metadataComponents.add("Community");
	    	
    		
    		for (String string : metadataComponents) {
    			 query = new ListMetadataQuery();
        		 query.setType(string);
        		 lmqList.add(query);        		 
			}    

		  FileProperties[] lmr = = metadataConnection.listMetadata(
    		    Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
			  showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);
		 
		 if(customTab!=null && customTab.length()!=0)
			 packageXMLString+="<types>\n"+customTab+"<name>CustomTab</name>\n</types>\n";
		 if(sharingRules!=null && sharingRules.length()!=0)
			 packageXMLString+="<types>\n"+sharingRules+"<name>sharingRules</name>\n</types>\n";
		 if(apexEmailNotifications!=null && apexEmailNotifications.length()!=0)
			 packageXMLString+="<types>\n"+apexEmailNotifications+"<name>ApexEmailNotifications</name>\n</types>\n";
		 if(audience!=null && audience.length()!=0)
			 packageXMLString+="<types>\n"+audience+"<name>Audience</name>\n</types>\n";
		 if(flows!=null && flows.length()!=0)
			 packageXMLString+="<types>\n"+flows+"<name>Flow</name>\n</types>\n";
		 if(flowDefinitions!=null && flowDefinitions.length()!=0)
			 packageXMLString+="<types>\n"+flowDefinitions+"<name>FlowDefinition</name>\n</types>\n";
		  if(queues!=null && queues.length()!=0)
			 packageXMLString+="<types>\n"+queues+"<name>Queue</name>\n</types>\n";
	
		insertPakageXML(userID,  fromDate,  toDate,  sessionId); 
		 packageXMLString = "";
		 csvRows="";
	customTab="";
        sharingRules = "";
        apexEmailNotifications = "";
        assignmentRules = "";
        audience="";
        flows="";
        flowDefinitions="";
        queues="";
        communities="";
        connectedApps="";
        sites="";
        duplicateRules="";
        emailservices="";
          escalationRules="";
          experiences="";
          installedPackages="";
        externalServiceRegistrations="";
        objectListViews="";
        matchingRules="";
        managedContentTypeBundles="";
        managedTopics="";
        navigationMenus="";
        networks="";
        networkBranding="";
        profilePasswordPolicies="";
        profileSessionSettings="";
        notificationTypeConfig="";
        remoteSiteSettings="";
        quickActions=""; 
         reportTypes="";
         ModerationRules="";
         topicsForObjects=""; 
         siteDotComSites=""; 
        entitlementProcesses = "";
        flexipages = "";
        moderation = "";
        SharingCriteriaRules = "";
        milestoneTypes = "";
        CanvasMetadatas = "";
        AutoResponseRules = "";
        AnalyticSnapshots = "";
        approvalProcesses = "";
        userCriterias = "";
        permissionsets = "";
        corsWhitelistOrigins = "";
        customPermissions = "";
        globalValueSets = "";
        groups = "";
        sharingSets = "";
        layouts = "";
    		
    		} catch (ConnectionException ce) {
    		 	ce.printStackTrace();
    	 	}
    		  
    }
    catch (Exception ex) {
        System.out.println("\n Error: \n" +ex.getMessage());
    }  
    }
	
     public  void showMetaDataComponents(FileProperties[] lmr,String userID, Date fromDateValue,Date toDateValue){
	  if (lmr != null) {
	      for (FileProperties n : lmr) {
		      Date dj = n.getLastModifiedDate().getTime();
		       String lastModifiedById = n.getLastModifiedById();
		      int yearValue = dj.getYear()+1900;
		      try{
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			      Date actualDate = formatter.parse(dj.getDay()+"/"+dj.getMonth()+"/"+yearValue);
			      System.out.println("fromDateValue "+fromDateValue+"toDateValue "+toDateValue+" actualDate "+actualDate+n.getFullName());
			      if(  (actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) && 
			    (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
				 userID.equals(lastModifiedById)
				){
		//	System.out.println("fromDateValue "+fromDateValue+"toDateValue "+toDateValue+" actualDate "+actualDate+n.getFullName());
		       System.out.println("satisfied records "+n.getFullName()+" files "+n.getFileName());
				      if(n.getFileName().startsWith("tabs/")){
				      customTab+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"Custom Tab\n";
				      }
				      else if(n.getFileName().startsWith("sharingRules/")){
				      sharingRules+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"sharing Rules\n";
				      }
				      else if(n.getFileName().startsWith("userCriteria/")){
				      userCriterias+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"User Criteria\n";
				      }
				      else if(n.getFileName().startsWith("apexEmailNotifications/")){
				      apexEmailNotifications+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"Apex Email Notifications\n";
				      }
				      else if(n.getFileName().startsWith("assignmentRules/")){
				      assignmentRules+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"AssignmentRule\n";
				      }
				      else if(n.getFileName().startsWith("audience/")){
				      audience+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"Audience\n";
				      }
				      else if(n.getFileName().startsWith("flows/")){
				      flows+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"flows\n";
				      }
				      else if(n.getFileName().startsWith("flowDefinitions/")){
				      flowDefinitions+="<members>"+n.getFullName()+"</members>\n";
				     csvRows+=n.getFullName()+","+"FlowDefinition\n";
				      }
				      else if(n.getFileName().startsWith("queues/")){
				      queues+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"Queue\n";
				      }
				      else if(n.getFileName().startsWith("communities/")){
				      communities+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"Community\n";
				      }
				      else if(n.getFileName().startsWith("connectedApps/")){
				      connectedApps+="<members>"+n.getFullName()+"</members>\n";
				     csvRows+=n.getFullName()+","+"ConnectedApp\n";
				      }
				      else if(n.getFileName().startsWith("sites/")){
				      sites+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"CustomSite\n";
				      }
		  }

}
catch (ParseException e) {e.printStackTrace();}

		      
	    		 		          		    		
	    	}
	  }	  
  }
	/* Method to insert package xml */
public void insertPakageXML(String userID, String fromDate, String toDate, String access_token) {
String uri =  "https://vinaypd2-dev-ed.my.salesforce.com/services/data/v56.0/sobjects/Package_XML__c/";
try {
Header oauthHeader = new BasicHeader("Authorization", "OAuth " + access_token);
JSONObject packageXMLRecord = new JSONObject();
packageXMLRecord.put("xml_string__c", packageXMLString);
packageXMLRecord.put("userid__c", userID);
	packageXMLRecord.put("from_date__c", fromDate);
	packageXMLRecord.put("to_date__c", toDate);
DefaultHttpClient httpClient = new DefaultHttpClient();
HttpPost httpPost = new HttpPost(uri);
httpPost.addHeader(oauthHeader);
StringEntity body = new StringEntity(packageXMLRecord.toString(1));
body.setContentType("application/json");
httpPost.setEntity(body);


HttpResponse response = httpClient.execute(httpPost);


int statusCode = response.getStatusLine().getStatusCode();
if (statusCode == 201) {
String response_string = EntityUtils.toString(response.getEntity());
JSONObject json = new JSONObject(response_string);
System.out.println("New packagexml id from response: " + json.getString("id"));      
} else {
System.out.println("Insertion unsuccessful. Status code returned is " + statusCode);
}
} catch (JSONException e) {
System.out.println("Issue creating JSON or processing results");
e.printStackTrace();
} catch (IOException ioe) {
ioe.printStackTrace();
} catch (NullPointerException npe) {
npe.printStackTrace();
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
