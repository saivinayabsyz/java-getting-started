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

   
    public AuthParams() {
    }

    public AuthParams(String accessToken, String orgURL, String userID,String fromDate,String toDate) {
        this.accessToken = accessToken;
        this.orgURL = orgURL;
        this.userID = userID;
		 this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
}
