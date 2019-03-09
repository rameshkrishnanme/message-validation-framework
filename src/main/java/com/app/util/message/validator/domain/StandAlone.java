package com.app.util.message.validator.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the StandAlone database table.
 * 
 */
@Entity
@Table(name="StandAlone")
@NamedQuery(name="StandAlone.findAll", query="SELECT p FROM StandAlone p")
public class StandAlone implements Serializable {
	private static final long serialVersionUID = 222221L;
	
	private int idStandAlone;
	
	private String name;
	
	private String pattern;
	
	private String testText;
	
	private String description;

	public StandAlone() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getIdStandAlone() {
		return this.idStandAlone;
	}

	public void setIdStandAlone(int idStandAlone) {
		this.idStandAlone = idStandAlone;
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