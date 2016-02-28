package de.l3s.forgetit.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*
 * This class will be mainly used for JSON converstion
 */
public class QuestionAns implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userName; // this the unique name
	
	/*
	 * Integer: questionID
	 * String: selected options for this question
	 */
	private Map<Integer,String> qa = new HashMap<Integer,String>();
	
	private String scenarioName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String user) {
		this.userName = user;
	}
	public Map<Integer, String> getQa() {
		return qa;
	}
	public void setQa(Map<Integer, String> qa) {
		this.qa = qa;
	}
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	
}
