package de.l3s.forgetit.model;

import java.io.Serializable;
import java.util.Comparator;

public class Resources implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String type = "file"; //this variable must be assigned either "photo" or "email" or "file"
	private String name;
	private String author; // for storing the first attribute of the document
	private String publishedDate; // for storing the second attribute of document 
	private double correctMB;
	private double correctPV;
	private boolean status = false; //At the beginning this is  set to false, meaning not changed, used for updating the DB entries
	private String scenario;
	private String ruleSet = null;
	private String uri;
	private String semanticType;

	//they are not being used currently 
	private String genPV;
	private double genMB;
	
	
	public String getSemanticType() {
		return semanticType;
	}
	public void setSemanticType(String semanticType) {
		this.semanticType = semanticType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public double getCorrectMB() {
		return correctMB;
	}
	public void setCorrectMB(double correctMB) {
		this.correctMB = correctMB;
	}
	public double getCorrectPV() {
		return correctPV;
	}
	public void setCorrectPV(double correctPV) {
		this.correctPV = correctPV;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getGenPV() {
		return genPV;
	}
	public void setGenPV(String genPV) {
		this.genPV = genPV;
	}
	public double getGenMB() {
		return genMB;
	}
	public void setGenMB(double genMB) {
		this.genMB = genMB;
	}
	public String getScenario() {
		return scenario;
	}
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}
	public String getRuleSet() {
		return ruleSet;
	}
	public void setRuleSet(String ruleSet) {
		this.ruleSet = ruleSet;
	}
	public String getUri() {return uri; }
	public void setUri(String uri) { this.uri = uri; }
	
	//This class is used to compare Resources objects by their name
	public static Comparator<Resources> SortResByName = new Comparator<Resources>(){
		public int compare(Resources o1, Resources o2) {
			
			return o1.getName().compareToIgnoreCase(o2.getName());
			
		}
	};
	
	//This class will be use to compare the Resources objects by by their published date
	public static Comparator<Resources> SortResByPublishedDate = new Comparator<Resources>(){

		public int compare(Resources o1, Resources o2) {
			
			return o1.getPublishedDate().compareTo(o2.getPublishedDate());
		}
	};
	
	
}
