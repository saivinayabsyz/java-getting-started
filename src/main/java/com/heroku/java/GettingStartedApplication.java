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
	   // System.out.println("metadataparams "+metadataparams+metadataparams.orgURL+metadataparams.accessToken);
        fetchMetadata(metadataparams.accessToken,metadataparams.orgURL,metadataparams.userID,metadataparams.fromDate,metadataparams.toDate);
        System.out.print("Hello!");  
      //  response.getWriter().write(hotel.accessToken);  
     
    }
     
    public void fetchMetadata(String sessionId, String endpoint, String userID, String fromDate, String toDate){
    ConnectorConfig metadataConfig = new ConnectorConfig();
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
		
	    	metadataComponents.add("CustomSite");
		metadataComponents.add("DuplicateRule");
		 metadataComponents.add("EmailServicesFunction");
	    	
    		
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

		 metadataComponents = new ArrayList<String>();
		lmqList = new ArrayList<ListMetadataQuery>();  
		metadataComponents.add("FlowDefinition");
		metadataComponents.add("Queue");
		metadataComponents.add("Community");
	    	
    		
    		for (String string : metadataComponents) {
    			 query = new ListMetadataQuery();
        		 query.setType(string);
        		 lmqList.add(query);        		 
			}    

		 lmr =  metadataConnection.listMetadata(
    		    Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
			  showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);

              metadataComponents = new ArrayList<String>();
		lmqList = new ArrayList<ListMetadataQuery>();  
		metadataComponents.add("EscalationRule");
		metadataComponents.add("ExperienceBundle");
		metadataComponents.add("InstalledPackage");
	    	
    		
    		for (String string : metadataComponents) {
    			 query = new ListMetadataQuery();
        		 query.setType(string);
        		 lmqList.add(query);        		 
			}    

		 lmr =  metadataConnection.listMetadata(
    		    Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
			  showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);

              metadataComponents = new ArrayList<String>();
              lmqList = new ArrayList<ListMetadataQuery>();  
              metadataComponents.add("ManagedContentTypeBundle");
              metadataComponents.add("ListView");
              metadataComponents.add("MatchingRule");
                  
                  
                  for (String string : metadataComponents) {
                       query = new ListMetadataQuery();
                       query.setType(string);
                       lmqList.add(query);        		 
                  }    
      
               lmr =  metadataConnection.listMetadata(
                      Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
                    showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);
		 

                    metadataComponents = new ArrayList<String>();
                    lmqList = new ArrayList<ListMetadataQuery>();  
                    metadataComponents.add("ManagedTopics");
                    metadataComponents.add("NavigationMenu");
                    metadataComponents.add("Network");
                        for (String string : metadataComponents) {
                             query = new ListMetadataQuery();
                             query.setType(string);
                             lmqList.add(query);        		 
                        }    
                     lmr =  metadataConnection.listMetadata(
                            Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
                          showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);

		  metadataComponents = new ArrayList<String>();
                    lmqList = new ArrayList<ListMetadataQuery>();  
                    metadataComponents.add("SharingRules");
                    metadataComponents.add("CustomTab");
                    metadataComponents.add("NetworkBranding");
                        for (String string : metadataComponents) {
                             query = new ListMetadataQuery();
                             query.setType(string);
                             lmqList.add(query);        		 
                        }    
                     lmr =  metadataConnection.listMetadata(
                            Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
                          showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);

		 metadataComponents = new ArrayList<String>();
                    lmqList = new ArrayList<ListMetadataQuery>();  
                    metadataComponents.add("ProfilePasswordPolicy");
                    metadataComponents.add("ProfileSessionSetting");
                    metadataComponents.add("NotificationTypeConfig");
                        for (String string : metadataComponents) {
                             query = new ListMetadataQuery();
                             query.setType(string);
                             lmqList.add(query);        		 
                        }    
                     lmr =  metadataConnection.listMetadata(
                            Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
                          showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);

		  metadataComponents = new ArrayList<String>();
                    lmqList = new ArrayList<ListMetadataQuery>();  
                    metadataComponents.add("RemoteSiteSetting");
                    metadataComponents.add("QuickAction");
                    metadataComponents.add("ReportType");
                        for (String string : metadataComponents) {
                             query = new ListMetadataQuery();
                             query.setType(string);
                             lmqList.add(query);        		 
                        }    
                     lmr =  metadataConnection.listMetadata(
                            Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
                          showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);

		  metadataComponents = new ArrayList<String>();
                    lmqList = new ArrayList<ListMetadataQuery>();  
                    metadataComponents.add("ModerationRule");
                    metadataComponents.add("SiteDotCom");
                    metadataComponents.add("EntitlementProcess");
                        for (String string : metadataComponents) {
                             query = new ListMetadataQuery();
                             query.setType(string);
                             lmqList.add(query);        		 
                        }    
                     lmr =  metadataConnection.listMetadata(
                            Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
                          showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);

		 metadataComponents = new ArrayList<String>();
                    lmqList = new ArrayList<ListMetadataQuery>();  
                    metadataComponents.add("FlexiPage");
                    metadataComponents.add("KeywordList");
                    metadataComponents.add("SharingCriteriaRule");
                        for (String string : metadataComponents) {
                             query = new ListMetadataQuery();
                             query.setType(string);
                             lmqList.add(query);        		 
                        }    
                     lmr =  metadataConnection.listMetadata(
                            Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length,ListMetadataQuery[].class), asOfVersion);
                          showMetaDataComponents(lmr,userID,fromDateValue,toDateValue);
		 
              if(assignmentRules!=null && assignmentRules.length()!=0)
              packageXMLString+="<types>\n"+assignmentRules+"<name>AssignmentRule</name>\n</types>\n";
              if(emailservices!=null && emailservices.length()!=0)
			 packageXMLString+="<types>\n"+emailservices+"<name>EmailServicesFunction</name>\n</types>\n";
		 if(audience!=null && audience.length()!=0)
			 packageXMLString+="<types>\n"+audience+"<name>Audience</name>\n</types>\n";
		 if(flows!=null && flows.length()!=0)
			 packageXMLString+="<types>\n"+flows+"<name>Flow</name>\n</types>\n";
		 if(flowDefinitions!=null && flowDefinitions.length()!=0)
			 packageXMLString+="<types>\n"+flowDefinitions+"<name>FlowDefinition</name>\n</types>\n";
		  if(queues!=null && queues.length()!=0)
			 packageXMLString+="<types>\n"+queues+"<name>Queue</name>\n</types>\n";
		 if(communities!=null && communities.length()!=0)
			 packageXMLString+="<types>\n"+communities+"<name>Community</name>\n</types>\n";
		 if(connectedApps!=null && connectedApps.length()!=0)
			 packageXMLString+="<types>\n"+connectedApps+"<name>ConnectedApp</name>\n</types>\n";
		  if(sites!=null && sites.length()!=0)
			 packageXMLString+="<types>\n"+sites+"<name>CustomSite</name>\n</types>\n";
		 if(duplicateRules!=null && duplicateRules.length()!=0)
			 packageXMLString+="<types>\n"+duplicateRules+"<name>DuplicateRule</name>\n</types>\n";
             if(escalationRules!=null && escalationRules.length()!=0)
			 packageXMLString+="<types>\n"+escalationRules+"<name>EscalationRules</name>\n</types>\n";
		  if(experiences!=null && experiences.length()!=0)
			 packageXMLString+="<types>\n"+experiences+"<name>ExperienceBundle</name>\n</types>\n";
		 if(installedPackages!=null && installedPackages.length()!=0)
			 packageXMLString+="<types>\n"+installedPackages+"<name>InstalledPackage</name>\n</types>\n";
             if(managedContentTypeBundles!=null && managedContentTypeBundles.length()!=0)
			 packageXMLString+="<types>\n"+managedContentTypeBundles+"<name>ManagedContentTypeBundle</name>\n</types>\n";
		  if(objectListViews!=null && objectListViews.length()!=0)
			 packageXMLString+="<types>\n"+objectListViews+"<name>ListView</name>\n</types>\n";
		 if(matchingRules!=null && matchingRules.length()!=0)
			 packageXMLString+="<types>\n"+matchingRules+"<name>MatchingRule</name>\n</types>\n";
             if(managedTopics!=null && managedTopics.length()!=0)
			 packageXMLString+="<types>\n"+managedTopics+"<name>ManagedTopics</name>\n</types>\n";
		  if(navigationMenus!=null && navigationMenus.length()!=0)
			 packageXMLString+="<types>\n"+navigationMenus+"<name>NavigationMenu</name>\n</types>\n";
		 if(networks!=null && networks.length()!=0)
			 packageXMLString+="<types>\n"+networks+"<name>Network</name>\n</types>\n";
		 if(customTab!=null && customTab.length()!=0)
			 packageXMLString+="<types>\n"+customTab+"<name>CustomTab</name>\n</types>\n";
		  if(sharingRules!=null && sharingRules.length()!=0)
			 packageXMLString+="<types>\n"+sharingRules+"<name>sharingRule</name>\n</types>\n";
		 if(networkBranding!=null && networkBranding.length()!=0)
			 packageXMLString+="<types>\n"+networkBranding+"<name>NetworkBranding</name>\n</types>\n";
		  if(profilePasswordPolicies!=null && profilePasswordPolicies.length()!=0)
			 packageXMLString+="<types>\n"+profilePasswordPolicies+"<name>ProfilePasswordPolicy</name>\n</types>\n";
		  if(profileSessionSettings!=null && profileSessionSettings.length()!=0)
			 packageXMLString+="<types>\n"+profileSessionSettings+"<name>ProfileSessionSetting</name>\n</types>\n";
		 if(notificationTypeConfig!=null && notificationTypeConfig.length()!=0)
			 packageXMLString+="<types>\n"+notificationTypeConfig+"<name>NotificationTypeConfig</name>\n</types>\n";
		 if(remoteSiteSettings!=null && remoteSiteSettings.length()!=0)
			 packageXMLString+="<types>\n"+remoteSiteSettings+"<name>RemoteSiteSetting</name>\n</types>\n";
		  if(quickActions!=null && quickActions.length()!=0)
			 packageXMLString+="<types>\n"+quickActions+"<name>QuickAction</name>\n</types>\n";
		 if(reportTypes!=null && reportTypes.length()!=0)
			 packageXMLString+="<types>\n"+reportTypes+"<name>ReportType</name>\n</types>\n";
		 if(ModerationRules!=null && ModerationRules.length()!=0)
			 packageXMLString+="<types>\n"+ModerationRules+"<name>ModerationRule</name>\n</types>\n";
		  if(siteDotComSites!=null && siteDotComSites.length()!=0)
			 packageXMLString+="<types>\n"+siteDotComSites+"<name>SiteDotCom</name>\n</types>\n";
		 if(entitlementProcesses!=null && entitlementProcesses.length()!=0)
			 packageXMLString+="<types>\n"+entitlementProcesses+"<name>EntitlementProcess</name>\n</types>\n";
		 if(flexipages!=null && flexipages.length()!=0)
			 packageXMLString+="<types>\n"+flexipages+"<name>FlexiPage</name>\n</types>\n";
		  if(moderation!=null && moderation.length()!=0)
			 packageXMLString+="<types>\n"+moderation+"<name>KeywordList</name>\n</types>\n";
		 if(SharingCriteriaRules!=null && SharingCriteriaRules.length()!=0)
			 packageXMLString+="<types>\n"+SharingCriteriaRules+"<name>SharingCriteriaRule</name>\n</types>\n";
		 
		insertPakageXML(userID,  fromDate,  toDate,  sessionId); 
		 packageXMLString = "";
		 csvRows="";
	customTab="";
        sharingRules = "";
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
		      int month = dj.getMonth()+1;
		      try{
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			      Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH)+"/"+month+"/"+yearValue);
			      System.out.println("fromDateValue "+fromDateValue+"toDateValue "+toDateValue+" actualDate "+actualDate+n);
			      if(  (actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) && 
			    (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
				 userID.equals(lastModifiedById)
				){
		//	System.out.println("fromDateValue "+fromDateValue+"toDateValue "+toDateValue+" actualDate "+actualDate+n.getFullName());
		       System.out.println("satisfied records "+n.getFullName()+" files "+n.getFileName());
				     if(n.getFileName().startsWith("userCriteria/")){
				      userCriterias+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"User Criteria\n";
				      }
				      else if(n.getFileName().startsWith("emailservices/")){
                        emailservices+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"EmailServicesFunction\n";
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
				      else if(n.getFileName().startsWith("duplicateRules/")){
				      duplicateRules+="<members>"+n.getFullName()+"</members>\n";
					      csvRows+=n.getFullName()+","+"DuplicateRule\n";
				      }
                      else if(n.getFileName().startsWith("escalationRules/")){
                        escalationRules+="<members>"+n.getFullName()+"</members>\n";
                       csvRows+=n.getFullName()+","+"EscalationRules\n";
                        }
                        else if(n.getFileName().startsWith("experiences/")){
                            experiences+="<members>"+n.getFullName()+"</members>\n";
                            csvRows+=n.getFullName()+","+"ExperienceBundle\n";
                        }
                        else if(n.getFileName().startsWith("installedPackages/")){
                            installedPackages+="<members>"+n.getFullName()+"</members>\n";
                            csvRows+=n.getFullName()+","+"InstalledPackage\n";
                        }
                        else if(n.getFileName().startsWith("managedContentTypeBundles/")){
                            managedContentTypeBundles+="<members>"+n.getFullName()+"</members>\n";
                           csvRows+=n.getFullName()+","+"ManagedContentTypeBundle\n";
                            }
                            else if(n.getFileName().startsWith("objects/")){
                                objectListViews+="<members>"+n.getFullName()+"</members>\n";
                                csvRows+=n.getFullName()+","+"ListView\n";
                            }
                            else if(n.getFileName().startsWith("matchingRules/")){
                                matchingRules+="<members>"+n.getFullName()+"</members>\n";
                                csvRows+=n.getFullName()+","+"MatchingRule\n";
                            }
                            else if(n.getFileName().startsWith("managedTopics/")){
                                managedTopics+="<members>"+n.getFullName()+"</members>\n";
                               csvRows+=n.getFullName()+","+"ManagedTopics\n";
                                }
                                else if(n.getFileName().startsWith("navigationMenus/")){
                                    navigationMenus+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"NavigationMenu\n";
                                }
                                else if(n.getFileName().startsWith("networks/")){
                                    networks+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"Network\n";
                                }
				       else if(n.getFileName().startsWith("tabs/")){
                                tabs+="<members>"+n.getFullName()+"</members>\n";
                               csvRows+=n.getFullName()+","+"Custom Tab\n";
                                }
                                else if(n.getFileName().startsWith("sharingRules/")){
                                    sharingRules+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"Sharing Rules\n";
                                }
                                else if(n.getFileName().startsWith("networkBranding/")){
                                    networkBranding+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"Network Branding\n";
                                }
				      else if(n.getFileName().startsWith("profilePasswordPolicies/")){
                                profilePasswordPolicies+="<members>"+n.getFullName()+"</members>\n";
                               csvRows+=n.getFullName()+","+"ProfilePasswordPolicy\n";
                                }
                                else if(n.getFileName().startsWith("profileSessionSettings/")){
                                    profileSessionSettings+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"Profile SessionSetting\n";
                                }
                                else if(n.getFileName().startsWith("notificationTypeConfig/")){
                                    notificationTypeConfig+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"NotificationTypeConfig\n";
                                }
				      else if(n.getFileName().startsWith("remoteSiteSettings/")){
                                remoteSiteSettings+="<members>"+n.getFullName()+"</members>\n";
                               csvRows+=n.getFullName()+","+"RemoteSiteSetting\n";
                                }
                                else if(n.getFileName().startsWith("quickActions/")){
                                    quickActions+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"QuickAction\n";
                                }
                                else if(n.getFileName().startsWith("reportTypes/")){
                                    reportTypes+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"ReportType\n";
                                }
				        else if(n.getFileName().startsWith("moderation/")){
                                ModerationRules+="<members>"+n.getFullName()+"</members>\n";
                               csvRows+=n.getFullName()+","+"ModerationRule\n";
                                }
                                else if(n.getFileName().startsWith("siteDotComSites/")){
                                    siteDotComSites+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"SiteDotCom\n";
                                }
                                else if(n.getFileName().startsWith("entitlementProcesses/")){
                                    entitlementProcesses+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"EntitlementProcess\n";
                                }
				      else if(n.getFileName().startsWith("flexipages/")){
                                flexipages+="<members>"+n.getFullName()+"</members>\n";
                               csvRows+=n.getFullName()+","+"FlexiPage\n";
                                }
                                else if(n.getFileName().startsWith("sharingRules/")){
                                    SharingCriteriaRules+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"SharingCriteriaRule\n";
                                }
				      else if(n.getFileName().startsWith("moderation/")){
                                    moderation+="<members>"+n.getFullName()+"</members>\n";
                                    csvRows+=n.getFullName()+","+"KeywordList\n";
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
packageXMLRecord.put("CSV_String__c", csvRows);
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

