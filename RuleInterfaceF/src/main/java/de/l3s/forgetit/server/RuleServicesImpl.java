package de.l3s.forgetit.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.l3s.forgetit.drools.Config;
import de.l3s.forgetit.drools.Reasoner;
import de.l3s.forgetit.model.QuestionData;
import de.l3s.forgetit.model.QuestionAns;
import de.l3s.forgetit.model.Resources;
import de.l3s.forgetit.model.SignupFormData;
import de.l3s.forgetit.model.StringPair;
import de.l3s.forgetit.presenter.RuleServices;

public class RuleServicesImpl extends RemoteServiceServlet implements RuleServices {

	private static final long serialVersionUID = 1L;

	private int userId;

	//this method returns the first and last name of the person
	public StringPair userAuth(String email, String userName, String password) {

		StringPair personName; //this is the name to be displayed at the page
		ContentHandling ch = new ContentHandling();
		int userID = ch.userAuth(email, userName, password);
		setUserId(userID);

		if(userID == 0)
			personName = new StringPair("nop","");
		else
			personName = ch.returnPersonNameById(userID);

		//if user has been logged in successfully then creates the session
		if(userID != 0){
			HttpServletRequest request = this.getThreadLocalRequest();
			HttpSession session = request.getSession(true);
			session.setAttribute("UserID", userID);

			System.out.println("----------session id : "+session.getId());
		}

		return personName;
	}

	public boolean logout() {

		HttpServletRequest request = this.getThreadLocalRequest();
		// dont create a new one -> false
		HttpSession session = request.getSession(false);
		if (session == null)
			return false;

		session.invalidate();
		return true;
	}

	public ArrayList<String> returnOrgNames() {

		ContentHandling ch = new ContentHandling();
		return ch.returnOrgNames();
	}

	public ArrayList<String> returnOrgUnitNames(String orgName) {

		ContentHandling ch = new ContentHandling();
		return ch.returnOrgUnitNames(orgName);
	}

	public ArrayList<String> returnRoleNames() {
		ContentHandling ch = new ContentHandling();
		return ch.returnRoleNames();
	}

	public ArrayList<Resources> returnResourcesList() {

		return new ContentHandling().returnResList(returnUserID());
	}

	public ArrayList<Resources> reason(ArrayList<Resources> res) {
		return new Reasoner().fireRule(getUserId(),res);
	}

	public ArrayList<Resources> reasonHelloRules() {
		return new Reasoner().fireHelloRules();
	}

	//change the QuestionData object into JSON format and store them into the DB
	public boolean changeQuestionIntoJSONAndSave(List<QuestionData> qd){

		ContentHandling ch = new ContentHandling();

		String userName = ch.returnUserNameById(getUserId());

		QuestionAns qa = new QuestionAns();//will be converted into JSON later on

		qa.setUserName(userName);
		qa.setScenarioName(qd.get(0).getScenarioName());

		System.out.println("----this is under observation------UserID and name : "+getUserId()+"  "+qa.getUserName());

		for(QuestionData q : qd){

			qa.getQa().put(q.getQuestionID(), q.getAnswers());

		}

		//now change the object into JSON format
		Gson json = new Gson();
		String jsonString = json.toJson(qa);

		//System.out.println("----------------------------------------");
		//System.out.println(jsonString);

		return ch.insertIntoRulesQa(getUserId(), qa.getScenarioName(), jsonString);
	}

	public HashMap<String, ArrayList<String>> returnOrgAndOrgUnits() {

		return new ContentHandling().returnOrgAndOrgUnits();
	}

	//This method save the signuppage data and create the session and returns the name of the user
	public String saveSignUpData(SignupFormData sd) {

		ContentHandling ch = new ContentHandling();
		ch.saveSignUpData(sd);

		return this.userAuth(sd.getEmail(), sd.getUserName(), sd.getPassword()).getLeft();

	}

	public ArrayList<QuestionData> returnQuestionsByScenarionName(String scenarioName, String userPref) {

		return new ContentHandling().returnQuestionsByScenarionName(scenarioName,userPref);
	}

	//this method returns the current userid
	public int returnUserID(){
		//get the userName from the session
		//get the id of the current user
		HttpServletRequest request = this.getThreadLocalRequest();
		// dont create a new one -> false
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("UserID") == null)
			return 0;
		//do something with the value
		Integer userID = (Integer) session.getAttribute("UserID");
		return userID;
	}

	//This method returns the current userName
	public String returnUserName(){

		ContentHandling ch = new ContentHandling();
		return ch.returnUserNameById(returnUserID());
	}
	
	//this method will return the rule-set-description
	public Map<String, String> returnRuleSetDescription(){
		
		return Config.ruleSetDescription;
		
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}








