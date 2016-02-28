package de.l3s.forgetit.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.l3s.forgetit.model.QuestionData;
import de.l3s.forgetit.model.Resources;
import de.l3s.forgetit.model.SignupFormData;
import de.l3s.forgetit.model.StringPair;

public interface RuleServicesAsync {

	void userAuth(String email, String userName, String password, AsyncCallback<StringPair> asyncCallback);
	
	void logout(AsyncCallback<Boolean> callback);

	void returnOrgNames(AsyncCallback<ArrayList<String>> callback);

	void returnOrgUnitNames(String orgName, AsyncCallback<ArrayList<String>> callback);

	void returnRoleNames(AsyncCallback<ArrayList<String>> callback);

	void returnResourcesList(AsyncCallback<ArrayList<Resources>> callback);
	
	void reason(ArrayList<Resources> res, AsyncCallback<ArrayList<Resources>> callback); 
	
	void reasonHelloRules(AsyncCallback<ArrayList<Resources>> callback);
	
	void changeQuestionIntoJSONAndSave(List<QuestionData> qd, AsyncCallback<Boolean> callback);
	
	void returnOrgAndOrgUnits(AsyncCallback<HashMap<String,ArrayList<String>>> callback);
	
	void saveSignUpData(SignupFormData sd, AsyncCallback<String> callback);
	
	void returnQuestionsByScenarionName(String scenarioName, String userPref, 
			AsyncCallback<ArrayList<QuestionData>> callback );
	
	void returnUserName(AsyncCallback<String> callback);
	
	void returnRuleSetDescription(AsyncCallback<Map<String, String>> callback);
	
}
