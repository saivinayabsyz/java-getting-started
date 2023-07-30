package com.heroku.java;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import com.heroku.java.AuthParams;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.*;
import java.nio.*;
import java.io.FileOutputStream;  
import java.nio.channels.WritableByteChannel;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import java.nio.channels.ReadableByteChannel;

import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.nio.channels.FileChannel;
import java.nio.channels.*;

import com.sforce.soap.metadata.*;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.soap.metadata.PackageTypeMembers;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileReader;
import java.io.IOException;
// importing the File class
import java.io.File;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayInputStream;

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
import com.sforce.soap.metadata.RetrieveRequest;
import com.sforce.soap.metadata.RetrieveResult;
import com.sforce.soap.metadata.MetadataConnection;


@SpringBootApplication
@Controller
public class GettingStartedApplication {
  private final DataSource dataSource;
  public String packageXMLString = "";
  public String csvRows = "";
  public String customTab = "";
  public String sharingRules = "";
  public String apexEmailNotifications = "";
  public String assignmentRules = "";
  public String audience = "";
  public String flows = "";
  public String flowDefinitions = "";
  public String queues = "";
  public String communities = "";
  public String connectedApps = "";
  public String sites = "";
  public String duplicateRules = "";
  public String emailservices = "";
  public String escalationRules = "";
  public String experiences = "";
  public String installedPackages = "";
  public String objectListViews = "";
  public String matchingRules = "";
  public String matchingRuleObject = "";
  public String managedContentTypeBundles = "";
  public String managedTopics = "";
  public String navigationMenus = "";
  public String networks = "";
  public String networkBranding = "";
  public String profilePasswordPolicies = "";
  public String profileSessionSettings = "";
  public String notificationTypeConfig = "";
  public String remoteSiteSettings = "";
  public String quickActions = "";
  public String reportTypes = "";
  public String ModerationRules = "";
  public String siteDotComSites = "";
  public String entitlementProcesses = "";
  public String flexipages = "";
  public String SharingCriteriaRules = "";
  public String milestoneTypes = "";
  public String CanvasMetadatas = "";
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
  public String pages = "";
  public String classes = "";
  public String labels = "";
  public String profiles = "";
  public String triggers = "";
  public String staticresources = "";
  public String aura = "";
  public String dataSources = "";
  public String lwc = "";
  public String restrictionRules = "";
  public String permissionsetgroups = "";
  public String namedCredentials = "";
  public String notificationtypes = "";
  public String components = "";
  public String homePageLayouts = "";
  public String workflows = "";
  public String autoResponseRules = "";
  public String workflowRules = "";
  public String workflowFieldUpdates = "";
  public String fieldSets = "";
  public String sharingCriteriaRules = "";
  public String escalationRuleObject = "";
  public String assignmentRulesObject = "";
  public String workflowOutBoundMessages = "";
  public String validationRules = "";
  public String recordTypes = "";
  public String externalServiceRegistrations = "";
  public String managedContentTypes = "";
  public String platformEventChannelMembers = "";
  public String cleanDataServices = "";
  public String samlssoconfigs = "";
  public String cspTrustedSites = "";
  public Set<String> workflowSet = new HashSet<String>();
  public Boolean includePackaged=false;
  public List<PackageTypeMembers> pd = new ArrayList<PackageTypeMembers>();
  public static final double API_VERSION = 42.0; 
 
  
public static void main(String[] args) {
    SpringApplication.run(GettingStartedApplication.class, args);
  }
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
    consumes = {
      "application/json",
      "application/xml"
    },
    produces = {
      "application/json",
      "application/xml"
    })
  @ResponseStatus(HttpStatus.CREATED)
  public void createHotel(@RequestBody AuthParams metadataparams) {
    System.out.println("metadataparams "+metadataparams+metadataparams.orgURL+metadataparams.accessToken+metadataparams.packageXMLAccessToken+metadataparams.includePackaged);
   includePackaged = metadataparams.includePackaged=="true"?true:false;  
    fetchMetadata(metadataparams.accessToken, metadataparams.orgURL, metadataparams.userID, metadataparams.fromDate, metadataparams.toDate,metadataparams.packageXMLAccessToken);
  }

  public void fetchMetadata(String sessionId, String endpoint, String userID, String fromDate, String toDate, String packageXMLAccessToken) {
    ConnectorConfig metadataConfig = new ConnectorConfig();
    metadataConfig.setServiceEndpoint(endpoint);
    // shove the partner's session id into the metadata configuration then connect
    metadataConfig.setSessionId(sessionId);
    String[] arrOfFromDate = fromDate.split("-");
    String[] arrOfToDate = toDate.split("-");
    Date fromDateValue = new Date();
    Date toDateValue = new Date();
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      fromDateValue = formatter.parse(arrOfFromDate[1] + "/" + arrOfFromDate[2] + "/" + arrOfFromDate[0]);
      toDateValue = formatter.parse(arrOfToDate[1] + "/" + arrOfToDate[2] + "/" + arrOfToDate[0]);
    } catch (ParseException e) {
      e.printStackTrace();
      System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
    }

    try {
      MetadataConnection metadataConnection = com.sforce.soap.metadata.Connector.newConnection(metadataConfig);
      try {
        double asOfVersion = 58.0;
        ListMetadataQuery query;
        List < String > metadataComponents = new ArrayList < String > ();
        List < ListMetadataQuery > lmqList = new ArrayList < ListMetadataQuery > ();

        metadataComponents.add("CustomSite");
        metadataComponents.add("DuplicateRule");
        metadataComponents.add("EmailServicesFunction");

        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
FileProperties[] lmr;
        try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
          System.out.println("sites "+sites);  
           System.out.println("duplicateRules "+duplicateRules);  
           System.out.println("emailservices "+emailservices);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  
        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();

        metadataComponents.add("AssignmentRule");
        metadataComponents.add("Audience");
        metadataComponents.add("Flow");

        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }

        try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
           System.out.println("assignmentRules "+assignmentRules);  
          System.out.println("audience "+audience);  
          System.out.println("flows "+flows);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("FlowDefinition");
        metadataComponents.add("Queue");
        metadataComponents.add("Community");

        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }

        try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
           System.out.println("flowDefinitions "+flowDefinitions);  
          System.out.println("queues "+queues);  
          System.out.println("communities "+communities);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("EscalationRule");
        metadataComponents.add("ExperienceBundle");
        metadataComponents.add("InstalledPackage");

        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }

           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
               System.out.println("escalationRules "+escalationRules);  
          System.out.println("experiences "+experiences);  
          System.out.println("installedPackages "+installedPackages);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("ManagedContentTypeBundle");
        metadataComponents.add("ListView");
        metadataComponents.add("MatchingRule");

        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }

           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
             System.out.println("managedContentTypeBundles "+managedContentTypeBundles);  
          System.out.println("objectListViews "+objectListViews);  
          System.out.println("matchingRules "+matchingRules);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  


        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("ManagedTopics");
        metadataComponents.add("NavigationMenu");
        metadataComponents.add("Network");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
             System.out.println("managedTopics "+managedTopics);  
          System.out.println("navigationMenus "+navigationMenus);  
          System.out.println("networks "+networks);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("SharingRules");
        metadataComponents.add("CustomTab");
        metadataComponents.add("NetworkBranding");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
               System.out.println("sharingRules "+sharingRules);  
          System.out.println("customTab "+customTab);  
          System.out.println("networkBranding "+networkBranding);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("ProfilePasswordPolicy");
        metadataComponents.add("ProfileSessionSetting");
        metadataComponents.add("NotificationTypeConfig");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
              System.out.println("profilePasswordPolicies "+profilePasswordPolicies);  
          System.out.println("profileSessionSettings "+profileSessionSettings);  
          System.out.println("notificationTypeConfig "+notificationTypeConfig);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  


        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("RemoteSiteSetting");
        metadataComponents.add("QuickAction");
        metadataComponents.add("ReportType");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
              System.out.println("remoteSiteSettings "+remoteSiteSettings);  
          System.out.println("quickActions "+quickActions);  
          System.out.println("reportTypes "+reportTypes);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  


        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("ModerationRule");
        metadataComponents.add("SiteDotCom");
          metadataComponents.add("ConnectedApp");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
             System.out.println("ModerationRules "+ModerationRules);  
          System.out.println("siteDotComSites "+siteDotComSites);  
        
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

         metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("EntitlementProcess");
        
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
             System.out.println("entitlementProcesses "+entitlementProcesses);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  


        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("FlexiPage");
        metadataComponents.add("SamlSsoConfig");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
          try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
              System.out.println("flexipages "+flexipages);  
          System.out.println("SharingCriteriaRules "+SharingCriteriaRules);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("PermissionSet");
        metadataComponents.add("Group");
        metadataComponents.add("SharingSet");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
          try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
             System.out.println("permissionsets "+permissionsets);  
          System.out.println("groups "+groups);  
            System.out.println("sharingSets "+sharingSets);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  


        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("Layout");
        metadataComponents.add("ApexPage");
        metadataComponents.add("ApexComponent");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
              System.out.println("layouts "+layouts);  
          System.out.println("pages "+pages);  
            System.out.println("components "+components);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  


        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("ApexTrigger");
        metadataComponents.add("StaticResource");
        metadataComponents.add("AuraDefinitionBundle");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
                System.out.println("staticresources "+staticresources);  
          System.out.println("triggers "+triggers);  
            System.out.println("aura "+aura);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("ApexClass");
        metadataComponents.add("CustomLabel");
        metadataComponents.add("Profile");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
           try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
                System.out.println("classes "+classes);  
          System.out.println("labels "+labels);  
            System.out.println("profiles "+profiles);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("ExternalDataSource");
        metadataComponents.add("LightningComponentBundle");
        metadataComponents.add("RestrictionRule");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
          try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
          System.out.println("dataSources "+dataSources);  
          System.out.println("lwc "+lwc);  
            System.out.println("restrictionRules "+restrictionRules);    
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("CustomNotificationType");
        metadataComponents.add("PermissionSetGroup");
        metadataComponents.add("WorkflowFieldUpdate");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
          try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
             System.out.println("notificationtypes "+notificationtypes);  
          System.out.println("permissionsetgroups "+permissionsetgroups);  
            System.out.println("workflowFieldUpdates "+workflowFieldUpdates);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  


        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("AutoResponseRule");
        metadataComponents.add("HomePageLayout");
        metadataComponents.add("CspTrustedSite");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
          try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showMetaDataComponents(lmr, userID, fromDateValue, toDateValue);
             System.out.println("autoResponseRules "+autoResponseRules);  
          System.out.println("workflows "+workflows);  
            System.out.println("homePageLayouts "+homePageLayouts);  
}
           catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  


        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("WorkflowRule");
        metadataComponents.add("NamedCredential");
        metadataComponents.add("SharingCriteriaRule");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
        try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showWorkFlowComponents(lmr, userID, fromDateValue, toDateValue);
          System.out.println("workflowRules "+workflowRules); 
          System.out.println("namedCredentials "+namedCredentials); 
           System.out.println("SharingCriteriaRule "+sharingCriteriaRules); 
        }
          catch(Exception e)  
        { 
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("WorkflowFieldUpdate");
          metadataComponents.add("MatchingRules");
          metadataComponents.add("FieldSet");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
        try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showWorkFlowFieldComponents(lmr, userID, fromDateValue, toDateValue);
           System.out.println("workflowFieldUpdates "+workflowFieldUpdates); 
          System.out.println("matchingRuleObject "+matchingRuleObject); 
          System.out.println("fieldSets "+fieldSets); 
        }
          catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("EscalationRules");
        metadataComponents.add("AssignmentRules");
        metadataComponents.add("WorkflowOutboundMessage");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
        try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showEscalationRulesComponents(lmr, userID, fromDateValue, toDateValue);
        }
          catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  

          metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("ValidationRule");
        metadataComponents.add("PlatformEventChannelMember");
        metadataComponents.add("CleanDataService");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
         try{
        
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showValidationRulesComponents(lmr, userID, fromDateValue, toDateValue);
}
          catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        } 

         metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("RecordType");
        metadataComponents.add("ExternalServiceRegistration");
        metadataComponents.add("ManagedContentType");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
         try{
        
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showRecordTypeComponents(lmr, userID, fromDateValue, toDateValue);
}
          catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        } 
        
        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("ReportFolder");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
        try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showReportomponents(lmr, userID, fromDateValue, toDateValue, metadataConnection);
            System.out.println("fieldSets "+fieldSets); 
        }
          catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  
        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("DashboardFolder");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
        try{
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showDashboardComponents(lmr, userID, fromDateValue, toDateValue, metadataConnection);
        }
          catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        }  
        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("DocumentFolder");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
         try{
        
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showDocumentComponents(lmr, userID, fromDateValue, toDateValue, metadataConnection);
}
          catch(Exception e)  
        {  
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
            System.out.println(e);  
        } 

        metadataComponents = new ArrayList < String > ();
        lmqList = new ArrayList < ListMetadataQuery > ();
        metadataComponents.add("EmailFolder");
        for (String string: metadataComponents) {
          query = new ListMetadataQuery();
          query.setType(string);
          lmqList.add(query);
        }
        lmr = metadataConnection.listMetadata(
          Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);
        showEmailFolderComponents(lmr, userID, fromDateValue, toDateValue, metadataConnection);
        
PackageTypeMembers pdi = new PackageTypeMembers();
 pd = new ArrayList<PackageTypeMembers>();
        if (userCriterias != null && userCriterias.length() != 0){
                    packageXMLString += "<types>\n" + userCriterias + "<name>UserCriteria</name>\n</types>\n";
            }
            if (assignmentRules != null && assignmentRules.length() != 0){
                    packageXMLString += "<types>\n" + assignmentRules + "<name>AssignmentRule</name>\n</types>\n";
                }
        if (emailservices != null && emailservices.length() != 0)
          packageXMLString += "<types>\n" + emailservices + "<name>EmailServicesFunction</name>\n</types>\n";
        if (audience != null && audience.length() != 0)
          packageXMLString += "<types>\n" + audience + "<name>Audience</name>\n</types>\n";
        if (flows != null && flows.length() != 0){
          packageXMLString += "<types>\n" + flows + "<name>Flow</name>\n</types>\n";
		}
        if (flowDefinitions != null && flowDefinitions.length() != 0)
          packageXMLString += "<types>\n" + flowDefinitions + "<name>FlowDefinition</name>\n</types>\n";
        if (queues != null && queues.length() != 0)
          packageXMLString += "<types>\n" + queues + "<name>Queue</name>\n</types>\n";
        if (communities != null && communities.length() != 0)
          packageXMLString += "<types>\n" + communities + "<name>Community</name>\n</types>\n";
        if (connectedApps != null && connectedApps.length() != 0)
          packageXMLString += "<types>\n" + connectedApps + "<name>ConnectedApp</name>\n</types>\n";
        if (sites != null && sites.length() != 0)
          packageXMLString += "<types>\n" + sites + "<name>CustomSite</name>\n</types>\n";
        if (duplicateRules != null && duplicateRules.length() != 0)
          packageXMLString += "<types>\n" + duplicateRules + "<name>DuplicateRule</name>\n</types>\n";
        if (escalationRules != null && escalationRules.length() != 0)
          packageXMLString += "<types>\n" + escalationRules + "<name>EscalationRule</name>\n</types>\n";
        if (experiences != null && experiences.length() != 0)
          packageXMLString += "<types>\n" + experiences + "<name>ExperienceBundle</name>\n</types>\n";
        if (installedPackages != null && installedPackages.length() != 0)
          packageXMLString += "<types>\n" + installedPackages + "<name>InstalledPackage</name>\n</types>\n";
        if (managedContentTypeBundles != null && managedContentTypeBundles.length() != 0)
          packageXMLString += "<types>\n" + managedContentTypeBundles + "<name>ManagedContentTypeBundle</name>\n</types>\n";
        if (objectListViews != null && objectListViews.length() != 0)
          packageXMLString += "<types>\n" + objectListViews + "<name>ListView</name>\n</types>\n";
        if (matchingRules != null && matchingRules.length() != 0)
          packageXMLString += "<types>\n" + matchingRules + "<name>MatchingRule</name>\n</types>\n";
        if (managedTopics != null && managedTopics.length() != 0)
          packageXMLString += "<types>\n" + managedTopics + "<name>ManagedTopics</name>\n</types>\n";
        if (navigationMenus != null && navigationMenus.length() != 0)
          packageXMLString += "<types>\n" + navigationMenus + "<name>NavigationMenu</name>\n</types>\n";
        if (networks != null && networks.length() != 0)
          packageXMLString += "<types>\n" + networks + "<name>Network</name>\n</types>\n";
        if (customTab != null && customTab.length() != 0)
          packageXMLString += "<types>\n" + customTab + "<name>CustomTab</name>\n</types>\n";
        if (sharingRules != null && sharingRules.length() != 0)
          packageXMLString += "<types>\n" + sharingRules + "<name>SharingRules</name>\n</types>\n";
        if (networkBranding != null && networkBranding.length() != 0)
          packageXMLString += "<types>\n" + networkBranding + "<name>NetworkBranding</name>\n</types>\n";
        if (profilePasswordPolicies != null && profilePasswordPolicies.length() != 0)
          packageXMLString += "<types>\n" + profilePasswordPolicies + "<name>ProfilePasswordPolicy</name>\n</types>\n";
        if (profileSessionSettings != null && profileSessionSettings.length() != 0)
          packageXMLString += "<types>\n" + profileSessionSettings + "<name>ProfileSessionSetting</name>\n</types>\n";
        if (notificationTypeConfig != null && notificationTypeConfig.length() != 0)
          packageXMLString += "<types>\n" + notificationTypeConfig + "<name>NotificationTypeConfig</name>\n</types>\n";
        if (remoteSiteSettings != null && remoteSiteSettings.length() != 0)
          packageXMLString += "<types>\n" + remoteSiteSettings + "<name>RemoteSiteSetting</name>\n</types>\n";
        if (quickActions != null && quickActions.length() != 0)
          packageXMLString += "<types>\n" + quickActions + "<name>QuickAction</name>\n</types>\n";
        if (reportTypes != null && reportTypes.length() != 0)
          packageXMLString += "<types>\n" + reportTypes + "<name>ReportType</name>\n</types>\n";
        if (ModerationRules != null && ModerationRules.length() != 0)
          packageXMLString += "<types>\n" + ModerationRules + "<name>ModerationRule</name>\n</types>\n";
        if (siteDotComSites != null && siteDotComSites.length() != 0)
          packageXMLString += "<types>\n" + siteDotComSites + "<name>SiteDotCom</name>\n</types>\n";
        if (entitlementProcesses != null && entitlementProcesses.length() != 0)
          packageXMLString += "<types>\n" + entitlementProcesses + "<name>EntitlementProcess</name>\n</types>\n";
        if (flexipages != null && flexipages.length() != 0)
          packageXMLString += "<types>\n" + flexipages + "<name>FlexiPage</name>\n</types>\n";
        if (permissionsets != null && permissionsets.length() != 0)
          packageXMLString += "<types>\n" + permissionsets + "<name>PermissionSet</name>\n</types>\n";
        if (groups != null && groups.length() != 0)
          packageXMLString += "<types>\n" + groups + "<name>Group</name>\n</types>\n";
        if (sharingSets != null && sharingSets.length() != 0)
          packageXMLString += "<types>\n" + sharingSets + "<name>SharingSet</name>\n</types>\n";
        if (layouts != null && layouts.length() != 0)
          packageXMLString += "<types>\n" + layouts + "<name>Layout</name>\n</types>\n";
        if (pages != null && pages.length() != 0)
          packageXMLString += "<types>\n" + pages + "<name>ApexPage</name>\n</types>\n";
        if (components != null && components.length() != 0)
          packageXMLString += "<types>\n" + components + "<name>ApexComponent</name>\n</types>\n";
        if (classes != null && classes.length() != 0){
          packageXMLString += "<types>\n" + classes + "<name>ApexClass</name>\n</types>\n";
		 pdi = new PackageTypeMembers();
                    pdi.setName("ApexClass");
		List<String> members = new ArrayList<String>();
		 for (int i = 0; i <classes.length() ; i++)
		 members.add(classes[i]);
                    pdi.setMembers(members.toArray(new String[members.size()]));
                  pd.add(pdi);
                       }
        if (labels != null && labels.length() != 0)
          packageXMLString += "<types>\n" + labels + "<name>CustomLabel</name>\n</types>\n";
        if (profiles != null && profiles.length() != 0)
          packageXMLString += "<types>\n" + profiles + "<name>Profile</name>\n</types>\n";
        if (triggers != null && triggers.length() != 0)
          packageXMLString += "<types>\n" + triggers + "<name>ApexTrigger</name>\n</types>\n";
        if (staticresources != null && staticresources.length() != 0)
          packageXMLString += "<types>\n" + staticresources + "<name>StaticResource</name>\n</types>\n";
        if (aura != null && aura.length() != 0)
          packageXMLString += "<types>\n" + aura + "<name>AuraDefinitionBundle</name>\n</types>\n";
        if (dataSources != null && dataSources.length() != 0)
          packageXMLString += "<types>\n" + dataSources + "<name>ExternalDataSource</name>\n</types>\n";
        if (lwc != null && lwc.length() != 0){
          packageXMLString += "<types>\n" + lwc + "<name>LightningComponentBundle</name>\n</types>\n";
		
	}
        if (restrictionRules != null && restrictionRules.length() != 0)
          packageXMLString += "<types>\n" + restrictionRules + "<name>RestrictionRule</name>\n</types>\n";
        if (permissionsetgroups != null && permissionsetgroups.length() != 0)
          packageXMLString += "<types>\n" + permissionsetgroups + "<name>PermissionSetGroup</name>\n</types>\n";
        if (namedCredentials != null && namedCredentials.length() != 0)
          packageXMLString += "<types>\n" + namedCredentials + "<name>NamedCredential</name>\n</types>\n";
        if (notificationtypes != null && notificationtypes.length() != 0)
          packageXMLString += "<types>\n" + notificationtypes + "<name>CustomNotificationType</name>\n</types>\n";
        if (homePageLayouts != null && homePageLayouts.length() != 0)
          packageXMLString += "<types>\n" + homePageLayouts + "<name>HomePageLayout</name>\n</types>\n";
        if (workflowOutBoundMessages != null && workflowOutBoundMessages.length() != 0)
          packageXMLString += "<types>\n" + workflowOutBoundMessages + "<name>WorkflowOutboundMessage</name>\n</types>\n";
        if (autoResponseRules != null && autoResponseRules.length() != 0)
          packageXMLString += "<types>\n" + autoResponseRules + "<name>AutoResponseRules</name>\n</types>\n";
        if (workflowRules != null && workflowRules.length() != 0)
          packageXMLString += "<types>\n" + workflowRules + "<name>WorkflowRule</name>\n</types>\n";
        if (workflowFieldUpdates != null && workflowFieldUpdates.length() != 0)
          packageXMLString += "<types>\n" + workflowFieldUpdates + "<name>WorkflowFieldUpdate</name>\n</types>\n";
         if (matchingRuleObject != null && matchingRuleObject.length() != 0)
          packageXMLString += "<types>\n" + matchingRuleObject + "<name>MatchingRules</name>\n</types>\n";
         if (fieldSets != null && fieldSets.length() != 0)
          packageXMLString += "<types>\n" + fieldSets + "<name>FieldSet</name>\n</types>\n";
          if (sharingCriteriaRules != null && sharingCriteriaRules.length() != 0)
          packageXMLString += "<types>\n" + sharingCriteriaRules + "<name>SharingCriteriaRule</name>\n</types>\n";
        if (escalationRuleObject != null && escalationRuleObject.length() != 0)
          packageXMLString += "<types>\n" + escalationRuleObject + "<name>EscalationRule</name>\n</types>\n";
          if (assignmentRulesObject != null && assignmentRulesObject.length() != 0)
          packageXMLString += "<types>\n" + assignmentRulesObject + "<name>AssignmentRules</name>\n</types>\n";
        if (validationRules != null && validationRules.length() != 0)
          packageXMLString += "<types>\n" + validationRules + "<name>ValidationRule</name>\n</types>\n";
          if (recordTypes != null && recordTypes.length() != 0)
          packageXMLString += "<types>\n" + recordTypes + "<name>RecordType</name>\n</types>\n";
        if (externalServiceRegistrations != null && externalServiceRegistrations.length() != 0)
          packageXMLString += "<types>\n" + externalServiceRegistrations + "<name>ExternalServiceRegistration</name>\n</types>\n";
        if (managedContentTypes != null && managedContentTypes.length() != 0)
          packageXMLString += "<types>\n" + managedContentTypes + "<name>ManagedContentType</name>\n</types>\n";
        if (platformEventChannelMembers != null && platformEventChannelMembers.length() != 0)
          packageXMLString += "<types>\n" + platformEventChannelMembers + "<name>PlatformEventChannelMember</name>\n</types>\n";
        if (cleanDataServices != null && cleanDataServices.length() != 0)
          packageXMLString += "<types>\n" + cleanDataServices + "<name>CleanDataService</name>\n</types>\n";
        if (cspTrustedSites != null && cspTrustedSites.length() != 0)
          packageXMLString += "<types>\n" + cspTrustedSites + "<name>CspTrustedSite</name>\n</types>\n";
        if (samlssoconfigs != null && samlssoconfigs.length() != 0)
          packageXMLString += "<types>\n" + samlssoconfigs + "<name>SamlSsoConfig</name>\n</types>\n";
      
         String workflowSetString="";
         System.out.println("workflowSet 843"+workflowSet); 
         Iterator<String> i = workflowSet.iterator();
         System.out.println("workflowSet 845"+workflowSet); 
       
        // It holds true till there is a single element
        // remaining in the object
        while (i.hasNext()){
           System.out.println("workflowSet 850"+workflowSet); 
          String wName = i.next();
          workflowSetString +="<members>"+ wName+"</members>\n";
           System.out.println("workflowSetString "+workflowSetString);  
           csvRows +=  wName + "," + "Workflow\n";
        }
        if (workflowSetString != null && workflowSetString.length() != 0)
          packageXMLString += "<types>\n" + workflowSetString + "<name>Workflow</name>\n</types>\n";
	      String retrieveRequestID;
            try{
      retrieveRequestID =  createChangeSet(metadataConnection);
	    }
	      catch(Exception ex){
		      System.out.println("\n Error: \n" + ex.getMessage());
                       System.out.println("line number "+ex.getStackTrace()[0].getLineNumber());
	      }
	      pd=null;
        insertPakageXML(userID, fromDate, toDate, packageXMLAccessToken,retrieveRequestID);
        packageXMLString = "";
        csvRows = "";
        customTab = "";
        sharingRules = "";
        assignmentRules = "";
        audience = "";
        flows = "";
        flowDefinitions = "";
        queues = "";
        communities = "";
        connectedApps = "";
        sites = "";
        duplicateRules = "";
        emailservices = "";
        escalationRules = "";
        experiences = "";
        installedPackages = "";
        objectListViews = "";
        matchingRules = "";
        matchingRuleObject = "";
        managedContentTypeBundles = "";
        managedTopics = "";
        navigationMenus = "";
        networks = "";
        networkBranding = "";
        profilePasswordPolicies = "";
        profileSessionSettings = "";
        notificationTypeConfig = "";
        remoteSiteSettings = "";
        quickActions = "";
        reportTypes = "";
        ModerationRules = "";
        siteDotComSites = "";
        entitlementProcesses = "";
        flexipages = "";
        SharingCriteriaRules = "";
        milestoneTypes = "";
        CanvasMetadatas = "";
        autoResponseRules = "";
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
        components = "";
        pages = "";
        classes = "";
        labels = "";
        profiles = "";
        staticresources = "";
        triggers = "";
        aura = "";
        dataSources = "";
        lwc = "";
        restrictionRules = "";
        permissionsetgroups = "";
        namedCredentials = "";
        notificationtypes = "";
        workflowFieldUpdates = "";
        workflowRules = "";
        fieldSets = "";
        sharingCriteriaRules = "";
        escalationRuleObject="";
        assignmentRulesObject = "";
        workflowOutBoundMessages="";
        validationRules="";
        recordTypes="";
        externalServiceRegistrations = "";
  managedContentTypes = "";
  platformEventChannelMembers = "";
        cleanDataServices="";
         samlssoconfigs = "";
  cspTrustedSites = "";
     
      } catch (ConnectionException ce) {
        ce.printStackTrace();
        System.out.println("line number "+ce.getStackTrace()[0].getLineNumber());
      }

    } catch (Exception ex) {
      System.out.println("\n Error: \n" + ex.getMessage());
      System.out.println("line number "+ex.getStackTrace()[0].getLineNumber());
    }
  }

  public String createChangeSet( MetadataConnection metadataConnection){
      try{
    RetrieveRequest retrieveRequest = new RetrieveRequest();
    System.out.println("pd   "+pd+ pd.toArray(new PackageTypeMembers[pd.size()]));
          com.sforce.soap.metadata.Package r = new com.sforce.soap.metadata.Package();
            r.setTypes(pd.toArray(new PackageTypeMembers[pd.size()]));
           r.setVersion(API_VERSION + "");
	 retrieveRequest.setApiVersion(API_VERSION);
            retrieveRequest.setUnpackaged(r);
	       System.out.println("retrieveRequest "+retrieveRequest);
	    /*  RetrieveRequest retrieveRequest = new RetrieveRequest();
		retrieveRequest.setSinglePackage(true);
		com.sforce.soap.metadata.Package packageManifest = new com.sforce.soap.metadata.Package();
		ArrayList<PackageTypeMembers> types = new ArrayList<PackageTypeMembers>();
		PackageTypeMembers packageTypeMember = new PackageTypeMembers();
		packageTypeMember.setName("CustomObject");
		packageTypeMember.setMembers(new String[] { "Account" });
		types.add(packageTypeMember);
		packageManifest.setTypes((PackageTypeMembers[]) types.toArray(new PackageTypeMembers[] {}));
	      
		retrieveRequest.setUnpackaged(packageManifest);
	      retrieveRequest.setApiVersion(API_VERSION);
	      System.out.println("retrieveRequest "+retrieveRequest);*/
      AsyncResult response = metadataConnection.retrieve(retrieveRequest);
		while(!response.isDone())
		{
		    Thread.sleep(1000);
		    response = metadataConnection.checkStatus(new String[] { response.getId()} )[0];
		}
	        System.out.println("Retrieve Status 1155 " +response.getId());
	    // System.out.println("Retrieve Status 1156 " + metadataConnection.checkRetrieveStatus(response.getId()));
		 /* RetrieveResult retrieveResult = metadataConnection.checkRetrieveStatus(response.getId());
           //  System.out.println("Retrieve Status 1172 " + retrieveResult);
            // Write the zip to the file system
            System.out.println("Writing results to zip file");
          /*  ByteArrayInputStream bais = new ByteArrayInputStream(retrieveResult.getZipFile());
            File resultsFile = new File("retrieveResults.zip");
            FileOutputStream os = new FileOutputStream(resultsFile);
            try {
                ReadableByteChannel src = Channels.newChannel(bais);
                FileChannel dest = os.getChannel();
                copy(src, dest);
                System.out.println("Results written to " + resultsFile.getAbsolutePath());
            } finally {
                os.close();
            }*/
      }
       catch (ConnectionException ce) {
        ce.printStackTrace();
        System.out.println("line number "+ce.getStackTrace()[0].getLineNumber());
      }
       catch (InterruptedException ce) {
        ce.printStackTrace();
        System.out.println("line number "+ce.getStackTrace()[0].getLineNumber());
      }
     
     /* catch (IOException ce) {
        ce.printStackTrace();
        System.out.println("line number "+ce.getStackTrace()[0].getLineNumber());
      }*/
       catch (Exception ce) {
        ce.printStackTrace();
        System.out.println("line number "+ce.getStackTrace()[0].getLineNumber());
      }
        }
  

  public void showRecordTypeComponents(FileProperties[] lmr, String userID, Date fromDateValue, Date toDateValue) {
    if (lmr != null) {
      for (FileProperties n: lmr) {
        if(includePackaged || n.getManageableState()==null || (n.getManageableState()!=null && n.getManageableState().toString() != "installed")){
        Date dj = n.getLastModifiedDate().getTime();
        String lastModifiedById = n.getLastModifiedById();
        int yearValue = dj.getYear() + 1900;
        int month = dj.getMonth() + 1;
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
          if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
            (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
            userID.equals(lastModifiedById)
          ) {
           if (n.getFileName().startsWith("objects/")) {
              recordTypes += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Record Type\n";
            }
            if (n.getFileName().startsWith("externalServiceRegistrations/")) {
              externalServiceRegistrations += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "ExternalService Registrations\n";
            }
            if (n.getFileName().startsWith("managedContentTypes/")) {
              managedContentTypes += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Managed Content Types\n";
            }
            
          }
        } catch (ParseException e) {
          e.printStackTrace();
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
        }
        }
      }
    }
  }
  
  public void showValidationRulesComponents(FileProperties[] lmr, String userID, Date fromDateValue, Date toDateValue) {
    if (lmr != null) {
      for (FileProperties n: lmr) {
        if(includePackaged || n.getManageableState()==null || (n.getManageableState()!=null && n.getManageableState().toString() != "installed")){
        Date dj = n.getLastModifiedDate().getTime();
        String lastModifiedById = n.getLastModifiedById();
        int yearValue = dj.getYear() + 1900;
        int month = dj.getMonth() + 1;
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
          if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
            (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
            userID.equals(lastModifiedById)
          ) {
           if (n.getFileName().startsWith("objects/")) {
              validationRules += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Validation Rules\n";
            }
            if (n.getFileName().startsWith("platformEventChannelMembers/")) {
              platformEventChannelMembers += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Platform Event Channel Members\n";
            }
            if (n.getFileName().startsWith("cleanDataServices/")) {
              cleanDataServices += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "CleanData Services\n";
            }
          }
        } catch (ParseException e) {
          e.printStackTrace();
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
        }
        }
      }
    }
  }

 public void showEmailFolderComponents(FileProperties[] lmr, String userID, Date fromDateValue, Date toDateValue, MetadataConnection metadataConnection) {
    String emailTemplates = "";
    double asOfVersion = 58.0;
    if (lmr != null) {
      for (FileProperties n: lmr) {
        if(includePackaged || n.getManageableState().toString() != "installed"){
        Date dj = n.getLastModifiedDate().getTime();
        String lastModifiedById = n.getLastModifiedById();
        int yearValue = dj.getYear() + 1900;
        int month = dj.getMonth() + 1;
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
          if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
            (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
            userID.equals(lastModifiedById)
          ) {
            if (n.getFileName().startsWith("email/")) {
              emailTemplates += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Document Folder\n";
            }
          }
          ListMetadataQuery query = new ListMetadataQuery();
          ArrayList < String > metadataComponents = new ArrayList < String > ();
          ArrayList < ListMetadataQuery > lmqList = new ArrayList < ListMetadataQuery > ();
          metadataComponents.add("EmailTemplate");
          for (String string: metadataComponents) {
            query = new ListMetadataQuery();
            query.setType(string);
            query.setFolder(n.getFullName());
            lmqList.add(query);
          }
          try {
            lmr = metadataConnection.listMetadata(
              Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);

            if (lmr != null) {
              for (FileProperties n2: lmr) {
                dj = n2.getLastModifiedDate().getTime();
                lastModifiedById = n2.getLastModifiedById();
                yearValue = dj.getYear() + 1900;
                month = dj.getMonth() + 1;
                try {
                  formatter = new SimpleDateFormat("dd/MM/yyyy");
                  actualDate = formatter.parse(n2.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
                  if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
                    (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
                    userID.equals(lastModifiedById)
                  ) {
                    if (n2.getFileName().startsWith("email/")) {
                      emailTemplates += "<members>" + n2.getFullName() + "</members>\n";
                      csvRows += n2.getFullName() + "," + "Email Template\n";
                    }
                  }
                } catch (ParseException e) {
                  e.printStackTrace();
                  System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
                }
              }
            }
          } catch (ConnectionException ce) {
            ce.printStackTrace();
          }
        } catch (ParseException e) {
          e.printStackTrace();
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
        }
        }
      }
    }
    if (emailTemplates != null && emailTemplates.length() != 0)
      packageXMLString += "<types>\n" + emailTemplates + "<name>EmailTemplate</name>\n</types>\n";
  }
  
   public void showEscalationRulesComponents(FileProperties[] lmr, String userID, Date fromDateValue, Date toDateValue) {
    if (lmr != null) {
      for (FileProperties n: lmr) {
        if(includePackaged || n.getManageableState()==null || (n.getManageableState()!=null && n.getManageableState().toString() != "installed")){
        Date dj = n.getLastModifiedDate().getTime();
        String lastModifiedById = n.getLastModifiedById();
        int yearValue = dj.getYear() + 1900;
        int month = dj.getMonth() + 1;
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
          if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
            (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
            userID.equals(lastModifiedById)
          ) {
           if (n.getFileName().startsWith("escalationRules/")) {
              escalationRuleObject += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Escalation Rules\n";
            }
            else if (n.getFileName().startsWith("assignmentRules/")) {
              assignmentRulesObject += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Assignment Rules\n";
            }
            else if (n.getFileName().startsWith("workflows/")) {
              workflowOutBoundMessages += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Workflow OutBound Messages\n";
            }

          }
        } catch (ParseException e) {
          e.printStackTrace();
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
        }
        }
      }
    }
  }

  public void showDocumentComponents(FileProperties[] lmr, String userID, Date fromDateValue, Date toDateValue, MetadataConnection metadataConnection) {
    String documents = "";
    double asOfVersion = 58.0;
    if (lmr != null) {
      for (FileProperties n: lmr) {
        if(includePackaged || n.getManageableState()==null || (n.getManageableState()!=null && n.getManageableState().toString() != "installed")){
        Date dj = n.getLastModifiedDate().getTime();
        String lastModifiedById = n.getLastModifiedById();
        int yearValue = dj.getYear() + 1900;
        int month = dj.getMonth() + 1;
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
          if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
            (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
            userID.equals(lastModifiedById)
          ) {
            if (n.getFileName().startsWith("documents/")) {
              documents += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Document Folder\n";
            }
          }
          ListMetadataQuery query = new ListMetadataQuery();
          ArrayList < String > metadataComponents = new ArrayList < String > ();
          ArrayList < ListMetadataQuery > lmqList = new ArrayList < ListMetadataQuery > ();
          metadataComponents.add("Document");
          for (String string: metadataComponents) {
            query = new ListMetadataQuery();
            query.setType(string);
            query.setFolder(n.getFullName());
            lmqList.add(query);
          }
          try {
            lmr = metadataConnection.listMetadata(
              Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);

            if (lmr != null) {
              for (FileProperties n2: lmr) {
                dj = n2.getLastModifiedDate().getTime();
                lastModifiedById = n2.getLastModifiedById();
                yearValue = dj.getYear() + 1900;
                month = dj.getMonth() + 1;
                try {
                  formatter = new SimpleDateFormat("dd/MM/yyyy");
                  actualDate = formatter.parse(n2.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
                  if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
                    (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
                    userID.equals(lastModifiedById)
                  ) {
                    if (n2.getFileName().startsWith("documents/")) {
                      documents += "<members>" + n2.getFullName() + "</members>\n";
                      csvRows += n2.getFullName() + "," + "Document\n";
                    }
                  }
                } catch (ParseException e) {
                  e.printStackTrace();
                  System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
                }
              }
            }
          } catch (ConnectionException ce) {
            ce.printStackTrace();
          }
        } catch (ParseException e) {
          e.printStackTrace();
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
        }
        }
      }
    }
    if (documents != null && documents.length() != 0)
      packageXMLString += "<types>\n" + documents + "<name>Document</name>\n</types>\n";
  }

  public void showWorkFlowFieldComponents(FileProperties[] lmr, String userID, Date fromDateValue, Date toDateValue) {
    if (lmr != null) {
      for (FileProperties n: lmr) {
          if(includePackaged || n.getManageableState()==null || (n.getManageableState()!=null && n.getManageableState().toString() != "installed")){
        Date dj = n.getLastModifiedDate().getTime();
        String lastModifiedById = n.getLastModifiedById();
        int yearValue = dj.getYear() + 1900;
        int month = dj.getMonth() + 1;
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
          if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
            (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
            userID.equals(lastModifiedById)
          ) {
            if (n.getFileName().startsWith("workflows/")) {
              workflowFieldUpdates += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Workflow Field Updates\n";
              System.out.println("split "+n.getFullName());  
              workflowSet.add(n.getFullName().split("[.]")[0]);
            }
             if (n.getFileName().startsWith("MatchingRules/")) {
              matchingRuleObject += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Matching Rules\n";
            }
            if (n.getFileName().startsWith("objects/")) {
              fieldSets += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "FieldSet\n";
            }

          }
        } catch (ParseException e) {
          e.printStackTrace();
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
        }
          }
      }
    }
  }

  public void showWorkFlowComponents(FileProperties[] lmr, String userID, Date fromDateValue, Date toDateValue) {
    if (lmr != null) {
      for (FileProperties n: lmr) {
          if(includePackaged || n.getManageableState()==null || (n.getManageableState()!=null && n.getManageableState().toString() != "installed")){
        Date dj = n.getLastModifiedDate().getTime();
        String lastModifiedById = n.getLastModifiedById();
        int yearValue = dj.getYear() + 1900;
        int month = dj.getMonth() + 1;
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
          if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
            (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
            userID.equals(lastModifiedById)
          ) {
            if (n.getFileName().startsWith("namedCredentials/")) {
              namedCredentials += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "NamedCredentials\n";
            } else if (n.getFileName().startsWith("workflows/")) {
              workflowRules += "<members>" + n.getFullName() + "</members>\n";
              workflowSet.add(n.getFullName().split("[.]")[0]);
              csvRows += n.getFullName() + "," + "Workflow Rule\n";
            }
            else if (n.getFileName().startsWith("sharingRules/")) {
              sharingCriteriaRules += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "SharingCriteriaRule\n";
            }

          }
        } catch (ParseException e) {
          e.printStackTrace();
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
        }
          }
      }
    }
  }

  public void showDashboardComponents(FileProperties[] lmr, String userID, Date fromDateValue, Date toDateValue, MetadataConnection metadataConnection) {
    String dashboards = "";
    double asOfVersion = 58.0;
    if (lmr != null) {
      for (FileProperties n: lmr) {
          if(includePackaged || n.getManageableState().toString() != "installed"){
        Date dj = n.getLastModifiedDate().getTime();
        String lastModifiedById = n.getLastModifiedById();
        int yearValue = dj.getYear() + 1900;
        int month = dj.getMonth() + 1;
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
          if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
            (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
            userID.equals(lastModifiedById)
          ) {
            if (n.getFileName().startsWith("dashboards/")) {
              dashboards += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Dashboard Folder\n";
            }
          }
          ListMetadataQuery query = new ListMetadataQuery();
          ArrayList < String > metadataComponents = new ArrayList < String > ();
          ArrayList < ListMetadataQuery > lmqList = new ArrayList < ListMetadataQuery > ();
          metadataComponents.add("Dashboard");
          for (String string: metadataComponents) {
            query = new ListMetadataQuery();
            query.setType(string);
            query.setFolder(n.getFullName());
            lmqList.add(query);
          }
          try {
            lmr = metadataConnection.listMetadata(
              Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);

            if (lmr != null) {
              for (FileProperties n2: lmr) {
                dj = n2.getLastModifiedDate().getTime();
                lastModifiedById = n2.getLastModifiedById();
                yearValue = dj.getYear() + 1900;
                month = dj.getMonth() + 1;
                try {
                  formatter = new SimpleDateFormat("dd/MM/yyyy");
                  actualDate = formatter.parse(n2.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
                  if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
                    (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
                    userID.equals(lastModifiedById)
                  ) {
                    if (n2.getFileName().startsWith("dashboards/")) {
                      dashboards += "<members>" + n2.getFullName() + "</members>\n";
                      csvRows += n2.getFullName() + "," + "Dashboard\n";
                    }
                  }
                } catch (ParseException e) {
                  e.printStackTrace();
                  System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
                }
              }
            }
          } catch (ConnectionException ce) {
            ce.printStackTrace();
          }
        } catch (ParseException e) {
          e.printStackTrace();
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
        }
          }
      }
    }
    if (dashboards != null && dashboards.length() != 0)
      packageXMLString += "<types>\n" + dashboards + "<name>Dashboard</name>\n</types>\n";
  }

  public void showReportomponents(FileProperties[] lmr, String userID, Date fromDateValue, Date toDateValue, MetadataConnection metadataConnection) {
    String reports = "";
    double asOfVersion = 58.0;
    if (lmr != null) {
      for (FileProperties n: lmr) {
          if(includePackaged || n.getManageableState().toString() != "installed"){
        Date dj = n.getLastModifiedDate().getTime();
        String lastModifiedById = n.getLastModifiedById();
        int yearValue = dj.getYear() + 1900;
        int month = dj.getMonth() + 1;
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
          if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
            (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
            userID.equals(lastModifiedById)
          ) {
            if (n.getFileName().startsWith("reports/")) {
              reports += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "ReportFolder\n";
            }
          }
          ListMetadataQuery query = new ListMetadataQuery();
          ArrayList < String > metadataComponents = new ArrayList < String > ();
          ArrayList < ListMetadataQuery > lmqList = new ArrayList < ListMetadataQuery > ();
          metadataComponents.add("Report");
          for (String string: metadataComponents) {
            query = new ListMetadataQuery();
            query.setType(string);
            query.setFolder(n.getFullName());
            lmqList.add(query);
          }
          try {
            lmr = metadataConnection.listMetadata(
              Arrays.copyOf(lmqList.toArray(), lmqList.toArray().length, ListMetadataQuery[].class), asOfVersion);

            if (lmr != null) {
              for (FileProperties n2: lmr) {
                dj = n2.getLastModifiedDate().getTime();
                lastModifiedById = n2.getLastModifiedById();
                yearValue = dj.getYear() + 1900;
                month = dj.getMonth() + 1;
                try {
                  formatter = new SimpleDateFormat("dd/MM/yyyy");
                  actualDate = formatter.parse(n2.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
                  if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
                    (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
                    userID.equals(lastModifiedById)
                  ) {
                    if (n2.getFileName().startsWith("reports/")) {
                      reports += "<members>" + n2.getFullName() + "</members>\n";
                      csvRows += n2.getFullName() + "," + "ReportFolder\n";
                    }
                  }
                } catch (ParseException e) {
                  e.printStackTrace();
                  System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
                }
              }
            }
          } catch (ConnectionException ce) {
            ce.printStackTrace();
          }
        } catch (ParseException e) {
          e.printStackTrace();
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
        }
          }
      }
    }
    if (reports != null && reports.length() != 0)
      packageXMLString += "<types>\n" + reports + "<name>Report</name>\n</types>\n";
    
  }

  public void showMetaDataComponents(FileProperties[] lmr, String userID, Date fromDateValue, Date toDateValue) {
    if (lmr != null) {
      for (FileProperties n: lmr) {
          if(includePackaged || n.getManageableState()==null || (n.getManageableState()!=null && n.getManageableState().toString() != "installed")){
        Date dj = n.getLastModifiedDate().getTime();
        String lastModifiedById = n.getLastModifiedById();
        int yearValue = dj.getYear() + 1900;
        int month = dj.getMonth() + 1;
        try {
          SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
          Date actualDate = formatter.parse(n.getLastModifiedDate().get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + yearValue);
          if ((actualDate.after(fromDateValue) || actualDate.equals(fromDateValue)) &&
            (actualDate.before(toDateValue) || actualDate.equals(toDateValue)) &&
            userID.equals(lastModifiedById)
          ) {
            if (n.getFileName().startsWith("userCriteria/")) {
             userCriterias += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "User Criteria\n";
            } else if (n.getFileName().startsWith("emailservices/")) {
              emailservices += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "EmailServicesFunction\n";
            } else if (n.getFileName().startsWith("assignmentRules/")) {
              assignmentRules += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "AssignmentRule\n";
            } else if (n.getFileName().startsWith("audience/")) {
              audience += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Audience\n";
            } else if (n.getFileName().startsWith("flows/")) {
              flows += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "flows\n";
            } else if (n.getFileName().startsWith("flowDefinitions/")) {
              flowDefinitions += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "FlowDefinition\n";
            } else if (n.getFileName().startsWith("queues/")) {
              queues += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Queue\n";
            } else if (n.getFileName().startsWith("communities/")) {
              communities += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Community\n";
            } else if (n.getFileName().startsWith("connectedApps/")) {
              connectedApps += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "ConnectedApp\n";
            } else if (n.getFileName().startsWith("sites/")) {
              sites += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Custom Site\n";
            } else if (n.getFileName().startsWith("duplicateRules/")) {
              duplicateRules += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Duplicate Rule\n";
            } else if (n.getFileName().startsWith("escalationRules/")) {
              escalationRules += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Escalation Rule\n";
            } else if (n.getFileName().startsWith("experiences/")) {
              experiences += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Experience Bundle\n";
            } else if (n.getFileName().startsWith("installedPackages/")) {
              installedPackages += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Installed Package\n";
            } else if (n.getFileName().startsWith("managedContentTypeBundles/")) {
              managedContentTypeBundles += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "ManagedContentTypeBundle\n";
            } else if (n.getFileName().startsWith("objects/")) {
              objectListViews += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "ListView\n";
            } else if (n.getFileName().startsWith("matchingRules/")) {
              matchingRules += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Matching Rule\n";
            } else if (n.getFileName().startsWith("managedTopics/")) {
              managedTopics += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Managed Topics\n";
            } else if (n.getFileName().startsWith("navigationMenus/")) {
              navigationMenus += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Navigation Menu\n";
            } else if (n.getFileName().startsWith("networks/")) {
              networks += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Network\n";
            } else if (n.getFileName().startsWith("tabs/")) {
              customTab += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Custom Tab\n";
            } else if (n.getFileName().startsWith("sharingRules/")) {
              sharingRules += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Sharing Rules\n";
            } else if (n.getFileName().startsWith("networkBranding/")) {
              networkBranding += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Network Branding\n";
            } else if (n.getFileName().startsWith("profilePasswordPolicies/")) {
              profilePasswordPolicies += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "ProfilePasswordPolicy\n";
            } else if (n.getFileName().startsWith("profileSessionSettings/")) {
              profileSessionSettings += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Profile SessionSetting\n";
            } else if (n.getFileName().startsWith("notificationTypeConfig/")) {
              notificationTypeConfig += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "NotificationTypeConfig\n";
            } else if (n.getFileName().startsWith("remoteSiteSettings/")) {
              remoteSiteSettings += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "RemoteSiteSetting\n";
            } else if (n.getFileName().startsWith("quickActions/")) {
              quickActions += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "QuickAction\n";
            } else if (n.getFileName().startsWith("reportTypes/")) {
              reportTypes += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "ReportType\n";
            } else if (n.getFileName().startsWith("moderation/")) {
              ModerationRules += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "ModerationRule\n";
            } else if (n.getFileName().startsWith("siteDotComSites/")) {
              siteDotComSites += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "SiteDotCom\n";
            } else if (n.getFileName().startsWith("entitlementProcesses/")) {
              entitlementProcesses += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "EntitlementProcess\n";
            } else if (n.getFileName().startsWith("flexipages/")) {
              flexipages += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "FlexiPage\n";
            }  else if (n.getFileName().startsWith("permissionsets/")) {
              permissionsets += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "PermissionSet\n";
            } else if (n.getFileName().startsWith("groups/")) {
              groups += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Group\n";
            } else if (n.getFileName().startsWith("sharingSets/")) {
              sharingSets += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "SharingSet\n";
            } else if (n.getFileName().startsWith("layouts/")) {
              layouts += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Layout\n";
            } else if (n.getFileName().startsWith("pages/")) {
              pages += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Apex Page\n";
            } else if (n.getFileName().startsWith("components/")) {
              components += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Apex Component\n";
            } else if (n.getFileName().startsWith("classes/")) {
              classes += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Apex Class\n";
            } else if (n.getFileName().startsWith("labels/")) {
              labels += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Custom Label\n";
            } else if (n.getFileName().startsWith("profiles/")) {
              profiles += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Profile\n";
            } else if (n.getFileName().startsWith("homePageLayouts/")) {
              homePageLayouts += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "HomePageLayout\n";
            } else if (n.getFileName().startsWith("autoResponseRules/")) {
              autoResponseRules += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "AutoResponseRule\n";
            } else if (n.getFileName().startsWith("triggers/")) {
              triggers += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Apex Trigger\n";
            } else if (n.getFileName().startsWith("staticresources/")) {
              staticresources += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "StaticResource\n";
            } else if (n.getFileName().startsWith("aura/")) {
              aura += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Aura Definition Bundle\n";
            } else if (n.getFileName().startsWith("dataSources/")) {
              dataSources += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "External Data Source\n";
            } else if (n.getFileName().startsWith("lwc/")) {
              lwc += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "LightningComponentBundle (lwc)\n";
            } else if (n.getFileName().startsWith("restrictionRules/")) {
              restrictionRules += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "RestrictionRules\n";
            } else if (n.getFileName().startsWith("permissionsetgroups/")) {
              permissionsetgroups += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "PermissionSet Groups\n";
            } else if (n.getFileName().startsWith("notificationtypes/")) {
              notificationtypes += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "Notification Types\n";
            }
            else if (n.getFileName().startsWith("samlssoconfigs/")) {
              samlssoconfigs += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "SamlSsoConfig\n";
            } else if (n.getFileName().startsWith("cspTrustedSites/")) {
              cspTrustedSites += "<members>" + n.getFullName() + "</members>\n";
              csvRows += n.getFullName() + "," + "CspTrustedSite\n";
            }

          }

        } catch (ParseException e) {
          e.printStackTrace();
          System.out.println("line number "+e.getStackTrace()[0].getLineNumber());
        }
          }

      }
    }
  }
  /* Method to insert package xml */
  public void insertPakageXML(String userID, String fromDate, String toDate, String access_token,String retrieveRequestID) {
    String uri = "https://vinay9-dev-ed.my.salesforce.com/services/data/v56.0/sobjects/Package_XML__c/";
    try {
      System.out.println("access_token "+access_token);
      Header oauthHeader = new BasicHeader("Authorization", "OAuth " + access_token);
      JSONObject packageXMLRecord = new JSONObject();
      packageXMLRecord.put("xml_string__c", packageXMLString);
      packageXMLRecord.put("CSV_String__c", csvRows);
      packageXMLRecord.put("userid__c", userID);
      packageXMLRecord.put("from_date__c", fromDate);
      packageXMLRecord.put("to_date__c", toDate);
	      packageXMLRecord.put("Retrieve_Request_ID__c", retrieveRequestID);
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
  String database(Map < String, Object > model) {
    try (Connection connection = dataSource.getConnection()) {
      final
      var statement = connection.createStatement();
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      statement.executeUpdate("INSERT INTO ticks VALUES (now())");

      final
      var resultSet = statement.executeQuery("SELECT tick FROM ticks");
      final
      var output = new ArrayList < > ();
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

 
}

