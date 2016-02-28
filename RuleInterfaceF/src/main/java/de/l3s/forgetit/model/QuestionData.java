package de.l3s.forgetit.model;

import java.io.Serializable;
import java.util.HashMap;

public class QuestionData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int questionNo;//this is like an auto increment to display the question numbers
	
	private int questionID;
	private String question;
	
	private HashMap<String,Integer> options ; //map all the options name to its id.
	
	// this is just a hard-code fix
	private int defaultOption;
	
	/*
	 * Options is presented by the following type
	 * 1. multiple choice : answers are stored as a concatination of all the selected options in the String variable
	 * 2. free options : a text box will be provided as a option where user can input the answer and again stored in the string variable
	 * 3. ync : answers are stored as either "0" or "1"
	 */
	private String answers = "?";
	private String scenarioName;
	
	/*
	 * option type should one of the following
	 * 1. mc (for multiple choice)
	 * 2. fc (for free choice)
	 * 2. y/n (for yes/no)
	 */
	private String type; //this is the way to present all the options

	public int getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public HashMap<String, Integer> getOptions() {
		return options;
	}

	public void setOptions(HashMap<String, Integer> options) {
		this.options = options;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	
	public int getDefaultOption() {
		return defaultOption;
	}
	
	public void setDefaultOption(int opt) {
		this.defaultOption = opt;
	}
}
