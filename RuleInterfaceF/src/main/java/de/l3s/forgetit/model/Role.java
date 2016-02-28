package de.l3s.forgetit.model;

import java.io.Serializable;

public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int personID;
	private int orgUnitID;
	private int orgID;
	private String role;
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	public int getOrgUnitID() {
		return orgUnitID;
	}
	public void setOrgUnitID(int orgUnitID) {
		this.orgUnitID = orgUnitID;
	}
	public int getOrgID() {
		return orgID;
	}
	public void setOrgID(int orgID) {
		this.orgID = orgID;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
