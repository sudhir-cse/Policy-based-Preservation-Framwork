package de.l3s.forgetit.model;

import java.io.Serializable;

public class OrganizationalUnit implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String description;
	private int orgID;
	private int locationID;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getOrgID() {
		return orgID;
	}
	public void setOrgID(int orgID) {
		this.orgID = orgID;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
}
