package com.heroku.java;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/*
 * a simple domain entity doubling as a DTO
 */
@Entity
@Table(name = "authParams")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthParams {

  @Column()
    String accessToken;
	@Column()
    String orgURL;
	@Column()
    String userID;
	@Column()
    String fromDate;
	@Column()
    String toDate;
	@Column()
    String packageXMLAccessToken;
	@Column()
    String includePackaged;

   
    public AuthParams() {
    }
public String getaccessToken() {
        return accessToken;
    }
	public String getpackageXMLAccessToken() {
        return packageXMLAccessToken;
    }

    public void setaccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
	public String getincludePackaged() {
        return includePackaged;
    }

    public void setincludePackaged(String accessToken) {
        this.includePackaged = includePackaged;
    }
	public void setpackageXMLAccessToken(String packageXMLAccessToken) {
        this.packageXMLAccessToken = packageXMLAccessToken;
    }
	
	public String getorgURL() {
        return orgURL;
    }

    public void setorgURL(String orgURL) {
        this.orgURL = orgURL;
    }
	
	public String getuserID() {
        return userID;
    }

    public void setuserID(String userID) {
        this.userID = userID;
    }
	
	
	public String getfromDate() {
        return fromDate;
    }

    public void setfromDate(String fromDate) {
        this.fromDate = fromDate;
    }
	
	
	public String gettoDate() {
        return toDate;
    }

    public void settoDate(String toDate) {
        this.toDate = toDate;
    }
	
    public AuthParams(String accessToken, String orgURL, String userID,String fromDate,String toDate, String packageXMLAccessToken, String includePackaged) {
        this.accessToken = accessToken;
        this.orgURL = orgURL;
        this.userID = userID;
		 this.fromDate = fromDate;
        this.toDate = toDate;
	    this.packageXMLAccessToken = packageXMLAccessToken;
	    this.includePackaged = includePackaged;
    }
    
}
