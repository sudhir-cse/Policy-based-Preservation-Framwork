package de.l3s.forgetit.client;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.l3s.forgetit.model.QuestionData;
import de.l3s.forgetit.model.Resources;
import de.l3s.forgetit.presenter.RuleServices;
import de.l3s.forgetit.presenter.RuleServicesAsync;

public class MainViewController extends Composite {

	private VerticalPanel mainPanel = new VerticalPanel(); //this is the main panel
	private HorizontalPanel headerPanel;// this holds the header information
	
	private Label userName = new Label("");
	
	private String userPref;
		
	private Label logout = new Label("Login"); //this label is for login and logout both
	
	private String personName = "non"; // This string variable store the name of the user. "non" meaning no user logged into the system

	private HorizontalPanel contentPanel;// this holds the main content

	public ArrayList<Resources> allRes = new ArrayList<Resources>(); // to store all the returned Resources results 

	private MainViewController thisClass = this;	

	private final RuleServicesAsync serverServices = GWT.create(RuleServices.class);

	public MainViewController() {
		initWidget(mainPanel);

		//set the header
		headerPanel = new HorizontalPanel();
		mainPanel.add(headerPanel);
		headerPanel.setSize("1000px","80px");
		//DOM.setElementAttribute(headerPanel.getElement(), "id", "headerPanel"); //set id to apply css
		headerPanel.getElement().setAttribute("id", "headerPanel");

		Image logo = new Image("images/forgetIT_logo.png");
		headerPanel.add(logo);

		userName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		userName.getElement().getStyle().setMarginTop(20, Style.Unit.PX);
		headerPanel.add(userName);

		logout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		logout.getElement().getStyle().setMarginTop(20, Style.Unit.PX);
		//logout.getElement().getStyle().setMarginLeft(15, Style.Unit.PX);
		headerPanel.add(logout);
		//add an click handler on this 
		
		logout.addClickHandler(new LogoutLoginOnClickHandler());
//		logout.addClickHandler(new ClickHandler() {
//			
//			public void onClick(ClickEvent arg0) {
//
//				serverServices.logout(new AsyncCallback<Boolean>() {
//
//					public void onFailure(Throwable arg0) {
//
//						Window.alert("Unable to obtain server response: "+ arg0.getMessage());
//					}
//
//					public void onSuccess(Boolean arg0) {
//
//						if(arg0){
//
//							//open the signin page
//							openSignInPage();
//							getLogout().setText("");
//							setUserName("Login");
//						}
//					}
//				});
//
//			}
//		});


		//set content panel
		contentPanel = new HorizontalPanel();
		mainPanel.add(contentPanel);
		//DOM.setElementAttribute(contentPanel.getElement(), "id", "contentPanel");//setting id to apply css
		contentPanel.getElement().setAttribute("id", "contentPanel");

		//-----------------------------------------------------

		openSignInPage();

	}

	public void setUserName(String name){

		this.userName.setText(name);
	}

	public void setUserPreference(String pref) {
		this.userPref = pref;
	}
	
	public void openSignInPage(){
		contentPanel.clear();
		contentPanel.add(new SignInView(this));	
	}

	public void openSignUpPage(){

		//clear the contentPanel
		contentPanel.clear();
		contentPanel.add(new SignUpView(this));
	}

	void openUpdatePrefOrManageDoc(){
		contentPanel.clear();
		contentPanel.add(new UpdatePerfOrManageDocPage(this));
	}

	void openScenarioPage(MainViewController mmvc){
		contentPanel.clear();
		contentPanel.add(new ScenarioPage(mmvc,userPref));
	}

	void openQuestionPage(ArrayList<QuestionData> qList){
		contentPanel.clear();
		contentPanel.add(new QuestionPage(this, qList));
	}

	void openResourcesContainer(){

		contentPanel.clear();

		/*
		 * This method will return all the resources list of the current user; user information 
		 * will be retrieved from the session at the server side
		 * 
		 */
		serverServices.returnResourcesList(new AsyncCallback<ArrayList<Resources>>(){

			public void onFailure(Throwable caught) {
				Window.alert("Unable to obtain server response: "+ caught.getMessage());	
			}

			public void onSuccess(ArrayList<Resources> result) {
				//allRes = result;
				contentPanel.clear();
				contentPanel.add(new ResourcesContainer(result, thisClass ));
			}

		});
	}

	public void refreshPage(ArrayList<Resources> res){

		serverServices.reason(res,new AsyncCallback<ArrayList<Resources>>(){

			public void onFailure(Throwable caught) {
				Window.alert("Unable to obtain server response: "+ caught.getMessage());	
			}

			public void onSuccess(ArrayList<Resources> result) {
				//allRes = result;
				Window.alert("successful !!");
				contentPanel.clear();
				contentPanel.add(new ResourcesContainer(result, thisClass));
			}
		});
	}

	public void hellowRefresh(){
		serverServices.reasonHelloRules(new AsyncCallback<ArrayList<Resources>>() {

			public void onFailure(Throwable arg0) { 
				Window.alert("Unable to obtain server response: "+ arg0.getMessage());

			}

			public void onSuccess(ArrayList<Resources> arg0) {
				Window.alert(String.valueOf(arg0.get(0).getCorrectPV()));
			}

		});
	}

	public Label getLogout() {
		return logout;
	}
	
//	public Label getUserName() {
//		return userName;
//	}

//	public void setUserName(Label userName) {
//		this.userName = userName;
//	}
	
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}


	//this class is onclick handler for the logout label
	public class LogoutLoginOnClickHandler implements ClickHandler{

		public void onClick(ClickEvent arg0) {
			
			setPersonName("non");
			if(logout.getText().toString().equalsIgnoreCase("Login")){
				openSignInPage();
			}
			else{ //means that the label is Logout so perform the logout operatins
				
				serverServices.logout(new AsyncCallback<Boolean>() {

					public void onFailure(Throwable arg0) {

						Window.alert("Unable to obtain server response: "+ arg0.getMessage());
					}

					public void onSuccess(Boolean arg0) {

						if(arg0){

							//open the signin page
							openSignInPage();
							getLogout().setText("Login");
							setUserName("");
						}
					}
				});
				
			}
		}
		
	}
	
}

















