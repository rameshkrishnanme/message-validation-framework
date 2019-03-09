package com.app.util.message.validator.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the MessagePattern database table.
 * 
 */
@Entity
@Table(name="MessagePattern")
@NamedQuery(name="MessagePattern.findAll", query="SELECT p FROM MessagePattern p")
public class MessagePattern implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int idMessagePattern;
    
    private String name;
    
    private String pattern;
    
    private String testText;
    
    private String description;

    public MessagePattern() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getIdMessagePattern() {
        return idMessagePattern;
    }

    public void setIdMessagePattern(int idMessagePattern) {
        this.idMessagePattern = idMessagePattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getTestText() {
        return testText;
    }

    public void setTestText(String testText) {
        this.testText = testText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}