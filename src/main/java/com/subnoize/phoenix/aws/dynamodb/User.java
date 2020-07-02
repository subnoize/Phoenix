package com.subnoize.phoenix.aws.dynamodb;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;


/**
 * 
 * @author youca
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBTable(tableName = "snz-user") 
public class User { 
 
    @JsonProperty("username") 
    @DynamoDBHashKey(attributeName = "username") 
    private String username; 
 
    @JsonProperty("password") 
    @DynamoDBAttribute(attributeName = "password") 
    private String password; 
 
    @JsonProperty("enabled") 
    @DynamoDBAttribute(attributeName = "enabled") 
    private boolean enabled; 
 
    @JsonProperty("expired") 
    @DynamoDBAttribute(attributeName = "expired") 
    private boolean expired; 
 
    @JsonProperty("locked") 
    @DynamoDBAttribute(attributeName = "locked") 
    private boolean locked; 
 
    @JsonProperty("dateCreated") 
    @DynamoDBAttribute(attributeName = "dateCreated") 
    private LocalDateTime dateCreated; 
 
    public User() { 
        // TODO Auto-generated constructor stub 
    } 
 
    public String getUsername() { 
        return username; 
    } 
 
    public void setUsername(String username) { 
        this.username = username; 
    } 
 
    public String getPassword() { 
        return password; 
    } 
 
    public void setPassword(String password) { 
        this.password = password; 
    } 
 
    public boolean isEnabled() { 
        return enabled; 
    } 
 
    public void setEnabled(boolean enabled) { 
        this.enabled = enabled; 
    } 
 
    public boolean isExpired() { 
        return expired; 
    } 
 
    public void setExpired(boolean expired) { 
        this.expired = expired; 
    } 
 
    public boolean isLocked() { 
        return locked; 
    } 
 
    public void setLocked(boolean locked) { 
        this.locked = locked; 
    } 
 
    @DynamoDBTypeConverted( converter = DynamoDBLocalDateTimeConverter.class )
    public LocalDateTime getDateCreated() { 
        return dateCreated; 
    } 
 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public void setDateCreated(LocalDateTime dateCreated) { 
        this.dateCreated = dateCreated; 
    }
    
    //public void setDateCreated(String dateString) {
    //	this.dateCreated = LocalDateTime.parse(dateString);
    //}
} 
 
