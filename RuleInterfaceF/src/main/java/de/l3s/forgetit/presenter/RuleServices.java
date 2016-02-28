package de.l3s.forgetit.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.l3s.forgetit.model.QuestionData;
import de.l3s.forgetit.model.Resources;
import de.l3s.forgetit.model.SignupFormData;
import de.l3s.forgetit.model.StringPair;

@RemoteServiceRelativePath("rules")
public interface RuleServices extends RemoteService {
	
	public StringPair userAuth(String email,String userName, String password);
	
	public boolean logout();
	
	public ArrayList<String> returnOrgNames();
	
	public ArrayList<String> returnOrgUnitNames(String orgName);
	
	public ArrayList<String> returnRoleNames();
	
	public ArrayList<Resources> returnResourcesList();
	
	public ArrayList<Resources> reason(ArrayList<Resources> res);
	
	public ArrayList<Resources> reasonHelloRules();
	
	public boolean changeQuestionIntoJSONAndSave(List<QuestionData> qd);
	
	public HashMap<String,ArrayList<String>> returnOrgAndOrgUnits();
	
	public String saveSignUpData(SignupFormData sd);
	
	public ArrayList<QuestionData> returnQuestionsByScenarionName(String scenarioName, String userPref);

	public String returnUserName();
	
	public Map<String, String> returnRuleSetDescription();
	
}
